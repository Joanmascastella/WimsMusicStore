package com.inholland.nl.wimsmusicstore.Database;

import com.inholland.nl.wimsmusicstore.Model.Order;
import com.inholland.nl.wimsmusicstore.Model.Product;
import com.inholland.nl.wimsmusicstore.Model.User;

import java.io.Serializable;
import java.util.List;

public class DataWrapper  implements Serializable {
    List<User> users;
    List<Product> products;
    List<Order> orders;

    public DataWrapper(List<User> users, List<Product> products, List<Order> orders) {
        this.users = users;
        this.products = products;
        this.orders = orders;
    }
}
