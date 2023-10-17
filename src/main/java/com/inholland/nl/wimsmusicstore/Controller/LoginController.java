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
    @FXML private Button loginButton;
    @FXML private PasswordField passwordField;
    @FXML private Label feedbackLabel;
    @FXML private TextField userNameField;
    private Database database;
    private User user;
    //Sets Instance of database
    public void setDatabase(Database database) {
        this.database = database;
    }
    //Disables or enables the login button depending on if the entered password is valid or not. This is checked by calling the isPasswordValid method
    @FXML
    public void onPasswordTextChange(StringProperty observable, String oldValue, String newValue) {
        loginButton.setDisable(!isPasswordValid(newValue));
    }
    //Simply checks if user login was successful and if it is a user is not null then it loads the new view, if not feed back message is show to the user
    @FXML
    public void onLoginButtonClick(ActionEvent actionEvent) {
        userLogin();
        if (user != null) {
            loadScene();
        } else {
            feedbackLabel.setText("Invalid Username or Password");
        }
    }
    //This is the main logic for the login. Ite gets the text from the fields and passes this text to the method in database which validates the data and returns its corresponding user.
    public void userLogin(){
        String username = userNameField.getText();
        String password = passwordField.getText();
        user = database.getUser(username, password);
    }
    //This method is validating password making sure field is not empty, is longer than 7 char, has at least one digit and at least one special character
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
    //This method loads the mainview when login is successful
    public void loadScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/inholland/nl/wimsmusicstore/MainView.fxml"));
            Parent root = loader.load();
            MainViewController mainViewController = loader.getController();
            //Passing instance of user to new view
            mainViewController.setUser(user);
            //Passing instance of database to new view
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