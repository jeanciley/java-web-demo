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
        String operacao = req.getParameter("operacao");
        double n1 = Double.parseDouble(req.getParameter("n1"));
        double n2 = Double.parseDouble(req.getParameter("n2"));

        switch (operacao) {
            case "soma" -> resp.getWriter().println("Resultado: " + (n1 + n2));

            case "multiplicacao" -> resp.getWriter().println("Resultado: " + (n1 * n2));

            case "divisao" -> {
                if (n1 == 0 || n2 == 0) {
                    resp.getWriter().println("Erro: não é possível dividir por zero.");
                } else {
                    resp.getWriter().println("Resultado: " + (n1 / n2));
                }
            }
        }
    }
}
