package connection.mysql;

import conexaoDB.exception.DbException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Rafael Nunes
 */
public class MysqlConnector {

    final private String driver = "com.mysql.jdbc.Driver";
    private Connection conn = null;
    private String servidor;
    private String database;
    private String usuario;
    private String senha;
    private String porta;

    public MysqlConnector() throws Exception {
        setarLoginBanco(); // Na instanciação da Classe ja executa esse metodo;
    }

    private void setarLoginBanco() {
        File file = new File(System.getProperty("user.dir") + "\\rede.txt");

        if (!file.exists()) {
            System.out.println("Arquivo rede.txt inexistente.");
        } else if (file.length() == 0) {
            System.out.println("Arquivo rede.txt vazio.");
        } else {

            String linha;
            try (FileReader fileReader = new FileReader(file);
                    BufferedReader reader = new BufferedReader(fileReader)) {

                while ((linha = reader.readLine()) != null) {

                    String[] split = linha.split(":");

                    if (split[0].equalsIgnoreCase("IP")) {
                        servidor = (linha.split(":")[1]);
                    }
                    if (split[0].equalsIgnoreCase("DB")) {
                        database = (linha.split(":")[1]);
                    }
                    if (split[0].equalsIgnoreCase("USER")) {
                        usuario = (linha.split(":")[1]);
                    }
                    if (split[0].equalsIgnoreCase("KEY")) {
                        senha = (linha.split(":")[1]);
                    }
                    if (split[0].equalsIgnoreCase("PORT")) {
                        porta = (linha.split(":")[1]);
                    }
                }
            } catch (FileNotFoundException e) {
                throw new DbException("Erro! Arquivo rede.txt não encontrado:" + e.getMessage() + "\n\n\nCaminho: " + file.getPath());
            } catch (IOException e) {
                throw new DbException("Erro na leitura do arquivo rede.txt:" + e.getMessage());
            }
        }
    }

    public Connection getConnection() throws Exception {
        if (conn == null) {
            abrirConexao();
        }
        return conn;
    }

    private void abrirConexao() throws Exception {
        try {
            String url = "jdbc:mysql://" + servidor + ":" + porta + "/" + database + "?useUnicode=true&characterEncoding=UTF-8";
            Class.forName(driver);
            conn = DriverManager.getConnection(url, usuario, senha);
            System.out.println("Conexao Mysql efetuada com sucesso!");
        } catch (ClassNotFoundException | SQLException ex) {
            throw new DbException(melhorarMensagemDeErroNaConexao(ex.getMessage()));
        }
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Conexao Mysql fechada com sucesso!");
            } catch (SQLException e) {
                System.out.println("Error! closeConnection " + e.getMessage());
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                System.out.println("Error! closeStatement " + e.getMessage());
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.out.println("Error! closeResultSet " + e.getMessage());
                throw new DbException(e.getMessage());
            }
        }
    }

    private String melhorarMensagemDeErroNaConexao(String msg) {
        if (msg.isEmpty()) {
            return "Não Foi Possivel Conctar ao Servidor\nVerifique a Conecxao: " + msg;
        } else if (msg.contains("Unknown database")) {
            return "Banco de dados " + extrairNomeErro(msg) + " não encontrado!";
        } else {
            return "";
        }
    }

    private String extrairNomeErro(String msgErro) {
        String[] linhas = msgErro.split("Unknown database");
        return linhas[1];
    }
}
