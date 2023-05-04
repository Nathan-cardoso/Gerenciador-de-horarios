import java.sql.Connection;
import java.sql.PreparedStatement;

public class ComponenteCurricular {
    private String codDisciplina;
    private String nomeDisciplina;
    private int cargaHorariaComp;
    private boolean componenteObrigatorio;
    private String semestre;


    //Construtor
    public ComponenteCurricular(String codDisciplina, String nomeDisciplina, int cargaHorariaComp,
            boolean componenteObrigatorio, String semestre) {
        this.codDisciplina = codDisciplina;
        this.nomeDisciplina = nomeDisciplina;
        this.cargaHorariaComp = cargaHorariaComp;
        this.componenteObrigatorio = componenteObrigatorio;
        this.semestre = semestre;
    }
 
    //Criando um get para ser utilizado por professor.
    public int getCargaHorariaComp() {
        return cargaHorariaComp;
    }

    public void cadastrarComponenteCurricular(){
        try{
        Connection connection = ElephantSQLConnection.getConnection();
        String query = "INSERT INTO comp_curricular (cod_disciplina, nome_disciplina, carga_horaria_comp, componente_ob, semestre) VALUES (?,?,?,?,?)";
        PreparedStatement pmst = connection.prepareStatement(query);

        pmst.setString(1, this.codDisciplina);
        pmst.setString(2, this.nomeDisciplina);
        pmst.setInt(3, this.cargaHorariaComp);
        pmst.setBoolean(4, this.componenteObrigatorio);
        pmst.setString(5, this.semestre);
        int qntLinhasInseridas = pmst.executeUpdate();

        if(qntLinhasInseridas > 0){
            System.out.println("Item inserido");
        }else{
            System.out.println("Erro na inserção dos dados");
        }
            pmst.close();
        }catch(java.sql.SQLException e) {
            System.out.println(e.getMessage());

        }{

        }
    }



    public String toString() {
        return "ComponenteCurricular [codDisciplina=" + codDisciplina + ", nomeDisciplina=" + nomeDisciplina
                + ", cargaHorariaComp=" + cargaHorariaComp + ", componenteObrigatorio=" + componenteObrigatorio + ", semestre="
                + semestre + "]";
    }



}
