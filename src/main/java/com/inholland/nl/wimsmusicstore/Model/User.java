package com.inholland.nl.wimsmusicstore.Model;

import com.inholland.nl.wimsmusicstore.Enum.UserType;

import java.io.Serializable;

public class User implements Serializable {

    private String username;
    private String password;
    private final String firstName;
    private final String lastName;
    private UserType userType;
    private final String email;
    private final int phoneNumber;

    //Constructor that is used for creating sales and manager personnel
    public User(String username, String password, String firstName, String lastName, String email, int phoneNumber, UserType userType) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userType = userType;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    //Constructor used to create customers when placing an order
    public User(String firstName, String lastName, String email, int phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public UserType getUserType() {
        return userType;
    }

    public String getEmail() {
        return email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }
}
