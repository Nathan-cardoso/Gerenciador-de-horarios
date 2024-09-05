import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/*Restrições que devem ser respeitadas

    Pode haver mais de uma turma do mesmo componente curricular
    Turmas do mesmo semestre não podem ter o mesmo horário
    Turmas ministradas por um mesmo professor não podem ter o mesmo horário
 */
public class Turma {
    private String codTurma;
    private String codProf;
    private String codComponente;
    private String horario;
    private int horarioAula;
    private int semestre;

    // Construtor
    public Turma(String codTurma, String horario, int horarioAula, int semestre, String codComponente, String codProf) {
        this.codTurma = codTurma;
        this.horario = horario;
        this.horarioAula = horarioAula;
        this.semestre = semestre;
        this.codComponente = codComponente;
        this.codProf = codProf;
    }

    public String getCodTurma() {
        return codTurma;
    }

    public String getCodProf() {
        return codProf;
    }

    public String getCodComponente() {
        return codComponente;
    }

    public String getHorario() {
        return horario;
    }

    public int getHorarioAula() {
        return horarioAula;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setCodComponente(String codComponente) {
        this.codComponente = codComponente;
    }

    public static void cadastrarTurma(Turma turma) {

        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {

            Connection connection = ElephantSQLConnection.getConnection();

            String query = "SELECT * FROM turma WHERE horario = ? AND semestre = ? AND horario_aula = ?";
            pstm = connection.prepareStatement(query);
            pstm.setString(1, turma.getHorario());
            pstm.setInt(2, turma.getSemestre());
            pstm.setInt(3, turma.getHorarioAula());

            rs = pstm.executeQuery();
            if (rs.next()) {

                System.out.println("Erro! Já existe uma turma com o mesmo horário e semestre.");

            } else {

                query = "SELECT * FROM turma WHERE ciap_professor = ? AND horario = ? AND horario_aula = ?";
                pstm = connection.prepareStatement(query);
                pstm.setString(1, turma.getCodProf());
                pstm.setString(2, turma.getHorario());
                pstm.setInt(3, turma.getHorarioAula());

                rs = pstm.executeQuery();

                if (rs.next()) {

                    System.out.println("Erro! O professor já possui uma turma no mesmo horário.");

                } else {

                    query = "SELECT * FROM comp_curricular WHERE cod_componente = ? AND semestre = ?";
                    pstm = connection.prepareStatement(query);
                    pstm.setString(1, turma.getCodComponente());
                    pstm.setInt(2, turma.getSemestre());

                    rs = pstm.executeQuery();

                    if (!rs.next()) {

                        System.out.println("Erro! O componente curricular não possui um semestre correspondente.");

                    } else {

                        query = "INSERT INTO turma (cod_turma, horario, horario_aula, semestre, cod_componente, ciap_professor) VALUES (?,?,?,?,?,?)";

                        pstm = connection.prepareStatement(query);

                        pstm.setString(1, turma.getCodTurma());
                        pstm.setString(2, turma.getHorario());
                        pstm.setInt(3, turma.getHorarioAula());
                        pstm.setInt(4, turma.getSemestre());
                        pstm.setString(5, turma.getCodComponente());
                        pstm.setString(6, turma.getCodProf());

                        int linhasAfetadas = pstm.executeUpdate();

                        if (linhasAfetadas > 0) {
                            System.out.println("Turma cadastrada com sucesso.");
                        } else {
                            System.out.println("Erro no cadastro da turma.");
                        }
                    }
                }
            }

            pstm.close();
            rs.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar turma: " + e.getMessage());
        }
    }

    public static void editarTurma(String codTurma) {
        Scanner scan = new Scanner(System.in);

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Connection connection = ElephantSQLConnection.getConnection();

            String query = "SELECT * FROM turma WHERE cod_turma = ?";
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, codTurma);
            rs = pstmt.executeQuery();

            if (rs.next()) {

                int horario_aula;
                System.out.println("Informe os novos valores para a turma " + codTurma + " : ");
                System.out.println("Informe o turno");
                Menu.menuTurno();
                String horario = scan.nextLine();

                if (horario.equalsIgnoreCase("manha")) {
                    Menu.exibirHorariosManha();

                    System.out.println("Digite o horario da aula: ");
                    horario_aula = scan.nextInt();

                    if (horario_aula < 1 || horario_aula > 6) {
                        System.out.println("Horario não é disponivel");
                        return;
                    }
                } else if (horario.equalsIgnoreCase("tarde")) {
                    Menu.exibirHorariosTarde();
                    System.out.println("Digite o horario da aula: ");
                    horario_aula = scan.nextInt();

                    if (horario_aula < 1 || horario_aula > 6) {
                        System.out.println("Horario não é disponivel");
                        return;
                    }
                } else if (horario.equalsIgnoreCase("noite")) {
                    Menu.exibirHorariosNoite();

                    System.out.println("Digite o horario da aula: ");
                    horario_aula = scan.nextInt();

                    if (horario_aula < 1 || horario_aula > 6) {
                        System.out.println("Horario não é disponivel");
                        return;
                    }
                } else {
                    System.out.println("Turno inválido.");
                    return;
                }

                System.out.println("Semestre: ");
                int semestre = scan.nextInt();

                scan.nextLine();
                System.out.println("Atribua o componente curricular digitando o seu codigo: ");
                String codCC = scan.nextLine();

                System.out.println("Para atribuir um professor a turma, digite seu ciap: ");
                String codProf = scan.nextLine();

                Turma turma = new Turma(codTurma, horario, horario_aula, semestre, codCC, codProf);

                query = "SELECT * FROM turma WHERE horario = ? AND semestre = ? AND horario_aula = ?";

                pstmt = connection.prepareStatement(query);
                pstmt.setString(1, turma.getHorario());
                pstmt.setInt(2, turma.getSemestre());
                pstmt.setInt(3, turma.getHorarioAula());

                rs = pstmt.executeQuery();

                if (rs.next()) {
                    System.out.println("Erro! Já existe uma turma com o mesmo horário e semestre.");
                } else {

                    query = "SELECT * FROM turma WHERE ciap_professor = ? AND horario = ? AND horario_aula = ?";

                    pstmt = connection.prepareStatement(query);
                    pstmt.setString(1, turma.getCodProf());
                    pstmt.setString(2, turma.getHorario());
                    pstmt.setInt(3, turma.getHorarioAula());

                    rs = pstmt.executeQuery();

                    if (rs.next()) {
                        System.out.println("Erro! O professor já possui uma turma no mesmo horário.");
                    } else {

                        query = "SELECT * FROM comp_curricular WHERE cod_componente = ? AND semestre = ?";

                        pstmt = connection.prepareStatement(query);
                        pstmt.setString(1, turma.getCodComponente());
                        pstmt.setInt(2, turma.getSemestre());

                        rs = pstmt.executeQuery();

                        if (!rs.next()) {
                            System.out.println("Erro! O componente curricular não possui um semestre correspondente.");
                        } else {

                            query = "UPDATE turma SET horario = ?, horario_aula = ?, semestre = ?, cod_componente = ?, ciap_professor = ? WHERE cod_turma = ?";
                            pstmt = connection.prepareStatement(query);
                            pstmt.setString(1, turma.getHorario());
                            pstmt.setInt(2, turma.getHorarioAula());
                            pstmt.setInt(3, turma.getSemestre());
                            pstmt.setString(4, turma.getCodComponente());
                            pstmt.setString(5, turma.getCodProf());
                            pstmt.setString(6, turma.getCodTurma());

                            int linhasAfetadas = pstmt.executeUpdate();

                            if (linhasAfetadas > 0) {
                                System.out.println("Turma atualizada com sucesso.");
                            } else {
                                System.out.println("Erro na atualização da turma.");
                            }
                        }
                    }
                }
            } else {
                System.out.println("Não foi encontrado nenhuma turma com esse código");
            }

            pstmt.close();
            rs.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Erro ao editar turma: " + e.getMessage());
        }

    }

    public static void verDadosDeUmaTurma(String codTurma) {
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            Connection connection = ElephantSQLConnection.getConnection();
            String querySearch = "SELECT * FROM turma WHERE  cod_turma = ?";
            psmt = connection.prepareStatement(querySearch);
            psmt.setString(1, codTurma);
            rs = psmt.executeQuery();

            if (!rs.next()) {
                System.out.println("Esse código da turma é inválido .");
            } else {
                do {

                    String idTurma = rs.getString("cod_turma");
                    String codComponente = rs.getString("cod_componente");
                    String horario = rs.getString("horario");
                    int horarioAula = rs.getInt("horario_aula");
                    int semestre = rs.getInt("semestre");
                    String codProf = rs.getString("ciap_professor");
                    Turma turma = new Turma(idTurma, horario, horarioAula, semestre, codComponente, codProf);
                    System.out.println(turma);
                } while (rs.next());
            }

            connection.close();
            rs.close();
            psmt.close();
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void listarTurmas() {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Connection connection = ElephantSQLConnection.getConnection();
            String query = "SELECT * FROM turma";
            pstmt = connection.prepareStatement(query);
            rs = pstmt.executeQuery();

            if (!rs.next()) {
                System.out.println("Não há nenhuma turma cadastrada.");
            } else {
                do {

                    String codTurma = rs.getString("cod_turma");
                    String codComponente = rs.getString("cod_componente");
                    String horario = rs.getString("horario");
                    int horarioAula = rs.getInt("horario_aula");
                    int semestre = rs.getInt("semestre");
                    String codProf = rs.getString("ciap_professor");

                    Turma turma = new Turma(codTurma, horario, horarioAula, semestre, codComponente, codProf);
                    System.out.println(turma);

                } while (rs.next());

            }
            pstmt.close();
            connection.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Erro ao listar turmas: " + e.getMessage());
        }
    }

    public static void listarTurmasPorSemestre(int semestre) {
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            Connection connection = ElephantSQLConnection.getConnection();
            String querySearch = "SELECT * FROM turma WHERE semestre = ?";
            psmt = connection.prepareStatement(querySearch);
            psmt.setInt(1, semestre);
            rs = psmt.executeQuery();

            if (!rs.next()) {
                System.out.println("Não foram encontradas turmas para o semestre " + semestre);
            } else {

                System.out.printf("%-12s%-20s%-16s%-15s%-25s%-20s%n", "Código", "Componente", "Turno", "Horário",
                        "Ciap Professor", "Semestre");
                System.out.println(
                        "-------------------------------------------------------------------------------------------------------------------");
                do {

                    String idTurma = rs.getString("cod_turma");
                    String codComponente = rs.getString("cod_componente");
                    String horario = rs.getString("horario");
                    int horarioAula = rs.getInt("horario_aula");
                    String codProf = rs.getString("ciap_professor");
                    int semestreTurma = rs.getInt("semestre");

                    System.out.printf("%-12s%-20s%-20s%-15d%-25s%-20d%n", idTurma, codComponente, horario, horarioAula,
                            codProf, semestreTurma);
                } while (rs.next());
            }

            connection.close();
            rs.close();
            psmt.close();
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void listarTurmasPorProfessor(String ciapProf) {
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {

            Connection connection = ElephantSQLConnection.getConnection();
            String query = "SELECT t.*, p.nome FROM turma t JOIN professor p ON t.ciap_professor = p.ciap WHERE t.ciap_professor = ? ";
            pstm = connection.prepareStatement(query);
            pstm.setString(1, ciapProf);
            rs = pstm.executeQuery();

            if (!rs.next()) {
                System.out.println("Professor não tem nenhuma turma ");

            } else {

                do {

                    String idTurma = rs.getString("cod_turma");
                    String codComponente = rs.getString("cod_componente");
                    String horario = rs.getString("horario");
                    int horarioAula = rs.getInt("horario_aula");
                    int semestre = rs.getInt("semestre");
                    String codProf = rs.getString("ciap_professor");
                    String nomeProf = rs.getString("nome");
                    Turma turma = new Turma(idTurma, horario, horarioAula, semestre, codComponente, codProf);
                    System.out.println(
                            "Turma ministrada por: " + nomeProf + " código da turma: " + turma.getCodComponente()
                                    + " | Componente: " + turma.getCodComponente() + " | turno: " + turma.getHorario()
                                    + " | Horario: " + turma.getHorarioAula() + " | Semestre: " + turma.getSemestre());
                } while (rs.next());

            }

            connection.close();
            rs.close();
            pstm.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void excluirTurma(String codTurma) {
        PreparedStatement pstmt = null;

        try {

            Connection connection = ElephantSQLConnection.getConnection();
            pstmt = connection.prepareStatement("DELETE from turma where cod_turma = ?");

            pstmt.setString(1, codTurma);

            int qntLinhas = pstmt.executeUpdate();

            if (qntLinhas > 0) {
                System.out.println("Turma excluída com sucesso!");
            } else {
                System.out.println("Turma não encontrada!");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao excluir turma: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return String.format(
                "codigo: %-5s | Componente: %-5s | Turno: %-5s | Horario: %s | Semestre: %s | Ciap Professor: %s |",
                codTurma, codComponente, horario, horarioAula, semestre, codProf);
    }
}