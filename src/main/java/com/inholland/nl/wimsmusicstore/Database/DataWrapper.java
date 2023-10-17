package com.inholland.nl.wimsmusicstore.Database;

import com.inholland.nl.wimsmusicstore.Model.Order;
import com.inholland.nl.wimsmusicstore.Model.Product;
import com.inholland.nl.wimsmusicstore.Model.User;

import java.io.Serializable;
import java.util.List;

public class DataWrapper implements Serializable {
    List<User> users;
    List<Product> products;
    List<Order> orders;

    //This constructor is wrapping the three lists of data that I want to be saved, which be called in the database class
    public DataWrapper(List<User> users, List<Product> products, List<Order> orders) {
        this.users = users;
        this.products = products;
        this.orders = orders;
    }
}
