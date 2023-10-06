package com.inholland.nl.wimsmusicstore.Controller;

import com.inholland.nl.wimsmusicstore.Database.Database;
import com.inholland.nl.wimsmusicstore.Model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DashboardController {
    @FXML private Label userName;
    @FXML private VBox navigationContainer;
    @FXML private Label userRole;
    @FXML private Label dateAndTime;
    private User user;
    private Database database;

    public void setUser(User user) {
        this.user = user;
        getUserInfo();
    }
    public void setDatabase(Database database) {
        this.database = database;
        loadNavigationView();
    }
    public void getUserInfo() {
        if (user != null) {
            userName.setText("Welcome " + user.getFirstName() + " " + user.getLastName() + "!");
            userRole.setText("Your role is: " + user.getUserType().toString());
        } else {
            userName.setText("Welcome!");
            userRole.setText("Your role is: Unknown");
        }
        updateDateTime();
    }
    public void updateDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        dateAndTime.setText("It is now: " + formattedDateTime);
    }
    private void loadNavigationView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/inholland/nl/wimsmusicstore/NavigationView.fxml"));
            VBox navigationView = loader.load();

            NavigationController navigationController = loader.getController();
            navigationController.setDatabase(this.database);
            navigationController.setUser(this.user);

            navigationContainer.getChildren().setAll(navigationView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}