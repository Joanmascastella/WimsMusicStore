package com.inholland.nl.wimsmusicstore.controller;

import com.inholland.nl.wimsmusicstore.database.Database;
import com.inholland.nl.wimsmusicstore.model.Product;
import com.inholland.nl.wimsmusicstore.interfaces.ProductSelectedListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PopAddOrderController implements Initializable {

    @FXML
    private TableView<Product> productTableView;
    @FXML
    private Button addOrder;
    @FXML
    private Button cancel;
    @FXML
    private TextField quantityInput;
    @FXML
    private Label message;
    private Database database;
    private ProductSelectedListener listener;

    public void setDatabase(Database database) {
        this.database = database;
        try {
            loadData();
        } catch (Exception e) {
            message.setText("Error loading data.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            productTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        } catch (Exception e) {
            message.setText("Initialization error.");
        }
    }

    public void loadData() {
        try {
            ObservableList<Product> products = FXCollections.observableArrayList(database.getProducts());
            productTableView.setItems(products);
        } catch (Exception e) {
            message.setText("Error loading products.");
        }
    }

    public void setOnProductSelected(ProductSelectedListener listener) {
        this.listener = listener;
    }

    public void addOrder() {
        try {
            int quantity = Integer.parseInt(quantityInput.getText());
            if (quantity < 1) {
                message.setText("Quantity must be a positive amount.");
                return;
            }
            Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
            if (selectedProduct == null) {
                message.setText("Please select a product.");
                return;
            }
            if (selectedProduct.getStock() < quantity) {
                message.setText("Not enough stock for this product.");
                return;
            }
            Product newProduct = new Product(quantity, selectedProduct.getProductName(), selectedProduct.getCategory(), selectedProduct.getPrice());
            listener.onProductSelected(newProduct);
            Stage currentStage = (Stage) addOrder.getScene().getWindow();
            currentStage.close();
        } catch (NumberFormatException e) {
            message.setText("Please enter a valid integer.");
        } catch (Exception e) {
            message.setText("An error occurred while adding the order.");
        }
    }


    public void cancel() {
        try {
            Stage currentStage = (Stage) cancel.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            message.setText("An error occurred while closing the window.");
        }
    }
}
