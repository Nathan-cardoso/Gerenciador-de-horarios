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
                System.out.println("Ok");
                break;

                case 3:
                System.out.println("Ok");
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

    }

    public static void menuTurma(){

    }




}
