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
        loadData();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void loadData() {
        ObservableList<Product> products;
        products = FXCollections.observableArrayList(database.getProducts());
        productTableView.setItems(products);
    }

    public void setOnProductSelected(ProductSelectedListener listener) {
        this.listener = listener;
    }

    public void addOrder() {
        try {
            int quantity = Integer.parseInt(quantityInput.getText());
            if (quantity < 1) {
                message.setText("Quantity must be a positive integer.");
                return;
            }
            Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
            Product newProduct = new Product(quantity, selectedProduct.getProductName(), selectedProduct.getCategory(), selectedProduct.getPrice());
            selectedProduct.reduceStock(quantity);
            selectedProduct.getFinalPrice();
            listener.onProductSelected(newProduct);
            Stage currentStage = (Stage) addOrder.getScene().getWindow();
            currentStage.close();
        } catch (NumberFormatException e) {
            message.setText("Invalid input. Please enter a valid integer.");
        }
    }


    public void cancel() {
        Stage currentStage = (Stage) cancel.getScene().getWindow();
        currentStage.close();
    }
}
