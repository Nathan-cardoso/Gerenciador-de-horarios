import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ComponenteCurricular {
    private String codCompCurricular;
    private String nomeDisciplina;
    private int cargaHorariaComp;
    private boolean componenteObrigatorio;
    private int semestre;


    //Construtor
    public ComponenteCurricular(String codCompCurricular, String nomeDisciplina, int cargaHorariaComp,
            boolean componenteObrigatorio, int semestre) {
        this.codCompCurricular = codCompCurricular;
        this.nomeDisciplina = nomeDisciplina;
        this.cargaHorariaComp = cargaHorariaComp;
        this.componenteObrigatorio = componenteObrigatorio;
        this.semestre = semestre;
    }

    //Criando um get para ser utilizado por professor.
    public int getCargaHorariaComp() {
        return cargaHorariaComp;
    }

    
    //Criando um get para acessar o código com componente futuramente 
    public String getCodCompCurricular() {
        return codCompCurricular;
    }

    //O método realiza o cadastro do professor e armazena no banco de dados.
    public void cadastrarComponenteCurricular(){
        try{

        Connection connection = ElephantSQLConnection.getConnection();
        String query = "INSERT INTO comp_curricular (cod_componente, nome_disciplina, carga_horaria_comp, comp_obrigatorio, semestre) VALUES (?,?,?,?,?)"; //A váriável query é utilizada para guardar o comando a ser passado no PreparedStatement
        PreparedStatement pmst = connection.prepareStatement(query); //PreparedStatement é um sub-classe que permite os comandos do sql.

        pmst.setString(1, this.codCompCurricular); //psmt é variável do PS então ela usa o método set <o tipo do dado a ser inserido> para definir os valores dos campos.
        pmst.setString(2, this.nomeDisciplina);
        pmst.setInt(3, this.cargaHorariaComp);
        pmst.setBoolean(4, this.componenteObrigatorio);
        pmst.setInt(5, this.semestre);
        //Os números antes dos atributos mostram a posição do parâmetro na consulta, posição essa que é referente ao '?' na query acima.
        int qntLinhasInseridas = pmst.executeUpdate(); //executeUpdata executa a query.
        //qntLinharasInseridas é uma variável que identifica se foram inseridos.

        if(qntLinhasInseridas > 0){ //Verificando se foi inserido.

            System.out.println("Componente curricular cadastrado...");

        }else{

            System.out.println("Não foi possivel cadastrar componente curricular");
        }
            pmst.close();
            connection.close();

        }catch(java.sql.SQLException e) {

            System.out.println(e.getMessage());

        }

        //Se tudo ok, o usuário será notificado que o componente foi cadastrado
    }

    //Esse método irá editar o componente curricular com base no código fornecido.
    public static void editarComponenteCurricular() {
        try {
            Scanner scan = new Scanner(System.in);
    
            System.out.println("Digite o código do componente curricular que deseja editar: ");
            String cod_componente = scan.nextLine();
    
            Connection connection = ElephantSQLConnection.getConnection();
            String query = "SELECT * FROM comp_curricular WHERE cod_componente=?"; //Verificação se existe o componente curricular.
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
    
                System.out.println("Componente curricular encontrado:");
                System.out.println("Código: " + codCompCurricular);
                System.out.println("Disciplina: " + nomeDisciplina);
                System.out.println("Carga Horária: " + cargaHorariaComp);
                System.out.println("Componente Obrigatório: " + componenteObrigatorio);
                System.out.println("Semestre: " + semestre);
    
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
                    default:
                        System.out.println("Opção inválida.");
                        
                        break;
                }
    
                int rowsAffected = pstmt.executeUpdate(); // O comando executeUpdate retorna as linhas que foram atualizadas
    
                if (rowsAffected > 0) {//Fazendo a verificação se o compone foi atualizado
                    System.out.println("Componente curricular atualizado com sucesso!");
                }else{
                    System.out.println("Erro na atualização do componente");
                }

            //Fechando as conexões para evitar erros futuros 
            pstmt.close();
            connection.close();
            scan.close();
        }
    } catch (SQLException e) {//Tratamento de exeção 
        System.out.println("Erro ao editar componente curricular: " + e.getMessage());
    }
}

