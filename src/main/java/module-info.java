module br.com.fatec {
    requires javafx.controls;
    requires javafx.fxml;
    requires mail;
    requires java.base;
    requires mysql.connector.j;
    requires java.sql;
    requires java.desktop;
            
    opens br.com.fatec to javafx.fxml;
    opens br.com.fatec.Entidades to javafx.base;
    exports br.com.fatec;
}
