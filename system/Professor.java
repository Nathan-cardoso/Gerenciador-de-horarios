import java.util.ArrayList;
import java.util.List;

public class Professor {
    private String nome;
    private String formacao;
    private String email;
    private int cargaHoraria;
    private List<ComponenteCurricular> componentes;

    // Construtor da classe Professor
    public Professor(String nome, String formacao, String email) {
        this.nome = nome;
        this.formacao = formacao;
        this.email = email;
        this.cargaHoraria = 0;
        this.componentes = new ArrayList<>();
    }

    // Get para obter o nome do professor
    public String getNome() {
        return nome;
    }

    // Get para obter a formaçãp do professor
    public String getFormacao() {
        return formacao;
    }

    // Get para obter o email do professor 
    public String getEmail() {
        return email;
    }

    // Get para obter a carga horária do professor
    public int getCargaHoraria() {
        return cargaHoraria;
    }

    // Método para adicionar um componente curricular ministrado pelo professor
    public void addComponente(ComponenteCurricular componente) {
        componentes.add(componente);
        // Incrementa a carga horária do professor com a carga horária do componente
        cargaHoraria += componente.getCargaHorariaComp();
    }

    // Verifica se o professor pode ministrar um componente curricular
    public boolean podeMinistrar(ComponenteCurricular componente) {
        return (cargaHoraria + componente.getCargaHorariaComp()) <= 20;
    }
}