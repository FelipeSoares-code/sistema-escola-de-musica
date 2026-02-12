/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.DAO;

import br.com.fatec.Entidades.Professor;
import br.com.fatec.Estaticos.Banco;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author felip
 */
public class ProfessorDAO implements DAO <Professor> {

    private Professor professor;
     //para conter os comandos DML
    private PreparedStatement pst; //pacote java.sql
    
    //para conter os dados vindos do BD
    private ResultSet rs; //pacote java.sql
    
    @Override
    public boolean insere(Professor model) throws SQLException {
        String sql = "INSERT INTO Professores (nome, cpf, "
                + "dataNascimento, email, telefone, dataEfetivacao, "
                + "especialidade, ativo) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

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
        pst.setDate(6, java.sql.Date.valueOf(model.getDataEfetivacao()));
        pst.setString(7, model.getEspecialidade());
        pst.setBoolean(8, model.isAtivo());

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
    public boolean remove(Professor model) throws SQLException {
        String sql = "DELETE FROM Professores WHERE cpf = ?;";

        //Abre a conexao
        Banco.conectar();        

        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);

        //coloca os valores dentro do comando
        //substitui as '?' por dados
        pst.setString(1, model.getCpf());

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
    public boolean altera(Professor model) throws SQLException {
        String sql = "UPDATE Professores SET nome = ?, dataNascimento = ?, "
                    + "email = ?, telefone = ?, dataEfetivacao = ?, especialidade = ?, "
                    + "ativo = ? " + "WHERE cpf = ?;";
  
        //Abre a conexao
        Banco.conectar();        

        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);

        //coloca os valores dentro do comando
        //substitui as '?' por dados
        pst.setString(1, model.getNome());
        //
        pst.setDate(2, java.sql.Date.valueOf(model.getDataNascimento()));
        pst.setString(3, model.getEmail());
        pst.setString(4, model.getTelefone());
        pst.setDate(5, java.sql.Date.valueOf(model.getDataEfetivacao()));
        pst.setString(6, model.getEspecialidade());
        pst.setBoolean(7, model.isAtivo());
        pst.setString(8, model.getCpf());

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
    public Professor buscaID(Professor model) throws SQLException {
       professor = null;
       //Comando SELECT
       String sql = "SELECT * FROM Professores WHERE id = ?;";

       //conecta ao banco
       Banco.conectar();

       //cria o comando preparado
       pst = Banco.obterConexao().prepareStatement(sql);

       //troca a ?
       pst.setInt(1, model.getId());

       //Executa o comando SELECT
       rs = pst.executeQuery();

       //le o próximo regitro
       if(rs.next()) { try {
           //achou 1 registro
           professor = new Professor();
           professor.setId(rs.getInt("id"));
           professor.setNome(rs.getString("nome"));
           professor.setCpf(rs.getString("cpf"));
           professor.setDataNascimento(rs.getDate("dataNascimento").toLocalDate());
           professor.setEmail(rs.getString("email"));
           professor.setTelefone(rs.getString("telefone"));
           professor.setDataEfetivacao(rs.getDate("dataEfetivacao").toLocalDate());
           professor.setEspecialidade(rs.getString("especialidade"));
           professor.setAtivo(rs.getBoolean("ativo"));
           } catch (IOException ex) {
               Logger.getLogger(ProfessorDAO.class.getName()).log(Level.SEVERE, null, ex);
           }
       }

       Banco.desconectar();

       return professor;    
    }
    
    public Professor buscaCpf(Professor model) throws SQLException {
       professor = null;
       //Comando SELECT
       String sql = "SELECT * FROM Professores WHERE cpf = ?;";

       //conecta ao banco
       Banco.conectar();

       //cria o comando preparado
       pst = Banco.obterConexao().prepareStatement(sql);

       //troca a ?
       pst.setString(1, model.getCpf());

       //Executa o comando SELECT
       rs = pst.executeQuery();

       //le o próximo regitro
       if(rs.next()) { try {
           //achou 1 registro
           professor = new Professor();
           professor.setId(rs.getInt("id"));
           professor.setNome(rs.getString("nome"));
           professor.setCpf(rs.getString("cpf"));
           professor.setDataNascimento(rs.getDate("dataNascimento").toLocalDate());
           professor.setEmail(rs.getString("email"));
           professor.setTelefone(rs.getString("telefone"));
           professor.setDataEfetivacao(rs.getDate("dataEfetivacao").toLocalDate());
           professor.setEspecialidade(rs.getString("especialidade"));
           professor.setAtivo(rs.getBoolean("ativo"));
           } catch (IOException ex) {
               Logger.getLogger(ProfessorDAO.class.getName()).log(Level.SEVERE, null, ex);
           }
       }

       Banco.desconectar();

       return professor;    
    }

    @Override
    public Collection<Professor> lista(String criterio) throws SQLException {
        Collection<Professor> listagem = new ArrayList<>();

       professor = null;
       //Comando SELECT
       String sql = "SELECT * FROM Professores ";
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
       while(rs.next()) { try {
           //achou 1 registro
           professor = new Professor();
           professor.setId(rs.getInt("id"));
           professor.setNome(rs.getString("nome"));
           professor.setCpf(rs.getString("cpf"));
           professor.setDataNascimento(rs.getDate("dataNascimento").toLocalDate());
           professor.setEmail(rs.getString("email"));
           professor.setTelefone(rs.getString("telefone"));
           professor.setDataEfetivacao(rs.getDate("dataEfetivacao").toLocalDate());
           professor.setEspecialidade(rs.getString("especialidade"));
           professor.setAtivo(rs.getBoolean("ativo"));
           listagem.add(professor);
            } catch (IOException ex) {
                Logger.getLogger(ProfessorDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
       }

       Banco.desconectar();

       return listagem;   
    }
    
}
