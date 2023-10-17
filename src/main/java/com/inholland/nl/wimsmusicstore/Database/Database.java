package com.inholland.nl.wimsmusicstore.Database;
import com.inholland.nl.wimsmusicstore.Model.Order;
import com.inholland.nl.wimsmusicstore.Model.Product;
import com.inholland.nl.wimsmusicstore.Model.User;
import com.inholland.nl.wimsmusicstore.Enum.UserType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private final List<User> users;
    private List<Product> products = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();
    private DataWrapper databaseData;

    //Creating default users
    public Database(){
        users = new ArrayList<>();
        users.addAll(
                List.of(
                        new User("Sales", "Sales123@", "Joan", "Mas", "joan.mas@email.com", 1234567890, UserType.sales),
                        new User("Manager", "Manager123@", "John", "Micheal", "john.micheal@email.com", 987654210, UserType.manager)
                )
        );
        //Loading data from file
        loadDatabaseFromFile();
    }

    //Validating login
    public User getUser(String username, String password) {
        for(User user : users) {
            if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public List<Product> getProducts() {
        return products;
    }

    //This method adds new products to the existing product list and saves them
    public void addProduct(Product product) {
        products.add(product);
        saveDatabaseToFile();
    }
    //This method removes products to the existing product list and saves them
    public void removeProduct(Product product) {
        products.remove(product);
        saveDatabaseToFile();
    }

    public List<Order> getOrders() {
        return orders;
    }

    //This method creates a new order and saves it to the order list
    public void addOrder(Order order){
        orders.add(order);
        saveDatabaseToFile();
    }
    //This method removes products from an order and saves to list
    public void removeProductFromOrder(Order order){
        orders.remove(order);
        saveDatabaseToFile();
    }

    //This method serializes the database data by making use of the data wrapper
    public void saveDatabaseToFile() {
        databaseData = new DataWrapper(users, products, orders);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("database.dat"))) {
            oos.writeObject(databaseData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //This method loads all data and injects data into the corresponding lists when the application runs
    public void loadDatabaseFromFile() {
        File file = new File("database.dat");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                databaseData = (DataWrapper) ois.readObject();
                users.clear();
                products.clear();
                orders.clear();
                users.addAll(databaseData.users);
                products.addAll(databaseData.products);
                orders.addAll(databaseData.orders);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
