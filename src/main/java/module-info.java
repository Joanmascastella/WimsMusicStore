module com.inholland.nl.wimsmusicstore {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.inholland.nl.wimsmusicstore to javafx.fxml;
    exports com.inholland.nl.wimsmusicstore;
    exports com.inholland.nl.wimsmusicstore.Controller;
    opens com.inholland.nl.wimsmusicstore.Controller to javafx.fxml;
    opens com.inholland.nl.wimsmusicstore.Model to javafx.fxml;
    exports com.inholland.nl.wimsmusicstore.Enum;
    opens com.inholland.nl.wimsmusicstore.Enum to javafx.fxml;
}