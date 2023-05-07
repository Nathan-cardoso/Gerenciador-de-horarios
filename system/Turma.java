import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
/*
Uma turma pode ter vários professores
Pode haver mais de uma turma do mesmo componente curricular
Turmas do mesmo semestre não podem ter o mesmo horário
Turmas ministradas por um mesmo professor não podem ter o mesmo horário
 */
public class Turma {
    private String codTurma;
    private ArrayList<String> prof = new ArrayList<>();
    private String codComponente;
    private String horario;
    private int semestre;

    public Turma(String codTurma, String horario, int semestre,String codComponente) {
        this.codTurma = codTurma;
        this.horario = horario;
        this.semestre = semestre;
        this.codComponente = codComponente;
    }

    

    // public void addProfessor(String codProf){ //Melhorias futuras
    //     prof.add(codProf);
    // }



    public void setCodComponente(String codComponente) {
        this.codComponente = codComponente;
    }



    public void cadastrarTurma(){
        PreparedStatement pstm = null;

        try {
            Connection connection = ElephantSQLConnection.getConnection();
            String query = "INSERT INTO turma (cod_turma, horario, semestre,cod_componente) values (?,?,?,?)";
            pstm = connection.prepareStatement(query);

            pstm.setString(1, this.codTurma);
            pstm.setString(2, this.horario);
            pstm.setInt(3,this.semestre);
            pstm.setString(4, this.codComponente);
            int linhasVerifica = pstm.executeUpdate();
            if(linhasVerifica > 0){
                System.out.println("Dados iniciais cadastrados");
            }else{
                System.out.println("Erro no cadastro...");
            }

            
        } catch (SQLException e) {
           System.out.println("Erro: " + e.getMessage());
        }


    }


}