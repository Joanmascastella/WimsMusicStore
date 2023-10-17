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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static java.lang.System.*;

public class CreateOrderController implements Initializable {
    private List<Product> selectedProducts = new ArrayList<>();
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
    private ProductSelectedListener listener;
    private Database database;

    //Sets database instance
    public void setDatabase(Database database) {
        this.database = database;
    }

    //This method sets data to table view
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

    //initializing product listner
    public void setOnProductSelected(ProductSelectedListener listener) {
        this.listener = listener;
    }

    //Loads add product popup
    public void addOrderButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/inholland/nl/wimsmusicstore/PopUpViews/PopUpAddOrder.fxml"));
            Parent root = loader.load();
            PopAddOrderController popController = loader.getController();
            //Passing database instance
            popController.setDatabase(database);
            //sets the product listener to get those selected products and adds them to list
            popController.setOnProductSelected(product -> {
                selectedProducts.add(product);
                updateTableView();
            });
            //Creating dialog popup to make sure user can't move on without providing an input or canceling the operation
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Add Product To Order");
            dialog.getDialogPane().setContent(root);
            dialog.showAndWait();
        } catch (IOException e) {
            out.println("Unable to load view");
            e.printStackTrace();
        }
    }

    //Refreshes the tableview
    public void updateTableView() {
        ObservableList<Product> observableProducts = FXCollections.observableArrayList(selectedProducts);
        productTableView.setItems(observableProducts);
    }

    //This method deletes product item from order
    public void deletOrderButton() {
        //Removes product from order that has been selected
        List<Product> productsToRemove = new ArrayList<>(productTableView.getSelectionModel().getSelectedItems());
        //checks if an item has been selected to delete
        if (productsToRemove.isEmpty()) {
            message.setText("Please select a product to delete");
            return;
        }
        //Removes products by calling removeall method in database
        selectedProducts.removeAll(productsToRemove);
        //Updates the view
        updateTableView();
        productTableView.getSelectionModel().clearSelection();
        message.setText("Product(s) removed successfully!");
    }

    //This method creates orders
    public void createOrderButton() {
        //Parse the string input to string
        int phoneNumber = Integer.parseInt(phoneNumberTextField.getText());
        //Creating a new user utilizing the second constructor in user
        User user = new User(
                firstNameTextField.getText(),
                lastNameTextField.getText(),
                emailTextField.getText(),
                phoneNumber
        );
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        //Creates new order utilizing the second constructor
        Order order = new Order(formattedDateTime, user, selectedProducts);
        //Adds new order to list
        database.addOrder(order);
        message.setText("Created Order");
        clearFields();
    }

    //Clears fields
    public void clearFields() {
        firstNameTextField.clear();
        lastNameTextField.clear();
        emailTextField.clear();
        phoneNumberTextField.clear();
    }
}
