package com.inholland.nl.wimsmusicstore.Model;

import java.io.Serializable;
import java.util.List;
import java.time.LocalDateTime;


public class Order implements Serializable {
    private User user;
    private Product product;
    private List<Product> products;
    private LocalDateTime orderDate;

    //Constructor used to make an order
    public Order(User user, List<Product> products) {
        this.user = user;
        this.products = products;
    }

    //Constructor used to make an order
    public Order(LocalDateTime orderDate, User user, List<Product> products) {
        this.orderDate = orderDate;
        this.user = user;
        this.products = products;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}