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
public class TelaPrincipalController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private void btnAlunosAction() throws IOException{
        App.setRoot("telaAlunos");
    }
    @FXML
    private void btnProfessoresAction() throws IOException{
        App.setRoot("telaProfessor");
    }
    @FXML
    private void btnPartiturasAction() throws IOException{
        App.setRoot("telaPartituras");
    }
    @FXML
    private void btnInstrumentosAction() throws IOException{
        App.setRoot("telaInstrumentos");
    }
    @FXML
    private void btnTurmasAction() throws IOException{
        App.setRoot("telaTurmas");
    }
    @FXML
    private void btnApresentacoesAction() throws IOException{
        App.setRoot("telaApresentacoes");
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
