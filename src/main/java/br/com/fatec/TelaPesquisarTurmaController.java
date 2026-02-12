/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec;

import br.com.fatec.DAO.AlunoDAO;
import br.com.fatec.DAO.InstrumentoDAO;
import br.com.fatec.DAO.ProfessorDAO;
import br.com.fatec.DAO.TurmaDAO;
import br.com.fatec.Entidades.Aluno;
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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author regin
 */
public class TelaPesquisarTurmaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TextField txtPesquisar;
    
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
    private Button btnPesquisar;
    
    @FXML 
    private Button btnAlterar;
    
    @FXML 
    private Button btnExcluir;
    
    private void limparDados(){
            txtNome.clear();
            txtSiglaTurma.clear();
            cmbTurno.setValue(null);
            cmbNivel.setValue(null);
            cmbInstrumento.setValue(null);
            chkTurmaAtiva.setSelected(false);
            chkTeorica.setSelected(false);
            cmbInstrumento.setValue(null);
            cmbProfessor.setValue(null);
            
        }
    
    @FXML
    private void btnVoltarAction() throws IOException{
        App.setRoot("telaTurmas");
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //ComboBox
        addItensCombo();
        
        ObservableList<Integer> itensNivel = FXCollections.observableArrayList(1, 2, 3, 4);
        cmbNivel.setItems(itensNivel);
        
        ObservableList<String> itensTurno = FXCollections.observableArrayList("Manhã", "Tarde", "Noite");
        cmbTurno.setItems(itensTurno);
        
        
        btnPesquisar.setDisable(true);
        txtNome.setDisable(true);
        txtSiglaTurma.setDisable(true);
        cmbTurno.setDisable(true);
        cmbNivel.setDisable(true);
        cmbInstrumento.setDisable(true);
        cmbProfessor.setDisable(true);
        chkTurmaAtiva.setDisable(true);
        chkTeorica.setDisable(true);
        
        txtNome.textProperty().addListener((observable, oldValue, newValue) -> { 
            //Se o txtNome ficar vazio, deixa o botão indisponivel
            try {
                btnAlterar();
                btnExcluir();
            }   
            catch (IOException e) {
                //tomare que o codigo nunca entre nesse erro
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Deu ruim no botão Alterar ou Excluir");
                alert.setContentText("Aperte de novo");
                alert.showAndWait();
            }
        });  
        
        txtPesquisar.textProperty().addListener((observable, oldValue, newValue) -> { 
        //Se o txtPesquisar ficar vazio, deixa o botão indisponivel
            try {
                btnPesquisar();
            } catch (IOException e) {
                //tomare que o codigo nunca entre nesse erro
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Deu ruim no botão pEsquisar");
                alert.setContentText("Aperte de novo");
                alert.showAndWait();
            }
        });
        // Configuração inicial do estado do botão
        btnPesquisar.setDisable(true);
        btnAlterar.setDisable(true);
        btnExcluir.setDisable(true);
    }    
    
    @FXML
    //Botão Disponivel e indisponivel
    private void btnPesquisar() throws IOException{
        if(txtPesquisar.getText().isEmpty()){
            btnPesquisar.setDisable(true);
        }
        else{
            btnPesquisar.setDisable(false);
        }        
    }
    
    @FXML
    private void btnPesquisarAction() throws IOException{   
        //Limpa Tudo
        limparDados();
        
        Turma turmaAux = new Turma(new Professor());
        
        TurmaDAO turmaDao = new TurmaDAO();            
        turmaAux.setSiglaTurma(txtPesquisar.getText());
        
        
        try{   

            Turma turma = turmaDao.buscaSigla(turmaAux);
                 
            if(turma != null){
                //Ativa os campos TEXT FIELD
                txtNome.setDisable(false);
                //txtSiglaTurma.setDisable(false);
                cmbTurno.setDisable(false);
                cmbNivel.setDisable(false);
                cmbInstrumento.setDisable(false);
                cmbProfessor.setDisable(false);
                chkTurmaAtiva.setDisable(false);
                chkTeorica.setDisable(false);
                
                //Insere dados
                txtNome.setText(turma.getNome());
                txtSiglaTurma.setText(turma.getSiglaTurma());
                cmbTurno.setValue(turma.getTurno());
                cmbNivel.setValue(turma.getNivel());
                cmbInstrumento.setValue(turma.getInstrumentoPratica());
                cmbProfessor.setValue(turma.getProfessor());
                chkTurmaAtiva.setSelected(turma.isAtivo());
                chkTeorica.setSelected(turma.isTeorica());
   
            }            
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Turma não encontrada");
                alert.setContentText("Por favor, Digite a Sigla novamente!");
                alert.showAndWait();
            }
        } catch(SQLException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Turma não encontrada");
            alert.setContentText("Por favor, Digite a Sigla novamente!");
            alert.showAndWait();
        }
    }
    
    @FXML
    private void btnAlterar() throws IOException{
        if(txtNome.getText().isEmpty()){
            btnAlterar.setDisable(true);
        }
        else{
            btnAlterar.setDisable(false);
        }
    }
    
    private void btnExcluir() throws IOException{
        if(txtNome.getText().isEmpty()){
            btnExcluir.setDisable(true);
        }
        else{
            btnExcluir.setDisable(false);
        }
    }
    
    @FXML
    private void btnAlterarAction() throws IOException{
        TurmaDAO turmaDao = new TurmaDAO();
        try{
            Turma turma = new Turma(new Professor());
            //professor.setNome(txtNome.getText());
            turma.setNome(txtNome.getText());
            turma.setSiglaTurma(txtSiglaTurma.getText());
            turma.setTurno(cmbTurno.getValue());
            turma.setNivel(cmbNivel.getValue());
            turma.setAtivo(chkTurmaAtiva.isSelected());
            turma.setTeorica(chkTeorica.isSelected());
            turma.setInstrumentoPratica(cmbInstrumento.getValue());
            turma.setProfessor(cmbProfessor.getValue());
            
            
            //verifica se houve update, se não houve e não teve erro, é pq nenhum dado mudou
            if(turmaDao.altera(turma)){
                Mensagens.Msg("Turma modificada com sucesso!", "Turma Alterada!");
            }
                //limpar dados
                limparDados();
                txtPesquisar.clear();
                
                //bloqueia entrada de dados
                txtNome.setDisable(true);
                txtSiglaTurma.setDisable(true);
                cmbTurno.setDisable(true);
                cmbNivel.setDisable(true);
                cmbInstrumento.setDisable(true);
                cmbProfessor.setDisable(true);
                chkTurmaAtiva.setDisable(true);
                chkTeorica.setDisable(true);
                
        } catch(SQLException ex){
            Mensagens.ErroBanco();
        }
    } 
    
    @FXML
    private void btnExcluirAction() throws IOException{
        Turma turmaAux = new Turma(new Professor());
        turmaAux.setSiglaTurma(txtPesquisar.getText());
        TurmaDAO turmaDao = new TurmaDAO();            

        
        try{
            Turma turma = turmaDao.buscaSigla(turmaAux);
            //verifica se houve remoção
            
            if(turmaDao.remove(turma)){
                Mensagens.Msg("Remoção realizada com sucesso!", "Remoção");
            }
            //limpar dados
            limparDados();
            txtPesquisar.clear();
        } catch(SQLException ex){
            Mensagens.ErroBanco();
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
