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
import br.com.fatec.Entidades.Professor;
import br.com.fatec.Entidades.Turma;
import br.com.fatec.Estaticos.Banco;
import br.com.fatec.Estaticos.Mensagens;
import br.com.fatec.Estaticos.Metodos;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author regin
 */
public class TelaCadastrarAlunoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ComboBox<Instrumento> cmbInstrumento;

    @FXML
    private ComboBox<InstrumentoEscola> cmbInstrumentoEscola;

    @FXML
    private ComboBox<Turma> cmbTurma;

    @FXML
    private TextField txtNome;

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
    private void btnVoltarAction() throws IOException {
        App.setRoot("telaAlunos");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addItensCombo();

        //desabilita os campos do responsavel
        txtNomeResponsavel.setDisable(true);
        txtTelefoneResp.setDisable(true);

        // Aplica a máscara de CPF
        txtCpf.setTextFormatter(Metodos.criarMascaraCPF());
    }

    @FXML
    private void btnAvancarAction() throws IOException, SQLException { //Verifica se todos os campos estão Preenchidos
        //logica do Responsavel
        boolean condicaoResponsavel;
        if (chkMenor18.isSelected() && (txtNomeResponsavel.getText().isEmpty()
                || txtTelefoneResp.getText().isEmpty())) {
            condicaoResponsavel = true;
        } else {
            condicaoResponsavel = false;
        }

        //Verificar todos os campos
        if (txtNome.getText().isEmpty() || txtCpf.getText().isEmpty()
                || dateNascimento.getValue() == null || txtEmail.getText().isEmpty()
                || txtTelefone.getText().isEmpty() || dateInscricao.getValue() == null
                || cmbInstrumento.getValue() == null
                || (!(chkInstrumento.isSelected()) && cmbInstrumentoEscola.getValue() == null)
                || cmbTurma.getValue() == null || condicaoResponsavel == true) {
            //Deu ruim
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro 224");
            alert.setHeaderText("Algum campo não foi preenchido");
            alert.setContentText("Por favor, preencha todos os campos.");
            alert.showAndWait();

        } else {
            //Começo da validação do email
            if (!Metodos.validarEmail(txtEmail.getText())) {
                mensagemEmail();
                return;
            }
            //Final da validação
            try {
                Aluno aluno = new Aluno();
                aluno.setNome(txtNome.getText());
                aluno.setCpf(txtCpf.getText());
                aluno.setDataNascimento(dateNascimento.getValue());
                aluno.setEmail(txtEmail.getText());
                aluno.setTelefone(txtTelefone.getText());
                aluno.setDataInscricao(dateInscricao.getValue());
                aluno.setNomeResponsavel(txtNomeResponsavel.getText());
                aluno.setTelefoneResponsavel(txtTelefoneResp.getText());
                if (cmbInstrumento.getValue() != null) {
                    aluno.setInstrumentoPratica(cmbInstrumento.getValue());
                } else {
                    //variavel aux para determinar instrumento de pratica
                    Instrumento InstruAux = aluno.getInstrumentoEmprestado().getInstrumento();
                    aluno.setInstrumentoPratica(InstruAux);
                }
                if (cmbInstrumentoEscola.getValue() != null) {
                    aluno.setInstrumentoEmprestado(cmbInstrumentoEscola.getValue());
                }
                aluno.setAtivo(chkAlunoAtivo.isSelected());
                aluno.setTurma(cmbTurma.getValue());

                AlunoDAO alunoDao = new AlunoDAO();
                //verificar se cpf já esta cadastrado
                Collection<Aluno> listaAluno = alunoDao.lista("cpf = '" + aluno.getCpf() + "'"); //add aposfrp
                if(listaAluno.size() >= 1){
                    Mensagens.Msg("CPF já Cadastrado", "Erro");
                    return;
                }
                
                //inserção do aluno no banco
                alunoDao.insere(aluno);
                
                //alteração do instrumento emprestado no banco                
                if(aluno.getInstrumentoEmprestado() != null){
                    Aluno alunoAux = alunoDao.buscaID(aluno);
                    alunoAux.getInstrumentoEmprestado().setAlunoVinculado(alunoAux);
                    alunoAux.getInstrumentoEmprestado().setDisponivel(false);
                    
                    InstrumentoEscolaDAO instrumentoEscolaDAO = new InstrumentoEscolaDAO();
                    InstrumentoEscola instrumentoEmprestado = alunoAux.getInstrumentoEmprestado();
                    instrumentoEscolaDAO.altera(instrumentoEmprestado);
                }
                
                Mensagens.alerta("Aluno adicionado com sucesso!", "", "Sucesso");

                limparDados();
            } catch (SQLException ex) {
                corretorInstrumentosEmprestados();
                Mensagens.ErroBanco();
            }
        }
    }

    @FXML
    private void chkMenor18Action() throws IOException {
        if (chkMenor18.isSelected()) {
            // Se a CheckBox não estiver selecionada, desabilita os campos
            txtNomeResponsavel.setDisable(false);
            txtTelefoneResp.setDisable(false);
        } else {
            // Se a CheckBox  estiver selecionada, habilita os campos
            txtNomeResponsavel.setDisable(true);
            txtTelefoneResp.setDisable(true);
        }
    }

    @FXML
    private void chkInstrumentoAction() throws IOException {
        if (chkInstrumento.isSelected()) {
            cmbInstrumento.setDisable(false);
            cmbInstrumentoEscola.setDisable(true);
            cmbInstrumentoEscola.setValue(null);
        } else {
            cmbInstrumentoEscola.setDisable(false);
        }
    }

    public void mensagemEmail() throws IOException {  //Mensagem de erro da validação de EMAIL
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro 226");
        alert.setHeaderText("EMAIL INVÁLIDO");
        alert.setContentText("Por favor, digite um Email válido");
        alert.showAndWait();
    }

    private void addItensCombo() {
        // Programação das Combo Box
        cmbInstrumento.getItems().clear();
        cmbInstrumentoEscola.getItems().clear();
        cmbTurma.getItems().clear();
        
        Collection<Instrumento> instrumentos = new ArrayList<>();
        InstrumentoDAO instrumentoDao = new InstrumentoDAO();
        try {
            instrumentos = instrumentoDao.lista("");
        } catch (SQLException ex) {
            Mensagens.ErroBanco();
        }
        cmbInstrumento.getItems().addAll(instrumentos);

        Collection<InstrumentoEscola> instrumentosEscola = new ArrayList<>();
        InstrumentoEscolaDAO instrumentoEscolaDao = new InstrumentoEscolaDAO();
        try {
            instrumentosEscola = instrumentoEscolaDao.lista("disponivel = 1");
        } catch (SQLException ex) {
            Mensagens.ErroBanco();
        }
        cmbInstrumentoEscola.getItems().addAll(instrumentosEscola);

        Collection<Turma> turmas = new ArrayList<>();
        TurmaDAO turmaDao = new TurmaDAO();
        try {
            turmas = turmaDao.lista("");
        } catch (SQLException ex) {
            Mensagens.ErroBanco();
        }
        cmbTurma.getItems().addAll(turmas);
    }
    //corrige instrumento indisponivel que não tem aluno vinculado
    private void corretorInstrumentosEmprestados(){ 
        InstrumentoEscolaDAO emprestadoDao = new InstrumentoEscolaDAO();
        InstrumentoEscola emprestado = new InstrumentoEscola(new Instrumento());
        
        try{            
            Collection<InstrumentoEscola> listaEmprestado = emprestadoDao.lista("disponivel = 0");
            for(InstrumentoEscola i : listaEmprestado){
                if(i.getAlunoVinculado() == null){ //se não tem aluno vinculado
                    i.setDisponivel(true);
                    emprestadoDao.altera(i); //testar depois                    
                }
            }
        }catch(SQLException ex){
            Mensagens.Msg("Erro corretor de instrumento escola", "erro");
        }
    }
    private void limparDados(){
        //Limpa Tudo                
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
                cmbInstrumento.setValue(null);
                cmbInstrumentoEscola.setValue(null);
                cmbTurma.setValue(null);
                addItensCombo();
    }
}
