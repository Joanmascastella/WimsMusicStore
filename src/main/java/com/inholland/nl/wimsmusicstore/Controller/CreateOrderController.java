package com.inholland.nl.wimsmusicstore.Controller;

import com.inholland.nl.wimsmusicstore.Database.Database;
import com.inholland.nl.wimsmusicstore.Model.Order;
import com.inholland.nl.wimsmusicstore.Model.Product;
import com.inholland.nl.wimsmusicstore.Model.User;
import com.inholland.nl.wimsmusicstore.Interface.ProductSelectedListener;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CreateOrderController implements Initializable {
    private List<Product> selectedProducts = new ArrayList<>();

    @FXML private TextField firstNameTextField;
    @FXML private TextField lastNameTextField;
    @FXML private TextField emailTextField;
    @FXML private TextField phoneNumberTextField;
    @FXML private TableView<Product> productTableView;
    @FXML private TableColumn<Product, Integer> quantityRow;
    @FXML private TableColumn<Product, Double> priceRow;
    @FXML private Button addOrderButton;
    @FXML private Button deleteOrderButton;
    @FXML private Button createOrderButton;
    @FXML private Label message;
    private ProductSelectedListener listener;
    private Database database;

    public void setDatabase(Database database) {
        this.database = database;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        quantityRow.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
        priceRow.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getFinalPrice()).asObject());
        productTableView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldProduct, newProduct) -> {
            if (newProduct != null) {
                selectedProducts.add(newProduct);
            }
        });
    }

    public void setOnProductSelected(ProductSelectedListener listener) {
        this.listener = listener;
    }

    public void addOrderButton(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/inholland/nl/wimsmusicstore/PopUpViews/PopUpAddOrder.fxml"));
            Parent root = loader.load();
            PopAddOrderController popController = loader.getController();
            popController.setDatabase(database);
            popController.setOnProductSelected(product -> {
                selectedProducts.add(product);
                updateTableView();
            });
            Stage stage = new Stage();
            stage.setTitle("Add Product To Order");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateTableView() {
        ObservableList<Product> observableProducts = FXCollections.observableArrayList(selectedProducts);
        productTableView.setItems(observableProducts);
    }

    public void deletOrderButton(ActionEvent actionEvent) {
        List<Product> productsToRemove = new ArrayList<>(productTableView.getSelectionModel().getSelectedItems());

        if (productsToRemove.isEmpty()) {
            message.setText("Please select a product to delete");
            return;
        }
        selectedProducts.removeAll(productsToRemove);
        updateTableView();
        productTableView.getSelectionModel().clearSelection();
        message.setText("Product(s) removed successfully!");
    }

    public void createOrderButton(ActionEvent actionEvent) {
        int phoneNumber = Integer.parseInt(phoneNumberTextField.getText());
        User user = new User(
                firstNameTextField.getText(),
                lastNameTextField.getText(),
                emailTextField.getText(),
                phoneNumber
        );
        Order order = new Order(user, selectedProducts);
        database.addOrder(order);
        clearFields();
    }

    public void clearFields() {
        firstNameTextField.clear();
        lastNameTextField.clear();
        emailTextField.clear();
        phoneNumberTextField.clear();
    }
}
