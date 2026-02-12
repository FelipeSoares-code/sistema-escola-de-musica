/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author regin
 */
public class TelaPartiturasController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private void btnSairAction() throws IOException{
        App.setRoot("telaPrincipal");
    }
    @FXML
    private void btnAdicionarAction() throws IOException{
        App.setRoot("telaAddPartitura");
    }
    //Tela pesquisar
    @FXML
    private void btnPesquisarAction() throws IOException{
        App.setRoot("telaPesquisarPartituras");
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
