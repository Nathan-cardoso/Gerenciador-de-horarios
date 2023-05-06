import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;

// Teste do cadastrar Professor
/*public class Main {
    public static void main(String[] args) {
        Professor professor = new Professor("PEDRO THIAGO VALERIO DE SOUZA", "Mestrado", "9596", "pedro.souza@ufersa.edu.br");
        Professor.cadastrarProfessor(professor);
    }
}*/

// Teste para listar professores

public class Main {
    public static void main(String[] args) {
        listarProfessores();
    }

    public static void listarProfessores() {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Connection connection = ElephantSQLConnection.getConnection(); // obtém a conexão com o banco de dados
            String query = "SELECT * FROM professor"; // query para selecionar todos os professores
            pstmt = connection.prepareStatement(query); // prepara a query para ser executada
            rs = pstmt.executeQuery(); // executa a query e obtém os resultados

         if (!rs.next()) {
                System.out.println("Não há nenhum professor cadastrado.");
            } else {
                do {
                    String nome = rs.getString("nome");
                    String formacao = rs.getString("formacao");
                    String ciap = rs.getString("ciap");
                    String email = rs.getString("email");

                    Professor professor = new Professor(nome, formacao, ciap, email);
                    System.out.println(professor);
                } while (rs.next());
            }

            pstmt.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Erro ao listar professores: " + e.getMessage());
        }
    }
}

    /*public class Main {
        public static void main(String[] args) {
            excluirProfessor("12345");
        }
     public static void excluirProfessor(String ciap) {
            try {
                Connection connection = ElephantSQLConnection.getConnection(); // obtém a conexão com o banco de dados
    
             // Query para deletar professor com o CIAP informado
                String query = "DELETE FROM professor WHERE ciap = ?";
                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setString(1, ciap);
    
                int linhasAfetadas = pstmt.executeUpdate();
    
                if (linhasAfetadas > 0) {
                    System.out.println("Professor excluído com sucesso!");
                } else {
                    System.out.println("Professor não encontrado.");
                }
    
                pstmt.close();
                connection.close();
    
            } catch (SQLException e) {
                System.out.println("Erro ao excluir professor: " + e.getMessage());
            }
        }
    }*/