package br.com.senac.banco;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/produtos")
public class ProdutoServlet extends HttpServlet {

    private static final String URL = "jdbc:mysql://localhost:3306/aula_java";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Produto> produtos = new ArrayList<>();
        String query = "SELECT * FROM produto;";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()){
                while (rs.next()) {
                    Produto produto = new Produto(rs.getInt("id"), rs.getString("nome"), rs.getBigDecimal("preco"));
                    produtos.add(produto);
                }
            } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        Gson gson = new Gson();
        String json = gson.toJson(produtos);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().print(json);
    }
}
