package com.inholland.nl.wimsmusicstore.controller;

import com.inholland.nl.wimsmusicstore.database.Database;
import com.inholland.nl.wimsmusicstore.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MainViewController {
    @FXML private Label message;
    @FXML
    private VBox newViewContainter;
    @FXML
    private Button dashboardButton;
    @FXML
    private Button orderHistoryButton;
    @FXML
    private Button productInventoryButton;
    @FXML
    private Button createOrderButton;
    private Database database;
    private User user;


    public void initialize() {
        try {
            loadDefaultView();
        } catch (Exception e) {
          message.setText("Error initializing the default view.");
        }
    }

    public void setUser(User user) {
        this.user = user;
        try {
            allowAccessDependingOnRole();
            loadDefaultView();
        } catch (Exception e) {
            message.setText("Error setting the user.");

        }
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    private void allowAccessDependingOnRole() {
        try {
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
        } catch (Exception e) {
            message.setText("Error allowing access based on role.");

        }
    }

    private void loadDefaultView() {
        navigateToDashboard();
    }

    private void loadView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            VBox navigationView = loader.load();
            Object controller = loader.getController();
            if (controller instanceof DashboardController dashboardController) {
                dashboardController.setDatabase(database);
                dashboardController.setUser(user);
            } else if (controller instanceof CreateOrderController createOrderController) {
                createOrderController.setDatabase(database);
            } else if (controller instanceof ProductInventoryController productInventoryController) {
                productInventoryController.setDatabase(database);
                productInventoryController.loadData();
            } else if (controller instanceof OrderHistoryController orderHistoryController) {
                orderHistoryController.setDatabase(database);
                orderHistoryController.loadData();
            }

            newViewContainter.getChildren().setAll(navigationView);
        } catch (IOException e) {
            message.setText("Unable to find view");
        } catch (Exception e) {
            message.setText("Error loading the view.");

        }
    }

    public void navigateToDashboard() {
        loadView("/com/inholland/nl/wimsmusicstore/DashboardView.fxml");
    }

    public void navigateToCreateOrder() {
        loadView("/com/inholland/nl/wimsmusicstore/CreateOrderView.fxml");
    }

    public void navigateToProductInventory() {
        loadView("/com/inholland/nl/wimsmusicstore/ProductInventoryView.fxml");
    }

    public void navigateToOrderHistory() {
        loadView("/com/inholland/nl/wimsmusicstore/OrderHistoryView.fxml");
    }

}
