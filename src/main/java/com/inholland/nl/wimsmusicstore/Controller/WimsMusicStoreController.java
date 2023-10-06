package com.inholland.nl.wimsmusicstore.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WimsMusicStoreController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}