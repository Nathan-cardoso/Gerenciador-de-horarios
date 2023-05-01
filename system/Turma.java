import java.util.ArrayList;

public class Turma {
    private String turno;
    private ArrayList<String> matriculaProf = new ArrayList<>(); //Vai ter alterações futuras!
    private ArrayList<String> codComponenteCurricular = new ArrayList<>();
    private int horario;


    public Turma(String turno,String matriculaProf, String codComponenteCurricular,
            int horario) {
            this.turno = turno;
            this.matriculaProf.add(matriculaProf);
            this.codComponenteCurricular.add(codComponenteCurricular);
            this.horario = horario;
    }



    public String toString() {
        return "Turma [turno=" + turno + ", matriculaProf=" + matriculaProf + ", codComponenteCurricular="
                + codComponenteCurricular + ", horario=" + horario + "]";
    }

    

    

    
    



}
