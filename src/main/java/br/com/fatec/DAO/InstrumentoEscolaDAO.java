/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.DAO;

import br.com.fatec.Entidades.Aluno;
import br.com.fatec.Entidades.Instrumento;
import br.com.fatec.Entidades.InstrumentoEscola;
import br.com.fatec.Estaticos.Banco;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author felip
 */
public class InstrumentoEscolaDAO implements DAO<InstrumentoEscola> {
    
    private InstrumentoEscola instrumentoEscola;
    private PreparedStatement pst;
    private ResultSet rs;
    
    @Override
    public boolean insere(InstrumentoEscola model) throws SQLException {
        String sql = "INSERT INTO InstrumentosEscola (instrumento, marca, "
                + "dataCompra, alunoEmprestado, dataEmprestimo, "
                + "disponivel) VALUES (?, ?, ?, ?, ?, ?);";
    
        Banco.conectar();

        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);

        //coloca os valores dentro do comando
        //substitui as '?' por dados
        pst.setInt(1, model.getInstrumento().getId()); 
        pst.setString(2, model.getMarca());
        pst.setDate(3, java.sql.Date.valueOf(model.getDataCompra()));
        if (model.getAlunoVinculado() != null) {
            pst.setInt(4, model.getAlunoVinculado().getIdMatricula());
        } else {
            pst.setNull(4, java.sql.Types.INTEGER);  // Use setNull para valores nulos
        }        
        pst.setNull(5, java.sql.Types.INTEGER);  // Use setNull para valores nulos
        pst.setBoolean(6, model.isDisponivel());

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
    public boolean remove(InstrumentoEscola model) throws SQLException {
        String sql = "DELETE FROM InstrumentosEscola WHERE id = ?;";

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
    public boolean altera(InstrumentoEscola model) throws SQLException {
        String sql = "UPDATE InstrumentosEscola SET instrumento = ?, "
                    + "marca = ?, dataCompra = ?, alunoEmprestado = ?, disponivel = ? "
                    + "WHERE id = ?;";

        //Abre a conexao
        Banco.conectar();        

        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);

        //coloca os valores dentro do comando
        //substitui as '?' por dados
        pst.setInt(1, model.getInstrumento().getId());
        pst.setString(2, model.getMarca());
        pst.setDate(3, java.sql.Date.valueOf(model.getDataCompra()));
        if(model.getAlunoVinculado() != null && model.getAlunoVinculado().getIdMatricula() != 0){
            pst.setInt(4, model.getAlunoVinculado().getIdMatricula()); 
        } else {
            pst.setNull(4, java.sql.Types.INTEGER);
        }
        pst.setBoolean(5, model.isDisponivel());
        pst.setInt(6, model.getId());

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
    public InstrumentoEscola buscaID(InstrumentoEscola model) throws SQLException {
        instrumentoEscola = null;
        //Comando SELECT
        String sql = "SELECT * FROM InstrumentosEscola WHERE id = ?;";

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
            Instrumento instrumentoAux = new Instrumento(rs.getInt("instrumento"));
            InstrumentoDAO instrumentoDao = new InstrumentoDAO();
            Instrumento instrumento = instrumentoDao.buscaID(instrumentoAux);
            
            Aluno alunoAux = new Aluno(rs.getInt("alunoEmprestado"));
            AlunoDAO alunoDao = new AlunoDAO();
            Aluno aluno = alunoDao.buscaID(alunoAux);
            
            instrumentoEscola = new InstrumentoEscola(instrumento);            
            instrumentoEscola.setId(rs.getInt("id"));
            instrumentoEscola.setDisponivel(rs.getBoolean("disponivel"));
            instrumentoEscola.setMarca(rs.getString("marca"));
            instrumentoEscola.setDataCompra(rs.getDate("dataCompra").toLocalDate());
            java.sql.Date dataEmprestimoSql = rs.getDate("dataEmprestimo");
            if (dataEmprestimoSql != null) {
                LocalDate dataEmprestimo = dataEmprestimoSql.toLocalDate();
                instrumentoEscola.setDataEmprestimo(dataEmprestimo);
            } 
            if(aluno != null){
                instrumentoEscola.setAlunoVinculado(aluno); 
            } else {
                instrumentoEscola.setAlunoVinculado(null);
            }     
        }

        Banco.desconectar();

        return instrumentoEscola;  
    }

    @Override
    public Collection<InstrumentoEscola> lista(String criterio) throws SQLException {
        Collection<InstrumentoEscola> listagem = new ArrayList<>();

        instrumentoEscola = null;
        //Comando SELECT
        String sql = "SELECT * FROM InstrumentosEscola ";
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
            Instrumento instrumentoAux = new Instrumento(rs.getInt("instrumento"));
            InstrumentoDAO instrumentoDao = new InstrumentoDAO();
            Instrumento instrumento = instrumentoDao.buscaID(instrumentoAux);
            
            Aluno alunoAux = new Aluno(rs.getInt("alunoEmprestado"));
            AlunoDAO alunoDao = new AlunoDAO();
            Aluno aluno = alunoDao.buscaMatricula(alunoAux);
            
            instrumentoEscola = new InstrumentoEscola(instrumento);            
            instrumentoEscola.setId(rs.getInt("id"));
            instrumentoEscola.setDisponivel(rs.getBoolean("disponivel"));
            instrumentoEscola.setMarca(rs.getString("marca"));
            instrumentoEscola.setDataCompra(rs.getDate("dataCompra").toLocalDate());
            
            java.sql.Date dataEmprestimoSql = rs.getDate("dataEmprestimo");
            if (dataEmprestimoSql != null) {
                LocalDate dataEmprestimo = dataEmprestimoSql.toLocalDate();
                instrumentoEscola.setDataEmprestimo(dataEmprestimo);
            } 
            if(aluno != null){
                instrumentoEscola.setAlunoVinculado(aluno); 
            } else {
                instrumentoEscola.setAlunoVinculado(null);
            }
            listagem.add(instrumentoEscola);
        }
        Banco.desconectar(); 
        
        return listagem;
    }
}
