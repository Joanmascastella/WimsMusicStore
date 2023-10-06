module com.inholland.nl.wimsmusicstore {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.inholland.nl.wimsmusicstore to javafx.fxml;
    exports com.inholland.nl.wimsmusicstore;
}