package com.inholland.nl.wimsmusicstore.Controller;

import com.inholland.nl.wimsmusicstore.Database.Database;
import com.inholland.nl.wimsmusicstore.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class NavigationController {
    @FXML private Button dashboardButton;
    @FXML private Button createOrderButton;
    @FXML private Button productInventoryButton;
    @FXML private Button orderHistoryButton;
    private Database database;
    private User user;

    public void setDatabase(Database db) {
        this.database = db;
    }
    public void setUser(User user) {
        this.user = user;
        setupNavigationButtons();
    }
    private void setupNavigationButtons() {
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
    public void navigateToDashboard(ActionEvent actionEvent) {
        navigateToView("/com/inholland/nl/wimsmusicstore/DashboardView.fxml", DashboardController.class);
    }

    public void navigateToCreateOrder(ActionEvent actionEvent) {
        navigateToView("/com/inholland/nl/wimsmusicstore/CreateOrderView.fxml", CreateOrderController.class);
    }

    public void navigateToProductInventory(ActionEvent actionEvent) {
        navigateToView("/com/inholland/nl/wimsmusicstore/ProductInventoryView.fxml", ProductInventoryController.class);
    }

    public void navigateToOrderHistory(ActionEvent actionEvent) {
        navigateToView("/com/inholland/nl/wimsmusicstore/OrderHistoryView.fxml", OrderHistoryController.class);
    }
    private void navigateToView(String fxmlFile, Class<?> controllerClass) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            Object controller = loader.getController();
            if(controllerClass.isInstance(controller)) {
                if(controller instanceof DashboardController) {
                    ((DashboardController) controller).setDatabase(database);
                } else if(controller instanceof CreateOrderController) {
                    ((CreateOrderController) controller).setDatabase(database);
                } else if(controller instanceof ProductInventoryController) {
                    ((ProductInventoryController) controller).setDatabase(database);
                } else if(controller instanceof OrderHistoryController) {
                    ((OrderHistoryController) controller).setDatabase(database);
                }
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
