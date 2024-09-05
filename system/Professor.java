import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Professor {
    private String ciap;
    private String nome;
    private String formacao;
    private String email;
    private int cargaHoraria;

    public Professor(String nome, String formacao, String ciap, String email) {
        this.nome = nome;
        this.formacao = formacao;
        this.ciap = ciap;
        this.email = email;
    }

    
    public String getCiap() {
        return ciap;
    }

    public String getNome() {
        return nome;
    }

    public String getFormacao() {
        return formacao;
    }

    public String getEmail() {
        return email;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }
    
    public static void cadastrarProfessor(Professor professor) {
        try {

            Connection connection = ElephantSQLConnection.getConnection();
            String query = "INSERT INTO professor (ciap, nome, formacao, email) VALUES (?,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1,professor.getCiap());
            pstmt.setString(2, professor.getNome());
            pstmt.setString(3, professor.getFormacao());
            pstmt.setString(4, professor.getEmail());
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

    public static void editarProfessor() {
        try {
            Scanner scan = new Scanner(System.in);
   
            do {
                System.out.println("Digite o CIAP do professor que deseja editar: ");
                String ciap = scan.nextLine();

                Connection connection = ElephantSQLConnection.getConnection();
                String query = "SELECT * FROM professor WHERE ciap=?";
                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setString(1, ciap);

                ResultSet rs = pstmt.executeQuery();
                if (!rs.next()) {
                    System.out.println("Professor não encontrado.");
                } else {
                    System.out.println("Digite a opção que deseja editar:");
                    System.out.println("1 - Nome");
                    System.out.println("2 - Formação");
                    System.out.println("3 - Email");
                    int opcao = scan.nextInt();
                    scan.nextLine();
                    
                    String campo = "";
                    switch(opcao) {
                        case 1:
                            campo = "nome";
                            break;
                        case 2:
                            campo = "formacao";
                            break;
                        case 3:
                            campo = "email";
                            break;
                        default:
                            System.out.println("Opção inválida.");
                            break;
                    }
   
                    if (!campo.equals("")) {
                        System.out.print("Digite o novo valor para " + campo + ": ");
                        String novoValor = scan.nextLine();
                        
                        query = "UPDATE professor SET " + campo + "=? WHERE ciap=?";
                        pstmt = connection.prepareStatement(query);
                        pstmt.setString(1, novoValor);
                        pstmt.setString(2, ciap);
                        
                        int linhasAfetadas = pstmt.executeUpdate();
   
                        if (linhasAfetadas > 0) {
                            System.out.println("Professor atualizado com sucesso!");
                        } else {
                            System.out.println("Erro ao atualizar professor.");
                        }
                        break;
                          
                    }
                }
                rs.close();
                pstmt.close();
                connection.close();
                scan.close();
                
            } while (true);
   
        } catch (Exception e) { 
            System.out.println("Erro ao editar professor: " + e.getMessage());
        }
    }  

    public static void verDadosDeUmProfessor(String idProf){
         // Declara as variaveis
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            Connection connection = ElephantSQLConnection.getConnection();
            String querySearch = "SELECT * FROM professor WHERE ciap = ?";
            psmt = connection.prepareStatement(querySearch);
            psmt.setString(1, idProf);
            // Execução da consulta SQL
            rs = psmt.executeQuery();
            
            if(!rs.next()){
                System.out.println("O ciap não foi encontrado...");
            }else{
                do{
                    String ciap = rs.getString("ciap");
                    String nome = rs.getString("nome");
                    String formacao = rs.getString("formacao");
                    String email = rs.getString("email");
                     
                    Professor prof = new Professor(nome, formacao, ciap, email);
                     
                    System.out.println("Professor correspondente ao CIAP " + idProf);
                    System.out.println(prof.getNome() + "\t\t " + prof.getFormacao() + "\t\t" + prof.getEmail() );
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
            Connection connection = ElephantSQLConnection.getConnection();
            String query = "SELECT * FROM professor"; 
            pstmt = connection.prepareStatement(query); 
            rs = pstmt.executeQuery(); 
    
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

    
    public static void excluirProfessor(String ciap) {
        try {
            Connection connection = ElephantSQLConnection.getConnection(); 
    
            
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