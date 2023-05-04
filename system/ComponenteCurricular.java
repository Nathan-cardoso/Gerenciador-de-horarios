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
    
    public ComponenteCurricular(){
        
    }

    public void setCodCompCurricular(String codCompCurricular) {
        this.codCompCurricular = codCompCurricular;
    }
 
    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }



    public void setCargaHorariaComp(int cargaHorariaComp) {
        this.cargaHorariaComp = cargaHorariaComp;
    }



    public void setComponenteObrigatorio(boolean componenteObrigatorio) {
        this.componenteObrigatorio = componenteObrigatorio;
    }



    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }



    //Criando um get para ser utilizado por professor.
    public int getCargaHorariaComp() {
        return cargaHorariaComp;
    }

    

    public String getCodCompCurricular() {
        return codCompCurricular;
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
        //Os números antes dos atributos mostram a posição do parâmetro na consulta, posição essa que é referente ao '?' na query acima.
        int qntLinhasInseridas = pmst.executeUpdate(); //executeUpdata executa a query.
        //qntLinharasInseridas é uma variável que identifica se foram inseridos.

        if(qntLinhasInseridas > 0){ //Verificando se foi inserido.

            System.out.println("Componente curricular cadastrado...");

        }else{

            System.out.println("Não foi possivel cadastrar componente curricular");
        }
            pmst.close();

        }catch(java.sql.SQLException e) {

            System.out.println(e.getMessage());

        }

        //Se tudo ok, o usuário será notificado que o componente foi cadastrado
    }

    public void editarComponenteCurricular(){
        try {
            Connection connection = ElephantSQLConnection.getConnection();
            String query = "UPDATE comp_curricular SET nome_disciplina = ?, carga_horaria_comp = ?,componente_ob = ?, semestre = ? WHERE cod_disciplina = ?";
            PreparedStatement psmt = connection.prepareStatement(query);
            psmt.setString(1, this.nomeDisciplina);
            psmt.setInt(2, this.cargaHorariaComp);
            psmt.setBoolean(3, this.componenteObrigatorio);
            psmt.setString(4, this.semestre);
            psmt.setString(5, this.codCompCurricular);

            int qntLinharasAlteradas = psmt.executeUpdate();

            if(qntLinharasAlteradas > 0){
                System.out.println("Componente editado com sucesso!");
            }else{
                System.out.println("Não editado");
            }

        } catch (java.sql.SQLException e) {

            System.out.println(e.getMessage());

        }
    }

    public void verDadosDeUmComponenteCurricular(){

    }

    public void listarComponentesCurriculares(){

    }

    public void excluirComponentesCurriculares(){
        
    }



    public String toString() {
        return "ComponenteCurricular [codCompCurricular=" + codCompCurricular + ", nomeDisciplina=" + nomeDisciplina
                + ", cargaHorariaComp=" + cargaHorariaComp + ", componenteObrigatorio=" + componenteObrigatorio + ", semestre="
                + semestre + "]";
    }


}
