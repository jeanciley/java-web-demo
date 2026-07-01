package br.com.senac.time;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Time;
import java.util.List;

@WebServlet("/time/*")
public class TimeServlet extends HttpServlet {

    private final TimeDAO dao = new TimeDAO();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String pathInfo = req.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                List<TimeEntity> times = dao.getAll();
                resp.getWriter().write(gson.toJson(times));
            } else {
                int id = Integer.parseInt(pathInfo.substring(1));
                TimeEntity time = dao.getById(id);
                if (time != null) {
                    resp.getWriter().println(gson.toJson(time));
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            }
        } catch (Exception e) {
            Error error = throwError("Erro ao listar", e);
            resp.setStatus(500);
            resp.getWriter().println(gson.toJson(error));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            TimeEntity time = lerCorpoJson(req, TimeEntity.class);

            TimeEntity timeCriado = dao.insert(time);
            resp.setStatus(201);
            resp.getWriter().print(gson.toJson(timeCriado));
        } catch (Exception e) {
            Error error = throwError("Erro ao inserir", e);
            resp.setStatus(500);
            resp.getWriter().println(gson.toJson(error));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        String pathInfo = req.getPathInfo();

        try {
            if (pathInfo != null || pathInfo.length() > 1) {
                int id = Integer.parseInt(pathInfo.substring(1));
                boolean deleted = dao.deleteById(id);
                if (deleted) {
                    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                }
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            Error error = throwError("Erro ao excluir", e);
            resp.setStatus(500);
            resp.getWriter().println(gson.toJson(error));
        }
    }

    private <T> T lerCorpoJson(HttpServletRequest req, Class<T> classe) throws IOException {
        StringBuilder sb = new StringBuilder();

        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        return gson.fromJson(sb.toString(), classe);
    }

    private Error throwError(String msg, Exception e) {
        return new Error(msg, e.getMessage());
    }
}












