package br.com.senac.time;

import br.com.senac.banco.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//DAO = Data Acess Object - responsável por conectar ao banco
public class TimeDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/aula_java";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public TimeDAO() {
    }

    private Connection connection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public TimeEntity insert(TimeEntity time) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO TIME(NOME) VALUES (?) ";

        try (Connection conn = connection();
            PreparedStatement stmt = conn.prepareStatement(sql,  Statement.RETURN_GENERATED_KEYS)){

            stmt.setString(1, time.getNome());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()){
                if (rs.next()) {
                    time.setId(rs.getInt(1));
                }
            }
        }
        return time;
    }

    public List<TimeEntity> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM TIME";
        List<TimeEntity> times = new ArrayList<>();

        try (Connection conn = connection();) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                times.add(new TimeEntity(rs.getInt(1), rs.getString(2)));
            }
        }
        return times;
    }

}
