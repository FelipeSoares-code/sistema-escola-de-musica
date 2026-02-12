/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.Estaticos;

import javafx.scene.control.Alert;
import javax.swing.JOptionPane;

/**
 *
 * @author felip
 */
public class Mensagens {
    private Mensagens(){}
    
    public static void ErroBanco(){
        JOptionPane.showMessageDialog(null, "Erro ao conectar banco!",
                    "Erro", JOptionPane.INFORMATION_MESSAGE);
    }
    public static void Ok(){
        JOptionPane.showMessageDialog(null, "Ok",
                    "Ok", JOptionPane.INFORMATION_MESSAGE);
    }
    public static void Msg(String msg, String titulo){
        JOptionPane.showMessageDialog(null, msg,
                    titulo, JOptionPane.INFORMATION_MESSAGE);
    }
    public static void alerta(String texto, String corpo, String titulo){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(titulo);
            alert.setHeaderText(corpo);
            alert.setContentText(texto);
            alert.showAndWait();
    }
}
