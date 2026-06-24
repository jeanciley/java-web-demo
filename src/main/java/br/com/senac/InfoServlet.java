package br.com.senac;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/info")
public class InfoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().println("<p>Desenvolvimento Web com Java</p>");
        resp.getWriter().println("<p>Data de hoje: " + java.time.LocalDate.now() + "</p>");
        resp.getWriter().println("<p>Versão do Java: " + System.getProperty("java.version") + "</p>");
    }
}
