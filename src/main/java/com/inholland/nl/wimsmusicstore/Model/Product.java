package com.inholland.nl.wimsmusicstore.Model;

public class Product {
    private int stock;
    private String productName;
    private String category;
    private double price;
    private String description;

    public Product(int stock, String productName, String category, double price, String description) {
        this.stock = stock;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.description = description;
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
}
