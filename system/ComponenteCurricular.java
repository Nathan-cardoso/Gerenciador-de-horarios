import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ComponenteCurricular {
    private String codCompCurricular;
    private String nomeDisciplina;
    private int cargaHorariaComp;
    private boolean componenteObrigatorio;
    private int semestre;

    public ComponenteCurricular(String codCompCurricular, String nomeDisciplina, int cargaHorariaComp,
            boolean componenteObrigatorio, int semestre) {
        this.codCompCurricular = codCompCurricular;
        this.nomeDisciplina = nomeDisciplina;
        this.cargaHorariaComp = cargaHorariaComp;
        this.componenteObrigatorio = componenteObrigatorio;
        this.semestre = semestre;
    }

    public int getCargaHorariaComp() {
        return cargaHorariaComp;
    }


    public String getCodCompCurricular() {
        return codCompCurricular;
    }

    public void cadastrarComponenteCurricular(){
        try{

        Connection connection = ElephantSQLConnection.getConnection();
        String query = "INSERT INTO comp_curricular (cod_componente, nome_disciplina, carga_horaria_comp, comp_obrigatorio, semestre) VALUES (?,?,?,?,?)"; 
        PreparedStatement pmst = connection.prepareStatement(query); 

        pmst.setString(1, this.codCompCurricular);
        pmst.setString(2, this.nomeDisciplina);
        pmst.setInt(3, this.cargaHorariaComp);
        pmst.setBoolean(4, this.componenteObrigatorio);
        pmst.setInt(5, this.semestre);

        int qntLinhasInseridas = pmst.executeUpdate(); 

        if(qntLinhasInseridas > 0){ 

            System.out.println("Componente curricular cadastrado...");

        }else{

            System.out.println("Não foi possivel cadastrar componente curricular");
        }
            pmst.close();
            connection.close();

        }catch(java.sql.SQLException e) {

            System.out.println(e.getMessage());

        }

    }

    public static void editarComponenteCurricular() {
        try {
            Scanner scan = new Scanner(System.in);
    
            System.out.println("Digite o código do componente curricular que deseja editar: ");
            String cod_componente = scan.nextLine();
    
            Connection connection = ElephantSQLConnection.getConnection();
            String query = "SELECT * FROM comp_curricular WHERE cod_componente=?"; 
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, cod_componente);
    
            ResultSet rs = pstmt.executeQuery();
    
            if (!rs.next()) {

                System.out.println("Componente curricular não encontrado.");

            } else {

                String codCompCurricular = rs.getString("cod_componente");
                String nomeDisciplina = rs.getString("nome_disciplina");
                int cargaHorariaComp = rs.getInt("carga_horaria_comp");
                boolean componenteObrigatorio = rs.getBoolean("comp_obrigatorio");
                int semestre = rs.getInt("semestre");
    
                System.out.println("Componente curricular encontrado:");
                System.out.println("Código: " + codCompCurricular);
                System.out.println("Disciplina: " + nomeDisciplina);
                System.out.println("Carga Horária: " + cargaHorariaComp);
                System.out.println("Componente Obrigatório: " + componenteObrigatorio);
                System.out.println("Semestre: " + semestre);
    
                System.out.println("\nDigite a opção que deseja editar: \n");
                System.out.println("1 - Nome da disciplina");
                System.out.println("2 - Carga horária");
                System.out.println("3 - Componente obrigatório");
                System.out.println("4 - Semestre");
                int opcao = scan.nextInt();
                scan.nextLine(); 
    
                switch(opcao) {
                    case 1:
                        System.out.print("Digite o novo nome da disciplina: ");
                        String novoNome = scan.nextLine();
                        query = "UPDATE comp_curricular SET nome_disciplina=? WHERE cod_componente=?";
                        pstmt = connection.prepareStatement(query);
                        pstmt.setString(1, novoNome);
                        pstmt.setString(2, cod_componente);
                        break;
                    case 2:
                        System.out.print("Digite a nova carga horária: ");
                        int novaCarga = scan.nextInt();
                        scan.nextLine();
                        query = "UPDATE comp_curricular SET carga_horaria_comp=? WHERE cod_componente=?";
                        pstmt = connection.prepareStatement(query);
                        pstmt.setInt(1, novaCarga);
                        pstmt.setString(2, cod_componente);
                        break;
                    case 3:
                        System.out.print("O componente é obrigatório? (S/N)");
                        String resposta = scan.nextLine();
                        boolean novoObrigatorio = resposta.equalsIgnoreCase("S");
                        query = "UPDATE comp_curricular SET comp_obrigatorio=? WHERE cod_componente=?";
                        pstmt = connection.prepareStatement(query);
                        pstmt.setBoolean(1, novoObrigatorio);
                        pstmt.setString(2, cod_componente);
                        break;
                    case 4:
                        System.out.print("Digite o novo semestre: ");
                        int novoSemestre = scan.nextInt();
                        scan.nextLine();
                        query = "UPDATE comp_curricular SET semestre=? WHERE cod_componente=?";
                        pstmt = connection.prepareStatement(query);
                        pstmt.setInt(1, novoSemestre);
                        pstmt.setString(2, cod_componente);
                        break;
                    default:
                        System.out.println("Opção inválida.");
                        
                        break;
                }
    
                int rowsAffected = pstmt.executeUpdate(); 
    
                if (rowsAffected > 0) {
                    System.out.println("Componente curricular atualizado com sucesso!");
                }else{
                    System.out.println("Erro na atualização do componente");
                }

             
            pstmt.close();
            connection.close();
            
        }
    } catch (SQLException e) {
        System.out.println("Erro ao editar componente curricular: " + e.getMessage());
    }
}


    public static void verDadosDeUmComponenteCurricular(String codComponenteCurricular){
        
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {

            Connection connection = ElephantSQLConnection.getConnection(); 
            String querySearch = "SELECT * FROM comp_curricular WHERE  cod_componente = ?"; 
            psmt = connection.prepareStatement(querySearch); 
            psmt.setString(1, codComponenteCurricular);
            rs = psmt.executeQuery(); 

            if (!rs.next()) {
                System.out.println("Esse código do componente curricular é inválido .");
            } else{

                do {
                    
                    String codCompCurricular = rs.getString("cod_componente");
                    String nomeDisciplina = rs.getString("nome_disciplina");
                    int cargaHorariaComp = rs.getInt("carga_horaria_comp");
                    boolean componenteObrigatorio = rs.getBoolean("comp_obrigatorio");
                    int semestre = rs.getInt("semestre");
                    
                    ComponenteCurricular componente = new ComponenteCurricular(codCompCurricular, nomeDisciplina, cargaHorariaComp, componenteObrigatorio, semestre);
                    
                    System.out.println(codCompCurricular.toUpperCase() + " - " + nomeDisciplina + " - " + cargaHorariaComp + "h" + "\t " + componente.mostrarTipoComponente());
                     
                } while (rs.next());

            }

            
            connection.close();
            rs.close();
            psmt.close();
        } catch (java.sql.SQLException e) {

            System.out.println(e.getMessage());
        }
    }

    public static  void listarComponentesCurriculares(){
        
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try{

            Connection connection = ElephantSQLConnection.getConnection(); 
            String query = "SELECT * FROM comp_curricular"; 
            psmt = connection.prepareStatement(query); 
            rs = psmt.executeQuery(); 

            if (!rs.next()) {
                System.out.println("Não há nenhum componente curricular cadastrado.");
            } else {

                do {
                    
                    String codCompCurricular = rs.getString("cod_componente");
                    String nomeDisciplina = rs.getString("nome_disciplina");
                    int cargaHorariaComp = rs.getInt("carga_horaria_comp");
                    boolean componenteObrigatorio = rs.getBoolean("comp_obrigatorio");
                    int semestre = rs.getInt("semestre");
    
                    ComponenteCurricular componente = new ComponenteCurricular(codCompCurricular, nomeDisciplina, cargaHorariaComp, componenteObrigatorio, semestre);

                    System.out.println(componente);
                    //Saída
                } while (rs.next());

            }

            
            rs.close();
            psmt.close();
            connection.close();
    
        }catch(java.sql.SQLException e) {

            System.out.println(e.getMessage());
        }
    }
    

    public static void excluirComponentesCurriculares(String codCompCurricular){
        PreparedStatement pstmt = null;

        try {

            Connection connection = ElephantSQLConnection.getConnection();
            String query = "DELETE from comp_curricular where cod_componente = ?";
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, codCompCurricular);
            int qntLinhas = pstmt.executeUpdate(); 
    
            if (qntLinhas > 0){ 
                System.out.println("Componente curricular excluído com sucesso!");
            }
            else {

                System.out.println("Componente curricular não encontrado!");
            }
    
        } catch (SQLException e) {
            System.out.println("Erro ao excluir componente curricular: " + e.getMessage());
        }
    }

  

    public String mostrarTipoComponente() {
        if (componenteObrigatorio) {
            return "Obrigatório";
        } else {
            return "Optativa";
        }
    }

@Override
    public String toString() { 
        if(componenteObrigatorio){ 
        return String.format("Codigo: %-10s | Disciplina: %-10s | Carga Horaria: %-5s | Componente : %s | Semestre : %s",codCompCurricular, nomeDisciplina, cargaHorariaComp, mostrarTipoComponente(), semestre);
        }else{ 
            return String.format("Codigo: %-10s | Disciplina: %-10s | Carga Horaria: %-5s | Componente : %s | Semestre : %s",codCompCurricular, nomeDisciplina, cargaHorariaComp, mostrarTipoComponente(), semestre);
        }
    }
}
