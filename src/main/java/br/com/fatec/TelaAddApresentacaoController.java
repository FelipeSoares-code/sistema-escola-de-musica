/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec;

import br.com.fatec.DAO.TurmaDAO;
import br.com.fatec.Entidades.Apresentacao;
import br.com.fatec.Entidades.Turma;
import br.com.fatec.Estaticos.DataHolder;
import br.com.fatec.Estaticos.Mensagens;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class TelaAddApresentacaoController implements Initializable {
    
    
    //Variaveis
    @FXML
    private TextField txtNomeEvento;
    @FXML
    private DatePicker dateData;
    @FXML
    private ComboBox<String> cmbTurno;
    @FXML 
    private ComboBox<Turma> cmbTurma;
    
    //Criação da Collection
    private Collection<Apresentacao> apresentacoes = new ArrayList<>();
    
    @FXML
    private Button btnAvancar;
    @FXML
    private Button btnVoltar;
    
    @FXML
    private void btnVoltarAction() throws IOException{
        App.setRoot("telaApresentacoes");
    }
    
    
    //Botão Avançar
    @FXML
    private void btnAvancarAction() {   //Prog do botão
        if (txtNomeEvento.getText().isEmpty() ||  //Verifica campos vazios
            dateData.getValue() == (null) || cmbTurma.getValue() == null || 
            cmbTurno.getValue() == null) {
            
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro 224");
            alert.setHeaderText("Algum campo não foi preenchido");
            alert.setContentText("Por favor, preencha todos os campos.");
            alert.showAndWait();
        } else {
                try {
                    String nome = txtNomeEvento.getText();
                    LocalDate data = dateData.getValue();
                    String turno = cmbTurno.getValue();
                    Turma turma = cmbTurma.getValue();

                    Apresentacao apresentacao = new Apresentacao(nome, data, turno, turma);
                    apresentacoes.add(apresentacao); //Adiciona para a Collection
                    DataHolder.Apresentacoes.addAll(apresentacoes);


                    // Limpar os campos depois de salvar
                    txtNomeEvento.clear();
                    dateData.setValue(null);
                    cmbTurno.setValue(null);
                    cmbTurma.setValue(null);
                    //System.out.println("A collection funfou");
                    
                    //Deu certo
                    Alert successAlert = new Alert(AlertType.INFORMATION);
                    successAlert.setTitle("Bom trabalho");
                    successAlert.setHeaderText("Apresentação adicionada");
                    successAlert.setContentText("A apresentação foi adicionada com sucesso.");
                    successAlert.showAndWait();

                }    
                catch (NumberFormatException e) {
                    Mensagens.Msg("Erro", "");
                }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbTurno.getItems().addAll("Manhã", "Tarde", "Noite");//Programação da Combo Box
        addComboTurma();
    }
    
    private void addComboTurma(){
        Collection<Turma> turmas = new ArrayList<>();
        TurmaDAO turmaDao = new TurmaDAO();
        try {
            turmas = turmaDao.lista("");
        } catch (SQLException ex) {
            Mensagens.ErroBanco();
        }
        cmbTurma.getItems().addAll(turmas);
    }

    @FXML
    private void cmbTurma(ActionEvent event) {
    }
}
    
//    public void addApresentacao(variaveis aqui){ 
//        Apresentacao apresentacao = new Apresentacao();
//        //adcionar atributos com setters ao objeto apresentacao
//        apresentacoes.add(apresentacao);
//    }


