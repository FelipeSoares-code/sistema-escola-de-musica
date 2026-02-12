/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.Estaticos;

//import br.com.fatec.TelaCadastrarAlunoController;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.function.UnaryOperator;
import javafx.scene.control.Alert;
import javafx.scene.control.TextFormatter;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 *
 * @author felip
 */
public class Metodos {

    private Metodos() {
    }
    
    private static DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static DateTimeFormatter getFormatoData() {
        return formatoData;
    }
 
    public static boolean validarEmail(String email){
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
            return true;
        } catch (AddressException ex) {
            return false;     
        }

    }
    
    public static TextFormatter<String> criarMascaraCPF() {
        UnaryOperator<TextFormatter.Change> filtro = change -> {
            String text = change.getControlNewText();
            String currentText = change.getControlText();

            // Remove todos os caracteres não numéricos
            text = text.replaceAll("[^0-9]", "");

            // Limita o comprimento a 11 dígitos
            if (text.length() > 11) {
                text = text.substring(0, 11);
            }

            StringBuilder result = new StringBuilder();
            int length = text.length();

            // Aplica a máscara de CPF
            for (int i = 0; i < length; i++) {
                if (i == 3 || i == 6) {
                    result.append('.');
                } else if (i == 9) {
                    result.append('-');
                }
                result.append(text.charAt(i));
            }

            // Atualiza o texto da mudança com o texto formatado
            String formattedText = result.toString();
            change.setText(formattedText);

            // Ajusta o range da mudança para cobrir o texto atual
            change.setRange(0, currentText.length());

            // Define a posição do cursor
            change.setCaretPosition(formattedText.length());

            return change;
        };

        return new TextFormatter<>(filtro);
    }
 
}
