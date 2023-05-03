

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ElephantSQLConnection {

    private static final String URL = "jdbc:postgresql://tuffi.db.elephantsql.com:5432/onhjfeum";
    private static final String USER = "onhjfeum";
    private static final String PASSWORD = "ZmAXUbTMUwf4Mf3Ia8WD31SZ6_7d9sq_";

    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexão com banco de dados estabelecida com sucesso.");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver PostgreSQL não encontrado.");
        } catch (SQLException e) {
            System.out.println("Não foi possível conectar ao banco de dados: " + e.getMessage());
        }
        return connection;
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Conexão com banco de dados fechada.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao fechar a conexão com o banco de dados: " + e.getMessage());
        }
    }
}
