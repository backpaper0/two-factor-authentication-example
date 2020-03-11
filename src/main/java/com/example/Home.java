package com.example;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.user.User;

@WebServlet(urlPatterns = "/")
public class Home extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {

        final User user = User.get(req);
        final String name = user.getName();
        req.setAttribute("name", name);

        resp.setContentType("text/html; charset=UTF-8");
        req.getRequestDispatcher("WEB-INF/views/home.jsp").forward(req, resp);
    }
}
