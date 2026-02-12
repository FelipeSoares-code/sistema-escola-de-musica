/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec;

import br.com.fatec.DAO.InstrumentoDAO;
import br.com.fatec.DAO.ProfessorDAO;
import br.com.fatec.DAO.TurmaDAO;
import br.com.fatec.Entidades.Instrumento;
import br.com.fatec.Entidades.Professor;
import br.com.fatec.Entidades.Turma;
import br.com.fatec.Estaticos.Mensagens;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author regin
 */
public class TelaCadastrarTurmaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TextField txtNome;
    
    @FXML
    private TextField txtSiglaTurma;
    
    @FXML
    private ComboBox<String> cmbTurno;
    
    @FXML
    private ComboBox<Integer> cmbNivel;
    
    @FXML
    private ComboBox<Instrumento> cmbInstrumento;
    
    @FXML
    private ComboBox<Professor> cmbProfessor;
    
    @FXML
    private CheckBox chkTeorica;
    
    @FXML
    private CheckBox chkTurmaAtiva;
    
    @FXML
    private void btnVoltarAction() throws IOException{
        App.setRoot("telaTurmas");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        addItensCombo();//chama as ComboBox
        //Itens a Combo Nivel
        
        ObservableList<Integer> itensNivel = FXCollections.observableArrayList(1, 2, 3, 4);
        cmbNivel.setItems(itensNivel);
        
        ObservableList<String> itensTurno = FXCollections.observableArrayList("Manhã", "Tarde", "Noite");
        cmbTurno.setItems(itensTurno);
    }
    @FXML
    private void btnAvancarAction() throws IOException, SQLException { //Verifica se todos os campos estão Preenchidos
        if (txtNome.getText().isEmpty()|| txtSiglaTurma.getText().isEmpty() || 
            cmbTurno.getValue() == null || cmbNivel.getValue() == null ||
            cmbInstrumento.getValue() == null)
        {
             //Deu ruim
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro 224");
            alert.setHeaderText("Algum campo não foi preenchido");
            alert.setContentText("Por favor, preencha todos os campos.");
            alert.showAndWait();
             
        }  
        else{
            Turma turma = new Turma(new Professor());
            turma.setNome(txtNome.getText());
            turma.setSiglaTurma(txtSiglaTurma.getText());
            turma.setTurno(cmbTurno.getValue());
            turma.setNivel(cmbNivel.getValue());
            turma.setAtivo(chkTurmaAtiva.isSelected());
            turma.setTeorica(chkTeorica.isSelected());
            turma.setInstrumentoPratica(cmbInstrumento.getValue());
            turma.setProfessor(cmbProfessor.getValue());
            
            TurmaDAO turmaDao = new TurmaDAO();
                //Mensagens.Msg("criou obj dao");
                try{
                    turmaDao.insere(turma);   
                    //Mensagens.Msg("Inseriu no banco");
                } catch (SQLException ex){
                    Mensagens.ErroBanco();
                }
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Gravado com sucesso");
                alert.setHeaderText("Turma Adicionada");
                alert.setContentText("Boa");
                alert.showAndWait();
                
                //Limpa Tudo   
                txtNome.clear();
                txtSiglaTurma.clear();
                cmbTurno.setValue(null);
                cmbNivel.setValue(null);
                chkTurmaAtiva.setSelected(false);
                chkTeorica.setSelected(false);
                cmbInstrumento.setValue(null);
                cmbProfessor.setValue(null);
                
                
        }
    }
    private void addItensCombo(){
        cmbInstrumento.getItems().clear();
        cmbProfessor.getItems().clear();
        
        Collection<Instrumento> instrumentos = new ArrayList<>();
        InstrumentoDAO instrumentoDao = new InstrumentoDAO();
        try {
            instrumentos = instrumentoDao.lista("");
        } catch (SQLException ex) {
            Mensagens.ErroBanco();
        }
        cmbInstrumento.getItems().addAll(instrumentos);
        
        //ComoBox Professores
        Collection<Professor> professor = new ArrayList<>();
        ProfessorDAO ProfessorDao = new ProfessorDAO();
        try {
            professor = ProfessorDao.lista("");
        } catch (SQLException ex) {
            Mensagens.ErroBanco();
        }
        cmbProfessor.getItems().addAll(professor);
    }
    
    
    
}
