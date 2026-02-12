/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.Entidades;

import br.com.fatec.Estaticos.Tonalidade;

/**
 *
 * @author felip
 */
public class Instrumento {
    private int id; 
    private String nome;
    private String afinacao;   

    public Instrumento(int id) {
        this.id = id;
    }

    public Instrumento() {
    }
    

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getAfinacao() {
        return afinacao;
    }

    public void setId(int id) {
        this.id = id;
    }    

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setAfinacao(String afinacao) {
        if(Tonalidade.existeNota(afinacao)){
            this.afinacao = afinacao;
        }
    }

    @Override
    public String toString() {
        return nome + " (" + afinacao + ") ";
    }
    
}
