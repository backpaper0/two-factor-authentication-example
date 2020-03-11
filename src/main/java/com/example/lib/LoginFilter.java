package com.example.lib;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.Users;

public class LoginFilter implements Filter {

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response,
            final FilterChain chain)
            throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse resp = (HttpServletResponse) response;
        final HttpSession session = req.getSession();

        if (req.getMethod().equals("GET")) {
            resp.setContentType("text/html; charset=UTF-8");
            req.getRequestDispatcher("WEB-INF/views/login.jsp").forward(req, resp);

        } else if (req.getMethod().equals("POST")) {
            final String username = req.getParameter("username");
            final String password = req.getParameter("password");
            final Principal principal = tryLogin(username, password);
            if (principal != null) {
                session.setAttribute(Principal.class.getName(), principal);
                resp.sendRedirect(req.getContextPath() + "/");
            } else {
                resp.sendRedirect(req.getRequestURI());
            }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
        }
    }

    private Principal tryLogin(final String username, final String password) {
        return Users.find(username).filter(a -> a.testPassword(password)).orElse(null);
    }
}
