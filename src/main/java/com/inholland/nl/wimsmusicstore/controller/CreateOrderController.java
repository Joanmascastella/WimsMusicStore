package com.inholland.nl.wimsmusicstore.controller;

import com.inholland.nl.wimsmusicstore.database.Database;
import com.inholland.nl.wimsmusicstore.model.InputValidator;
import com.inholland.nl.wimsmusicstore.model.Order;
import com.inholland.nl.wimsmusicstore.model.Product;
import com.inholland.nl.wimsmusicstore.model.User;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CreateOrderController implements Initializable {
    private final List<Product> selectedProducts = new ArrayList<>();
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private TableView<Product> productTableView;
    @FXML
    private TableColumn<Product, Integer> quantityRow;
    @FXML
    private TableColumn<Product, Double> priceRow;
    @FXML
    private Button addOrderButton;
    @FXML
    private Button deleteOrderButton;
    @FXML
    private Button createOrderButton;
    @FXML
    private Label message;
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

    public void addOrderButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/inholland/nl/wimsmusicstore/popUpViews/PopUpAddOrder.fxml"));
            Parent root = loader.load();
            PopAddOrderController popController = loader.getController();
            popController.setDatabase(database);
            popController.setOnProductSelected(product -> {
                selectedProducts.add(product);
                updateTableView();
            });
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Add Product To Order");
            dialog.getDialogPane().setContent(root);
            dialog.showAndWait();
        } catch (IOException e) {
            message.setText("Error loading the Add Product view.");
        }
    }

    public void updateTableView() {
        ObservableList<Product> observableProducts = FXCollections.observableArrayList(selectedProducts);
        productTableView.setItems(observableProducts);
    }

    public void deleteOrderButton() {
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

    public void createOrderButton() {
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String email = emailTextField.getText();

        if (!validateFields(firstName, lastName)) {
            return;
        }

        try {
            int phoneNumber = Integer.parseInt(phoneNumberTextField.getText());
            User user = new User(firstName, lastName, email, phoneNumber);
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter);
            Order order = new Order(formattedDateTime, user, selectedProducts);
            database.addOrder(order);
            message.setText("Created Order");
            clearFields();
        } catch (NumberFormatException e) {
            message.setText("Invalid phone number.");
        } catch (Exception e) {
            message.setText("Error creating the order.");
        }
    }

    private boolean validateFields(String firstName, String lastName) {
        InputValidator validator = new InputValidator();

        if (!validator.containsOnlyCharacters(firstName) || !validator.containsOnlyCharacters(lastName)) {
            message.setText("First name and last name should contain only characters.");
            return false;
        }

        if (!validator.isPositiveNumber(phoneNumberTextField.getText())) {
            message.setText("Please enter a valid positive phone number.");
            return false;
        }

        return true;
    }



    public void clearFields() {
        firstNameTextField.clear();
        lastNameTextField.clear();
        emailTextField.clear();
        phoneNumberTextField.clear();
    }
}
