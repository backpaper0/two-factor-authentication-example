package com.example.twofactorauthn;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.user.User;

/**
 * 2要素認証画面の表示と2要素認証処理を行う。
 *
 */
public class TwoFactorAuthenticator implements Filter {

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response,
            final FilterChain chain)
            throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse resp = (HttpServletResponse) response;
        final HttpSession session = req.getSession();

        if (req.getMethod().equals("GET")) {
            resp.setContentType("text/html; charset=UTF-8");
            req.getRequestDispatcher("WEB-INF/views/two_factor_authn.jsp").forward(req, resp);
            return;
        }

        if (req.getMethod().equals("POST")) {
            final User user = User.get(req);
            final String otp = req.getParameter("otp");
            if (user.testOTP(otp)) {
                session.setAttribute("twoFactorAuthenticated", new Object());
                resp.sendRedirect(req.getContextPath() + "/");
            } else {
                resp.sendRedirect(req.getRequestURI());
            }
            return;
        }

        resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
    }
}
