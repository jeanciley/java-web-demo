package br.com.senac.team;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//DAO = Data Acess Object - responsável por conectar ao banco
public class TeamDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/aula_java";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public TeamDAO() {
    }

    private Connection connection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public TeamEntity insert(TeamEntity team) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO TIME(NOME) VALUES (?) ";

        try (Connection conn = connection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, team.getNome());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    team.setId(rs.getInt(1));
                }
            }
        }
        return team;
    }

    public TeamEntity getById(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM TIME WHERE id = ?";

        TeamEntity team = null;
        try (Connection connection = connection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    team = new TeamEntity(rs.getInt("id"), rs.getString("nome"));
                }
            }
        }
        return team;
    }

    public List<TeamEntity> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM TIME";
        List<TeamEntity> times = new ArrayList<>();

        try (Connection conn = connection();) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                times.add(new TeamEntity(rs.getInt(1), rs.getString(2)));
            }
        }
        return times;
    }

    public boolean deleteById(int id) throws SQLException, ClassNotFoundException{
        String sql = "DELETE FROM TIME WHERE ID = ?";

        try (Connection connection = connection();
        PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, id);

            int affectedLines = stmt.executeUpdate();

            return affectedLines > 0;
        }

    }

    public boolean update(TeamEntity team) throws SQLException, ClassNotFoundException{
        String sql = "UPDATE TIME SET NOME = ? WHERE ID = ?";

        try (Connection connection = connection();
            PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, team.getNome());
            stmt.setInt(2, team.getId());

            int affectedLines = stmt.executeUpdate();

            return affectedLines > 0;
        }

    }
}
