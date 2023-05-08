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
    
        try {
            Connection connection = ElephantSQLConnection.getConnection();
    
            // Verifica se já existe uma turma com o mesmo componente curricular, semestre e horário
            String query = "SELECT * FROM turma WHERE cod_componente = ? AND semestre = ? AND horario = ?";
            pstm = connection.prepareStatement(query);
            pstm.setString(1, turma.getCodComponente());
            pstm.setInt(2, turma.getSemestre());
            pstm.setString(3, turma.getHorario());
            ResultSet rs = pstm.executeQuery();
    
            if (rs.next()) {
                System.out.println("Já existe uma turma cadastrada para o mesmo componente curricular e horário no semestre.");
            } else {
                // Nenhuma turma encontrada, pode cadastrar a nova turma
    
                // Verifica se o componente curricular e a turma possuem o mesmo semestre
                query = "SELECT * FROM comp_curricular WHERE cod_componente = ? AND semestre = ?";
                pstm = connection.prepareStatement(query);
                pstm.setString(1, turma.getCodComponente());
                pstm.setInt(2, turma.getSemestre());
                rs = pstm.executeQuery();
    
                if (!rs.next()) {
                    System.out.println("O componente curricular e a turma não possuem o mesmo semestre.");
                } else {
                    // Verifica se o professor já está dando aula em outro horário
                    query = "SELECT * FROM turma WHERE ciap_professor = ? AND horario = ? AND semestre = ?";
                    pstm = connection.prepareStatement(query);
                    pstm.setString(1, turma.getCodProf());
                    pstm.setString(2, turma.getHorario());
                    pstm.setInt(3, turma.getSemestre());
                    rs = pstm.executeQuery();
    
                    if (rs.next()) {
                        System.out.println("O professor já está dando aula em outro horário.");
                    } else {
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
    

    public static void listarTurmasPorSemestre(){
        
    }

    public static void listarTurmasPorProfessor(){
        
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