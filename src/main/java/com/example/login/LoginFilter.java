package com.example.login;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.user.User;
import com.example.user.Users;

/**
 * ログイン画面の表示と認証処理を行う。
 *
 */
public class LoginFilter implements Filter {

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response,
            final FilterChain chain)
            throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse resp = (HttpServletResponse) response;

        if (req.getMethod().equals("GET")) {
            resp.setContentType("text/html; charset=UTF-8");
            req.getRequestDispatcher("WEB-INF/views/login.jsp").forward(req, resp);
            return;
        }

        if (req.getMethod().equals("POST")) {
            final String username = req.getParameter("username");
            final String password = req.getParameter("password");
            final User user = tryLogin(username, password);
            if (user != null) {
                user.setTo(req);
                resp.sendRedirect(req.getContextPath() + "/");
            } else {
                resp.sendRedirect(req.getRequestURI());
            }
            return;
        }

        resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
    }

    private User tryLogin(final String username, final String password) {
        return Users.find(username).filter(a -> a.testPassword(password)).orElse(null);
    }
}
