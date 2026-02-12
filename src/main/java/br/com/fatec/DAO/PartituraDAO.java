/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.DAO;

import br.com.fatec.Entidades.Instrumento;
import br.com.fatec.Entidades.Partitura;
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
public class PartituraDAO implements DAO <Partitura> {
     private Partitura partitura;
    
    //para conter os comandos DML
    private PreparedStatement pst; //pacote java.sql
    
    //para conter os dados vindos do BD
    private ResultSet rs; //pacote java.sql
    
    @Override
    public boolean insere(Partitura model) throws SQLException {
        String sql = "INSERT INTO Partituras (nome, tomMaior, "
                   + "instrumento, genero, ano, compositor, formulaCompasso) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?);";

        Banco.conectar();

        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);

        //coloca os valores dentro do comando
        //substitui as '?' por dados
        pst.setString(1, model.getNome());
        pst.setString(2, model.getTom());
        pst.setInt(3, model.getTipoInstrumento().getId()); // Considerando que 'instrumento' é uma referência a Instrumentos
        pst.setString(4, model.getGenero());
        pst.setInt(5, model.getAno());
        pst.setString(6, model.getCompositor());
        pst.setString(7, model.getFormulaCompasso());

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
    public boolean remove(Partitura model) throws SQLException {
        String sql = "DELETE FROM Partituras WHERE id = ?;";

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
    public boolean altera(Partitura model) throws SQLException {
        String sql = "UPDATE Partituras SET nome = ?, tomMaior = ?, "
                    + "instrumento = ?, genero = ?, ano = ?, compositor = ?, "
                    + "formulaCompasso = ? WHERE id = ?;";

        //Abre a conexao
        Banco.conectar();        

        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);

        //coloca os valores dentro do comando
        //substitui as '?' por dados
        pst.setString(1, model.getNome());
        pst.setString(2, model.getTom());
        pst.setInt(3, model.getTipoInstrumento().getId()); 
        pst.setString(4, model.getGenero());
        pst.setInt(5, model.getAno());
        pst.setString(6, model.getCompositor());
        pst.setString(7, model.getFormulaCompasso());
        pst.setInt(8, model.getId());

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
    public Partitura buscaID(Partitura model) throws SQLException {
        partitura = null;
        //Comando SELECT
        String sql = "SELECT * FROM Partituras WHERE id = ?;";

        //conecta ao banco
        Banco.conectar();

        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);

        //troca a ?
        pst.setInt(1, model.getId());

        //Executa o comando SELECT
        rs = pst.executeQuery();

        //le o próximo regitro
        if(rs.next()) {
            partitura = new Partitura();
            
            Instrumento instrumentoAux = new Instrumento(rs.getInt("instrumento"));
            InstrumentoDAO instrumentoDao = new InstrumentoDAO();
            Instrumento instrumento = instrumentoDao.buscaID(instrumentoAux);
            
            partitura.setId(rs.getInt("id"));
            partitura.setNome(rs.getString("nome"));
            partitura.setTom(rs.getString("tomMaior"));
            partitura.setTipoInstrumento(instrumento);
            partitura.setGenero(rs.getString("genero"));
            partitura.setAno(rs.getInt("ano"));
            partitura.setCompositor(rs.getString("compositor"));
            partitura.setFormulaCompasso(rs.getString("formulaCompasso"));
    }
    
    Banco.desconectar();
    
    return partitura; 
    }

    @Override
    public Collection<Partitura> lista(String criterio) throws SQLException {
       Collection<Partitura> listagem = new ArrayList<>();

       partitura = null;
       //Comando SELECT
       String sql = "SELECT * FROM Partituras ";
       //colocar filtro ou nao
       if(criterio.length() != 0) {
           sql += "WHERE " + criterio;
       }

       //conecta ao banco
       Banco.conectar();

       pst = Banco.obterConexao().prepareStatement(sql);

       //Executa o comando SELECT
       rs = pst.executeQuery();

       //le o próximo regitro
       while(rs.next()) { //achou 1 registro
            partitura = new Partitura();
            
            Instrumento instrumentoAux = new Instrumento(rs.getInt("instrumento"));
            InstrumentoDAO instrumentoDao = new InstrumentoDAO();
            Instrumento instrumento = instrumentoDao.buscaID(instrumentoAux);
            
            partitura.setId(rs.getInt("id"));
            partitura.setNome(rs.getString("nome"));
            partitura.setTom(rs.getString("tomMaior"));
            partitura.setTipoInstrumento(instrumento);
            partitura.setGenero(rs.getString("genero"));
            partitura.setAno(rs.getInt("ano"));
            partitura.setCompositor(rs.getString("compositor"));
            partitura.setFormulaCompasso(rs.getString("formulaCompasso"));
            listagem.add(partitura);
       }

       Banco.desconectar();

       return listagem;  
    }
}