//Na classe executável o usuário vai passar o código do componente curricular e então será mostrado os dados do componente.
    public static void verDadosDeUmComponenteCurricular(String codComponenteCurricular){
        //Iniciando as variáveis
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {

            Connection connection = ElephantSQLConnection.getConnection(); //Inciando a conexão com o banco.
            String querySearch = "SELECT * FROM comp_curricular WHERE  cod_componente = ?"; //Query para buscar o componente pelo código digitado.
            psmt = connection.prepareStatement(querySearch); //executando a query.
            psmt.setString(1, codComponenteCurricular);//Subistituindo o ? da query pelo conteudo da variável, assim a query vai direto ao codComp
            rs = psmt.executeQuery(); //resultSet representa o resultado da consultado da query em psmt

            if (!rs.next()) {//Se o rs estiver vázia é porque não tem um componente com o código digitado
                System.out.println("Esse código do componente curricular é inválido .");
            } else{

                do {
                    //rs possibilita armazenar em variáveis os resultados da query.
                    String codCompCurricular = rs.getString("cod_componente");
                    String nomeDisciplina = rs.getString("nome_disciplina");
                    int cargaHorariaComp = rs.getInt("carga_horaria_comp");
                    boolean componenteObrigatorio = rs.getBoolean("comp_obrigatorio");
                    int semestre = rs.getInt("semestre");
                    //Definida as variáveis que receberão os valores de cada campo...

                    ComponenteCurricular componente = new ComponenteCurricular(codCompCurricular, nomeDisciplina, cargaHorariaComp, componenteObrigatorio, semestre);
                     //Será instânciado um objeto Componente para mostrar o componente
                    System.out.println(codCompCurricular.toUpperCase() + " - " + nomeDisciplina + " - " + cargaHorariaComp + "h" + "\t " + componente.mostrarTipoComponente());
                     //Saída.
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

    public static  void listarComponentesCurriculares(){
        //Esse método segue a mesma lógica do método acima, porém, sem nenhum código para filtrar ele exibe todos os componentes armazenados no banco.
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try{

            Connection connection = ElephantSQLConnection.getConnection(); //Inicia a conexão com o banco de dados
            String query = "SELECT * FROM comp_curricular"; // A variável query está pedindo para mostrar todas as linhas do banco 
            psmt = connection.prepareStatement(query); //PrepararedStatement executa a query
            rs = psmt.executeQuery(); //ResultSet percorre os resultados 

            if (!rs.next()) {//Verifica se tem dados no banco...
                System.out.println("Não há nenhum componente curricular cadastrado.");
            } else {

                do {
                    //A variável rs pode assumir os valores dos cantos, então é criado váriaveis para armazenar esse valores.
                    String codCompCurricular = rs.getString("cod_componente");
                    String nomeDisciplina = rs.getString("nome_disciplina");
                    int cargaHorariaComp = rs.getInt("carga_horaria_comp");
                    boolean componenteObrigatorio = rs.getBoolean("comp_obrigatorio");
                    int semestre = rs.getInt("semestre");
    
                    ComponenteCurricular componente = new ComponenteCurricular(codCompCurricular, nomeDisciplina, cargaHorariaComp, componenteObrigatorio, semestre);

                    System.out.println(componente);
                    //Saída
                } while (rs.next());

            }

            //Fechando as variáveis que se conectam 
            rs.close();
            psmt.close();
            connection.close();
    
        }catch(java.sql.SQLException e) {//Tratamento de exeções

            System.out.println(e.getMessage());
        }
    }
    
//Esse método o usuário ira passar o código do componente que deseja excluir, se o banco tiver o código irá excluir senão apresentará um erro.
    public static void excluirComponentesCurriculares(String codCompCurricular){
        PreparedStatement pstmt = null;

        try {

            Connection connection = ElephantSQLConnection.getConnection();
            String query = "DELETE from comp_curricular where cod_componente = ?";
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, codCompCurricular);
            int qntLinhas = pstmt.executeUpdate(); //ExecuteUpdate retornara as linhas excluídas
    
            if (qntLinhas > 0){ //Verificação se o componente foi excluido.
                System.out.println("Componente curricular excluído com sucesso!");
            }
            else {

                System.out.println("Componente curricular não encontrado!");
            }
    
        } catch (SQLException e) { //Tratamento de exeção.
            System.out.println("Erro ao excluir componente curricular: " + e.getMessage());
        }
    }

  
//Esse método é utilizado para a impressão do componente obrigatório.
    public String mostrarTipoComponente() {
        if (componenteObrigatorio) {
            return "Obrigatório";
        } else {
            return "Optativa";
        }
    }

@Override
    public String toString() { //Configurando o toString com a saída adequada.
        if(componenteObrigatorio){ // Se o componente for obrigátorio o toString apresentará Obrigatório.
        return String.format("Codigo: %-10s | Disciplina: %-10s | Carga Horaria: %-5s | Componente : %s | Semestre : %s",codCompCurricular.toLowerCase(), nomeDisciplina, cargaHorariaComp, mostrarTipoComponente(), semestre);
        }else{ //Senão optativa.
            return String.format("Codigo: %-10s | Disciplina: %-10s | Carga Horaria: %-5s | Componente : %s | Semestre : %s",codCompCurricular.toUpperCase(), nomeDisciplina, cargaHorariaComp, mostrarTipoComponente(), semestre);
        }
    }
}
