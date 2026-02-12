/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.DAO;

import br.com.fatec.Entidades.Instrumento;
import br.com.fatec.Entidades.Professor;
import br.com.fatec.Entidades.Turma;
import br.com.fatec.Estaticos.Banco;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author felip
 */
public class TurmaDAO implements DAO<Turma> {

    private Turma turma;
    //para conter os comandos DML
    private PreparedStatement pst; //pacote java.sql

    //para conter os dados vindos do BD
    private ResultSet rs; //pacote java.sql

    @Override
    public boolean insere(Turma model) throws SQLException {
        String sql = "INSERT INTO Turmas (nome, sigla, turno, "
                + "nivel, ativo, teorico, professor, "
                + "instrumento) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

        Banco.conectar();

        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);

        //coloca os valores dentro do comando
        pst.setString(1, model.getNome());
        pst.setString(2, model.getSiglaTurma());
        pst.setString(3, model.getTurno());
        pst.setInt(4, model.getNivel());
        pst.setBoolean(5, model.isAtivo());
        pst.setBoolean(6, model.isTeorica());
        pst.setInt(7, model.getProfessor().getId());
        if (model.isTeorica()) {
            pst.setNull(8, Types.INTEGER);
        } else {
            pst.setInt(8, model.getInstrumentoPratica().getId());
        }

        //executa o comando
        if (pst.executeUpdate() >= 1) { //tudo certo
            Banco.desconectar();
            return true;
        } else {
            Banco.desconectar();
            return false;
        }
    }

    @Override
    public boolean remove(Turma model) throws SQLException {
        String sql = "DELETE FROM Turmas WHERE sigla = ?;";

        //Abre a conexao
        Banco.conectar();

        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);

        //coloca os valores dentro do comando
        pst.setString(1, model.getSiglaTurma());

        //executa o comando
        if (pst.executeUpdate() >= 1) { //tudo certo
            Banco.desconectar();
            return true;
        } else {
            Banco.desconectar();
            return false;
        }
    }

    @Override
    public boolean altera(Turma model) throws SQLException {
        String sql = "UPDATE Turmas SET nome = ?, sigla = ?,"
                + " turno = ?, nivel = ?, ativo = ?, teorico = ?,"
                + " professor = ?, instrumento = ? WHERE sigla = ?;";

        //Abre a conexao
        Banco.conectar();

        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);

        //coloca os valores dentro do comando
        pst.setString(1, model.getNome());
        pst.setString(2, model.getSiglaTurma());
        pst.setString(3, model.getTurno());
        pst.setInt(4, model.getNivel());
        pst.setBoolean(5, model.isAtivo());
        pst.setBoolean(6, model.isTeorica());
        pst.setInt(7, model.getProfessor().getId());
        if (model.isTeorica()) {
            pst.setNull(8, Types.INTEGER);
        } else {
            pst.setInt(8, model.getInstrumentoPratica().getId());
        }
        pst.setString(9, model.getSiglaTurma());

        //executa o comando
        if (pst.executeUpdate() >= 1) { //tudo certo
            Banco.desconectar();
            return true;
        } else {
            Banco.desconectar();
            return false;
        }
    }

    @Override
    public Turma buscaID(Turma model) throws SQLException {
        turma = null;
        //Comando SELECT
        String sql = "SELECT * FROM Turmas WHERE id = ?;";

        //conecta ao banco
        Banco.conectar();

        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);

        //troca a ?
        pst.setInt(1, model.getId());
        //pst.setString(1, model.getSiglaTurma());

        //Executa o comando SELECT
        rs = pst.executeQuery();

        //le o próximo regitro
        if (rs.next()) {
            Professor professorAux = new Professor(rs.getInt("professor"));
            ProfessorDAO professorDao = new ProfessorDAO();
            Professor professor = professorDao.buscaID(professorAux);

            Instrumento instrumentoAux = new Instrumento(rs.getInt("instrumento"));
            InstrumentoDAO instrumentoDao = new InstrumentoDAO();
            Instrumento instrumento = instrumentoDao.buscaID(instrumentoAux);

            turma = new Turma(professor);
            turma.setId(rs.getInt("id"));
            turma.setNome(rs.getString("nome"));
            turma.setSiglaTurma(rs.getString("sigla"));
            turma.setTurno(rs.getString("turno"));
            turma.setNivel(rs.getInt("nivel"));
            turma.setAtivo(rs.getBoolean("ativo"));
            turma.setTeorica(rs.getBoolean("teorico"));
            turma.setProfessor(professor);
            turma.setInstrumentoPratica(instrumento);
        }

        Banco.desconectar();

        return turma;
    }

    @Override
    public Collection<Turma> lista(String criterio) throws SQLException {
        Collection<Turma> listagem = new ArrayList<>();

        turma = null;
        //Comando SELECT
        String sql = "SELECT * FROM Turmas ";
        //colocar filtro ou nao
        if (criterio.length() != 0) {
            sql += "WHERE " + criterio;
        }

        //conecta ao banco
        Banco.conectar();

        pst = Banco.obterConexao().prepareStatement(sql);

        //Executa o comando SELECT
        rs = pst.executeQuery();

        //le o próximo regitro
        while (rs.next()) { //achou 1 registro
            Professor professorAux = new Professor(rs.getInt("professor"));
            ProfessorDAO professorDao = new ProfessorDAO();
            Professor professor = professorDao.buscaID(professorAux);

            Instrumento instrumentoAux = new Instrumento(rs.getInt("instrumento"));
            InstrumentoDAO instrumentoDao = new InstrumentoDAO();
            Instrumento instrumento = instrumentoDao.buscaID(instrumentoAux);

            turma = new Turma(professor);
            turma.setId(rs.getInt("id"));
            turma.setNome(rs.getString("nome"));
            turma.setSiglaTurma(rs.getString("sigla"));
            turma.setTurno(rs.getString("turno"));
            turma.setNivel(rs.getInt("nivel"));
            turma.setAtivo(rs.getBoolean("ativo"));
            turma.setTeorica(rs.getBoolean("teorico"));
            turma.setProfessor(professor);
            turma.setInstrumentoPratica(instrumento);
            listagem.add(turma);
        }

        Banco.desconectar();

        return listagem;
    }
    
    public Turma buscaSigla(Turma model) throws SQLException {
        turma = null;
        //Comando SELECT
        String sql = "SELECT * FROM Turmas WHERE sigla = ?;";

        //conecta ao banco
        Banco.conectar();

        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);

        //troca a ?
        pst.setString(1, model.getSiglaTurma());

        //Executa o comando SELECT
        rs = pst.executeQuery();

        //le o próximo regitro
        if (rs.next()) {
            Professor professorAux = new Professor(rs.getInt("professor"));
            ProfessorDAO professorDao = new ProfessorDAO();
            Professor professor = professorDao.buscaID(professorAux);

            Instrumento instrumentoAux = new Instrumento(rs.getInt("instrumento"));
            InstrumentoDAO instrumentoDao = new InstrumentoDAO();
            Instrumento instrumento = instrumentoDao.buscaID(instrumentoAux);

            turma = new Turma(professor);
            turma.setId(rs.getInt("id"));
            turma.setNome(rs.getString("nome"));
            turma.setSiglaTurma(rs.getString("sigla"));
            turma.setTurno(rs.getString("turno"));
            turma.setNivel(rs.getInt("nivel"));
            turma.setAtivo(rs.getBoolean("ativo"));
            turma.setTeorica(rs.getBoolean("teorico"));
            turma.setProfessor(professor);
            turma.setInstrumentoPratica(instrumento);
        }

        Banco.desconectar();

        return turma;
    }
    
}
