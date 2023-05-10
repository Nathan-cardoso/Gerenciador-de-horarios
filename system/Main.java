import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.util.Scanner;


public class Main{
    public static void main(String[] args) {
        editarTurma("1");
    }

    public static void editarTurma(String codTurma) {
        Scanner sc = new Scanner(System.in);

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
                String horario = sc.nextLine();

                //Coletando os novos dados da turma 
                if (horario.equalsIgnoreCase("manha")) {
                    Menu.exibirHorariosManha();

                    System.out.println("Digite o horario da aula: ");
                    horario_aula = sc.nextInt();

                    if (horario_aula < 1 || horario_aula > 6) {
                        System.out.println("Horario não é disponivel");
                        return;
                    }
                } else if (horario.equalsIgnoreCase("tarde")) {
                    Menu.exibirHorariosTarde();
                    System.out.println("Digite o horario da aula: ");
                    horario_aula = sc.nextInt();

                    if (horario_aula < 1 || horario_aula > 6) {
                        System.out.println("Horario não é disponivel");
                        return;
                    }
                } else if (horario.equalsIgnoreCase("noite")) {
                    Menu.exibirHorariosNoite();

                    System.out.println("Digite o horario da aula: ");
                    horario_aula = sc.nextInt();

                    if (horario_aula < 1 || horario_aula > 6) {
                        System.out.println("Horario não é disponivel");
                        return;
                    }
                } else {
                    System.out.println("Turno inválido.");
                    return;
                }

                System.out.println("Semestre: ");
                int semestre = sc.nextInt();

                sc.nextLine();
                System.out.println("Atribua o componente curricular digitando o seu codigo: ");
                String codCC = sc.nextLine();

                System.out.println("Para atribuir um professor a turma, digite seu ciap: ");
                String codProf = sc.nextLine();

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
                } else {                    query = "SELECT * FROM turma WHERE ciap_professor = ? AND horario = ? AND horario_aula = ?";
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


}







/*public class Main{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int horario_aula;
            System.out.println("Digite o codigo da turma: ");
            String codturma = sc.nextLine();


            System.out.println("Informe o turno");
            String horario = sc.nextLine();
            if (horario.equalsIgnoreCase("manha")) {
                Menu.exibirHorariosManha();
                System.out.println("Digite o horario da aula: ");
                 horario_aula = sc.nextInt();
                if(horario_aula < 1 || horario_aula > 6){
                    System.out.println("Horario não é disponivel");
                    return;
                }
            } else if (horario.equalsIgnoreCase("tarde")) {
                Menu.exibirHorariosTarde();
                System.out.println("Digite o horario da aula: ");
                 horario_aula = sc.nextInt();
                if(horario_aula < 1 || horario_aula > 6){
                    System.out.println("Horario não é disponivel");
                    return;
                }
            } else if (horario.equalsIgnoreCase("noite")) {
                Menu.exibirHorariosNoite();
                System.out.println("Digite o horario da aula: ");
                 horario_aula = sc.nextInt();
                if(horario_aula < 1 || horario_aula > 6){
                    System.out.println("Horario não é disponivel");
                    return;
                }
            } else {
                System.out.println("Turno inválido.");
                return;
            }

            System.out.println("Semestre: ");
            int semestre = sc.nextInt();

            sc.nextLine();
            System.out.println("Atribua o componente curricular digitando o seu codigo: ");
            String codCC = sc.nextLine();


                        System.out.println("Para atribuir um professor a turma, digite seu ciap: ");

                        String codProf = sc.nextLine();
                        
                            Turma turma = new Turma(codturma, horario,horario_aula, semestre, codCC,codProf);
                            Turma.cadastrarTurma(turma);

    }

}*/



// Teste do cadastrar Professor
/*public class Main {
    public static void main(String[] args) {
        Professor professor = new Professor("PEDRO THIAGO VALERIO DE SOUZA", "Mestrado", "9596", "pedro.souza@ufersa.edu.br");
        Professor.cadastrarProfessor(professor);
    }
}*/

// Teste para listar professores

/*public class Main {
    public static void main(String[] args) {
        listarProfessores();
    }

    public static void listarProfessores() {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Connection connection = ElephantSQLConnection.getConnection(); // obtém a conexão com o banco de dados
            String query = "SELECT * FROM professor"; // query para selecionar todos os professores
            pstmt = connection.prepareStatement(query); // prepara a query para ser executada
            rs = pstmt.executeQuery(); // executa a query e obtém os resultados

         if (!rs.next()) {
                System.out.println("Não há nenhum professor cadastrado.");
            } else {
                do {
                    String nome = rs.getString("nome");
                    String formacao = rs.getString("formacao");
                    String ciap = rs.getString("ciap");
                    String email = rs.getString("email");

                    Professor professor = new Professor(nome, formacao, ciap, email);
                    System.out.println(professor);
                } while (rs.next());
            }

            pstmt.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Erro ao listar professores: " + e.getMessage());
        }
    }
}*/

