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
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author ALUNO
 */
public class TelaApresentacoesController implements Initializable {

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
    
     @FXML
    private void btnAdicionarAction() throws IOException{
        App.setRoot("telaAddApresentacao");
    }
    //Tela pesquisar
     @FXML
    private void btnPesquisarAction() throws IOException{
        App.setRoot("telaPesquisarApresentacoes");
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
