package com.inholland.nl.wimsmusicstore.Controller;
import com.inholland.nl.wimsmusicstore.Database.Database;
import com.inholland.nl.wimsmusicstore.Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductInventoryController implements Initializable {
    private ObservableList<Product> products;
    private Database database;
    @FXML private Button editProduct;
    @FXML private TableView productTableView;
    @FXML private TextField stock;
    @FXML private TextField productName;
    @FXML private TextField category;
    @FXML private TextField price;
    @FXML private TextField description;
    @FXML private Button addProduct;
    @FXML private Button delete;
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
        products = FXCollections.observableArrayList(database.getProducts());
        productTableView.setItems(products);
    }


    public void onEditProductButtonClick(ActionEvent actionEvent) {
    }

    public void onAddProductButtonClick(ActionEvent actionEvent) {
    }

    public void onDeleteButtonClick(ActionEvent actionEvent) {
    }

}
