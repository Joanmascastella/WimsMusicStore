package com.inholland.nl.wimsmusicstore.database;

import com.inholland.nl.wimsmusicstore.model.Order;
import com.inholland.nl.wimsmusicstore.model.Product;
import com.inholland.nl.wimsmusicstore.model.User;
import com.inholland.nl.wimsmusicstore.enums.UserType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.*;

public class Database {
    private final List<User> users;
    private DataWrapper databaseData;


    public Database() {
        users = new ArrayList<>();
        users.addAll(
                List.of(
                        new User("Sales", "Sales123@", "Joan", "Mas", "joan.mas@email.com", 1234567890, UserType.sales),
                        new User("Manager", "Manager123@", "John", "Micheal", "john.micheal@email.com", 987654210, UserType.manager)
                )
        );
        loadDatabaseFromFile();
    }

    public User getUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

// In Database.java

    public Product findProductByName(String productName) {
        for (Product p : products) {
            if (p.getProductName().equals(productName)) {
                return p;
            }
        }
        return null;
    }


    public List<Product> getProducts() {
        return products;
    }


    public void addProduct(Product product) {
        products.add(product);
        saveDatabaseToFile();
    }

    public void removeProduct(Product product) {
        products.remove(product);
        saveDatabaseToFile();
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void addOrder(Order order) {
        orders.add(order);
        saveDatabaseToFile();
    }


    public void saveDatabaseToFile() {
        databaseData = new DataWrapper(users, products, orders);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("database.dat"))) {
            oos.writeObject(databaseData);
        } catch (IOException e) {
        }
    }


    public void loadDatabaseFromFile() {
        File file = new File("database.dat");
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            databaseData = (DataWrapper) ois.readObject();
            users.clear();
            products.clear();
            orders.clear();
            users.addAll(databaseData.users);
            products.addAll(databaseData.products);
            orders.addAll(databaseData.orders);
        } catch (IOException | ClassNotFoundException e) {
            out.println("Error loading data to file");
        }

    }

}
