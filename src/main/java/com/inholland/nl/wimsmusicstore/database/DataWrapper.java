package com.inholland.nl.wimsmusicstore.database;

import com.inholland.nl.wimsmusicstore.model.Order;
import com.inholland.nl.wimsmusicstore.model.Product;
import com.inholland.nl.wimsmusicstore.model.User;

import java.io.Serializable;
import java.util.List;

public class DataWrapper implements Serializable {
     List<User> users;
    List<Product> products;
    List<Order> orders;
    public DataWrapper(List<User> users, List<Product> products, List<Order> orders) {
        this.users = users;
        this.products = products;
        this.orders = orders;
    }
}
