/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.DAO;

import br.com.fatec.Entidades.Aluno;
import br.com.fatec.Entidades.Instrumento;
import br.com.fatec.Entidades.InstrumentoEscola;
import br.com.fatec.Entidades.Professor;
import br.com.fatec.Entidades.Turma;
import br.com.fatec.Estaticos.Banco;
import br.com.fatec.Estaticos.Mensagens;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;

/**
 *
 * @author felip
 */
public class AlunoDAO implements DAO<Aluno> {

    private Aluno aluno;

    //para conter os comandos DML
    private PreparedStatement pst; //pacote java.sql

    //para conter os dados vindos do BD
    private ResultSet rs; //pacote java.sql

    @Override
    public boolean insere(Aluno model) throws SQLException {

        String sql = "INSERT INTO alunos (nome, cpf, dataNascimento, email, telefone, "
                + "dataInscricao, nomeResponsavel, telefoneResponsavel, instrumentoPratica, "
                + "instrumentoEmprestado, ativo, turma) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        Banco.conectar();

        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);

        //coloca os valores dentro do comando
        //substitui as '?' por dados
        pst.setString(1, model.getNome());
        pst.setString(2, model.getCpf());
        pst.setDate(3, java.sql.Date.valueOf(model.getDataNascimento()));
        pst.setString(4, model.getEmail());
        pst.setString(5, model.getTelefone());
        pst.setDate(6, java.sql.Date.valueOf(model.getDataInscricao()));
        pst.setString(7, model.getNomeResponsavel());
        pst.setString(8, model.getTelefoneResponsavel());
        pst.setInt(9, model.getInstrumentoPratica().getId());
        if (model.getInstrumentoEmprestado() != null) {
            pst.setInt(10, model.getInstrumentoEmprestado().getId());
        } else {
            pst.setNull(10, java.sql.Types.INTEGER);  // Use setNull para valores nulos
        }

        pst.setBoolean(11, model.isAtivo());

        if (model.getTurma() != null) {
            pst.setInt(12, model.getTurma().getId());
        } else {
            pst.setNull(12, java.sql.Types.INTEGER);  // Use setNull para valores nulos
        }

