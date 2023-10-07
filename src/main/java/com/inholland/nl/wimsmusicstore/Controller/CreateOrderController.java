package com.inholland.nl.wimsmusicstore.Controller;

import com.inholland.nl.wimsmusicstore.Database.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateOrderController implements Initializable {
    private Database database;
    @FXML private TextField firstNameTextField;
    @FXML private TextField lastNameTextField;
    @FXML private TextField emailTextField;
    @FXML private TextField phoneNumberTextField;
    @FXML private TableView productTableView;
    @FXML private Button addOrderButton;
    @FXML private Button deleteOrderButton;
    @FXML private Button createOrderButton;
    @FXML private Label message;

    public void setDatabase(Database database) {
        this.database = database;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void addOrderButton(ActionEvent actionEvent) {
    }

    public void deletOrderButton(ActionEvent actionEvent) {
    }

    public void createOrderButton(ActionEvent actionEvent) {
    }


}
