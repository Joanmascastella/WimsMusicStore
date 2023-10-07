package com.inholland.nl.wimsmusicstore.Controller;
import com.inholland.nl.wimsmusicstore.Database.Database;
import com.inholland.nl.wimsmusicstore.Model.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateOrderController implements Initializable {
    private Database database;
    private ObservableList<Order> orders;

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
        loadData();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
    public void loadData() {
        orders = FXCollections.observableArrayList(database.getOrders());
        productTableView.setItems(orders);
    }

    public void addOrderButton(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/inholland/nl/wimsmusicstore/PopUpViews/PopUpAddOrder.fxml"));
            Parent root = loader.load();
            PopAddOrderController popController = loader.getController();
            popController.setDatabase(database);
            Stage stage = new Stage();
            stage.setTitle("Add Product To Order");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deletOrderButton(ActionEvent actionEvent) {
    }

    public void createOrderButton(ActionEvent actionEvent) {

    }
}
