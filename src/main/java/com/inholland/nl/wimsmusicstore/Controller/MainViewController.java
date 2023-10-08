package com.inholland.nl.wimsmusicstore.Controller;

import com.inholland.nl.wimsmusicstore.Database.Database;
import com.inholland.nl.wimsmusicstore.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MainViewController {
    @FXML private VBox newViewContainter;
    @FXML private Button dashboardButton;
    @FXML private Button orderHistoryButton;
    @FXML private Button productInventoryButton;
    @FXML private Button createOrderButton;

    private Database database;
    private User user;

    public void initialize() {
        loadDefaultView();
    }

    public void setUser(User user) {
        this.user = user;
        allowAccessDependingOnRole();
        loadDefaultView();
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    private void allowAccessDependingOnRole() {
        switch (user.getUserType()) {
            case sales:
                dashboardButton.setDisable(false);
                createOrderButton.setDisable(false);
                productInventoryButton.setDisable(true);
                orderHistoryButton.setDisable(false);
                break;
            case manager:
                dashboardButton.setDisable(false);
                createOrderButton.setDisable(true);
                productInventoryButton.setDisable(false);
                orderHistoryButton.setDisable(false);
                break;
            default:
                break;
        }
    }

    private void loadDefaultView() {
        navigateToDashboard(null);
    }

    public void navigateToDashboard(ActionEvent actionEvent) {
        loadView("/com/inholland/nl/wimsmusicstore/DashboardView.fxml");
    }

    public void navigateToCreateOrder(ActionEvent actionEvent) {
        loadView("/com/inholland/nl/wimsmusicstore/CreateOrderView.fxml");
    }

    public void navigateToProductInventory(ActionEvent actionEvent) {
        loadView("/com/inholland/nl/wimsmusicstore/ProductInventoryView.fxml");
    }

    public void navigateToOrderHistory(ActionEvent actionEvent) {
        loadView("/com/inholland/nl/wimsmusicstore/OrderHistoryView.fxml");
    }

    private void loadView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            VBox navigationView = loader.load();
            Object controller = loader.getController();
            if (controller instanceof DashboardController) {
                DashboardController dashboardController = (DashboardController) controller;
                dashboardController.setDatabase(database);
                dashboardController.setUser(user);
            } else if (controller instanceof CreateOrderController) {
                CreateOrderController createOrderController = (CreateOrderController) controller;
                createOrderController.setDatabase(database);
            } else if (controller instanceof ProductInventoryController) {
                ProductInventoryController productInventoryController = (ProductInventoryController) controller;
                productInventoryController.setDatabase(database);
                productInventoryController.loadData();
            } else if (controller instanceof OrderHistoryController) {
                OrderHistoryController orderHistoryController = (OrderHistoryController) controller;
                orderHistoryController.setDatabase(database);
                orderHistoryController.loadData();
            }

            newViewContainter.getChildren().setAll(navigationView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
