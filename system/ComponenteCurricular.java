
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
<<<<<<< HEAD

    public int getCargaHoraria() {
        return 0;
    }
    




=======
>>>>>>> 3dc0a505ebdef3a85b1250af532ef27f07c67da9


    
    public String toString() {
        return "ComponenteCurricular [codDisciplina=" + codDisciplina + ", nomeDisciplina=" + nomeDisciplina
                + ", cargaHoraria=" + cargaHoraria + ", componenteObrigatorio=" + componenteObrigatorio + ", semestre="
                + semestre + "]";
    }


}
