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
public class Partitura {
    private int id;
    private String nome;
    private String tom;
    private Instrumento tipoInstrumento;
    private String genero;
    private int ano;
    private String compositor;
    private String formulaCompasso;

    public Partitura() {
    }

    public Partitura(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTom() {
        return tom;
    }

    public Instrumento getTipoInstrumento() {
        return tipoInstrumento;
    }

    public String getGenero() {
        return genero;
    }

    public int getAno() {
        return ano;
    }

    public String getCompositor() {
        return compositor;
    }

    public String getFormulaCompasso() {
        return formulaCompasso;
    }

    public void setId(int id) {
            this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTom(String tom) {
        if(Tonalidade.existeNota(tom)){
            this.tom = tom;
        }
    }

    public void setTipoInstrumento(Instrumento tipoInstrumento) {
        this.tipoInstrumento = tipoInstrumento;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public void setCompositor(String compositor) {
        this.compositor = compositor;
    }

    public void setFormulaCompasso(String formulaCompasso) {
        this.formulaCompasso = formulaCompasso;
    }
    
   
}
