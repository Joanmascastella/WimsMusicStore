package com.inholland.nl.wimsmusicstore.model;

import java.io.Serializable;
import java.util.List;


public class Order implements Serializable {
    private User user;
    private Product product;
    private List<Product> products;
    private String orderDate;


    public Order(String orderDate, User user, List<Product> products) {
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

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}