import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
Uma turma pode ter vários professores
Pode haver mais de uma turma do mesmo componente curricular
    Turmas do mesmo semestre não podem ter o mesmo horário
Turmas ministradas por um mesmo professor não podem ter o mesmo horário
 */
public class Turma {
    private String codTurma;
    private String codProf;
    private String codComponente;
    private String horario;
    private int horarioAula;
    private int semestre;

    public Turma(String codTurma, String horario,int horarioAula, int semestre,String codComponente, String codProf) {
        this.codTurma = codTurma;
        this.horario = horario;
        this.horarioAula = horarioAula;
        this.semestre = semestre;
        this.codComponente = codComponente;
        this.codProf = codProf;
    }

    
    public String getCodTurma() {
        return codTurma;
    }


    public String getCodProf() {
        return codProf;
    }


    public String getCodComponente() {
        return codComponente;
    }


    public String getHorario() {
        return horario;
    }


    public int getHorarioAula() {
        return horarioAula;
    }


    public int getSemestre() {
        return semestre;
    }


    public void setCodComponente(String codComponente) {
        this.codComponente = codComponente;
    }



    public static void cadastrarTurma(Turma turma) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            Connection connection = ElephantSQLConnection.getConnection();
    
            // Verifica se já existe uma turma com o mesmo horário e semestre
            String query = "SELECT * FROM turma WHERE horario = ? AND semestre = ? AND horario_aula = ?";
            pstm = connection.prepareStatement(query);
            pstm.setString(1, turma.getHorario());
            pstm.setInt(2, turma.getSemestre());
            pstm.setInt(3, turma.getHorarioAula());
            rs = pstm.executeQuery();
    
            if (rs.next()) {
                System.out.println("Erro! Já existe uma turma com o mesmo horário e semestre.");
            } else {
                // Verifica se o professor já possui uma turma no mesmo horário
                query = "SELECT * FROM turma WHERE ciap_professor = ? AND horario = ? AND horario_aula = ?";
                pstm = connection.prepareStatement(query);
                pstm.setString(1, turma.getCodProf());
                pstm.setString(2, turma.getHorario());
                pstm.setInt(3, turma.getHorarioAula());
                rs = pstm.executeQuery();
    
                if (rs.next()) {
                    System.out.println("Erro! O professor já possui uma turma no mesmo horário.");
                } else {
                                    // Verifica se o semestre da turma é igual ao semestre do componente curricular
                query = "SELECT * FROM comp_curricular WHERE cod_componente = ? AND semestre = ?";
                pstm = connection.prepareStatement(query);
                pstm.setString(1, turma.getCodComponente());
                pstm.setInt(2, turma.getSemestre());
                rs = pstm.executeQuery();

                if (!rs.next()) {
                    System.out.println("Erro! O componente curricular não possui um semestre correspondente.");
                }else {
                    // Pode cadastrar a nova turma
                    query = "INSERT INTO turma (cod_turma, horario, horario_aula, semestre, cod_componente, ciap_professor) VALUES (?,?,?,?,?,?)";
                    pstm = connection.prepareStatement(query);
    
                    pstm.setString(1, turma.getCodTurma());
                    pstm.setString(2, turma.getHorario());
                    pstm.setInt(3, turma.getHorarioAula());
                    pstm.setInt(4, turma.getSemestre());
                    pstm.setString(5, turma.getCodComponente());
                    pstm.setString(6, turma.getCodProf());
    
                    int linhasAfetadas = pstm.executeUpdate();
    
                    if (linhasAfetadas > 0) {
                        System.out.println("Turma cadastrada com sucesso.");
                    } else {
                        System.out.println("Erro no cadastro da turma.");
                    }
                }
            }
        }
    
