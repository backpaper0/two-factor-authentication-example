package com.example.lib;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

public class UserPrincipalBindFilter implements Filter {

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response,
            final FilterChain chain)
            throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpSession session = req.getSession();

        final Principal principal = (Principal) session.getAttribute(Principal.class.getName());

        final HttpServletRequest r;
        if (principal != null) {
            r = new Wrapper(req, principal);
        } else {
            r = req;
        }

        chain.doFilter(r, response);
    }

    private static class Wrapper extends HttpServletRequestWrapper {

        private final Principal principal;

        public Wrapper(final HttpServletRequest request, final Principal principal) {
            super(request);
            this.principal = principal;
        }

        @Override
        public Principal getUserPrincipal() {
            return principal;
        }
    }
}
