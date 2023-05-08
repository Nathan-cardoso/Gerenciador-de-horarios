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
    

}