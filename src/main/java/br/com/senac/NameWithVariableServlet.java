package br.com.senac;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/name/*")
public class NameWithVariableServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");

        String pathInfo = req.getPathInfo();
        String name;

        if (pathInfo == null || pathInfo.length() <= 1) {
            name = "Visitor";
        } else {
            name = pathInfo.substring(1);
        }

        resp.getWriter().println("<h1>Hello, " + name + "</h1>");
    }
}
