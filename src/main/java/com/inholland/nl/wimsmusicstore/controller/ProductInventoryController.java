package com.inholland.nl.wimsmusicstore.controller;

import com.inholland.nl.wimsmusicstore.database.Database;
import com.inholland.nl.wimsmusicstore.model.InputValidator;
import com.inholland.nl.wimsmusicstore.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.System.*;

public class ProductInventoryController implements Initializable {
    private ObservableList<Product> products;
    private Database database;
    @FXML
    private Button editProduct;
    @FXML
    private TableView<Product> productTableView;
    @FXML
    private TextField stock;
    @FXML
    private TextField productName;
    @FXML
    private TextField category;
    @FXML
    private TextField price;
    @FXML
    private TextField description;
    @FXML
    private Button addProduct;
    @FXML
    private Button delete;
    @FXML
    private Label message;
    private Product selectedProductForEdit;
    private InputValidator inputValidator;

    public void setDatabase(Database database) {
        this.database = database;
        loadData();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        inputValidator = new InputValidator();
    }

    public void loadData() {
        products = FXCollections.observableArrayList(database.getProducts());
        productTableView.setItems(products);
    }

    public void onAddProductButtonClick() {
        try {
            int stockValue = Integer.parseInt(stock.getText());
            double priceValue = Double.parseDouble(price.getText());
            String productNameText = productName.getText();
            String categoryText = category.getText();
            String descriptionText = description.getText();

            if (validateFields(productNameText, categoryText, descriptionText)) {
                return; // Exit if validation failed
            }

            Product product = new Product(stockValue, productNameText, categoryText, priceValue, descriptionText);
            database.addProduct(product);
            products.add(product);
            clearFields();
            message.setText("Product has been added successfully.");
        } catch (NumberFormatException e) {
            message.setText("Error converting stock or price values.");
        } catch (Exception e) {
            message.setText("Error adding product.");
            err.println(e.getMessage());
        }
    }

    public void onEditProductButtonClick() {
        try {
            if (selectedProductForEdit == null) {
                setPromptText();
            } else {
                updateProduct();
                clearPromptText();
            }
        } catch (Exception e) {
            message.setText("Please select a product to edit.");
            err.println(e.getMessage());
        }
    }

    private void setPromptText() {
        try {
            Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                stock.setPromptText(String.valueOf(selectedProduct.getStock()));
                productName.setPromptText(selectedProduct.getProductName());
                category.setPromptText(selectedProduct.getCategory());
                price.setPromptText(String.valueOf(selectedProduct.getPrice()));
                description.setPromptText(selectedProduct.getDescription());
                selectedProductForEdit = selectedProduct;
            }
        } catch (Exception e) {
            message.setText("Error setting product details.");
            err.println(e.getMessage());
        }
    }

    private void updateProduct() {
        try {
            int stockValue = stock.getText().isEmpty() ? selectedProductForEdit.getStock() : Integer.parseInt(stock.getText());
            double priceValue = price.getText().isEmpty() ? selectedProductForEdit.getPrice() : Double.parseDouble(price.getText());
            String updatedProductName = productName.getText().isEmpty() ? selectedProductForEdit.getProductName() : productName.getText();
            String updatedCategory = category.getText().isEmpty() ? selectedProductForEdit.getCategory() : category.getText();
            String updatedDescription = description.getText().isEmpty() ? selectedProductForEdit.getDescription() : description.getText();
            if (validateFields(updatedProductName, updatedCategory, updatedDescription)) {
                message.setText("Please ensure the remaining textbox contains only text and not a number. After verifying, click the edit button again.");
                return;
            }
            Product updatedProduct = new Product(stockValue, updatedProductName, updatedCategory, priceValue, updatedDescription);
            database.removeProduct(selectedProductForEdit);
            database.addProduct(updatedProduct);
            int selectedIndex = products.indexOf(selectedProductForEdit);
            products.set(selectedIndex, updatedProduct);
            clearFields();
            selectedProductForEdit = null;
            message.setText("Product has been edited successfully.");
        } catch (NumberFormatException e) {
            message.setText("Error converting stock or price values. Please enter valid numbers.");
        } catch (Exception e) {
            message.setText("Error updating product.");
            err.println(e.getMessage());
        }
    }

    private boolean validateFields(String productNameText, String categoryText, String descriptionText) {
        if (!inputValidator.containsOnlyCharacters(productNameText) ||
                !inputValidator.containsOnlyCharacters(categoryText) ||
                !inputValidator.containsOnlyCharacters(descriptionText)) {
            message.setText("Product Name, Category, and Description must contain only letters.");
            return true;
        }
        return false;
    }

    public void onDeleteButtonClick() {
        try {
            ObservableList<Product> productsToDelete = productTableView.getSelectionModel().getSelectedItems();
            for (Product product : productsToDelete) {
                database.removeProduct(product);
            }
            products.removeAll(productsToDelete);
            message.setText("Product(s) have been deleted successfully.");
        } catch (Exception e) {
            message.setText("Error deleting product(s).");
            err.println(e.getMessage());
        }
    }

    public void clearFields() {
        stock.clear();
        productName.clear();
        category.clear();
        price.clear();
        description.clear();
    }

    private void clearPromptText() {
        stock.setPromptText("Stock");
        productName.setPromptText("Product Name");
        category.setPromptText("Category");
        price.setPromptText("Price");
        description.setPromptText("Description");
    }

}
