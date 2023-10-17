package com.inholland.nl.wimsmusicstore;

import com.inholland.nl.wimsmusicstore.Controller.LoginController;
import com.inholland.nl.wimsmusicstore.Database.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WimsMusicStore extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        //Initializing the first and only database instance
        Database database;
        database = new Database();
        //Loading the login view
        FXMLLoader fxmlLoader = new FXMLLoader(WimsMusicStore.class.getResource("LoginView.fxml"));
        Parent root = fxmlLoader.load();
        LoginController loginController = fxmlLoader.getController();
        //Passing the database object to the login view
        loginController.setDatabase(database);
        Scene scene = new Scene(root, 488, 369);
        stage.setTitle("Wim's Music Store");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        //Making sure that when the application closes, all data in database is saved to file
        Runtime.getRuntime().addShutdownHook(new Thread(database::saveDatabaseToFile));
    }

    public static void main(String[] args) {
        launch();
    }
}
