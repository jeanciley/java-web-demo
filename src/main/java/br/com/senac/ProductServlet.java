package br.com.senac;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/product/*")
public class ProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");

        String pathInfo = req.getPathInfo();
        String productName = pathInfo.substring(1, 2).toUpperCase() + pathInfo.substring(2).toLowerCase();


        if (pathInfo == null || pathInfo.length() <= 1) {
            resp.getWriter().println("Informe o nome do produto");
        } else {
            resp.getWriter().println("Você está vendo as informações do produto: " + productName);
        }
    }
}
