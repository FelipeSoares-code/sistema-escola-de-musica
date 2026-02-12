/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec;

import br.com.fatec.DAO.InstrumentoDAO;
import br.com.fatec.DAO.InstrumentoEscolaDAO;
import br.com.fatec.Entidades.Instrumento;
import br.com.fatec.Entidades.InstrumentoEscola;
import br.com.fatec.Estaticos.Mensagens;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author regin
 */
public class TelaAddInstrumentoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private ComboBox<Instrumento> cmbInstrumento;
    
    @FXML
    private TextField txtMarca;
    
    @FXML
    private DatePicker dateCompra;
    
    @FXML
    private void btnVoltarAction() throws IOException{
        App.setRoot("telaInstrumentos");
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //introduzindo instrumento na combo
        Collection<Instrumento> instrumentos = new ArrayList<>();
        InstrumentoDAO instrumentoDao = new InstrumentoDAO();
        try{
            instrumentos = instrumentoDao.lista("");
        } catch (SQLException ex){
            Mensagens.ErroBanco();
        }
        cmbInstrumento.getItems().addAll(instrumentos);
    }    
    
    public void btnAvancar(){
        InstrumentoEscolaDAO instrumentoEscolaDao = new InstrumentoEscolaDAO();
        InstrumentoEscola instrumentoEscola = new InstrumentoEscola(cmbInstrumento.getValue());
        
        if(cmbInstrumento.getValue() == null || dateCompra.getValue() == null
                || txtMarca.getText().isEmpty())
        {
            Mensagens.Msg("Preencha todos os campos!", "Campos vazios detectados!");
        } else {
            instrumentoEscola.setDataCompra(dateCompra.getValue());
            instrumentoEscola.setMarca(txtMarca.getText());
            instrumentoEscola.setDisponivel(true);
            try{
                instrumentoEscolaDao.insere(instrumentoEscola);
                txtMarca.clear();
                dateCompra.setValue(null);
                cmbInstrumento.setValue(null);
                Mensagens.Msg("Instrumento adicionado com sucesso!", "Instrumento adicionado!");
            } catch(SQLException ex){
                Mensagens.ErroBanco();
            }
        }
    }
    
}
