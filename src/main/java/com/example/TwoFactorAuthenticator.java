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

public class TwoFactorAuthenticator implements Filter {

    public static final String SUCCESS_PATH_KEY = "successPath";
    public static final String FAILURE_PATH_KEY = "failurePath";

    private String successPath;
    private String failurePath;

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        successPath = filterConfig.getInitParameter(SUCCESS_PATH_KEY);
        failurePath = filterConfig.getInitParameter(FAILURE_PATH_KEY);
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response,
            final FilterChain chain)
            throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse resp = (HttpServletResponse) response;
        final HttpSession session = req.getSession();

        if (req.getMethod().equals("GET")) {
            resp.setContentType("text/html; charset=UTF-8");
            req.getRequestDispatcher("WEB-INF/views/two_factor_authz.jsp").forward(req, resp);

        } else if (req.getMethod().equals("POST")) {
            final String code = req.getParameter("code");
            if (code.equals("000000")) { //TODO
                session.setAttribute("twoFactorAuthenticated", "true");
                resp.sendRedirect(req.getContextPath() + successPath);
            } else {
                resp.sendRedirect(req.getContextPath() + failurePath);
            }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
        }
    }
}
