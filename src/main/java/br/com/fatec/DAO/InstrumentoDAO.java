/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.DAO;

import br.com.fatec.Entidades.Instrumento;
import br.com.fatec.Estaticos.Banco;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author felip
 */
public class InstrumentoDAO implements DAO<Instrumento>{

    private Instrumento instrumento;
    
    //para conter os comandos DML
    private PreparedStatement pst; //pacote java.sql
    
    //para conter os dados vindos do BD
    private ResultSet rs; //pacote java.sql
    
    @Override
    public boolean insere(Instrumento model) throws SQLException {
        String sql = "INSERT INTO Instrumentos (nome, afinacao) VALUES (?, ?);";

        
        Banco.conectar();

        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);

        //coloca os valores dentro do comando
        //substitui as '?' por dados
        pst.setString(1, model.getNome());
        pst.setString(2, model.getAfinacao());
        


        //executa o comando
        if(pst.executeUpdate() >= 1) { //tudo certo
            Banco.desconectar();
            return true;
        }
        else {
            Banco.desconectar();
            return false;
        }          
    }

    @Override
    public boolean remove(Instrumento model) throws SQLException {
        String sql = "DELETE FROM Instrumentos WHERE id = ?;";
        
        //Abre a conexao
        
        Banco.conectar();        
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //coloca os valores dentro do comando
        //substitui as '?' por dados
        pst.setInt(1, model.getId());
        
        //executa o comando
        if(pst.executeUpdate() >= 1) { //tudo certo
            Banco.desconectar();
            return true;
        }
        else {
            Banco.desconectar();
            return false;
        }    
    }

    @Override
    public boolean altera(Instrumento model) throws SQLException {
           String sql = "UPDATE Instrumentos SET nome = ?, afinacao = ? "
           + "WHERE id = ?;";
        
        //Abre a conexao
       
        Banco.conectar();        
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //coloca os valores dentro do comando
        //substitui as '?' por dados
        pst.setString(1, model.getNome());
        pst.setString(2, model.getAfinacao());
        pst.setInt(3, model.getId());
        
        //executa o comando
        if(pst.executeUpdate() >= 1) { //tudo certo
            Banco.desconectar();
            return true;
        } else {
            Banco.desconectar();
            return false;
        }    
    }

    @Override
    public Instrumento buscaID(Instrumento model) throws SQLException {
        instrumento = null;
        //Comando SELECT
        String sql = "SELECT * FROM Instrumentos WHERE id = ?;";
        
        //conecta ao banco
        Banco.conectar();
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //troca a ?
        pst.setInt(1, model.getId());
        
        //Executa o comando SELECT
        rs = pst.executeQuery();
        
        //le o próximo regitro
        if(rs.next()) { //achou 1 registro
            
            instrumento = new Instrumento();            
            instrumento.setId(rs.getInt("id"));
            instrumento.setNome(rs.getString("nome"));
        }
        
        Banco.desconectar();
        
        return instrumento;       
    }

    @Override
    public Collection<Instrumento> lista(String criterio) throws SQLException {

        Collection<Instrumento> listagem = new ArrayList<>();
        
        instrumento = null;
        //Comando SELECT
        String sql = "SELECT * FROM Instrumentos ";
        //colocar filtro ou nao
        if(criterio.length() != 0) {
            sql += "WHERE " + criterio + ";";
        }
        
        //conecta ao banco
        Banco.conectar();
        
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //Executa o comando SELECT
        rs = pst.executeQuery();
        
        //le o próximo regitro
        while(rs.next()) { //achou 1 registro
            instrumento = new Instrumento();
            instrumento.setId(rs.getInt("id"));
            instrumento.setNome(rs.getString("nome"));
            instrumento.setAfinacao(rs.getString("afinacao"));
            listagem.add(instrumento);
        }
        
        Banco.desconectar();
        
        return listagem;    
    }    
}
