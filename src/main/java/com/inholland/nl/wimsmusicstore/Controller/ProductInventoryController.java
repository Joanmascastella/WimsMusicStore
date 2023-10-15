package com.inholland.nl.wimsmusicstore.Controller;

import com.inholland.nl.wimsmusicstore.Database.Database;
import com.inholland.nl.wimsmusicstore.Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.System.*;

public class ProductInventoryController implements Initializable {
    private ObservableList<Product> products;
    private Database database;
    @FXML private Button editProduct;
    @FXML private TableView productTableView;
    @FXML private TextField stock;
    @FXML private TextField productName;
    @FXML private TextField category;
    @FXML private TextField price;
    @FXML private TextField description;
    @FXML private Button addProduct;
    @FXML private Button delete;
    @FXML private Label message;
    private Product selectedProductForEdit;

    public void setDatabase(Database database) {
        this.database = database;
        loadData();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
    public void loadData() {
        products = FXCollections.observableArrayList(database.getProducts());
        productTableView.setItems(products);
    }

    public void onAddProductButtonClick() {
        try {
            //Parse int and double values
            int stockValue = Integer.parseInt(stock.getText());
            double priceValue = Double.parseDouble(price.getText());
            //Creates new product
            Product product = new Product(stockValue, productName.getText(), category.getText(), priceValue, description.getText());
            //Adds product to database and file
            database.addProduct(product);
            //adds product to tableview
            products.add(product);
            //clear fields
            clearFields();
            message.setText("Product has been added successfully.");
        } catch (NumberFormatException e) {
            message.setText("Error converting stock or price values. Please enter valid numbers.");
            err.println("Error converting stock or price values. Please enter valid numbers.");
        } catch (Exception e) {
            message.setText("Error adding product.");
            err.println(e.getMessage());
        }
    }
    public void onEditProductButtonClick() {
        if (selectedProductForEdit == null) {
            setPromptText();
        } else {
            updateProduct();
        }
    }
    private void setPromptText() {
        //This gets the selectedProduct and sets its values to the prompt text of the textFields
        try {
            Product selectedProduct = (Product) productTableView.getSelectionModel().getSelectedItem();
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
            // This is checking if the fields are empty
            int stockValue = stock.getText().isEmpty() ? selectedProductForEdit.getStock() : Integer.parseInt(stock.getText());
            double priceValue = price.getText().isEmpty() ? selectedProductForEdit.getPrice() : Double.parseDouble(price.getText());
            Product updatedProduct = getProduct(stockValue, priceValue);
            // Replacing the old product in the database
            database.removeProduct(selectedProductForEdit);
            database.addProduct(updatedProduct);
            int selectedIndex = products.indexOf(selectedProductForEdit);
            products.set(selectedIndex, updatedProduct);  // Updates the tableview
            clearFields();
            // Exits the editing function
            selectedProductForEdit = null;
            message.setText("Product has been edited successfully.");
        } catch (NumberFormatException e) {
            message.setText("Error converting stock or price values. Please enter valid numbers.");
            err.println("Error converting stock or price values. Please enter valid numbers.");
        } catch (Exception e) {
            message.setText("Error updating product.");
            err.println(e.getMessage());
        }
    }

    private Product getProduct(int stockValue, double priceValue) {
        String updatedProductName = productName.getText().isEmpty() ? selectedProductForEdit.getProductName() : productName.getText();
        String updatedCategory = category.getText().isEmpty() ? selectedProductForEdit.getCategory() : category.getText();
        String updatedDescription = description.getText().isEmpty() ? selectedProductForEdit.getDescription() : description.getText();
        // Creating the updated product
        return new Product(stockValue, updatedProductName, updatedCategory, priceValue, updatedDescription);
    }

    public void onDeleteButtonClick() {
        try {
            //Gets the selected product removes it from the list and file
            ObservableList<Product> productsToDelete = productTableView.getSelectionModel().getSelectedItems();
            for (Product product : productsToDelete) {
                database.removeProduct(product);
            }
            //removes products from tableview
            products.removeAll(productsToDelete);
            message.setText("Product(s) have been deleted successfully.");
        } catch (Exception e) {
            message.setText("Error deleting product(s).");
            err.println(e.getMessage());
        }
    }

    public void clearFields(){
        stock.clear();
        productName.clear();
        category.clear();
        price.clear();
        description.clear();
    }

}
