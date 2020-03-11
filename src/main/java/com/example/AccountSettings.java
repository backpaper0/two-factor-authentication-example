package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = "/account_settings")
public class AccountSettings extends HttpServlet {

    final String inputName = "twoFactorAuthentication";

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
        final User user = (User) req.getUserPrincipal();
        final boolean twoFactorAuthentication = user.isTwoFactorAuthentication();

        resp.setContentType("text/html; charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.printf("<!doctype html>%n");
            out.printf("<html lang=\"ja\">%n");
            out.printf("  <head>%n");
            out.printf("    <meta charset=\"utf-8\">%n");
            out.printf(
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">%n");
            out.printf("    <title>アカウント設定 - 2要素認証example</title>%n");
            out.printf("  </head>%n");
            out.printf("  <body>%n");
            out.printf("    <nav>%n");
            out.printf("      <p><a href=\"%s/\">戻る</a></p>%n",
                    req.getContextPath());
            out.printf("    </nav>%n");
            out.printf("    <h1>アカウント設定</h1>%n");
            out.printf("    <form method=\"POST\">%n");
            out.printf("      <p>%n");
            out.printf("        <label>%n");
            out.printf("          <input type=\"radio\" name=\"%s\" value=\"true\"%s>二要素認証あり%n",
                    inputName, twoFactorAuthentication ? " checked" : "");
            out.printf("        </label>%n");
            out.printf("        <label>%n");
            out.printf("          <input type=\"radio\" name=\"%s\" value=\"false\"%s>二要素認証なし%n",
                    inputName, twoFactorAuthentication ? "" : " checked");
            out.printf("        </label>%n");
            out.printf("      </p>%n");
            out.printf("      <p>%n");
            out.printf("        <button type=\"submit\">設定する</button>%n");
            out.printf("      </p>%n");
            out.printf("    </form>%n");
            out.printf("  </body>%n");
            out.printf("</html>%n");
        }
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
        final boolean twoFactorAuthentication = Boolean.parseBoolean(req.getParameter(inputName));
        final User user = (User) req.getUserPrincipal();
        final User newUser = user.withTwoFactorAuthentication(twoFactorAuthentication);
        Users.save(newUser);
        final HttpSession session = req.getSession();
        session.setAttribute(Principal.class.getName(), newUser);
        resp.sendRedirect(req.getContextPath() + "/account_settings");
    }
}
