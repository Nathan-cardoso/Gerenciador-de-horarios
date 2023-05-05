public class Main {

    public static void main(String[] args) {
        Professor professor = new Professor("João da Silva", "Doutorado", "joao.silva@example.com");
        Professor professorDAO = new Professor(null, null, null);
        professorDAO.cadastrarProfessor(professor);
    }

}