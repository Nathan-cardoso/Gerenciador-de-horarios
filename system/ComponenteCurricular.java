
public class ComponenteCurricular {
    private String codDisciplina;
    private String nomeDisciplina;
    private int cargaHoraria;
    private boolean componenteObrigatorio;
    private String semestre;


    //Construtor
    public ComponenteCurricular(String codDisciplina, String nomeDisciplina, int cargaHoraria,
            boolean componenteObrigatorio, String semestre) {
        this.codDisciplina = codDisciplina;
        this.nomeDisciplina = nomeDisciplina;
        this.cargaHoraria = cargaHoraria;
        this.componenteObrigatorio = componenteObrigatorio;
        this.semestre = semestre;
    }


    
    public String toString() {
        return "ComponenteCurricular [codDisciplina=" + codDisciplina + ", nomeDisciplina=" + nomeDisciplina
                + ", cargaHoraria=" + cargaHoraria + ", componenteObrigatorio=" + componenteObrigatorio + ", semestre="
                + semestre + "]";
    }


}
