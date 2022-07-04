module sample.flashcards {
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.java;
    requires java.sql;


    opens sample.flashcards to javafx.fxml;
    exports sample.flashcards;
}