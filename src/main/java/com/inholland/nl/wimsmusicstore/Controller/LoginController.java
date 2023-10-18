package com.inholland.nl.wimsmusicstore.Controller;

import com.inholland.nl.wimsmusicstore.Database.Database;
import com.inholland.nl.wimsmusicstore.Model.User;
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


    public void setDatabase(Database database) {
        this.database = database;
    }
    @FXML
    public void onPasswordTextChange(StringProperty observable, String oldValue, String newValue) {
        loginButton.setDisable(!isPasswordValid(newValue));
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
        user = database.getUser(username, password);
    }
    protected boolean isPasswordValid(String password) {
        var hasLetters = false;
        var hasDigits = false;
        var hasSpecial = false;

        for (var c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                hasDigits = true;
            } else if (Character.isLetter(c)) {
                hasLetters = true;
            } else {
                hasSpecial = true;
            }
        }
        return password.length() > 7 && (hasLetters && hasDigits && hasSpecial);
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