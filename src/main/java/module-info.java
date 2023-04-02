module com.example.progettoswe {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.mail;

    opens com.example.progettoswe to javafx.fxml;
    exports com.example.progettoswe;
}