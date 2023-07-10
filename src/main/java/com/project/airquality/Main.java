package com.project.airquality;

import database.DBController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import objects.Location;
import objects.Measurement;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main extends Application {
    /**
     *  creating new arraylist for all locations
     */
    public static ArrayList<Location> allLocations = new ArrayList<>();

    /**
     *  starting the Application, first scene will be shown
     */
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
            ArrayList<Measurement> test = Main.allLocations.get(0).getMeasurements();
            dbController.loadMeasurements();
            fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
            scene = new Scene(fxmlLoader.load());
            stage.setTitle("Dashboard App");
            stage.setResizable(false);
        }
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}