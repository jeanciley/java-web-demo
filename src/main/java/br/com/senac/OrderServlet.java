package br.com.senac;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/order/*")
public class OrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        String pathInfo = req.getPathInfo();

        if  (pathInfo == null || pathInfo.length() <= 1) {
            showErrorMessage(out);
            return;
        }

        String[] parts = pathInfo.split("/");

        if (parts.length != 4 || !parts[2].equalsIgnoreCase("item")) {
            showErrorMessage(out);
            return;
        }
        try {
            int orderNumber = Integer.parseInt(parts[1]);
            int itemNumber = Integer.parseInt(parts[3]);

            out.print("<h3>Pedido número " + orderNumber + ", item número " + itemNumber + "</h3>");

        } catch (NumberFormatException e) {
            out.print("<h3>Erro: Os valores para o pedido e para o item devem ser números válidos!</h3>");
        }
    }

    private void showErrorMessage(PrintWriter out) {
        out.print("<h3>URL inválida — formato esperado: /order/{numero}/item/{numero}</h3>");
    }
}
