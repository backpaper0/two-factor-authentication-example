package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/")
public class Home extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {

        final Principal user = req.getUserPrincipal();
        final String name = user.getName();

        resp.setContentType("text/html; charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.printf("<!doctype html>%n");
            out.printf("<html lang=\"ja\">%n");
            out.printf("  <head>%n");
            out.printf("    <meta charset=\"utf-8\">%n");
            out.printf(
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">%n");
            out.printf("    <title>ホーム - 2要素認証example</title>%n");
            out.printf("  </head>%n");
            out.printf("  <body>%n");
            out.printf("    <h1>ホーム</h1>%n");
            out.printf("    <p>こんにちは、%sさん！%n", name);
            out.printf("    <nav>%n");
            out.printf("      <ul>%n");
            out.printf("        <li><a href=\"%s/account_settings\">アカウント設定</a></li>%n",
                    req.getContextPath());
            out.printf("      </ul>%n");
            out.printf("    </nav>%n");
            out.printf("  </body>%n");
            out.printf("</html>%n");
        }
    }
}
