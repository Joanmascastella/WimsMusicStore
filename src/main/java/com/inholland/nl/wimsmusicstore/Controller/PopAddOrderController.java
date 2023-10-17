package com.inholland.nl.wimsmusicstore.Controller;

import com.inholland.nl.wimsmusicstore.Database.Database;
import com.inholland.nl.wimsmusicstore.Model.Product;
import com.inholland.nl.wimsmusicstore.Interface.ProductSelectedListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PopAddOrderController implements Initializable {
    private ObservableList<Product> products;
    @FXML
    private TableView productTableView;
    @FXML
    private Button addOrder;
    @FXML
    private Button Cancel;
    @FXML
    private TextField quantityInput;
    @FXML
    private Label message;
    private Database database;
    private ProductSelectedListener listener;

    //Sets database instance
    public void setDatabase(Database database) {
        this.database = database;
        loadData();
    }

    //Allows selection of items
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    //Loads data to tableview
    public void loadData() {
        products = FXCollections.observableArrayList(database.getProducts());
        productTableView.setItems(products);
    }

    //Initializing the product listener. This allows the selected products to be passed to its base controllers
    public void setOnProductSelected(ProductSelectedListener listener) {
        this.listener = listener;
    }

    //This method gets the selected product and passes it on to its base controller
    public void addOrder() {
        //Gets selected product from tableview
        Product selectedProduct = (Product) productTableView.getSelectionModel().getSelectedItem();
        //converts input (string) to int value
        int quantity = Integer.parseInt(quantityInput.getText());
        //Creating a new product for the order using the second constructor in product
        Product newProduct = new Product(quantity, selectedProduct.getProductName(), selectedProduct.getCategory(), selectedProduct.getPrice());
        //Reducing stock of product by - the quantity
        selectedProduct.reduceStock(quantity);
        //Calculating and setting the final price for the new order
        selectedProduct.setFinalPrice(newProduct.getPrice() * quantity);
        //Passes this new product to its base product
        listener.onProductSelected(newProduct);
        Stage currentStage = (Stage) addOrder.getScene().getWindow();
        currentStage.close();
    }

    //This method closes the view
    public void cancel() {
        Stage currentStage = (Stage) Cancel.getScene().getWindow();
        currentStage.close();
    }
}
