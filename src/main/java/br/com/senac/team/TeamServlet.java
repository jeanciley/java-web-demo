package br.com.senac.team;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/time/*")
public class TeamServlet extends HttpServlet {

    private final TeamDAO dao = new TeamDAO();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String pathInfo = req.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                List<TeamEntity> teams = dao.getAll();
                resp.getWriter().write(gson.toJson(teams));
            } else {
                int id = Integer.parseInt(pathInfo.substring(1));
                TeamEntity team = dao.getById(id);
                if (team != null) {
                    resp.getWriter().println(gson.toJson(team));
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
            TeamEntity team = lerCorpoJson(req, TeamEntity.class);

            TeamEntity createdTeam = dao.insert(team);
            resp.setStatus(201);
            resp.getWriter().print(gson.toJson(createdTeam));
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