    /*public class Main {
        public static void main(String[] args) {
            excluirProfessor("12345");
        }
     public static void excluirProfessor(String ciap) {
            try {
                Connection connection = ElephantSQLConnection.getConnection(); // obtém a conexão com o banco de dados
    
             // Query para deletar professor com o CIAP informado
                String query = "DELETE FROM professor WHERE ciap = ?";
                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setString(1, ciap);
    
                int linhasAfetadas = pstmt.executeUpdate();
    
                if (linhasAfetadas > 0) {
                    System.out.println("Professor excluído com sucesso!");
                } else {
                    System.out.println("Professor não encontrado.");
                }
    
                pstmt.close();
                connection.close();
    
            } catch (SQLException e) {
                System.out.println("Erro ao excluir professor: " + e.getMessage());
            }
        }
    }*/

     /*public class Main{
         public static void main(String[] args) {
             editarProfessor();
         }
         public static void editarProfessor() {
             try {
                 Scanner scan = new Scanner(System.in);
        
                 do {
                     System.out.println("Digite o CIAP do professor que deseja editar: ");
                     String ciap = scan.nextLine();
        
                     Connection connection = ElephantSQLConnection.getConnection();
                     String query = "SELECT * FROM professor WHERE ciap=?";
                     PreparedStatement pstmt = connection.prepareStatement(query);
                     pstmt.setString(1, ciap);
        
                     ResultSet rs = pstmt.executeQuery();
        
                     if (!rs.next()) {
                         System.out.println("Professor não encontrado.");
                     } else {
                         System.out.println("Digite a opção que deseja editar:");
                         System.out.println("1 - Nome");
                         System.out.println("2 - Formação");
                         System.out.println("3 - Email");
                         int opcao = scan.nextInt();
                         scan.nextLine();  //Consumir quebra de linha
        
                         String campo = "";
                         switch(opcao) {
                             case 1:
                                 campo = "nome";
                                 break;
                             case 2:
                                 campo = "formacao";
                                 break;
                             case 3:
                                 campo = "email";
                                 break;
                             default:
                                 System.out.println("Opção inválida.");
                                 break;
                         }
        
                         if (!campo.equals("")) {
                             System.out.print("Digite o novo valor para " + campo + ": ");
                             String novoValor = scan.nextLine();
        
                             query = "UPDATE professor SET " + campo + "=? WHERE ciap=?";
                             pstmt = connection.prepareStatement(query);
                             pstmt.setString(1, novoValor);
                             pstmt.setString(2, ciap);
        
                             int linhasAfetadas = pstmt.executeUpdate();
        
                             if (linhasAfetadas > 0) {
                                 System.out.println("Professor atualizado com sucesso!");
                             } else {
                                 System.out.println("Erro ao atualizar professor.");
                             }
        
                             System.out.println("Deseja editar mais alguma informação do professor? (S/N)");
                             String resposta = scan.nextLine();
        
                             if (!resposta.equalsIgnoreCase("S") && !resposta.equalsIgnoreCase("s")) {
                                 System.out.println("Professor atualizado com sucesso!");
                                 break;
                             }  
                         }
                     }
        
                     rs.close();
                     pstmt.close();
                     connection.close();
        
                 } while (true);
        
                 scan.close();
        
             } catch (Exception e) {
                 System.out.println("Erro ao editar professor: " + e.getMessage());
             }
         }       
     }*/
     
