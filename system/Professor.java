
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

    // Construtor da classe Professor
    public Professor(String nome, String formacao, String ciap, String email) {
        this.nome = nome;
        this.formacao = formacao;
        this.ciap = ciap;
        this.email = email;
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

    // public ArrayList<String> getComponentes() {
    //     return componentes;
    // } 

    // Método para adicionar um componente curricular ministrado pelo professor
    // public void addComponente(String componente) {
    //     componentes.add(componente);
    //     // Incrementa a carga horária do professor com a carga horária do componente
    // }

    // Verifica se o professor pode ministrar um componente curricular
    // public boolean podeMinistrar(ComponenteCurricular componente) {

    // }
    
    //Metodo que cafastra o Professor
    public static void cadastrarProfessor(Professor professor) {
        try {

            Connection connection = ElephantSQLConnection.getConnection();
            String query = "INSERT INTO professor (ciap, nome, formacao, email) VALUES (?,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1,professor.getCiap());
            pstmt.setString(2, professor.getNome());
            pstmt.setString(3, professor.getFormacao());
            pstmt.setString(4, professor.getEmail());
            // pstmt.setInt(5, professor.getCargaHoraria());
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
    public static void verDadosDeUmProfessor(String idProf){
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            Connection connection = ElephantSQLConnection.getConnection();
            String querySearch = "SELECT * FROM professor WHERE ciap = ?";
            psmt = connection.prepareStatement(querySearch);
            psmt.setString(1, idProf);
            rs = psmt.executeQuery();
            
            if(!rs.next()){
                System.out.println("O ciap não foi encontrado...");
            }else{
                do{
                    String ciap = rs.getString("ciap");
                    String nome = rs.getString("nome");
                    String formacao = rs.getString("formacao");
                    String email = rs.getString("email");

                    Professor prof = new Professor(ciap, nome, formacao, email);
                    System.out.println("Professor correspondente ao CIAP " + idProf);
                    System.out.println(prof.getNome() + "\t " + prof.getFormacao() + "\t\t " + prof.getCiap() + "\t\t" + prof.getEmail() );
                }while(rs.next());
            }

            connection.close();
            rs.close();
            psmt.close();

        } catch (SQLException e) {
            System.out.println("Erro em ver dados do professor: " + e.getMessage());
        }

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

    @Override
    public String toString() {
        return String.format("Nome: %-20s | Formação: %-10s | CIAP: %-5s | Email: %s", nome, formacao, ciap, email);
    }
}