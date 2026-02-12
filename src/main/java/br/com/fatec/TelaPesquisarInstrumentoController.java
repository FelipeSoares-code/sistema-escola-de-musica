/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec;

import br.com.fatec.DAO.AlunoDAO;
import br.com.fatec.DAO.InstrumentoEscolaDAO;
import br.com.fatec.Entidades.Aluno;
import br.com.fatec.Entidades.Instrumento;
import br.com.fatec.Entidades.InstrumentoEscola;
import br.com.fatec.Estaticos.Mensagens;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author regin
 */
public class TelaPesquisarInstrumentoController implements Initializable {

    @FXML
    private Button btnAvancar;
    @FXML
    private Button btnVoltar;
    @FXML
    private TableView<InstrumentoEscola> dgvInstrumentos;
    @FXML
    private TableColumn<InstrumentoEscola, Integer> colunaId;
    @FXML
    private TableColumn<InstrumentoEscola, String> colunaInstrumento;
    @FXML
    private TableColumn<InstrumentoEscola, String> colunaMarca;
    @FXML
    private TableColumn<InstrumentoEscola, String> colunaAluno;

    /**
     * Initializes the controller class.
     */
    @FXML
    private void btnVoltarAction() throws IOException {
        App.setRoot("telaInstrumentos");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Puxar dados do banco
            InstrumentoEscolaDAO instrumentoDao = new InstrumentoEscolaDAO();
            Collection<InstrumentoEscola> instrumentosEscola = instrumentoDao.lista("");
            ObservableList<InstrumentoEscola> instrumentosTabela = FXCollections.observableArrayList();
            instrumentosTabela.addAll(instrumentosEscola);
            AlunoDAO alunoDao = new AlunoDAO();

            //procura instrumentos da escola sem aluno vinculado
            //os que não tem aluno vinculado, é criado um novo aluno
            //esse novo aluno sempre tera o nome "-", para aparecer assim na tabela
            int num = 1;
            for (InstrumentoEscola i : instrumentosEscola) {
                if (i.getAlunoVinculado() == null) {
                    i.setAlunoVinculado(new Aluno(num));
                    i.getAlunoVinculado().setNome("-");
                    num++;
                } else {
                    try {
                        Aluno alunoAux = i.getAlunoVinculado();
                        Aluno aluno = alunoDao.buscaMatricula(alunoAux);
                        i.setAlunoVinculado(aluno);
                    } catch (SQLException ex) {
                        Mensagens.ErroBanco();
                    }
                }
            }

            // Configurar colunas
            colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colunaInstrumento.setCellValueFactory((TableColumn.CellDataFeatures<InstrumentoEscola, String> param) -> {
                Instrumento instrumento = param.getValue().getInstrumento();
                return new SimpleStringProperty(instrumento.getNome());
            });
            colunaMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
            colunaAluno.setCellValueFactory((TableColumn.CellDataFeatures<InstrumentoEscola, String> param) -> {
                Aluno aluno = param.getValue().getAlunoVinculado();
                if(aluno == null){
                    aluno = new Aluno();
                    aluno.setNome("-");
                }
                return new SimpleStringProperty(aluno.getNome());
            });

            // Definir itens da tabela
            dgvInstrumentos.setItems(instrumentosTabela);

            addBtnAcoes();

        } catch (SQLException ex) {
            Mensagens.ErroBanco();
        }
    }

    private void addBtnAcoes() {
        TableColumn<InstrumentoEscola, Void> colBtn = new TableColumn<>("Ações");

        Callback<TableColumn<InstrumentoEscola, Void>, TableCell<InstrumentoEscola, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<InstrumentoEscola, Void> call(final TableColumn<InstrumentoEscola, Void> param) {
                final TableCell<InstrumentoEscola, Void> cell = new TableCell<>() {

                    private final Button btnDeletar = new Button("Deletar");
                    private final Button btnDisponibilizar = new Button("Disponibilizar");

                    {
                        btnDeletar.setOnAction(event -> {
                            InstrumentoEscola instrumentoEscola = getTableView().getItems().get(getIndex());
                            desejaDeletar(instrumentoEscola);
                        });

                        btnDisponibilizar.setOnAction(event -> {
                            InstrumentoEscola instrumentoEscola = getTableView().getItems().get(getIndex());
                            DesejaDisponibilizar(instrumentoEscola);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox actionButtons = new HBox(btnDeletar, btnDisponibilizar);
                            actionButtons.setSpacing(10);
                            setGraphic(actionButtons);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);
        dgvInstrumentos.getColumns().add(colBtn);
    }

    private void desejaDeletar(InstrumentoEscola instrumentoEscola) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Exclusão");
        alert.setHeaderText("Tem certeza que deseja excluir o instrumento?");
        alert.setContentText("Instrumento: " + instrumentoEscola.getInstrumento().getNome());

        if (alert.showAndWait().get() == ButtonType.OK) {
            try {
                InstrumentoEscolaDAO instrumentoDao = new InstrumentoEscolaDAO();
                instrumentoDao.remove(instrumentoEscola);  // Método para excluir o instrumento do banco
                dgvInstrumentos.getItems().remove(instrumentoEscola);  // Atualizar a tabela
            } catch (SQLException ex) {
                Mensagens.ErroBanco();
            }
        }
    }
    
    private void DesejaDisponibilizar(InstrumentoEscola instrumentoEscola) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Desassociação");
        alert.setHeaderText("Tem certeza que deseja desassociar o aluno do instrumento?");
        alert.setContentText("Instrumento: " + instrumentoEscola.getInstrumento().getNome() + "\nAluno: " + (instrumentoEscola.getAlunoVinculado() != null ? instrumentoEscola.getAlunoVinculado().getNome() : "-"));

        if (alert.showAndWait().get() == ButtonType.OK) {
            try {
                InstrumentoEscolaDAO instrumentoDao = new InstrumentoEscolaDAO();
                instrumentoEscola.setAlunoVinculado(null);  // Remover o aluno vinculado
                instrumentoEscola.setDisponivel(true);
                instrumentoDao.altera(instrumentoEscola);  // Método para atualizar o instrumento no banco
                dgvInstrumentos.refresh();  // Atualizar a tabela
            } catch (SQLException ex) {
                Mensagens.ErroBanco();
            }
        }
    }
}
