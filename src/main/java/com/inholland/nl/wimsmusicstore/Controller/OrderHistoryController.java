package com.inholland.nl.wimsmusicstore.Controller;

import com.inholland.nl.wimsmusicstore.Database.Database;
import com.inholland.nl.wimsmusicstore.Model.Order;
import com.inholland.nl.wimsmusicstore.Model.Product;
import com.inholland.nl.wimsmusicstore.Model.User;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class OrderHistoryController implements Initializable {
    private ObservableList<Order> orders;
    @FXML private TableColumn<Order, String> dateTimeRow;
    @FXML private TableColumn<Order, String> userNameRow;
    @FXML private TableColumn<Order, Double> totalPriceRow;
    private Database database;
    @FXML
    private TableView<Order> orderView;
    @FXML
    private TableView<Product> orderProductsView;

    //Sets database instance
    public void setDatabase(Database database) {
        this.database = database;
        loadData();
    }
    //Allows to select items and sets cellvalue factories
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        dateTimeRow.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOrderDate()));
        userNameRow.setCellValueFactory(cellData -> {
            User user = cellData.getValue().getUser();
            return new SimpleStringProperty(user.getFirstName());
        });
        totalPriceRow.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getProducts().stream()
                .mapToDouble(Product::getFinalPrice)
                .sum()).asObject());
        orderView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldOrder, newOrder) -> {
            if (newOrder != null) {
                displayOrderProducts(newOrder);
            }
        });
    }
    //Displays products in an order
    private void displayOrderProducts(Order order) {
        ObservableList<Product> products = FXCollections.observableArrayList(order.getProducts());
        orderProductsView.setItems(products);
    }
    //Sets items in order table
    public void loadData() {
        orders = FXCollections.observableArrayList(database.getOrders());
        orderView.setItems(orders);
    }
}

