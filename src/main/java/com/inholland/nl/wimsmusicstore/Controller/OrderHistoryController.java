package com.inholland.nl.wimsmusicstore.Controller;

import com.inholland.nl.wimsmusicstore.Database.Database;
import com.inholland.nl.wimsmusicstore.Model.Order;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;


import java.net.URL;
import java.util.ResourceBundle;

public class OrderHistoryController implements Initializable {
    ObservableList<Order> orders;
    private Database database;
    @FXML private TableView orderView;
    @FXML private TableView orderProductsView;

    //Sets database instance
    public void setDatabase(Database database) {
        this.database = database;
        loadData();
    }
    //Allows to select items
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    //Loads data to tableview
    public void loadData() {

    }
}

