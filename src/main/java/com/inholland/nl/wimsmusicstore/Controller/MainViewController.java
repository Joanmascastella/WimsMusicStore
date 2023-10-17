package com.inholland.nl.wimsmusicstore.Controller;

import com.inholland.nl.wimsmusicstore.Database.Database;
import com.inholland.nl.wimsmusicstore.Model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;

import static java.lang.System.out;

public class MainViewController {
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

    //Makes sure that when form loads the dashboard views loads first
    public void initialize() {
        loadDefaultView();
    }

    //Sets user instance of user
    public void setUser(User user) {
        this.user = user;
        allowAccessDependingOnRole();
        loadDefaultView();
    }

    //Sets database instance of database
    public void setDatabase(Database database) {
        this.database = database;
    }

    //This method checks the usertype of the logged-in user and depending on its role it disables or enables buttons of which they are allowed or not allowed to access
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

    //This method makes sure that dashboard is automatically opens when mainview loads
    private void loadDefaultView() {
        navigateToDashboard();
    }

    //Sets fxml path for the corresponding views
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

    //This method checks if the controller of the fxmlpath provided is of a specific instance. If it is it then passes an instance of database or of user if needed
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
            out.println("Unable to find view");
            e.printStackTrace();
        }
    }

}
