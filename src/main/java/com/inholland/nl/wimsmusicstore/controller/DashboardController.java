package com.inholland.nl.wimsmusicstore.controller;

import com.inholland.nl.wimsmusicstore.database.Database;
import com.inholland.nl.wimsmusicstore.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DashboardController {
    @FXML
    private Label userName;
    @FXML
    private Label userRole;
    @FXML
    private Label dateAndTime;
    private User user;
    private Database database;

    public void setUser(User user) {
        this.user = user;
        try {
            getUserInfo();
        } catch (Exception e) {
            userName.setText("Error displaying user information.");
        }
    }

    public void setDatabase(Database database) {
        this.database = database;
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
        try {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter);
            dateAndTime.setText("It is now: " + formattedDateTime);
        } catch (Exception e) {
            dateAndTime.setText("Error displaying the date and time.");
        }
    }
}
