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

    //Construtor
    public Turma(String codTurma, String horario,int horarioAula, int semestre,String codComponente, String codProf) {
        this.codTurma = codTurma;
        this.horario = horario;
        this.horarioAula = horarioAula;
        this.semestre = semestre;
        this.codComponente = codComponente;
        this.codProf = codProf;
    }

    //get para acessar o código da turma
    public String getCodTurma() {
        return codTurma;
    }

    //get para acessar o código do professor
    public String getCodProf() {
        return codProf;
    }

    //get para acessar o código do componente
    public String getCodComponente() {
        return codComponente;
    }

    //get para acessar o horário que se refere ao turno
    public String getHorario() {
        return horario;
    }

    //get para acessar o horario aula
    public int getHorarioAula() {
        return horarioAula;
    }

    //get para acessar o semestre
    public int getSemestre() {
        return semestre;
    }


    public void setCodComponente(String codComponente) {
        this.codComponente = codComponente;
    }


    //Esse método cadastrar a turma passando os dados para o banco de dados
    public static void cadastrarTurma(Turma turma) {

        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {

            Connection connection = ElephantSQLConnection.getConnection(); //Estabelecendo a conexão com o banco
    
            // Verifica se já existe uma turma com o mesmo horário e semestre
            String query = "SELECT * FROM turma WHERE horario = ? AND semestre = ? AND horario_aula = ?"; //Essa query procura no banco alguma turma com o semestre, horario e hora_aula igual ao que foi passado pelo usuário.
            pstm = connection.prepareStatement(query);
            pstm.setString(1, turma.getHorario());
            pstm.setInt(2, turma.getSemestre());
            pstm.setInt(3, turma.getHorarioAula());

            rs = pstm.executeQuery();//rs para percorrer os resudados da consulta que foram executados através do método executeQuery.
    
            if (rs.next()) { //Se  rs não estiver vázia significa que o banco já tem uma turma com o mesmo semestre, horario e horario_aula.

                System.out.println("Erro! Já existe uma turma com o mesmo horário e semestre.");

            } else {
                // Verifica se o professor já possui uma turma no mesmo horário
                query = "SELECT * FROM turma WHERE ciap_professor = ? AND horario = ? AND horario_aula = ?"; //Essa query lista as tabelas com o ciap, horario e horario aula fornecido pelo usuário.
                pstm = connection.prepareStatement(query);
                pstm.setString(1, turma.getCodProf());
                pstm.setString(2, turma.getHorario());
                pstm.setInt(3, turma.getHorarioAula());

                rs = pstm.executeQuery();//rs para percorrer os resudados da consulta que foram executados através do método executeQuery.
    
                if (rs.next()) {//Nesse caso, se rs não estiver vázio é porque o professor já está dando aula no turno(horario) e horario_aula.

                    System.out.println("Erro! O professor já possui uma turma no mesmo horário.");

                } else {
                // Verifica se o semestre da turma é igual ao semestre do componente curricular
                query = "SELECT * FROM comp_curricular WHERE cod_componente = ? AND semestre = ?"; //Essa query mostra o semestre do componente curricular que é igual ao semestre que o usuário passou para a turma
                pstm = connection.prepareStatement(query);
                pstm.setString(1, turma.getCodComponente());
                pstm.setInt(2, turma.getSemestre());

                rs = pstm.executeQuery();//rs para percorrer os resudados da consulta que foram executados através do método executeQuery.

                if (!rs.next()) { //Nesse caso, se o semestre da turma não corresponder ao do componente não será possível cadastrar.

                    System.out.println("Erro! O componente curricular não possui um semestre correspondente.");

                }else {//Depois de verificar todas as condições...
                    // Pode cadastrar a nova turma
                    query = "INSERT INTO turma (cod_turma, horario, horario_aula, semestre, cod_componente, ciap_professor) VALUES (?,?,?,?,?,?)";
                    //Essa query insere os valores passados.
                    pstm = connection.prepareStatement(query);
    
                    pstm.setString(1, turma.getCodTurma());
                    pstm.setString(2, turma.getHorario());
                    pstm.setInt(3, turma.getHorarioAula());
                    pstm.setInt(4, turma.getSemestre());
                    pstm.setString(5, turma.getCodComponente());
                    pstm.setString(6, turma.getCodProf());
    
                    int linhasAfetadas = pstm.executeUpdate();
    
                    if (linhasAfetadas > 0) {//Verificação se foi possível cadastrar.
                        System.out.println("Turma cadastrada com sucesso.");
                    } else {
                        System.out.println("Erro no cadastro da turma.");
                    }
                }
            }
        }
            //fechando as variáveis de conexão
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
            Connection connection = ElephantSQLConnection.getConnection(); //Veriável de conexão com o banco 

            String query = "SELECT * FROM turma WHERE cod_turma = ?"; //Verifica se o código passado pelo usuário está registrado no banco 
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, codTurma);
            rs = pstmt.executeQuery();

            if (rs.next()) { //Se caso o código seja válido...
                 //Coletando os novos dados da turma 
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

                // Verifica se já existe uma turma com o mesmo horário e semestre
                query = "SELECT * FROM turma WHERE horario = ? AND semestre = ? AND horario_aula = ?";
                // Essa query procura no banco alguma turma com o semestre, horario e hora_aula igual ao que foi passado pelo usuário.
                pstmt = connection.prepareStatement(query);
                pstmt.setString(1, turma.getHorario());
                pstmt.setInt(2, turma.getSemestre());
                pstmt.setInt(3, turma.getHorarioAula());

                rs = pstmt.executeQuery();

                if (rs.next()) {
                    System.out.println("Erro! Já existe uma turma com o mesmo horário e semestre.");
                } else {

                query = "SELECT * FROM turma WHERE ciap_professor = ? AND horario = ? AND horario_aula = ?";
                // Essa query lista as tabelas com o ciap, horario e horario aula fornecido pelo usuário.
                pstmt = connection.prepareStatement(query);
                pstmt.setString(1, turma.getCodProf());
                pstmt.setString(2, turma.getHorario());
                pstmt.setInt(3, turma.getHorarioAula());

                rs = pstmt.executeQuery();

                if (rs.next()) {
                    System.out.println("Erro! O professor já possui uma turma no mesmo horário.");
                } else {
                    // Verifica se o semestre da turma é igual ao semestre do componente curricular
                    query = "SELECT * FROM comp_curricular WHERE cod_componente = ? AND semestre = ?";
                    // Essa query mostra o semestre do componente curricular que é igual ao semestre que o usuário passou para a turma
                    pstmt = connection.prepareStatement(query);
                    pstmt.setString(1, turma.getCodComponente());
                    pstmt.setInt(2, turma.getSemestre());

                    rs = pstmt.executeQuery();

                    if (!rs.next()) {
                        System.out.println("Erro! O componente curricular não possui um semestre correspondente.");
                    } else {
                        // Atualiza a turma com os novos valores
                        query = "UPDATE turma SET horario = ?, horario_aula = ?, semestre = ?, cod_componente = ?, ciap_professor = ? WHERE cod_turma = ?";
                        pstmt = connection.prepareStatement(query);
                        pstmt.setString(1, turma.getHorario());
                        pstmt.setInt(2, turma.getHorarioAula());
                        pstmt.setInt(3, turma.getSemestre());
                        pstmt.setString(4, turma.getCodComponente());
                        pstmt.setString(5, turma.getCodProf());
                        pstmt.setString(6, turma.getCodTurma());

                        int linhasAfetadas = pstmt.executeUpdate(); 

                        if (linhasAfetadas > 0) {//Verifica se a turma foi cadastrada com sucesso.
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

        //Fechando as variáveis.
        pstmt.close();
        rs.close();
        connection.close();

    } catch (SQLException e) {
        System.out.println("Erro ao editar turma: " + e.getMessage());
    }


}

    //Esse método recebe do usuário o código da turma que ele deseja ver os dados.
    public static void verDadosDeUmaTurma(String codTurma){
        PreparedStatement psmt = null;
        ResultSet rs = null;
    
        try {
            Connection connection = ElephantSQLConnection.getConnection(); //Iniciando a conexão com o banco
            String querySearch = "SELECT * FROM turma WHERE  cod_turma = ?"; //Query para buscar a turma pelo código digitado.
            psmt = connection.prepareStatement(querySearch); //executando a query.
            psmt.setString(1, codTurma);//Substituindo o ? da query pelo conteúdo da variável, assim a query vai direto ao codTurma
            rs = psmt.executeQuery(); //resultSet representa o resultado da consultado da query em psmt
    
            if (!rs.next()) /*Se o rs estiver vazio é porque não tem uma turma com o código digitado*/{//verificação
                System.out.println("Esse código da turma é inválido .");
            } else{
                do {
                    //rs possibilita armazenar em variáveis os resultados da query.
                    String idTurma = rs.getString("cod_turma");
                    String codComponente = rs.getString("cod_componente");
                    String horario = rs.getString("horario");
                    int horarioAula = rs.getInt("horario_aula");
                    int semestre = rs.getInt("semestre");
                    String codProf = rs.getString("ciap_professor");
                    //Definida as variáveis que receberão os valores de cada campo...
                    Turma turma = new Turma(idTurma, horario, horarioAula, semestre, codComponente, codProf); //Será instanciado um objeto Turma para mostrar a turma
                    System.out.println(turma); //Saída.
                } while (rs.next());
            }
    
            //Fechando a conexão
            connection.close();
            rs.close();
            psmt.close();
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    
    //Esse método lista todas as turmas que estão cadastradas no banco de dados.
    public static void listarTurmas() {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
    
        try {
            Connection connection = ElephantSQLConnection.getConnection(); // obtém a conexão com o banco de dados
            String query = "SELECT * FROM turma"; // query para selecionar todas as turmas
            pstmt = connection.prepareStatement(query); // prepara a query para ser executada
            rs = pstmt.executeQuery(); // executa a query e obtém os resultados
    
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
                    
                    //Criando o objeto que ira assumir os valores
                    Turma turma = new Turma(codTurma, horario, horarioAula, semestre, codComponente, codProf);
                    System.out.println(turma);
                    //Saída.
                } while (rs.next());

            }
        //fechando as variáveis de conexão
            pstmt.close();
            connection.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Erro ao listar turmas: " + e.getMessage());
        }
    }
    
    //O usuário irá digitar o semestre e irá ser impresso todas as turmas daquele semestre.
    public static void listarTurmasPorSemestre(int semestre) {
        PreparedStatement psmt = null;
        ResultSet rs = null;
    
        try {
            Connection connection = ElephantSQLConnection.getConnection(); //Iniciando a conexão com o banco
            String querySearch = "SELECT * FROM turma WHERE semestre = ?"; //Query para buscar as turmas pelo semestre digitado.
            psmt = connection.prepareStatement(querySearch); //executando a query.
            psmt.setInt(1, semestre); //Substituindo o ? da query pelo conteúdo da variável, assim a query vai direto ao semestre
            rs = psmt.executeQuery(); //resultSet representa o resultado da consultado da query em psmt
    
            if (!rs.next()) { //Se o rs estiver vazio é porque não tem turmas com o semestre digitado.
                System.out.println("Não foram encontradas turmas para o semestre " + semestre);
            } else {
                //Criando a tabela de turmas
                System.out.printf("%-12s%-20s%-16s%-15s%-25s%-20s%n", "Código", "Componente", "Turno", "Horário", "Ciap Professor", "Semestre");
                System.out.println("-------------------------------------------------------------------------------------------------------------------");
                do {
                    //rs possibilita armazenar em variáveis os resultados da query.
                    String idTurma = rs.getString("cod_turma");
                    String codComponente = rs.getString("cod_componente");
                    String horario = rs.getString("horario");
                    int horarioAula = rs.getInt("horario_aula");
                    String codProf = rs.getString("ciap_professor");
                    int semestreTurma = rs.getInt("semestre");
    
                    //Imprimindo as informações da turma na tabela
                    System.out.printf("%-12s%-20s%-20s%-15d%-25s%-20d%n", idTurma, codComponente, horario, horarioAula, codProf, semestreTurma);
                } while (rs.next());
            }
    
            //Fechando a conexão
            connection.close();
            rs.close();
            psmt.close();
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //Usuário irá passar o ciap do professor e então o método retornará as turmas que o professor equivalente ao ciap ministra.
    public static void listarTurmasPorProfessor(String ciapProf){
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {

            Connection connection = ElephantSQLConnection.getConnection(); //Variável que estabele a conexão com o banco de dados
            String query = "SELECT t.*, p.nome FROM turma t JOIN professor p ON t.ciap_professor = p.ciap WHERE t.ciap_professor = ? "; //Essa query vai fazer a junção das tabelas turma e professor e irá listar todas as turmas que o professor ministra
            pstm = connection.prepareStatement(query);
            pstm.setString(1, ciapProf);
            rs = pstm.executeQuery();
            
            if(!rs.next()){ //Caso a tabela esteja vázia é porque o professor não tem nenhuma turma atribuida a ele.

                System.out.println("Professor não tem nenhuma turma ");

            }else{

                do{

                    String idTurma = rs.getString("cod_turma");
                    String codComponente = rs.getString("cod_componente");
                    String horario = rs.getString("horario");
                    int horarioAula = rs.getInt("horario_aula");
                    int semestre = rs.getInt("semestre");
                    String codProf = rs.getString("ciap_professor");
                    String nomeProf = rs.getString("nome");
                    Turma turma = new Turma(idTurma, horario, horarioAula, semestre, codComponente, codProf);
                    System.out.println("Turma ministrada por: " + nomeProf + " código da turma: " + turma.getCodComponente() + " | Componente: " + turma.getCodComponente() + " | turno: " + turma.getHorario() + " | Horario: " + turma.getHorarioAula() + " | Semestre: " + turma.getSemestre());
                }while(rs.next());

            }
        //fechando as variáveis de conexão
            connection.close();
            rs.close();
            pstm.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //O usuário irá passar o código da turma e o método irá excluilá. 
    public static void excluirTurma(String codTurma) {
        PreparedStatement pstmt = null;
    
        try {

            Connection connection = ElephantSQLConnection.getConnection();
            pstmt = connection.prepareStatement("DELETE from turma where cod_turma = ?"); //Essa query excluí a tupla em que aparece o código da turma passado pelo usuário.

            pstmt.setString(1, codTurma);

            int qntLinhas = pstmt.executeUpdate();
    
            if (qntLinhas > 0) {//Verificação se foi excluída com sucesso.
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
        return String.format("codigo: %-5s | Componente: %-5s | Turno: %-5s | Horario: %s | Semestre: %s | Ciap Professor: %s |", codTurma, codComponente, horario, horarioAula, semestre, codProf);
    }
}