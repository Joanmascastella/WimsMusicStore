package com.inholland.nl.wimsmusicstore.Controller;

import com.inholland.nl.wimsmusicstore.Database.Database;
import com.inholland.nl.wimsmusicstore.Model.User;
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

    //Sets instance of user
    public void setUser(User user) {
        this.user = user;
        getUserInfo();
    }

    //Sets instance of database
    public void setDatabase(Database database) {
        this.database = database;

    }

    //This method gets the user info of the logged-in user and displays that data
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

    //This method gets the current date and time and displays it in the correct format
    public void updateDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        dateAndTime.setText("It is now: " + formattedDateTime);
    }

}