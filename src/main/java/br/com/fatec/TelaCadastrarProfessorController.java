/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec;

import br.com.fatec.DAO.AlunoDAO;
import br.com.fatec.DAO.ProfessorDAO;
import br.com.fatec.Entidades.Aluno;
import br.com.fatec.Entidades.Professor;
import br.com.fatec.Estaticos.Mensagens;
import br.com.fatec.Estaticos.Metodos;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author regin
 */
public class TelaCadastrarProfessorController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
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
    private void btnVoltarAction() throws IOException{
        App.setRoot("telaProfessor");
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txtCpf.setTextFormatter(Metodos.criarMascaraCPF());
    }    
    
    @FXML 
    private void btnAvancarAction()throws IOException, SQLException{ //Verifica se todos os campos estão Preenchidos  
         //Verificar todos os campos
         if (txtNome.getText().isEmpty()|| txtCpf.getText().isEmpty() || 
                 dateNascimento.getValue() == null || txtEmail.getText().isEmpty() || 
                 txtTelefone.getText().isEmpty() || dateContratacao.getValue() == null ||
                 txtEspecialidade.getText().isEmpty())
         {
             //Deu ruim
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro 224");
            alert.setHeaderText("Algum campo não foi preenchido");
            alert.setContentText("Por favor, preencha todos os campos.");
            alert.showAndWait();
             
         }
         else{  
                //Começo da validação do email
                if (!Metodos.validarEmail(txtEmail.getText())) {
                mensagemEmail();
                return;
                }
                Professor professor = new Professor();

                
                professor.setNome(txtNome.getText());
                professor.setCpf(txtCpf.getText());
                professor.setDataNascimento(dateNascimento.getValue());
                professor.setEmail(txtEmail.getText());               
                professor.setTelefone(txtTelefone.getText());
                professor.setDataEfetivacao(dateContratacao.getValue());
                professor.setEspecialidade(txtEspecialidade.getText());
                professor.setAtivo(chkProfessorAtivo.isSelected());
                
                
                ProfessorDAO professorDao = new ProfessorDAO();
                //Mensagens.Msg("criou obj dao");
                try{
                    professorDao.insere(professor);   
                    //Mensagens.Msg("Inseriu no banco");
                } catch (SQLException ex){
                    Mensagens.ErroBanco();
                }
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Gravado com sucesso");
                alert.setHeaderText("Professor Adicionado");
                alert.setContentText("Boa");
                alert.showAndWait();
                
                //Limpa Tudo
                txtNome.clear();
                txtCpf.clear();
                dateNascimento.setValue(null);
                txtEmail.clear();
                txtTelefone.clear();
                dateContratacao.setValue(null);
                chkProfessorAtivo.setSelected(false);
                txtEspecialidade.clear();
                
           // } catch (Exception ex){
                
           // }
         }
    }
    
    
    public void mensagemEmail()throws IOException{  //Mensagem de erro da validação de EMAIL
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro 226");
        alert.setHeaderText("EMAIL INVÁLIDO");
        alert.setContentText("Por favor, digite um EMAIl válido");
        alert.showAndWait();
    }
}
