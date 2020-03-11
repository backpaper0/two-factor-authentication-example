package com.example;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.user.User;
import com.example.user.Users;

@WebServlet(urlPatterns = "/account_settings")
public class AccountSettings extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
        final User user = (User) req.getUserPrincipal();
        final boolean twoFactorAuthz = user.isTwoFactorAuthentication();
        req.setAttribute("twoFactorAuthz", twoFactorAuthz);

        resp.setContentType("text/html; charset=UTF-8");
        req.getRequestDispatcher("WEB-INF/views/account_settings.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
        final boolean twoFactorAuthz = Boolean.parseBoolean(req.getParameter("twoFactorAuthz"));
        final User user = (User) req.getUserPrincipal();
        final User newUser = user.withTwoFactorAuthentication(twoFactorAuthz);
        Users.save(newUser);
        final HttpSession session = req.getSession();
        session.setAttribute(Principal.class.getName(), newUser);
        if (twoFactorAuthz) {
            session.setAttribute("twoFactorAuthenticated", "true");
        }
        resp.sendRedirect(req.getContextPath() + "/account_settings");
    }
}
