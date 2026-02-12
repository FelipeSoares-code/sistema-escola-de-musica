package br.com.fatec;

import br.com.fatec.Entidades.Apresentacao;
import br.com.fatec.Estaticos.DataHolder;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author felip
 */
public class TelaPesquisarApresentacoesController implements Initializable {

    @FXML
    private Button btnVoltar;
    @FXML
    private TableView<Apresentacao> dgvApresentacoes;
    @FXML
    private TableColumn<Apresentacao, String> colunaNome;
    @FXML
    private TableColumn<Apresentacao, String> colunaData;
    @FXML
    private TableColumn<Apresentacao, String> colunaTurno;
    @FXML
    private TableColumn<Apresentacao, String> colunaTurma;
    /**
     * Initializes the controller class.
     */

    private Collection<Apresentacao> apresentacoes = DataHolder.Apresentacoes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (apresentacoes != null) {
            // Configura as colunas
            colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
            colunaData.setCellValueFactory(new PropertyValueFactory<>("data"));
            colunaTurno.setCellValueFactory(new PropertyValueFactory<>("turno"));
            colunaTurma.setCellValueFactory(new PropertyValueFactory<>("turma"));

            // Carrega os dados na tabela
            ObservableList<Apresentacao> apresentacoesTabl = FXCollections.observableArrayList(apresentacoes);
            dgvApresentacoes.setItems(apresentacoesTabl);
        }
    }

    @FXML
    private void btnVoltarAction(ActionEvent event) throws IOException {
        App.setRoot("telaApresentacoes");
    }
}
