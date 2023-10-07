package com.inholland.nl.wimsmusicstore.Database;

import com.inholland.nl.wimsmusicstore.Model.Product;
import com.inholland.nl.wimsmusicstore.Model.User;
import com.inholland.nl.wimsmusicstore.Enum.UserType;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Database {


    private final List<User> users;
    private List<Product> products = new ArrayList<>();

    public Database(){
        users = new ArrayList<>();
        users.addAll(
                List.of(
                        new User("Sales", "Sales123@", "Joan", "Mas", "joan.mas@email.com", 1234567890, UserType.sales),
                        new User("Manager", "Manager123@", "John", "Micheal", "john.micheal@email.com", 987654210, UserType.manager)
                )
        );
        loadProductsFromFile();
    }

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

    public void addProduct(Product product) {
        products.add(product);
        saveProductsToFile();
    }


    public void saveProductsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("products.dat"))) {
            oos.writeObject(products);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadProductsFromFile() {
        File file = new File("products.dat");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                products = (List<Product>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            products.addAll(
                    List.of(
                            new Product(3,"White Guitar", "Strings", 2000.3, "White wide neck guitar"),
                            new Product(5,"Black Guitar", "Strings", 1920.43, "Black wide neck guitar")
                    )
            );
        }
    }

}
