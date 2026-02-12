/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.Estaticos;
/**
 *
 * @author felip
 */
public class Tonalidade { //não precisa de tabela no BD
    
    private Tonalidade(){}
    
    private static String[][] notas = {
        {"C", "Cb", "C#"},
        {"D", "Db", "D#"},
        {"E", "Eb", "E#"},
        {"F", "Fb", "F#"},
        {"G", "Gb", "G#"},
        {"A", "Ab", "A#"},
        {"B", "Bb", "B#"}        
    };    
    
    public static String getNota(String nota){
        for(String[] s : notas){
            for(String n : s){
                if(n.equals(nota)){
                    return nota;
                }
            }
        }
        return null;
    }
    public static boolean existeNota(String nota){
        for(String[] s : notas){
            for(String n : s){
                if(n.equals(nota)){
                    return true;
                }
            }
        }
        return false;
    }
}
