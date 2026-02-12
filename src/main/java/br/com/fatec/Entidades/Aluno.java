/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.Entidades;
import br.com.fatec.DAO.InstrumentoEscolaDAO;
import br.com.fatec.Estaticos.Mensagens;
import br.com.fatec.Estaticos.Metodos;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 *
 * @author felip
 */
public class Aluno {
    private int idMatricula;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String email;
    private String telefone;
    private LocalDate dataInscricao;
    private String nomeResponsavel;
    private String telefoneResponsavel;    
    private Instrumento instrumentoPratica;
    private InstrumentoEscola instrumentoEmprestado;
    private boolean ativo;
    private Turma turma; //FK vai puxar a sigla da Tb turmas
    //variavel para padornizar o formato de data

    public Aluno(int idMatricula) {
        this.idMatricula = idMatricula;
        ativo = true;
    }        
    public Aluno(){        
    }
    
    public int getIdMatricula() {
        return idMatricula;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public LocalDate getDataInscricao() {
        return dataInscricao;
    }

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public String getTelefoneResponsavel() {
        return telefoneResponsavel;
    }

    public Instrumento getInstrumentoPratica() {
        return instrumentoPratica;
    }

    public InstrumentoEscola getInstrumentoEmprestado() {
        return instrumentoEmprestado;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public Turma getTurma() {
        return turma;
    }  

    public void setIdMatricula(int idMatricula) {
        this.idMatricula = idMatricula;
    }
    
    public void setNome(String nome) {
        //colocar codigo para verificar se tem numero
        //colocar padrao primeira letra maiúscula e resto minusculo
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setDataNascimento(LocalDate dataNascimento) {        
        //dataNascimento.format(Metodos.formatoDate);
        DateTimeFormatter formato = Metodos.getFormatoData();
        dataNascimento.format(formato);
        this.dataNascimento = dataNascimento;
    }

    public void setEmail(String email) throws IOException {
        if(Metodos.validarEmail(email)){
            this.email = email;
        }
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setDataInscricao(LocalDate dataInscricao) {
       // dataInscricao.format(formato);
       DateTimeFormatter formato = Metodos.getFormatoData();
       dataInscricao.format(formato);
       this.dataInscricao = dataInscricao;       
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    public void setTelefoneResponsavel(String telefoneResponsavel) {
        this.telefoneResponsavel = telefoneResponsavel;
    }

    public void setInstrumentoPratica(Instrumento instrumentoPratica) {
        this.instrumentoPratica = instrumentoPratica;
    }

    public void setInstrumentoEmprestado(InstrumentoEscola instrumentoEmprestado) {
        this.instrumentoEmprestado = instrumentoEmprestado;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }    

    @Override
    public String toString() {
        return nome + " (" + cpf + ")";
    }
    
    
    
}
