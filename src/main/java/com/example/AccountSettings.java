package com.example;

import java.io.IOException;

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
        final User user = User.get(req);
        final boolean twoFactorAuthN = user.isTwoFactorAuthN();
        req.setAttribute("twoFactorAuthN", twoFactorAuthN);

        final HttpSession session = req.getSession();
        if (session.getAttribute("twoFactorAuthNSettingChangedFromOffToOn") != null) {
            session.removeAttribute("twoFactorAuthNSettingChangedFromOffToOn");

            //2要素認証なしからありに変えた場合は鍵を表示してあげる
            req.setAttribute("optKey", user.getKeyAsHex());
        }

        resp.setContentType("text/html; charset=UTF-8");
        req.getRequestDispatcher("WEB-INF/views/account_settings.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
        final boolean twoFactorAuthN = Boolean.parseBoolean(req.getParameter("twoFactorAuthN"));
        final User user = User.get(req);
        final User newUser = user.withTwoFactorAuthentication(twoFactorAuthN);
        Users.save(newUser);
        newUser.setTo(req);
        if (twoFactorAuthN) {
            //2要素認証済みにしておかないと2要素認証画面に行っちゃう
            //イケてないコード。もっと綺麗な場所があるはず
            final HttpSession session = req.getSession();
            session.setAttribute("twoFactorAuthenticated", new Object());
        }
        if (user.isTwoFactorAuthN() == false && newUser.isTwoFactorAuthN()) {
            //2要素認証なしからありに変えた場合は鍵を表示してあげる
            final HttpSession session = req.getSession();
            session.setAttribute("twoFactorAuthNSettingChangedFromOffToOn", new Object());
        }
        resp.sendRedirect(req.getContextPath() + "/account_settings");
    }
}
