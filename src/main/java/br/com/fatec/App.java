
package br.com.fatec;

import br.com.fatec.DAO.InstrumentoDAO;
import br.com.fatec.DAO.InstrumentoEscolaDAO;
import br.com.fatec.DAO.TurmaDAO;
import br.com.fatec.Entidades.Apresentacao;
import br.com.fatec.Entidades.Instrumento;
import br.com.fatec.Entidades.InstrumentoEscola;
import br.com.fatec.Entidades.Professor;
import br.com.fatec.Entidades.Turma;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import br.com.fatec.Estaticos.Banco;
import br.com.fatec.Estaticos.Mensagens;
import java.sql.SQLException;
import java.util.ArrayList;
import static javafx.application.Application.launch;
import javax.swing.JOptionPane;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * JavaFX App
 */

public class App extends Application {
    
    private static Scene scene;
    
    
    @Override
    
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("telaPrincipal"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        try{
            Mensagens.Ok();            
            Banco.conectar();
            
            Banco.desconectar();
            launch();
        } catch (SQLException ex) {
            Mensagens.ErroBanco();
            launch(); //apagar depois
        }        
    }
    
   
}

//Teste Da collection 
//Não apague por enquanto. Apagar depois
/*
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("TelaAddApresentacao"), 640, 480);
        stage.setScene(scene);
        stage.setTitle("Adicionar Nova Data de Apresentação");
        stage.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        // Carrega a tela inicial
        scene = new Scene(loadFXML("TelaPrincipal"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    // Método para trocar a raiz da cena
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    // Método para carregar um arquivo FXML
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
*/