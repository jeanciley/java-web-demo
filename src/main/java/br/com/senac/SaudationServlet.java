package br.com.senac;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/saudation")
public class SaudationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");
        String name = req.getParameter("name");

        if (name == null || name.isEmpty()) {
            name = "Visitante";
            resp.getWriter().println("<p>Olá " + name + ", seja bem-vindo(a)</p>");
        } else if (name.length() == 1) {
            resp.getWriter().println("<p style=\"color:red\">Erro: nome deve ter ao menos 2 caracteres</p>");
        } else {
            resp.getWriter().println("<p>Olá " + name + ", seja bem-vindo(a)</p>");
        }
    }
}
