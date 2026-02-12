/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec;

import br.com.fatec.DAO.AlunoDAO;
import br.com.fatec.DAO.InstrumentoDAO;
import br.com.fatec.DAO.InstrumentoEscolaDAO;
import br.com.fatec.DAO.ProfessorDAO;
import br.com.fatec.DAO.TurmaDAO;
import br.com.fatec.Entidades.Aluno;
import br.com.fatec.Entidades.Instrumento;
import br.com.fatec.Entidades.InstrumentoEscola;
import br.com.fatec.Entidades.Professor;
import br.com.fatec.Entidades.Turma;
import br.com.fatec.Estaticos.Mensagens;
import br.com.fatec.Estaticos.Metodos;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author regin
 */
public class TelaPesquisarProfessorController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    private void limparDados(){
            txtNome.clear();
            txtCpf.clear();
            dateNascimento.setValue(null);
            txtEmail.clear();
            txtTelefone.clear();
            dateContratacao.setValue(null);
            chkProfessorAtivo.setSelected(false);
            txtEspecialidade.clear();
        }
    @FXML 
    private TextField txtPesquisar;
    
    @FXML
    private TextField txtNome;
    
    @FXML
    private TextField txtCpf;
    
    @FXML
    private TextField txtEmail;
    
    @FXML
    private TextField txtTelefone;
    
    @FXML
    private TextField txtEspecialidade;
    
    @FXML
    private DatePicker dateNascimento;
    
    @FXML
    private DatePicker dateContratacao;
    
    @FXML
    private CheckBox chkProfessorAtivo;
    
    
    @FXML 
    private Button btnPesquisar;
    
    @FXML 
    private Button btnAlterar;
    
    @FXML 
    private Button btnExcluir;
    
    
    @FXML
    private void btnVoltarAction() throws IOException{
        App.setRoot("telaProfessor");
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btnPesquisar.setDisable(true);
        btnAlterar.setDisable(true);
        btnExcluir.setDisable(true);
        txtCpf.setDisable(true);
        txtNome.setDisable(true);
        dateNascimento.setDisable(true);
        txtEmail.setDisable(true);
        txtTelefone.setDisable(true);
        dateContratacao.setDisable(true);
        chkProfessorAtivo.setDisable(true);
        txtEspecialidade.setDisable(true);
        txtCpf.setTextFormatter(Metodos.criarMascaraCPF());
        txtPesquisar.setTextFormatter(Metodos.criarMascaraCPF());
        
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
        
        Professor professorAux = new Professor();
        
        ProfessorDAO professorDao = new ProfessorDAO();            
        professorAux.setCpf(txtPesquisar.getText());
        
        try{   
            //System.out.println("Erro AQUI 1");
            Professor professor = professorDao.buscaCpf(professorAux);
                 
            if(professor != null){
                //Ativa os campos TEXT FIELD
                txtNome.setDisable(false);
                dateNascimento.setDisable(false);
                txtEmail.setDisable(false);
                txtTelefone.setDisable(false);
                dateContratacao.setDisable(false);
                txtEspecialidade.setDisable(false);
                chkProfessorAtivo.setDisable(false);  
                //Insere dados
                txtNome.setText(professor.getNome());
                txtCpf.setText(professor.getCpf());
                dateNascimento.setValue(professor.getDataNascimento());
                txtEmail.setText(professor.getEmail());
                txtTelefone.setText(professor.getTelefone());
                dateContratacao.setValue(professor.getDataEfetivacao());
                txtEspecialidade.setText(professor.getEspecialidade());    
                chkProfessorAtivo.setSelected(professor.isAtivo());
                
            }            
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Professor não encontrado");
                alert.setContentText("Por favor, Digite o CPF novamente!");
                alert.showAndWait();
            }
        } catch(SQLException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Professor não encontrado");
            alert.setContentText("Por favor, Digite o CPF novamente!");
            alert.showAndWait();
        }
    }
    
    @FXML 
    private void btnAlterarAction() throws IOException, SQLException{
        ProfessorDAO professorDao = new ProfessorDAO();
        try{
            Professor professor = new Professor();
            professor.setCpf(txtCpf.getText());
            professor.setNome(txtNome.getText());
            professor.setDataNascimento(dateNascimento.getValue());
            professor.setEmail(txtEmail.getText());               
            professor.setTelefone(txtTelefone.getText());
            professor.setDataEfetivacao(dateContratacao.getValue());
            professor.setEspecialidade(txtEspecialidade.getText());
            professor.setAtivo(chkProfessorAtivo.isSelected());
            
            //verifica se houve update, se não houve e não teve erro, é pq nenhum dado mudou
            if(professorDao.altera(professor)){
                Mensagens.Msg("Cadastro modificado com sucesso!", "Cadastro Alterado!");
            }
                //limpar dados
                limparDados();
                txtPesquisar.clear();
                
                //bloqueia entrada de dados
                txtNome.setDisable(true);
                txtCpf.setDisable(true);
                txtNome.setDisable(true);
                dateNascimento.setDisable(true);
                txtEmail.setDisable(true);
                txtTelefone.setDisable(true);
                dateContratacao.setDisable(true);
                chkProfessorAtivo.setDisable(true);
                txtEspecialidade.setDisable(true);
 
        } catch(SQLException ex){
            //Mensagens.ErroBanco();
        }
    }
    
    @FXML
    private void btnExcluirAction(){
        Professor professorAux = new Professor();
        ProfessorDAO professorDao = new ProfessorDAO();            
        professorAux.setCpf(txtCpf.getText());
        
        try{
            Professor professor = professorDao.buscaCpf(professorAux);
            //verifica se houve remoção
            if(professorDao.remove(professor)){
                Mensagens.Msg("Remoção realizada com sucesso!", "Remoção");
            }
            //limpar dados
            limparDados();
            txtPesquisar.clear();
        } catch(SQLException ex){
            Mensagens.ErroBanco();
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
    
    @FXML
    private void btnExcluir() throws IOException{
        if(txtNome.getText().isEmpty()){
            btnExcluir.setDisable(true);
        }
        else{
            btnExcluir.setDisable(false);
        }
    }
}