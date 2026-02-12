/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.Entidades;

/**
 *
 * @author felip
 */
public class Turma {

    private int id;
    private String siglaTurma;    
    private String nome;
    private String turno;
    private int nivel;
    private boolean ativo;
    private boolean teorica;
    private Professor professor;
    private Instrumento instrumentoPratica;

    public Turma(Professor professor) {//precisa de professor para existir turma
        this.professor = professor;
        ativo = true;
    }

    public int getId() {
        return id;
    }

    public String getSiglaTurma() {
        return siglaTurma;
    }

    public String getNome() {
        return nome;
    }

    public String getTurno() {
        return turno;
    }

    public int getNivel() {
        return nivel;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public boolean isTeorica() {
        return teorica;
    }

    public Professor getProfessor() {
        return professor;
    }

    public Instrumento getInstrumentoPratica() {
        return instrumentoPratica;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSiglaTurma(String siglaTurma) {
        this.siglaTurma = siglaTurma;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public void setTeorica(boolean teorica) {
        this.teorica = teorica;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public void setInstrumentoPratica(Instrumento instrumentoPratica) {
        this.instrumentoPratica = instrumentoPratica;
    }

    @Override
    public String toString() {
        return nome + " (" + siglaTurma + ")";
    }

}
