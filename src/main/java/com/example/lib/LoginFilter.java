package com.example.lib;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.security.Principal;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter {

    public static final String LOGIN_ACTION_CLASS_NAME_KEY = "loginActionClassName";
    public static final String LOGIN_SUCCESS_PATH_KEY = "loginSuccessPath";
    public static final String LOGIN_FAILURE_PATH_KEY = "loginFailurePath";

    private LoginAction loginAction;
    private String loginSuccessPath;
    private String loginFailurePath;

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        try {
            final String loginActionClassName = filterConfig
                    .getInitParameter(LOGIN_ACTION_CLASS_NAME_KEY);
            final Class<?> clazz = Class.forName(loginActionClassName);
            final Constructor<?> constructor = clazz.getConstructor();
            loginAction = (LoginAction) constructor.newInstance();
        } catch (final ReflectiveOperationException e) {
            throw new ServletException(e);
        }
        loginSuccessPath = filterConfig.getInitParameter(LOGIN_SUCCESS_PATH_KEY);
        loginFailurePath = filterConfig.getInitParameter(LOGIN_FAILURE_PATH_KEY);
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
            try (PrintWriter out = resp.getWriter()) {
                out.printf("<!doctype html>%n");
                out.printf("<html lang=\"ja\">%n");
                out.printf("  <head>%n");
                out.printf("    <meta charset=\"utf-8\">%n");
                out.printf(
                        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">%n");
                out.printf("    <title>ログイン - 2要素認証example</title>%n");
                out.printf("  </head>%n");
                out.printf("  <body>%n");
                out.printf("    <h1>ログイン</h1>%n");
                out.printf("    <form method=\"POST\">%n");
                out.printf(
                        "      <p><input type=\"text\" name=\"username\" placeholder=\"ユーザーID\"></p>%n");
                out.printf(
                        "      <p><input type=\"password\" name=\"password\" placeholder=\"パスワード\"></p>%n");
                out.printf("      <p><button type=\"submit\">ログインする</button></p>%n");
                out.printf("    </nav>%n");
                out.printf("  </body>%n");
                out.printf("</html>%n");
            }

        } else if (req.getMethod().equals("POST")) {
            final String username = req.getParameter("username");
            final String password = req.getParameter("password");
            final Principal principal = loginAction.login(username, password);
            if (principal != null) {
                session.setAttribute(Principal.class.getName(), principal);
                resp.sendRedirect(req.getContextPath() + loginSuccessPath);
            } else {
                resp.sendRedirect(req.getContextPath() + loginFailurePath);
            }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
        }
    }

    public interface LoginAction {
        Principal login(String username, String password);
    }
}
