import java.sql.Connection;
import java.sql.PreparedStatement;
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
    private int semestre;

    public Turma(String codTurma, String horario, int semestre,String codComponente, String codProf) {
        this.codTurma = codTurma;
        this.horario = horario;
        this.semestre = semestre;
        this.codComponente = codComponente;
        this.codProf = codProf;
    }

    public void setCodComponente(String codComponente) {
        this.codComponente = codComponente;
    }



    public void cadastrarTurma(){
        PreparedStatement pstm = null;

        try {
            Connection connection = ElephantSQLConnection.getConnection();
            String query = "INSERT INTO turma (cod_turma, horario, semestre,cod_componente) values (?,?,?,?,?)";
            pstm = connection.prepareStatement(query);

            pstm.setString(1, this.codTurma);
            pstm.setString(2, this.horario);
            pstm.setInt(3,this.semestre);
            pstm.setString(4, this.codComponente);
            pstm.setString(5, this.codProf);
            int linhasVerifica = pstm.executeUpdate();
            if(linhasVerifica > 0){
                System.out.println("Turma cadastrada...");
            }else{
                System.out.println("Erro no cadastro...");
            }

            pstm.close();
            connection.close();

            
        } catch (SQLException e) {
           System.out.println("Já tem uma turma cadastrada com esse codigo... " + e.getMessage());
        }


    }


}