        //executa o comando
        //esse if ta sendo ignorado
        if (pst.executeUpdate() >= 1) { //tudo certo
            Banco.desconectar();
            return true;
        } else {
            Banco.desconectar();
            return false;
        }
    }

    @Override
    public boolean remove(Aluno model) throws SQLException {
        String sql = "DELETE FROM alunos WHERE cpf = ?;";

        //Abre a conexao
        Banco.conectar();

        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);

        //coloca os valores dentro do comando
        //substitui as '?' por dados
        pst.setString(1, model.getCpf());

        //executa o comando
        if (pst.executeUpdate() >= 1) { //tudo certo
            Banco.desconectar();
            return true;
        } else {
            Mensagens.Msg("erro", "erro");
            Banco.desconectar();
            return false;
        }
    }

    @Override
    public boolean altera(Aluno model) throws SQLException {
        String sql = "UPDATE alunos SET nome = ?, dataNascimento = ?,"
                + " email = ?, telefone = ?, "
                + "dataInscricao = ?, nomeResponsavel = ?, telefoneResponsavel = ?, "
                + "instrumentoPratica = ?, instrumentoEmprestado = ?, ativo = ?, turma = ? "
                + "WHERE cpf = ?;";

        //Abre a conexao
        Banco.conectar();

        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);

        //coloca os valores dentro do comando
        //substitui as '?' por dados
        pst.setString(1, model.getNome());
        pst.setDate(2, java.sql.Date.valueOf(model.getDataNascimento()));
        pst.setString(3, model.getEmail());
        pst.setString(4, model.getTelefone());
        pst.setDate(5, java.sql.Date.valueOf(model.getDataInscricao()));
        pst.setString(6, model.getNomeResponsavel());
        pst.setString(7, model.getTelefoneResponsavel());
        pst.setInt(8, model.getInstrumentoPratica().getId());

        if (model.getInstrumentoEmprestado() != null) {
            pst.setInt(9, model.getInstrumentoEmprestado().getId());
        } else {
            pst.setNull(9, java.sql.Types.INTEGER);  // Use setNull para valores nulos
        }
        pst.setBoolean(10, model.isAtivo());
        if (model.getTurma() != null) {
            pst.setInt(11, model.getTurma().getId());
        } else {
            pst.setNull(11, java.sql.Types.INTEGER);  // Use setNull para valores nulos
        }
        pst.setString(12, model.getCpf());

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
    public Aluno buscaID(Aluno model) throws SQLException {
        aluno = null;
        //Comando SELECT
        String sql = "SELECT * FROM alunos WHERE cpf = ?;";

        //conecta ao banco
        Banco.conectar();

        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);

        //troca a ?
        pst.setString(1, model.getCpf());

        //Executa o comando SELECT
        rs = pst.executeQuery();

        //le o próximo regitro
        if (rs.next()) {
            try {
                //achou 1 registro
                aluno = new Aluno();
                //Coletar o objeto que é usado como atributo de outro objeto
                //tenta puxar int e vem null
                Instrumento instrumentoAux = new Instrumento(rs.getInt("instrumentoPratica"));
                InstrumentoDAO instrumentoDao = new InstrumentoDAO();
                Instrumento instrumento = instrumentoDao.buscaID(instrumentoAux);

                InstrumentoEscola EmprestadoAux = new InstrumentoEscola(instrumento);
                EmprestadoAux.setId(rs.getInt("instrumentoEmprestado"));
                InstrumentoEscolaDAO emprestadoDao = new InstrumentoEscolaDAO();
                InstrumentoEscola instrumentoEmprestado = emprestadoDao.buscaID(EmprestadoAux);

                Turma turmaAux = new Turma(new Professor());
                turmaAux.setId(rs.getInt("turma"));
                TurmaDAO turmaDao = new TurmaDAO();
                Turma turma = turmaDao.buscaID(turmaAux);

                aluno.setIdMatricula(rs.getInt("idMatricula"));
                aluno.setNome(rs.getString("nome"));
                aluno.setCpf(rs.getString("cpf"));
                aluno.setDataNascimento(rs.getDate("dataNascimento").toLocalDate());
                aluno.setEmail(rs.getString("email"));
                aluno.setTelefone(rs.getString("telefone"));
                aluno.setDataInscricao(rs.getDate("dataInscricao").toLocalDate());
                aluno.setNomeResponsavel(rs.getString("nomeResponsavel"));
                aluno.setTelefoneResponsavel(rs.getString("telefoneResponsavel"));
                aluno.setInstrumentoPratica(instrumento);
                if (instrumentoEmprestado != null) {
                    aluno.setInstrumentoEmprestado(instrumentoEmprestado);
                }
                aluno.setAtivo(rs.getBoolean("ativo"));
                aluno.setTurma(turma);
            } catch (IOException ex) {
                Logger.getLogger(AlunoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        Banco.desconectar();

        return aluno;
    }

    @Override
    public Collection<Aluno> lista(String criterio) throws SQLException {
        //criar uma coleção
        Collection<Aluno> listagem = new ArrayList<>();

        aluno = null;
        //Comando SELECT
        String sql = "SELECT * FROM Alunos ";
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
        while (rs.next()) {
            try {
                //achou 1 registro
                aluno = new Aluno();

                Instrumento instrumentoAux = new Instrumento(rs.getInt("instrumentoPratica"));
                InstrumentoDAO instrumentoDao = new InstrumentoDAO();
                Instrumento instrumento = instrumentoDao.buscaID(instrumentoAux);

                InstrumentoEscola EmprestadoAux = new InstrumentoEscola(instrumento);
                EmprestadoAux.setId(rs.getInt("instrumentoEmprestado"));
                InstrumentoEscolaDAO emprestadoDao = new InstrumentoEscolaDAO();
                InstrumentoEscola instrumentoEmprestado = emprestadoDao.buscaID(EmprestadoAux);

                Turma turmaAux = new Turma(new Professor());
                turmaAux.setId(rs.getInt("turma"));
                TurmaDAO turmaDao = new TurmaDAO();
                Turma turma = turmaDao.buscaID(turmaAux);

                aluno.setIdMatricula(rs.getInt("idMatricula"));
                aluno.setNome(rs.getString("nome"));
                aluno.setCpf(rs.getString("cpf"));
                aluno.setDataNascimento(rs.getDate("dataNascimento").toLocalDate());
                aluno.setEmail(rs.getString("email"));
                aluno.setTelefone(rs.getString("telefone"));
                aluno.setDataInscricao(rs.getDate("dataInscricao").toLocalDate());
                aluno.setNomeResponsavel(rs.getString("nomeResponsavel"));
                aluno.setTelefoneResponsavel(rs.getString("telefoneResponsavel"));
                aluno.setInstrumentoPratica(instrumento);
                if (instrumentoEmprestado != null) {
                    aluno.setInstrumentoEmprestado(instrumentoEmprestado);
                }
                aluno.setAtivo(rs.getBoolean("ativo"));
                aluno.setTurma(turma);
                listagem.add(aluno);
            } catch (IOException ex) {
                Logger.getLogger(AlunoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        Banco.desconectar();

        return listagem;
    }

    public Aluno buscaMatricula(Aluno model) throws SQLException {
        aluno = null;
        //Comando SELECT
        String sql = "SELECT * FROM alunos WHERE idMatricula = ?;";

        //conecta ao banco
        Banco.conectar();

        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);

        //troca a ?
        pst.setInt(1, model.getIdMatricula());

        //Executa o comando SELECT
        rs = pst.executeQuery();

        //le o próximo regitro
        if (rs.next()) {
            try {
                //achou 1 registro
                aluno = new Aluno();
                //Coletar o objeto que é usado como atributo de outro objeto
                //tenta puxar int e vem null
                Instrumento instrumentoAux = new Instrumento(rs.getInt("instrumentoPratica"));
                InstrumentoDAO instrumentoDao = new InstrumentoDAO();
                Instrumento instrumento = instrumentoDao.buscaID(instrumentoAux);

                InstrumentoEscola EmprestadoAux = new InstrumentoEscola(instrumento);
                EmprestadoAux.setId(rs.getInt("instrumentoEmprestado"));
                InstrumentoEscolaDAO emprestadoDao = new InstrumentoEscolaDAO();
                InstrumentoEscola instrumentoEmprestado = emprestadoDao.buscaID(EmprestadoAux);

                Turma turmaAux = new Turma(new Professor());
                turmaAux.setId(rs.getInt("turma"));
                TurmaDAO turmaDao = new TurmaDAO();
                Turma turma = turmaDao.buscaID(turmaAux);

                aluno.setIdMatricula(rs.getInt("idMatricula"));
                aluno.setNome(rs.getString("nome"));
                aluno.setCpf(rs.getString("cpf"));
                aluno.setDataNascimento(rs.getDate("dataNascimento").toLocalDate());
                aluno.setEmail(rs.getString("email"));
                aluno.setTelefone(rs.getString("telefone"));
                aluno.setDataInscricao(rs.getDate("dataInscricao").toLocalDate());
                aluno.setNomeResponsavel(rs.getString("nomeResponsavel"));
                aluno.setTelefoneResponsavel(rs.getString("telefoneResponsavel"));
                aluno.setInstrumentoPratica(instrumento);
                if (instrumentoEmprestado != null) {
                    aluno.setInstrumentoEmprestado(instrumentoEmprestado);
                }
                aluno.setAtivo(rs.getBoolean("ativo"));
                aluno.setTurma(turma);
            } catch (IOException ex) {
                Logger.getLogger(AlunoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Banco.desconectar();

        return aluno;
    }
}
