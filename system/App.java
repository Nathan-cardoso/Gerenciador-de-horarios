import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        short opcao;
        Scanner sc = new Scanner(System.in);
        ArrayList<ComponenteCurricular> componenteCurriculara= new ArrayList<>();

        do{
            menuPrincipal();
            System.out.print("Digite: ");
            opcao = sc.nextShort();

            switch(opcao){
                case 1:
                do{
                    menuProf();
                    System.out.print("Digite: ");
                    opcao = sc.nextShort();
        
                    switch(opcao){
                        case 1: 
                        System.out.println("ok");
                        break;
        
                        case 2: 
                        System.out.println("ok");
                        break;
        
                        case 3: 
                        System.out.println("ok");
                        break;

                        case 4: 
                        System.out.println("ok");
                        break;
        
                        case 5:
                        System.out.println("ok");
                        break;

                        case 6:
                        break;
                        default:
                        System.out.println("Erro de digitação");
                        break;
        
                    }
                }while(opcao != 6);
                break;

                case 2:
                do{
                    menuCompCurricular();
                    System.out.print("Digite: ");
                    opcao = sc.nextShort();
        
                    switch(opcao){
                        case 1: 
                        //Teste para usuário 
                        sc.nextLine();
                        System.out.println("Digite o cod");
                        String codCompCurricular = sc.nextLine();
                       

                        System.out.println("Digite o nome");
                        String nomeDisciplina = sc.nextLine();
                       

                        System.out.println("Digite a carga");
                        int cargaHorariaComp =sc.nextInt();
                        ;

                        System.out.println("O componente é obrigatorio");
                        boolean componenteObrigatorio = sc.nextBoolean();
                       
                        System.out.println("Digite o semestre");
                        sc.nextLine();
                        int semestre = sc.nextInt();
                       
                        ComponenteCurricular cc = new ComponenteCurricular(codCompCurricular, nomeDisciplina, cargaHorariaComp, componenteObrigatorio, semestre);
                        cc.cadastrarComponenteCurricular();
                        componenteCurriculara.add(cc);
                            
                        break;
        
                        case 2: 
                        System.out.println("ok");

                        break;
        
                        case 3: 
                        System.out.println("ok");
                        break;

                        case 4: 
                        ComponenteCurricular.listarComponentesCurriculares();
                        break;
        
                        case 5:
                        System.out.println("ok");
                        break;

                        case 6:
                        break;
                        default:
                        System.out.println("Erro de digitação");
                        break;
                    }
                }while(opcao != 6);
                break;

                case 3:
                do{
                    menuTurma();
                    System.out.print("Digite: ");
                    opcao = sc.nextShort();
        
                    switch(opcao){
                        case 1: 
                        System.out.println("ok");
                        break;
        
                        case 2: 
                        System.out.println("ok");
                        break;
        
                        case 3: 
                        System.out.println("ok");
                        break;

                        case 4: 
                        System.out.println("ok");
                        break;
        
                        case 5:
                        System.out.println("ok");
                        break;

                        case 6:
                        break;
                        default:
                        System.out.println("Erro de digitação");
                        break;
                    }
                }while(opcao != 6);
                break;

                case 4:
                System.out.println("Sessão encerrada...");
                break;

                default:
                System.out.println("\tErro! Digite novamente");
                break;
            }
        }while(opcao != 4);

        sc.close();
       
    }

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
        System.out.println("|[4] - Listar Professor\t\t |");
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
        System.out.println("|[4] - Listar Componente Curricular\t\t|");
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
        System.out.println("|[4] - Listar Turma\t\t|");
        System.out.println("|[5] - Excluir Turma\t\t|");
        System.out.println("|[6] - Voltar\t\t\t|\n");

    }

    public static void subMenuTurma(){
        /*Listar todas as Turmas (em formato de tabela)
        Listar Turmas por semestre (em formato de tabela)
        Listar Turmas por Professor (em formato de tabela) */
    }




}
