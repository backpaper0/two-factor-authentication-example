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

/**
 * ログイン済みのユーザーのみ通すフィルター。
 *
 */
public class SecurityFilter implements Filter {

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response,
            final FilterChain chain)
            throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse resp = (HttpServletResponse) response;

        if (isLoggedIn(req)) {
            chain.doFilter(request, response);
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/login");
    }

    private boolean isLoggedIn(final HttpServletRequest req) {
        return User.get(req) != null;
    }
}
