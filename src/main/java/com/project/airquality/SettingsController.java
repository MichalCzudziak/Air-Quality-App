package com.project.airquality;
import database.DBConfigManager;
import database.DBController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
public class SettingsController {
    private Stage stage;
    private Scene scene;

    /**
     *   All required FXML GUI Components declaration needed to display all the functionalities
     */

    @FXML
    private Button dbConnectButton;
    @FXML
    private TextField textDBName;
    @FXML
    private TextField textDBURL;
    @FXML
    private PasswordField textPassword;
    @FXML
    private TextField textUsername;
    @FXML
    private Label localTime;
    /**
     *   creating  the database connection
     */

    @FXML
    public void connectToDatabase(ActionEvent event) throws IOException, SQLException {
        DBConfigManager configManager = new DBConfigManager(textDBURL.getText(), textUsername.getText(),
                textPassword.getText(), textDBName.getText());

        if (configManager.saveConfig()){
            DBController dbController = new DBController();
            Main.allLocations = dbController.getAllLocations();
            dbController.loadMeasurements();
            Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            Rectangle2D mainScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((mainScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((mainScreenBounds.getHeight() - stage.getHeight()) / 2);
        }
    }
    /**
     *  switching panels
     */

    public void switchToHome(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToKelsterbach(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("kelsterbach.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToRodgau(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("rodgau.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToMaintal(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("maintal.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToConnectDB(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("settings.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
