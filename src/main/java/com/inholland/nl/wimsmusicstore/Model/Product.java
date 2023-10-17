package com.inholland.nl.wimsmusicstore.Model;

import java.io.Serializable;

public class Product implements Serializable {
    private int stock;
    private String productName;
    private String category;
    private double price;
    private String description;
    private int quantity;
    private double finalPrice;

    //Constructor used when creating a product in product inventory
    public Product(int stock, String productName, String category, double price, String description) {
        this.stock = stock;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.description = description;
    }

    //Constructor used when adding a product to an order
    public Product(int quantity, String productName, String category, double finalPrice) {
        this.quantity = quantity;
        this.productName = productName;
        this.category = category;
        this.finalPrice = finalPrice;
    }
    public double getFinalPrice() {
        return finalPrice * quantity;
    }
    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public void reduceStock(int quantity) {
        if (stock >= quantity) {
            stock -= quantity;
        } else {
            throw new IllegalArgumentException("Not enough stock");
        }
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
