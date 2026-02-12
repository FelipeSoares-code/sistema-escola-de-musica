/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 * @author ALUNO
 */
public class TelaTurmasController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private void btnSairAction() throws IOException{
        App.setRoot("telaPrincipal");
    }
    
    @FXML
    private void btnAdicionarAction() throws IOException{
        App.setRoot("telaCadastrarTurma");
    }
    //Tela pesquisar
    @FXML
    private void btnPesquisarAction() throws IOException{
        App.setRoot("telaPesquisarTurma");
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
