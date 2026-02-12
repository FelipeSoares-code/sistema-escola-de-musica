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
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author regin
 */
public class TelaAlunosController implements Initializable {

    @FXML
    private Button btnAdicionar;
    @FXML
    private Button btnPesquisar;
    @FXML
    private Button btnSair;

    /**
     * Initializes the controller class.
     */
    @FXML
    private void btnSairAction() throws IOException{
        App.setRoot("telaPrincipal");
    }
    //Tela adicionar
    @FXML
    private void btnAdicionarAction() throws IOException{
        App.setRoot("telaCadastrarAluno");
    }
    //Tela pesquisar
     @FXML
    private void btnPesquisarAction() throws IOException{
        App.setRoot("telaPesquisarAluno");
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
