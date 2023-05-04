import java.sql.Connection;
import java.sql.PreparedStatement;

public class ComponenteCurricular {
    private String codCompCurricular;
    private String nomeDisciplina;
    private int cargaHorariaComp;
    private boolean componenteObrigatorio;
    private String semestre;


    //Construtor
    public ComponenteCurricular(String codCompCurricular, String nomeDisciplina, int cargaHorariaComp,
            boolean componenteObrigatorio, String semestre) {
        this.codCompCurricular = codCompCurricular;
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
        String query = "INSERT INTO comp_curricular (cod_disciplina, nome_disciplina, carga_horaria_comp, componente_ob, semestre) VALUES (?,?,?,?,?)"; //A váriável query é utilizada para guardar o comando a ser passado no PreparedStatement
        PreparedStatement pmst = connection.prepareStatement(query); //PreparedStatement é um sub-classe que permite os comandos do sql.

        pmst.setString(1, this.codCompCurricular); //psmt é variável do PS então ela usa o método set <o tipo do dado a ser inserido> para definir os valores dos campos.
        pmst.setString(2, this.nomeDisciplina);
        pmst.setInt(3, this.cargaHorariaComp);
        pmst.setBoolean(4, this.componenteObrigatorio);
        pmst.setString(5, this.semestre);
        int qntLinhasInseridas = pmst.executeUpdate(); //executeUpdata executa a query.
        //qntLinharasInseridas é uma variável que identifica se foram inseridos.

        if(qntLinhasInseridas > 0){ //Verificação.

            System.out.println("Item inserido");

        }else{

            System.out.println("Erro na inserção dos dados");
        }
            pmst.close();

        }catch(java.sql.SQLException e) {

            System.out.println(e.getMessage());

        }
    }



    public String toString() {
        return "ComponenteCurricular [codCompCurricular=" + codCompCurricular + ", nomeDisciplina=" + nomeDisciplina
                + ", cargaHorariaComp=" + cargaHorariaComp + ", componenteObrigatorio=" + componenteObrigatorio + ", semestre="
                + semestre + "]";
    }



}
