package com.inholland.nl.wimsmusicstore;

import com.inholland.nl.wimsmusicstore.controller.LoginController;
import com.inholland.nl.wimsmusicstore.database.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WimsMusicStore extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        Database database;
        database = new Database();
        FXMLLoader fxmlLoader = new FXMLLoader(WimsMusicStore.class.getResource("LoginView.fxml"));
        Parent root = fxmlLoader.load();
        LoginController loginController = fxmlLoader.getController();
        loginController.setDatabase(database);
        Scene scene = new Scene(root, 488, 369);
        stage.setTitle("Wim's Music Store");
        stage.setResizable(false);
   a     stage.setScene(scene);
        stage.show();
        Runtime.getRuntime().addShutdownHook(new Thread(database::saveDatabaseToFile));
    }

    public static void main(String[] args) {
        launch();
    }
}
