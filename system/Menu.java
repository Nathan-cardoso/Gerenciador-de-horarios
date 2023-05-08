public class Menu {
    
    public static void menuPrincipal(){
        System.out.println("\n|============ Menu =============|");
        System.out.println("| [1] - Professor\t\t|");
        System.out.println("| [2] - Componente Curricular\t|");
        System.out.println("| [3] - Turma\t\t\t|");
        System.out.println("| [4] - Sair\t\t\t|");
        System.out.println("---------------------------------");
        System.out.println("Para consultar as áreas digite o número correspondente");

    }


    public static void menuProf() {
        /*Cadastrar Professor
            Editar Professor
            Ver dados de um Professor
            Listar Professores
            Excluir Professor
 */
        System.out.println("\n|========== Professor ===========|");
        System.out.println("|[1] - Cadastrar Professor\t |");
        System.out.println("|[2] - Editar Professor\t\t |");
        System.out.println("|[3] - Ver dados de um professor |");
        System.out.println("|[4] - Listar Professores\t\t |");
        System.out.println("|[5] - Excluir professor\t |");
        System.out.println("|[6] - Voltar\t\t\t |\n");
    }

    public static void menuCompCurricular(){
        /*Cadastrar Componente Curricular
        Editar Componente Curricular
        Ver dados de um Componente Curricular
        Listar Componentes Curriculares
        Excluir Componente Curricular
 */
        System.out.println("\n|=========== Componete Curricular ==============|");
        System.out.println("|[1] - Cadastrar Componente Curricular\t\t|");
        System.out.println("|[2] - Editar Componente Curricular\t\t|");
        System.out.println("|[3] - Ver dados de um Componente Curricular\t|");
        System.out.println("|[4] - Listar Componentes Curriculares\t\t|");
        System.out.println("|[5] - Excluir Componente Curricular\t\t|");
        System.out.println("|[6] - Voltar\t\t\t\t\t|\n");
    }

    public static void menuTurma(){
        /*Cadastrar Turma
        Editar Turma
        Ver dados de uma Turma
        Listar todas as Turmas (em formato de tabela)
        Listar Turmas por semestre (em formato de tabela)
        Listar Turmas por Professor (em formato de tabela)
        Excluir Turma
 */
        System.out.println("\n|=========== Turma =============|");
        System.out.println("|[1] - Cadastrar Turma\t\t|");
        System.out.println("|[2] - Editar Turma\t\t|");
        System.out.println("|[3] - Ver dados de uma Turma\t|");
        System.out.println("|[4] - Listar Turmas\t\t|");
        System.out.println("|[5] - Listar por Semestre\t|");
        System.out.println("|[6] - Listar por Professor\t|");
        System.out.println("|[7] - Excluir Turma\t\t|");
        System.out.println("|[8] - Voltar\t\t\t|\n");

    }

    public static void exibirHorariosManha(){
        System.out.println("1 - 07h00 - 07h55");
        System.out.println("2 - 07h55 - 08h50");
        System.out.println("3 - 08h50 - 09h45");
        System.out.println("4 - 09h55 - 10h50");
        System.out.println("5 - 10h50 - 11h45");
        System.out.println("6 - 11h45 - 12h40");
    }

    public static void exibirHorariosTarde(){
        System.out.println("1 - 13h00 - 13h55");
        System.out.println("2 - 13h55 - 14h50");
        System.out.println("3 - 14h50 - 15h45");
        System.out.println("4 - 15h45 - 16h50");
        System.out.println("5 - 16h50 - 17h45");
        System.out.println("6 - 17h45 - 18h40");
    }

    public static void exibirHorariosNoite(){
        System.out.println("1 - 18h50 - 19h45");
        System.out.println("2 - 19h45 - 20h40");
        System.out.println("3 - 20h50 - 21h35");
        System.out.println("4 - 21h35 - 22h30");
    }


}
