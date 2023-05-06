import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Professor {
    private String ciap;
    private String nome;
    private String formacao;
    private String email;
    private int cargaHoraria;
    private List<ComponenteCurricular> componentes;

    // Construtor da classe Professor
    public Professor(String nome, String formacao, String ciap, String email) {
        this.nome = nome;
        this.formacao = formacao;
        this.ciap = ciap;
        this.email = email;
        this.cargaHoraria = 0;
        this.componentes = new ArrayList<>();
    }

    
// Get para obtero nome do professor
    public String getCiap() {
        return ciap;
    }

    // Get para obter o nome do professor
    public String getNome() {
        return nome;
    }

    // Get para obter a formaçãp do professor
    public String getFormacao() {
        return formacao;
    }

    // Get para obter o email do professor 
    public String getEmail() {
        return email;
    }

    // Get para obter a carga horária do professor
    public int getCargaHoraria() {
        return cargaHoraria;
    }

    @Override
    public String toString() {
        return String.format("Nome: %-20s | Formação: %-10s | CIAP: %-5s | Email: %s", nome, formacao, ciap, email);
    }

    // Método para adicionar um componente curricular ministrado pelo professor
    public void addComponente(ComponenteCurricular componente) {
        componentes.add(componente);
        // Incrementa a carga horária do professor com a carga horária do componente
        cargaHoraria += componente.getCargaHorariaComp();
    }

    // Verifica se o professor pode ministrar um componente curricular
    public boolean podeMinistrar(ComponenteCurricular componente) {
        return (cargaHoraria + componente.getCargaHorariaComp()) <= 20;
    }
    
    //Metodo que cafastra o Professor
    public static void cadastrarProfessor(Professor professor) {
        try {
            Connection connection = ElephantSQLConnection.getConnection();
            String query = "INSERT INTO professor (ciap, nome, formacao, email, carga_horaria) VALUES (?,?,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1,professor.getCiap());
            pstmt.setString(2, professor.getNome());
            pstmt.setString(3, professor.getFormacao());
            pstmt.setString(4, professor.getEmail());
            pstmt.setInt(5, professor.getCargaHoraria());
            int linhasAfetadas = pstmt.executeUpdate();

            if (linhasAfetadas > 0) {
                 System.out.println("Professor cadastrado com sucesso!");
            } else {
                System.out.println("Erro ao cadastrar professor.");
            }

            pstmt.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar professor: " + e.getMessage());
        }
    } 

    // Metodo prara editar Professor
    public static void editarProfesor(){

    }

    // Metodo para ver dados de um professor
    public static void verDadosDeUmProfessor(){

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

    // Metodo que exlui o professor
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
}