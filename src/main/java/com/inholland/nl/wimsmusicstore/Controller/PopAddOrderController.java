package com.inholland.nl.wimsmusicstore.Controller;

import com.inholland.nl.wimsmusicstore.Database.Database;
import com.inholland.nl.wimsmusicstore.Model.Order;
import com.inholland.nl.wimsmusicstore.Model.Product;
import com.inholland.nl.wimsmusicstore.ProductSelectedListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PopAddOrderController implements Initializable {
    private ObservableList<Product> products;
    @FXML private TableView productTableView;
    @FXML private Button addOrder;
    @FXML private Button Cancel;
    @FXML private TextField quantityInput;
    @FXML private Label message;
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
        products = FXCollections.observableArrayList(database.getProducts());
        productTableView.setItems(products);
    }

    public void setOnProductSelected(ProductSelectedListener listener) {
        this.listener = listener;
    }
    public void addOrder(ActionEvent actionEvent) {
        Product selectedProduct = (Product) productTableView.getSelectionModel().getSelectedItem();
        int quantity = Integer.parseInt(quantityInput.getText());
        selectedProduct.reduceStock(quantity);
        selectedProduct.getTotalPrice();
        listener.onProductSelected(selectedProduct);
        Stage currentStage = (Stage) addOrder.getScene().getWindow();
        currentStage.close();
    }

    public void cancel(ActionEvent actionEvent) {
        Stage currentStage = (Stage) Cancel.getScene().getWindow();
        currentStage.close();
    }
}
