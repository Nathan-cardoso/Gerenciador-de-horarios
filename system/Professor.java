import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Professor {
    private String ciap;
    private String nome;
    private String formacao;
    private String email;
    private int cargaHoraria;
    private List<ComponenteCurricular> componentes;

    // Construtor da classe Professor
    public Professor(String ciap, String nome, String formacao, String email) {
        this.ciap = ciap;
        this.nome = nome;
        this.formacao = formacao;
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
}