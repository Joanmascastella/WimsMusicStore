package com.inholland.nl.wimsmusicstore.Controller;

import com.inholland.nl.wimsmusicstore.Database.Database;
import com.inholland.nl.wimsmusicstore.Model.Product;
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
    private Database database;
    @FXML private TableView productTableView;
    @FXML private Button addOrder;
    @FXML private Button Cancel;
    @FXML private TextField quantityInput;
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
    public void addOrder(ActionEvent actionEvent) {

    }

    public void cancel(ActionEvent actionEvent) {
        Stage currentStage = (Stage) Cancel.getScene().getWindow();
        currentStage.close();
    }
}
