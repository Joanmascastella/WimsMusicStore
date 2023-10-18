module com.inholland.nl.wimsmusicstore {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.inholland.nl.wimsmusicstore to javafx.fxml;
    exports com.inholland.nl.wimsmusicstore;
    exports com.inholland.nl.wimsmusicstore.controller;
    opens com.inholland.nl.wimsmusicstore.controller to javafx.fxml;

    opens com.inholland.nl.wimsmusicstore.model to javafx.base;

    exports com.inholland.nl.wimsmusicstore.enums;
    opens com.inholland.nl.wimsmusicstore.enums to javafx.fxml;
    exports com.inholland.nl.wimsmusicstore.interfaces;
    opens com.inholland.nl.wimsmusicstore.interfaces to javafx.fxml;
}
