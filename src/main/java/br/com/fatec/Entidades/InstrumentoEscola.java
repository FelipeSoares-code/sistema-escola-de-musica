/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.Entidades;
import br.com.fatec.Estaticos.Metodos;
import java.time.LocalDate;
/**
 *
 * @author felip
 */
//entidade fraca
public class InstrumentoEscola {
    private int id;
    private boolean disponivel;
    private Instrumento instrumento; //precisa estar vinculado a um instrumento para criar objeto
    private String marca;
    private LocalDate dataCompra;
    private Aluno alunoVinculado;
    private LocalDate dataEmprestimo;

    public InstrumentoEscola(Instrumento instrumento,int id) {
        this.id = id;
        this.instrumento = instrumento;
    }

    public InstrumentoEscola(Instrumento instrumento) {
        this.instrumento = instrumento;
    }
    
    public int getId() {
        return id;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public Instrumento getInstrumento() {
        return instrumento;
    }

    public String getMarca() {
        return marca;
    }

    public LocalDate getDataCompra() {
        return dataCompra;
    }

    public Aluno getAlunoVinculado() {
        return alunoVinculado;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setDataCompra(LocalDate dataCompra) {
        dataCompra.format(Metodos.getFormatoData());
        this.dataCompra = dataCompra;
    }

    public void setAlunoVinculado(Aluno alunoVinculado) {
        this.alunoVinculado = alunoVinculado;
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        if(dataEmprestimo != null){
            dataEmprestimo.format(Metodos.getFormatoData());
        }
        this.dataEmprestimo = dataEmprestimo;
    }

    @Override
    public String toString() {
        return instrumento.getNome() + " ("  + marca + ") ";
    }

    
    
}
