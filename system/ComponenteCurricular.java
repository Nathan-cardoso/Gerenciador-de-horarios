import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    

    public String getCodCompCurricular() {
        return codCompCurricular;
    }


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

    public void editarComponenteCurricular(){

    }
//Na classe executável o usuário vai passar o código do componente curricular
    public static void verDadosDeUmComponenteCurricular(String codComponenteCurricular){
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            Connection connection = ElephantSQLConnection.getConnection(); //Inciando a conexão com o banco.
            String querySearch = "SELECT * FROM comp_curricular WHERE  cod_componente = ?"; //Query para buscar o componente pelo código digitado.
            psmt = connection.prepareStatement(querySearch); //executando a query.
            psmt.setString(1, codComponenteCurricular);//Subistituindo o ? da query pelo conteudo da variável, assim a query vai direto ao codComp
            rs = psmt.executeQuery(); //resultSet representa o resultado da consultado da query em psmt

            if (!rs.next()) /*Se o rs estiver vázia é porque não tem um componente com o código digitado*/{//verificação
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
                    ComponenteCurricular componente = new ComponenteCurricular(codCompCurricular, nomeDisciplina, cargaHorariaComp, componenteObrigatorio, semestre); //Será instânciado um objeto Componente para mostrar o componente
                    System.out.println(codCompCurricular + " - " + nomeDisciplina + " - " + cargaHorariaComp + "h" + "\t " + componente.mostrarTipoComponente()); //Saída.
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

            if (!rs.next()) {
                System.out.println("Não há nenhum componente curricular cadastrado.");
            } else {
                do {
                    String codCompCurricular = rs.getString("cod_componente");
                    String nomeDisciplina = rs.getString("nome_disciplina");
                    int cargaHorariaComp = rs.getInt("carga_horaria_comp");
                    boolean componenteObrigatorio = rs.getBoolean("comp_obrigatorio");
                    int semestre = rs.getInt("semestre");
    
                    ComponenteCurricular componente = new ComponenteCurricular(codCompCurricular, nomeDisciplina, cargaHorariaComp, componenteObrigatorio, semestre);
                    System.out.println(codCompCurricular + " - " + nomeDisciplina + " - " + cargaHorariaComp + "h" + "\t " + componente.mostrarTipoComponente());
                } while (rs.next());
            }
    
            psmt.close();
            connection.close();
    
        }catch(java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    

    public static void excluirComponentesCurriculares(String codCompCurricular){
        PreparedStatement pstmt = null;

        try {
            Connection connection = ElephantSQLConnection.getConnection();
            pstmt = connection.prepareStatement("DELETE from comp_curricular where cod_componente = ?");
            pstmt.setString(1, codCompCurricular);
            int qntLinhas = pstmt.executeUpdate();
    
            if (qntLinhas > 0){
                System.out.println("Componente curricular excluído com sucesso!");
            }
            else {
                System.out.println("Componente curricular não encontrado!");
            }
    
        } catch (SQLException e) {
            System.out.println("Erro ao excluir componente curricular: " + e.getMessage());
        }
    }

    public static ComponenteCurricular buscarComponente(String cod){
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            Connection connection = ElephantSQLConnection.getConnection();
            String query = "SELECT * FROM comp_curricular WHERE cod_componente = ?";
            psmt = connection.prepareStatement(query);
            psmt.setString(1, cod);
             rs = psmt.executeQuery();

             if (rs.next()) {
                // Obter os dados do componente curricular a partir do ResultSet
                String codComponente = rs.getString("cod_componente");
                String nomeDisciplina = rs.getString("nome_disciplina");
                int cargaHorariaComp = rs.getInt("carga_horaria_comp");
                boolean componenteObrigatorio = rs.getBoolean("comp_obrigatorio");
                int semestre = rs.getInt("semestre");
            
                // Criar um objeto ComponenteCurricular com os dados obtidos
                ComponenteCurricular componente = new ComponenteCurricular(codComponente, nomeDisciplina, cargaHorariaComp, componenteObrigatorio, semestre);
            
                rs.close();
                psmt.close();
                connection.close();

                return componente;
                // Faça o que for necessário com o objeto componente
            } else {
                return null;// Componente curricular não encontrado
            }

            

        } catch (SQLException e) {
           System.out.println(e.getMessage());
            return null;
        }
    }

    public static String buscarCodigoComponente(String cod){
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            Connection connection = ElephantSQLConnection.getConnection();
            String query = "SELECT cod_componente FROM comp_curricular WHERE cod_componente = ?";
            psmt = connection.prepareStatement(query);
            psmt.setString(1, cod);
             rs = psmt.executeQuery();

             if (rs.next()) {
                // Obter os dados do componente curricular a partir do ResultSet
                String codComponente = rs.getString("cod_componente");
                       
                rs.close();
                psmt.close();
                connection.close();

                return codComponente; 
            
            
                // Faça o que for necessário com o objeto componente
            } else {

                return null;// Componente curricular não encontrado

            }            

        } catch (SQLException e) {
           System.out.println(e.getMessage());
            return null;
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


    public String toString() {
        return "ComponenteCurricular [codCompCurricular=" + codCompCurricular + ", nomeDisciplina=" + nomeDisciplina
                + ", cargaHorariaComp=" + cargaHorariaComp + ", componente=" + componenteObrigatorio + ", semestre="
                + semestre + "]";
    }

}
