package com.example.lib;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SecurityFilter implements Filter {

    public static final String LOGIN_PATH_KEY = "loginPath";

    private String loginPath;

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        loginPath = filterConfig.getInitParameter(LOGIN_PATH_KEY);
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response,
            final FilterChain chain)
            throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse resp = (HttpServletResponse) response;

        if (req.getUserPrincipal() == null) {
            resp.sendRedirect(req.getContextPath() + loginPath);
            return;
        }

        chain.doFilter(request, response);
    }
}
