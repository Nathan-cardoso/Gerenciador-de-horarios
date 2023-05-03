
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




    public String toString() {
        return "ComponenteCurricular [codDisciplina=" + codDisciplina + ", nomeDisciplina=" + nomeDisciplina
                + ", cargaHorariaComp=" + cargaHorariaComp + ", componenteObrigatorio=" + componenteObrigatorio + ", semestre="
                + semestre + "]";
    }










}
