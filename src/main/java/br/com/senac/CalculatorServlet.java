package br.com.senac;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/calculator")
public class CalculatorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");
        String operacao = req.getParameter("operator");
        double n1 = Double.parseDouble(req.getParameter("n1"));
        double n2 = Double.parseDouble(req.getParameter("n2"));

        switch (operacao) {
            case "adc" -> resp.getWriter().println("<h1>Resultado: " + (n1 + n2) + "</h1>");

            case "multi" -> resp.getWriter().println("<h1>Resultado: " + (n1 * n2) + "</h1>");

            case "sub" -> resp.getWriter().println("<h1>Resultado: " + (n1 - n2) + "</h1>");

            case "division" -> {
                if (n1 == 0 || n2 == 0) {
                    resp.getWriter().println("<h1 style=\"color:red\">Erro: não é possível dividir por zero.</h1>");
                } else {
                    resp.getWriter().println("<h1>Resultado:  " + (n1 / n2) + "</h1>");
                }
            }
        }
    }
}
