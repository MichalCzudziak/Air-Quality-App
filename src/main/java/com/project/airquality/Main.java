package com.project.airquality;

import database.DBController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import objects.Location;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main extends Application {

    public static ArrayList<Location> allLocations = new ArrayList<>();
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        FXMLLoader fxmlLoader;
        Scene scene;
        if (!new File("config.properties").exists()){
            fxmlLoader = new FXMLLoader(Main.class.getResource("connectDB.fxml"));
            scene = new Scene(fxmlLoader.load());
            stage.setTitle("Database Connection");
        } else {
            DBController dbController = new DBController();
            Main.allLocations = dbController.getAllLocations();
            dbController.loadMeasurements();
            fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
            scene = new Scene(fxmlLoader.load());
            stage.setTitle("Dashboard App");
        }

        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}