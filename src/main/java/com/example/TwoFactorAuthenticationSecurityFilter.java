package com.example;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TwoFactorAuthenticationSecurityFilter implements Filter {

    public static final String TWO_FACTOR_AUTHENTICATION_PATH_KEY = "twoFactorAuthenticationPath";

    private String twoFactorAuthenticationPath;

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        twoFactorAuthenticationPath = filterConfig
                .getInitParameter(TWO_FACTOR_AUTHENTICATION_PATH_KEY);
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response,
            final FilterChain chain)
            throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse resp = (HttpServletResponse) response;
        final HttpSession session = req.getSession();

        final User user = (User) req.getUserPrincipal();
        final String twoFactorAuthenticated = (String) session
                .getAttribute("twoFactorAuthenticated");

        if (user.isTwoFactorAuthentication() && twoFactorAuthenticated == null) {
            resp.sendRedirect(req.getContextPath() + twoFactorAuthenticationPath);
            return;
        }

        chain.doFilter(request, response);
    }
}
