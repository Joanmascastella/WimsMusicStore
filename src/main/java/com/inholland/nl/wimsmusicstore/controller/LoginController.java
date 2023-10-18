package com.inholland.nl.wimsmusicstore.controller;

import com.inholland.nl.wimsmusicstore.database.Database;
import com.inholland.nl.wimsmusicstore.model.InputValidator;
import com.inholland.nl.wimsmusicstore.model.User;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private Button loginButton;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label feedbackLabel;
    @FXML
    private TextField userNameField;
    private Database database;
    private User user;
    private final InputValidator inputValidator = new InputValidator();



    public void setDatabase(Database database) {
        this.database = database;
    }
    @FXML
    public void onPasswordTextChange(StringProperty observable, String oldValue, String newValue) {
        loginButton.setDisable(!inputValidator.isPasswordValid(newValue));
    }

    @FXML
    public void onLoginButtonClick(ActionEvent actionEvent) {
        userLogin();
        if (user != null) {
            loadScene();
        } else {
            feedbackLabel.setText("Invalid Username or Password");
        }
    }
    public void userLogin() {
        String username = userNameField.getText();
        String password = passwordField.getText();

        if (inputValidator.isPasswordValid(password)) {
            user = database.getUser(username, password);
        } else {
            feedbackLabel.setText("Invalid password or username.");
        }
    }


    public void loadScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/inholland/nl/wimsmusicstore/MainView.fxml"));
            Parent root = loader.load();
            MainViewController mainViewController = loader.getController();
            mainViewController.setUser(user);
            mainViewController.setDatabase(database);
            Stage view = new Stage();
            view.setScene(new Scene(root));
            view.setTitle("Wim's Music Store Dashboard");
            view.setResizable(false);
            view.show();
            ((Stage) loginButton.getScene().getWindow()).close();
        } catch (IOException e) {
            feedbackLabel.setText("Error opening new view.");
            e.printStackTrace();
        }
    }

}