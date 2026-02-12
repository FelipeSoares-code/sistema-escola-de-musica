/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.Estaticos;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author felip
 */
public class Banco {
    public static String bancoDados, usuario, senha, servidor;
    public static int porta;
    
    //variavel de conexão
    public static java.sql.Connection conexao = null;
    
    static {
        //mySQL e mariaDB
        bancoDados =  "colegio";
        usuario = "root";
        senha = "";
        servidor = "localhost";
        porta = 3306;
        /*
        //sqlServer
        bancoDados = "Loja";
        usuario = "sa";
        senha = "123456";
        servidor = "localhost";
        porta = 1433;
        */

    }
       
    public static void conectar() throws SQLException{
        
        //mySQL workbench
        String url = "jdbc:mysql://" + servidor +
                  ":" + porta + "/" + bancoDados;

        //MariaDB xampp
        //String url = "jdbc:mariadb://" + servidor +     
        //            ":" + porta + "/" + bancoDados;

        //sqlServer
        //String url = "jdbc:sqlserver://" + servidor + 
        //             ":" + porta + ";databaseName=" + bancoDados;

        conexao = DriverManager.getConnection(url, usuario, senha);        
    } 
    
    public static void desconectar()throws SQLException {
        conexao.close();
        
    }
    
    public static java.sql.Connection obterConexao()
        throws SQLException{
            if (conexao == null){
                throw new SQLException("Conexão está fechada..");                  
            }
            else{
                return conexao;
            }
    }
}
