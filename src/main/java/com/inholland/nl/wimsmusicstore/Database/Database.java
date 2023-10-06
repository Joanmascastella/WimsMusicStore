package com.inholland.nl.wimsmusicstore.Database;

import com.inholland.nl.wimsmusicstore.Model.User;
import com.inholland.nl.wimsmusicstore.UserType;


import java.util.ArrayList;
import java.util.List;

public class Database {

    private final List<User> users;

    public Database(){
        users = new ArrayList<>();
        users.addAll(
                List.of(
                        new User("Sales", "Sales123@", "Joan", "Mas", "joan.mas@email.com", 1234567890, UserType.sales),
                        new User("Manager", "Manager123@", "John", "Micheal", "john.micheal@email.com", 987654210, UserType.manager)
                )
        );
    }

    public User getUser(String username, String password) {
        for(User user : users) {
            if(user.getUsername().equals(username) && user.getPassword().equals(password)) {

                return user;
            }
        }
        return null;
    }

    public List<User> getUsers() {return users;}
}