            pstm.close();
            rs.close();
            connection.close();
    
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar turma: " + e.getMessage());
        }
    }
     

    public static void editarTurma(){
        
    }

    public static void verDadosDeUmaTurma(String codTurma){
        PreparedStatement psmt = null;
        ResultSet rs = null;
    
        try {
            Connection connection = ElephantSQLConnection.getConnection(); //Iniciando a conexão com o banco
            String querySearch = "SELECT * FROM turma WHERE  cod_turma = ?"; //Query para buscar a turma pelo código digitado.
            psmt = connection.prepareStatement(querySearch); //executando a query.
            psmt.setString(1, codTurma);//Substituindo o ? da query pelo conteúdo da variável, assim a query vai direto ao codTurma
            rs = psmt.executeQuery(); //resultSet representa o resultado da consultado da query em psmt
    
            if (!rs.next()) /*Se o rs estiver vazio é porque não tem uma turma com o código digitado*/{//verificação
                System.out.println("Esse código da turma é inválido .");
            } else{
                do {
                    //rs possibilita armazenar em variáveis os resultados da query.
                    String idTurma = rs.getString("cod_turma");
                    String codComponente = rs.getString("cod_componente");
                    String horario = rs.getString("horario");
                    int horarioAula = rs.getInt("horario_aula");
                    int semestre = rs.getInt("semestre");
                    String codProf = rs.getString("ciap_professor");
                    //Definida as variáveis que receberão os valores de cada campo...
                    Turma turma = new Turma(idTurma, horario, horarioAula, semestre, codComponente, codProf); //Será instanciado um objeto Turma para mostrar a turma
                    System.out.println(turma); //Saída.
                } while (rs.next());
            }
    
            //Fechando a conexão
            connection.close();
            rs.close();
            psmt.close();
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    

    public static void listarTurmas() {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
    
        try {
            Connection connection = ElephantSQLConnection.getConnection(); // obtém a conexão com o banco de dados
            String query = "SELECT * FROM turma"; // query para selecionar todas as turmas
            pstmt = connection.prepareStatement(query); // prepara a query para ser executada
            rs = pstmt.executeQuery(); // executa a query e obtém os resultados
    
            if (!rs.next()) {
                System.out.println("Não há nenhuma turma cadastrada.");
            } else {
                do {
                    String codTurma = rs.getString("cod_turma");
                    String codComponente = rs.getString("cod_componente");
                    String horario = rs.getString("horario");
                    int horarioAula = rs.getInt("horario_aula");
                    int semestre = rs.getInt("semestre");
                    
                    
                    Turma turma = new Turma(codTurma, horario, horarioAula, semestre, codComponente, horario);
                    System.out.println(turma);
                } while (rs.next());
            }
    
            pstmt.close();
            connection.close();
    
        } catch (SQLException e) {
            System.out.println("Erro ao listar turmas: " + e.getMessage());
        }
    }
    

    public static void listarTurmasPorSemestre(int semestre) {
        PreparedStatement psmt = null;
        ResultSet rs = null;
    
        try {
            Connection connection = ElephantSQLConnection.getConnection(); //Iniciando a conexão com o banco
            String querySearch = "SELECT * FROM turma WHERE semestre = ?"; //Query para buscar as turmas pelo semestre digitado.
            psmt = connection.prepareStatement(querySearch); //executando a query.
            psmt.setInt(1, semestre); //Substituindo o ? da query pelo conteúdo da variável, assim a query vai direto ao semestre
            rs = psmt.executeQuery(); //resultSet representa o resultado da consultado da query em psmt
    
            if (!rs.next()) { //Se o rs estiver vazio é porque não tem turmas com o semestre digitado.
                System.out.println("Não foram encontradas turmas para o semestre " + semestre);
            } else {
                //Criando a tabela de turmas
                System.out.printf("%-12s%-20s%-20s%-15s%-30s%-20s%n", "Código", "Componente", "Horário", "Horário de Aula", "Professor", "Semestre");
                System.out.println("-------------------------------------------------------------------------------------------------------------------");
                do {
                    //rs possibilita armazenar em variáveis os resultados da query.
                    String idTurma = rs.getString("cod_turma");
                    String codComponente = rs.getString("cod_componente");
                    String horario = rs.getString("horario");
                    int horarioAula = rs.getInt("horario_aula");
                    String codProf = rs.getString("ciap_professor");
                    int semestreTurma = rs.getInt("semestre");
    
                    //Imprimindo as informações da turma na tabela
                    System.out.printf("%-12s%-20s%-20s%-15d%-30s%-20d%n", idTurma, codComponente, horario, horarioAula, codProf, semestreTurma);
                } while (rs.next());
            }
    
            //Fechando a conexão
            connection.close();
            rs.close();
            psmt.close();
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void listarTurmasPorProfessor(String ciapProf){
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            Connection connection = ElephantSQLConnection.getConnection();
            String query = "SELECT t.*, p.nome FROM turma t JOIN professor p ON t.ciap_professor = p.ciap WHERE t.ciap_professor = ? ";
            pstm = connection.prepareStatement(query);
            pstm.setString(1, ciapProf);
            rs = pstm.executeQuery();
            
            if(!rs.next()){ 
                System.out.println("Professor não tem nenhuma turma ");
            }else{

                do{
                    String idTurma = rs.getString("cod_turma");
                    String codComponente = rs.getString("cod_componente");
                    String horario = rs.getString("horario");
                    int horarioAula = rs.getInt("horario_aula");
                    int semestre = rs.getInt("semestre");
                    String codProf = rs.getString("ciap_professor");
                    String nomeProf = rs.getString("nome");
                    Turma turma = new Turma(idTurma, horario, horarioAula, semestre, codComponente, codProf);
                    System.out.println("Turma ministrada por: " + nomeProf + " " + turma);
                }while(rs.next());
            }

            connection.close();
            rs.close();
            pstm.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void excluirTurma(String codTurma) {
        PreparedStatement pstmt = null;
    
        try {
            Connection connection = ElephantSQLConnection.getConnection();
            pstmt = connection.prepareStatement("DELETE from turma where cod_turma = ?");
            pstmt.setString(1, codTurma);
            int qntLinhas = pstmt.executeUpdate();
    
            if (qntLinhas > 0) {
                System.out.println("Turma excluída com sucesso!");
            } else {
                System.out.println("Turma não encontrada!");
            }
    
        } catch (SQLException e) {
            System.out.println("Erro ao excluir turma: " + e.getMessage());
        }
    }
    
    @Override
    public String toString() {
        return String.format("codigo: %-5s | Componente: %-5s | Turno: %-5s | Horario: %s | Semestre: %s", codTurma, codComponente, horario, horarioAula, semestre);
    }
}