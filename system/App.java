//import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        short opcao;
        Scanner sc = new Scanner(System.in);
        //ArrayList<ComponenteCurricular> componenteCurriculara= new ArrayList<>();

        do{
            Menu.menuPrincipal();
            System.out.print("Digite: ");
            opcao = sc.nextShort();

            switch(opcao){
                case 1:
                do{
                    Menu.menuProf();
                    System.out.print("Digite: ");
                    opcao = sc.nextShort();
        
                    switch(opcao){
                        case 1: 
                        sc.nextLine();
                        System.out.println("Digite seu ciap: ");
                        String ciap = sc.nextLine();
                
                        //sc.nextLine();
                
                        System.out.println("Nome:");
                        String nome = sc. nextLine();
                
                        System.out.println("titulo: ");
                        String formacao = sc.nextLine();
                
                
                        System.out.println("email: ");
                        String email = sc.nextLine();
                
                        Professor prof = new Professor(nome, formacao,ciap, email);
                        Professor.cadastrarProfessor(prof);
                        break;
        
                        case 2: 
                        Professor.editarProfessor();
                        break;
        
                        case 3: 
                        sc.nextLine();
                        System.out.println("Digite o ciap do professor: ");
                        String ciapTemp1 = sc.nextLine();
                        Professor.verDadosDeUmProfessor(ciapTemp1);
                        break;

                        case 4: 
                        Professor.listarProfessores();
                        break;
        
                        case 5:
                                
                        short acessoTemp;
                        sc.nextLine();
                        System.out.println("Digite o ciap do professor: ");
                        String ciapTemp = sc.nextLine();
                        System.out.println("\tDeseja realmente exluir o professor?");
                        System.out.println("Confirme a ação\t1 - sim \t outro numero - nao");
                        acessoTemp = sc.nextShort();
                        if(acessoTemp == 1){
                            Professor.excluirProfessor(ciapTemp);
                        }else{
                            System.out.println("Ação cancelada...");
                        }
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
                    Menu.menuCompCurricular();
                    System.out.print("Digite: ");
                    opcao = sc.nextShort();
        
                    switch(opcao){
                        case 1: 
                        short escolhaOptativa;
                        sc.nextLine();
                        System.out.println("Digite o codigo do componente");
                        String codCompCurricular = sc.nextLine();
                       

                        System.out.println("Digite o nome");
                        String nomeDisciplina = sc.nextLine();
                       

                        System.out.println("Digite a carga horaria ");
                        int cargaHorariaComp =sc.nextInt();

                        System.out.println("O componente é obrigatorio?");
                        System.out.println("1 - sim\n2 - nao");
                        escolhaOptativa = sc.nextShort();
                        boolean componenteObrigatorio;
                        if(escolhaOptativa == 1){
                            componenteObrigatorio = true;
                        }else if(escolhaOptativa == 2){
                            componenteObrigatorio = false;
                        }else{
                            System.out.println("Erro de digitação!");
                            return;
                        }
                       
                        System.out.println("Digite o semestre");
                        sc.nextLine();
                        int semestre = sc.nextInt();
                       
                        ComponenteCurricular cc = new ComponenteCurricular(codCompCurricular, nomeDisciplina, cargaHorariaComp, componenteObrigatorio, semestre);
                        cc.cadastrarComponenteCurricular();   
                        break;
                        
                        case 2: 
                        ComponenteCurricular.editarComponenteCurricular();

                        break;
        
                        case 3: 
                        System.out.println("Digite o código do componente: ");
                        sc.nextLine();
                        String codCC1 = sc.nextLine();
                        ComponenteCurricular.verDadosDeUmComponenteCurricular(codCC1);
                        break;

                        case 4: 
                         ComponenteCurricular.listarComponentesCurriculares();
                        break;
        
                        case 5:
                        
                        System.out.println("Digite o código do componente: ");
                        sc.nextLine();
                        String codCC = sc.nextLine();
                        System.out.println("\tDeseja excluir esse componente?\nConfirme a ação\n1 - sim\t outro numero - nao");
                        short confirmacao = sc.nextShort();
                        if(confirmacao == 1){
                            ComponenteCurricular.excluirComponentesCurriculares(codCC);
                        }else{
                            System.out.println("Ação cancelada...");
                        }

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
                    Menu.menuTurma();
                    System.out.print("Digite: ");
                    opcao = sc.nextShort();
        
                    switch(opcao){
                        case 1: 
                        int horario_aula;
                        sc.nextLine();
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
                        break;
        
                        case 2: 
                        System.out.println("ok");
                        break;
        
                        case 3: 
                        sc.nextLine();
                        System.out.println("Digite o codigo da turma: ");
                        String codTurma1 = sc.nextLine();
                        Turma.verDadosDeUmaTurma(codTurma1);
                        break;

                        case 4: 
                        Turma.listarTurmas();
                        break;
        
                        case 5:
                        System.out.println("Digite o código da turma: ");
                        sc.nextLine();
                        String codTurma = sc.nextLine();
                        System.out.println("\tDeseja excluir essa turma?\nConfirme a ação\n1 - sim\t outro número - não");
                        short confirmacao = sc.nextShort();
                        if(confirmacao == 1) {
                            Turma.excluirTurma(codTurma);
                        } else {
                            System.out.println("Ação cancelada...");
                        }

                        break;

                        case 6:
                        break;
                        default:
                        System.out.println();
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

    //Método para ajudar no tratamento de alguns erros
    public static boolean verificacaoDeCodigo(String cod){
        if(cod == null){
            return false;
        }else{
            return true;
        }
    }


}
