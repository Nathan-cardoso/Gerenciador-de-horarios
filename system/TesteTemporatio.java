//Essa classe é para testes do programa, ele é temporaria!

import java.util.Scanner;

public class TesteTemporatio {
    public static void main(String[] args) {
        short opcao;
        Scanner sc = new Scanner(System.in);
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
                System.out.println("Erro!");
                break;
            }
        }while(opcao != 4);


        
        sc.close();
       
    }

    public static void menuPrincipal(){
        System.out.println("\n\tPara consultar as áreas abaixo digite a tecla correspondente");
        System.out.println("[1] - Professor");
        System.out.println("[2] - Componente Curricular");
        System.out.println("[3] - Turma");
        System.out.println("[4] - Sair");
    }


    public static void menuProf() {
        /*Cadastrar Professor
            Editar Professor
            Ver dados de um Professor
            Listar Professores
            Excluir Professor
 */
        System.out.println("\t==Professor==");
        System.out.println("[1] - Cadastrar Professor");
        System.out.println("[2] - Editar Professor");
        System.out.println("[3] - Ver dados de um professor");
        System.out.println("[4] - Listar Professor");
        System.out.println("[5] - Excluir professor");
        System.out.println("[6] - Voltar");
    }

    public static void menuCompCurricular(){
        /*Cadastrar Componente Curricular
        Editar Componente Curricular
        Ver dados de um Componente Curricular
        Listar Componentes Curriculares
        Excluir Componente Curricular
 */
        System.out.println("\t==Componete Curricular==");
        System.out.println("[1] - Cadastrar Componente Curricular");
        System.out.println("[2] - Editar Componente Curricular");
        System.out.println("[3] - Ver dados de um Componente Curricular");
        System.out.println("[4] - Listar Componente Curricular");
        System.out.println("[5] - Excluir Componente Curricular");
        System.out.println("[6] - Voltar");
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
        System.out.println("\t==Turma==");
        System.out.println("[1] - Cadastrar Turma");
        System.out.println("[2] - Editar Turma");
        System.out.println("[3] - Ver dados de um Turma");
        System.out.println("[4] - Listar Turma");
        System.out.println("[5] - Excluir Turma");
        System.out.println("[6] - Voltar");

    }

    public static void subMenuTurma(){
        /*Listar todas as Turmas (em formato de tabela)
        Listar Turmas por semestre (em formato de tabela)
        Listar Turmas por Professor (em formato de tabela) */
    }




}
