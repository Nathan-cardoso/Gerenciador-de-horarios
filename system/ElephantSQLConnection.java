/*Essa classe foi criada para automatizar o processo de conexão e desconexão com o banco de dados, tendo em vista
 * que esse processo requer algumas linhas repedidas de código.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ElephantSQLConnection { //O nome do serviço que está o banco é elephant sql site:https://www.elephantsql.com/
    //Variaveis para a conexão.
    private static final String URL = "jdbc:postgresql://tuffi.db.elephantsql.com:5432/onhjfeum";
    private static final String USER = "onhjfeum";
    private static final String PASSWORD = "ZmAXUbTMUwf4Mf3Ia8WD31SZ6_7d9sq_";

    public static Connection getConnection() throws SQLException { //método para conexão
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("Driver PostgreSQL não encontrado.");
        } catch (SQLException e) {
            System.out.println("Não foi possível conectar ao banco de dados: " + e.getMessage());
        }
        return connection;
    }

    public static void closeConnection(Connection connection) {//Método que desconecta 
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao fechar a conexão com o banco de dados: " + e.getMessage());
        }
    }
}