     /*public class Main{
            public static void main(String[] args) {
                editarComponenteCurricular();
            }
        
            public static void editarComponenteCurricular() {
                try {
                    Scanner scan = new Scanner(System.in);
            
                    System.out.println("Digite o código do componente curricular que deseja editar: ");
                    String cod_componente = scan.nextLine();
            
                    Connection connection = ElephantSQLConnection.getConnection();
                    String query = "SELECT * FROM comp_curricular WHERE cod_componente=?";
                    PreparedStatement pstmt = connection.prepareStatement(query);
                    pstmt.setString(1, cod_componente);
            
                    ResultSet rs = pstmt.executeQuery();
            
                    if (!rs.next()) {
                        System.out.println("Componente curricular não encontrado.");
                    } else {
                        String codCompCurricular = rs.getString("cod_componente");
                        String nomeDisciplina = rs.getString("nome_disciplina");
                        int cargaHorariaComp = rs.getInt("carga_horaria_comp");
                        boolean componenteObrigatorio = rs.getBoolean("comp_obrigatorio");
                        int semestre = rs.getInt("semestre");
            
                        //ComponenteCurricular CC = new ComponenteCurricular(codCompCurricular, nomeDisciplina, cargaHorariaComp, componenteObrigatorio, semestre);
            
                        System.out.println("Componente curricular encontrado:");
                        System.out.println("Código: " + codCompCurricular);
                        System.out.println("Disciplina: " + nomeDisciplina);
                        System.out.println("Carga Horária: " + cargaHorariaComp);
                        System.out.println("Componente Obrigatório: " + componenteObrigatorio);
                        System.out.println("Semestre: " + semestre);
            
                        boolean continuar = true;
                        while (continuar) {
                            System.out.println("\nDigite a opção que deseja editar: \n");
                            System.out.println("1 - Nome da disciplina");
                            System.out.println("2 - Carga horária");
                            System.out.println("3 - Componente obrigatório");
                            System.out.println("4 - Semestre");
                            int opcao = scan.nextInt();
                            scan.nextLine(); // Consumir quebra de linha
            
                            switch(opcao) {
                                case 1:
                                    System.out.print("Digite o novo nome da disciplina: ");
                                    String novoNome = scan.nextLine();
                                    query = "UPDATE comp_curricular SET nome_disciplina=? WHERE cod_componente=?";
                                    pstmt = connection.prepareStatement(query);
                                    pstmt.setString(1, novoNome);
                                    pstmt.setString(2, cod_componente);
                                    break;
                                case 2:
                                    System.out.print("Digite a nova carga horária: ");
                                    int novaCarga = scan.nextInt();
                                    scan.nextLine();
                                    query = "UPDATE comp_curricular SET carga_horaria_comp=? WHERE cod_componente=?";
                                    pstmt = connection.prepareStatement(query);
                                    pstmt.setInt(1, novaCarga);
                                    pstmt.setString(2, cod_componente);
                                    break;
                                case 3:
                                    System.out.print("O componente é obrigatório? (S/N)");
                                    String resposta = scan.nextLine();
                                    boolean novoObrigatorio = resposta.equalsIgnoreCase("S");
                                    query = "UPDATE comp_curricular SET comp_obrigatorio=? WHERE cod_componente=?";
                                    pstmt = connection.prepareStatement(query);
                                    pstmt.setBoolean(1, novoObrigatorio);
                                    pstmt.setString(2, cod_componente);
                                    break;
                                case 4:
                                    System.out.print("Digite o novo semestre: ");
                                    int novoSemestre = scan.nextInt();
                                    scan.nextLine();
                                    query = "UPDATE comp_curricular SET semestre=? WHERE cod_componente=?";
                                    pstmt = connection.prepareStatement(query);
                                    pstmt.setInt(1, novoSemestre);
                                    pstmt.setString(2, cod_componente);
                                    break;
                            }
                            
                            int rowsAffected = pstmt.executeUpdate();
            
                            if (rowsAffected > 0) {
                                System.out.println("Componente curricular atualizado com sucesso!");
                            } else {
                                System.out.println("Erro ao atualizar componente curricular.");
                            }
        
                            System.out.println("Deseja editar mais alguma informação do componente curricular? (S/N)");
                            String resposta = scan.nextLine();
        
                            if (!resposta.equalsIgnoreCase("S") && !resposta.equalsIgnoreCase("s")) {
                                System.out.println("Componente curricular atualizado com sucesso!");
                                break;
                            }
                        }
                    }
        
                    rs.close();
                    pstmt.close();
                    connection.close();
                    scan.close();
                } 
             catch (Exception e) {
                System.out.println("Erro ao editar componente curricular: " + e.getMessage());
            }
        }
    }*/

    /*public class Main{
        public static void main(String[] args) {
            listarTurmas();
        }
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
                        String horario= rs.getString("horario");
                        int horarioAula = rs.getInt("horario_aula");
                        int semestre = rs.getInt("semestre");
                        
                        Turma turma = new Turma(codTurma, horario, horarioAula, semestre, codComponente, horario);
                        System.out.println(turma);
                    } while (rs.next());
                }
        
                pstmt.close();
                connection.close();
        
            } catch (SQLException e) {
                System.out.println("Erro ao listar turmas: " + e.getMessage());
            }
        }
    }*/

    /*public class Main{
        public static void main(String[] args) {
            excluirTurma("null");
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
        
    }*/