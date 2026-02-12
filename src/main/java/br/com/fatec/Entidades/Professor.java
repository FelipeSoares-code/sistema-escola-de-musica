/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.Entidades;
import br.com.fatec.Estaticos.Metodos;
import java.io.IOException;
import java.time.LocalDate;
/**
 *
 * @author felip
 */
public class Professor {
    private int id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String email;
    private String telefone;
    private LocalDate dataEfetivacao;
    private String especialidade;
    private boolean ativo;

    public Professor() {
        ativo = true;
    }

    public Professor(int id) {
        this.id = id;
        ativo = true;
    }
    
    public int getId() {
        return id;
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

    public LocalDate getDataEfetivacao() {
        return dataEfetivacao;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
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

    public void setDataEfetivacao(LocalDate dataEfetivacao) {
        this.dataEfetivacao = dataEfetivacao;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    
    @Override
    public String toString() {
        return nome;
    }  
    
}
