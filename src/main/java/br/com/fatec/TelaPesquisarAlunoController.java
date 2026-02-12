/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec;

import br.com.fatec.DAO.AlunoDAO;
import br.com.fatec.DAO.InstrumentoDAO;
import br.com.fatec.DAO.InstrumentoEscolaDAO;
import br.com.fatec.DAO.TurmaDAO;
import br.com.fatec.Entidades.Aluno;
import br.com.fatec.Entidades.Instrumento;
import br.com.fatec.Entidades.InstrumentoEscola;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author regin
 */
public class TelaPesquisarAlunoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Aluno cadastroAnterior; //sera salvo os dados por btn pesquisar e usado em alterar
    
    private void limparDados(){
        //limpa dados
        txtNome.clear();
        txtCpf.clear();
        dateNascimento.setValue(null);
        txtEmail.clear();
        txtTelefone.clear();
        dateInscricao.setValue(null);
        chkMenor18.setSelected(false);
        txtNomeResponsavel.clear();
        txtTelefoneResp.clear();
        chkAlunoAtivo.setSelected(false);
        chkInstrumento.setSelected(false);
        cmbInstrumento.setValue(null);
        cmbInstrumentoEscola.setValue(null);
        cmbInstrumentoEscola.setDisable(true);
        cmbTurma.setValue(null);
        txtNomeResponsavel.setDisable(true);
        txtTelefoneResp.setDisable(true);
        
        //desabilita campos
        txtCpf.setDisable(true);
        txtNome.setDisable(true);
        dateNascimento.setDisable(true);
        txtEmail.setDisable(true);
        txtTelefone.setDisable(true);
        dateInscricao.setDisable(true);
        chkMenor18.setDisable(true);
        chkAlunoAtivo.setDisable(true);
        chkInstrumento.setDisable(true);
        cmbInstrumento.setDisable(true);
        cmbTurma.setDisable(true);
    }
    @FXML
    private ComboBox<Instrumento> cmbInstrumento;
    
    @FXML
    private ComboBox<InstrumentoEscola> cmbInstrumentoEscola;
    
    @FXML
    private ComboBox<Turma> cmbTurma;
    
    @FXML    
    private TextField txtNome;
    
    @FXML
    private TextField txtPesquisar;
    
    @FXML    
    private TextField txtCpf;
    
    @FXML    
    private DatePicker dateNascimento;
    
    @FXML    
    private TextField txtEmail;
    
    @FXML    
    private TextField txtTelefone;
    
    @FXML    
    private DatePicker dateInscricao;
    
    @FXML
    private CheckBox chkMenor18;
    
    @FXML    
    private TextField txtNomeResponsavel;
    
    @FXML    
    private TextField txtTelefoneResp;
    
    @FXML
    private CheckBox chkAlunoAtivo;
    
    @FXML
    private CheckBox chkInstrumento;
    
    @FXML 
    private Button btnPesquisar;
    
    @FXML
    private Button btnAlterar;
    
    @FXML
    private Button btnExcluir;
    
    @FXML
    private void btnVoltarAction() throws IOException{
        App.setRoot("telaAlunos");
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        limparDados();
        
        txtCpf.setTextFormatter(Metodos.criarMascaraCPF());
        txtPesquisar.setTextFormatter(Metodos.criarMascaraCPF());
        
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
    @FXML
    //Começo do sofrimento do btn pesquisar
    private void btnPesquisarAction() throws IOException{   
        cadastroAnterior = null;
        //Limpa Tudo
        limparDados();
        
        Aluno alunoAux = new Aluno();
        AlunoDAO alunoDao = new AlunoDAO();            
        alunoAux.setCpf(txtPesquisar.getText());
        
        try{   
            
            Aluno aluno = alunoDao.buscaID(alunoAux);
            cadastroAnterior = alunoDao.buscaID(alunoAux);
            
            adcionarCombos();
            
            if(aluno != null){
                //Ativa os campos TEXT FIELD
                txtNome.setDisable(false);
                dateNascimento.setDisable(false);
                txtEmail.setDisable(false);
                txtTelefone.setDisable(false);
                dateInscricao.setDisable(false);
                chkMenor18.setDisable(false);
                chkAlunoAtivo.setDisable(false);
                chkInstrumento.setDisable(false);   
                cmbTurma.setDisable(false);   
                
                //seta os valores
                txtNome.setText(aluno.getNome());
                txtCpf.setText(aluno.getCpf());
                dateNascimento.setValue(aluno.getDataNascimento());
                txtEmail.setText(aluno.getEmail());
                txtTelefone.setText(aluno.getTelefone());
                dateInscricao.setValue(aluno.getDataInscricao());
                cmbTurma.setValue(aluno.getTurma());
                if(aluno.getNomeResponsavel().equals("")){
                    chkMenor18.setSelected(false);
                } else {
                    txtNomeResponsavel.setDisable(false);
                    txtNomeResponsavel.setText(aluno.getNomeResponsavel());
                    txtTelefoneResp.setDisable(false);
                    txtTelefoneResp.setText(aluno.getTelefoneResponsavel());
                    chkMenor18.setSelected(true);
                }
                chkAlunoAtivo.setSelected(aluno.isAtivo());
                if(aluno.getInstrumentoPratica() != null){
                    cmbInstrumento.setDisable(false);
                    chkInstrumento.setSelected(true);
                    cmbInstrumento.setValue(aluno.getInstrumentoPratica());
                    aluno.setTurma(null);
                    alunoDao.altera(aluno);
                }
                if(aluno.getInstrumentoEmprestado() != null) {
                    cmbInstrumentoEscola.setDisable(false);
                    chkInstrumento.setSelected(false);
                    cmbInstrumentoEscola.setValue(aluno.getInstrumentoEmprestado());
                }
                txtNomeResponsavel.setText(aluno.getNomeResponsavel());  
                
            } 
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Aluno não encontrado");
                alert.setContentText("Por favor, Digite o CPF novamente!");
                alert.showAndWait();
            }
            
        } catch(SQLException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Aluno não encontrado");
            alert.setContentText("Por favor, Digite o CPF novamente!");
            alert.showAndWait();
        }
    }
    
    @FXML
    private void btnAlterarAction() throws IOException{
        AlunoDAO alunoDao = new AlunoDAO();
        try{
            Aluno aluno = new Aluno();
            aluno.setCpf(txtCpf.getText());
            aluno.setNome(txtNome.getText());
            aluno.setDataNascimento(dateNascimento.getValue());
            aluno.setEmail(txtEmail.getText());               
            aluno.setTelefone(txtTelefone.getText());
            aluno.setDataInscricao(dateInscricao.getValue());
            aluno.setNomeResponsavel(txtNomeResponsavel.getText());
            aluno.setTelefoneResponsavel(txtTelefoneResp.getText());
            if(cmbInstrumento.getValue() != null){
                aluno.setInstrumentoPratica(cmbInstrumento.getValue());
            } 
            if(cmbInstrumentoEscola.getValue() != null){
                aluno.setInstrumentoEmprestado(cmbInstrumentoEscola.getValue());
            }
            aluno.setAtivo(chkAlunoAtivo.isSelected());
            aluno.setTurma(cmbTurma.getValue());
            
            //verifica se houve update, se não houve e não teve erro, é pq nenhum dado mudou
            if(alunoDao.altera(aluno)){
                //insere novo instrumento emprestado no banco                
                if(aluno.getInstrumentoEmprestado() != null){
                    Aluno alunoAux = alunoDao.buscaID(aluno);
                    alunoAux.getInstrumentoEmprestado().setAlunoVinculado(alunoAux);
                    alunoAux.getInstrumentoEmprestado().setDisponivel(false);
                    
                    InstrumentoEscolaDAO instrumentoEscolaDAO = new InstrumentoEscolaDAO();
                    InstrumentoEscola instrumentoEmprestado = alunoAux.getInstrumentoEmprestado();
                    instrumentoEscolaDAO.altera(instrumentoEmprestado);
                }
                
                //disponibliza o instrumento do cadastro anterior
                InstrumentoEscolaDAO instrumentoEscolaDAO = new InstrumentoEscolaDAO();
                InstrumentoEscola instrumentoEscola = instrumentoEscolaDAO.buscaID(
                        cadastroAnterior.getInstrumentoEmprestado());
                instrumentoEscola.setAlunoVinculado(null);
                instrumentoEscola.setDisponivel(true);
                instrumentoEscolaDAO.altera(instrumentoEscola);
                
                Mensagens.Msg("Cadastro modificado com sucesso!", "Cadastro Alterado!");
            
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
                dateInscricao.setDisable(true);
                chkMenor18.setDisable(true);
                chkAlunoAtivo.setDisable(true);
                chkInstrumento.setDisable(true);
                txtNomeResponsavel.setDisable(true);
                txtTelefoneResp.setDisable(true);
                cmbInstrumento.setDisable(true);
                cmbInstrumentoEscola.setDisable(true);
                cmbTurma.setDisable(true); 
            }
        } catch(SQLException ex){
            Mensagens.ErroBanco();
        }
    }    
    
    @FXML
    private void btnExcluirAction(){
        Aluno alunoAux = new Aluno();
        AlunoDAO alunoDao = new AlunoDAO();            
        alunoAux.setCpf(txtCpf.getText());
        
        try{
            Aluno aluno = alunoDao.buscaID(alunoAux);
            //verifica se houve remoção
            if(alunoDao.remove(aluno)){
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
    private void chkMenor18Action() throws IOException {
        if (chkMenor18.isSelected()) {
            // Se a CheckBox não estiver selecionada, desabilita os campos
            txtNomeResponsavel.setDisable(false);
            txtTelefoneResp.setDisable(false);
        }
        else {
            // Se a CheckBox  estiver selecionada, habilita os campos
            txtNomeResponsavel.setDisable(true);
            txtTelefoneResp.setDisable(true);
        }
    }
    
    @FXML
    private void chkInstrumentoAction() throws IOException{
        if (chkInstrumento.isSelected()){
            cmbInstrumento.setDisable(false);
            cmbInstrumentoEscola.setDisable(true);
            cmbInstrumentoEscola.setValue(null);
        }
        else{
            cmbInstrumentoEscola.setDisable(false);
        }
    }

    private void adcionarCombos() {
        // Programação das Combo Box
            Collection<Instrumento> instrumentos = new ArrayList<>();
            InstrumentoDAO instrumentoDao = new InstrumentoDAO();
            try{
                instrumentos = instrumentoDao.lista("");
            } catch (SQLException ex){
                Mensagens.ErroBanco();
            }
            cmbInstrumento.getItems().addAll(instrumentos);

            Collection<InstrumentoEscola> instrumentosEscola = new ArrayList<>();
            InstrumentoEscolaDAO instrumentoEscolaDao = new InstrumentoEscolaDAO();
            try{
                instrumentosEscola = instrumentoEscolaDao.lista("");
            } catch (SQLException ex){
                Mensagens.ErroBanco();
            }
            cmbInstrumentoEscola.getItems().addAll(instrumentosEscola);

            Collection<Turma> turmas = new ArrayList<>();
            TurmaDAO turmaDao = new TurmaDAO();
            try{
                turmas = turmaDao.lista("");
            } catch (SQLException ex){
                Mensagens.ErroBanco();
            }
            cmbTurma.getItems().addAll(turmas);
    }
    
    
}
