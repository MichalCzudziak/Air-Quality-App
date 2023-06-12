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
import objects.Measurement;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
public class settingsController {
    private Stage stage;
    private Scene scene;

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
    public void connectToDatabase(ActionEvent event) throws IOException, SQLException {
        DBConfigManager configManager = new DBConfigManager(textDBURL.getText(), textUsername.getText(),
                textPassword.getText(), textDBName.getText());

        if (configManager.saveConfig()){
            DBController dbController = new DBController();
            Main.allLocations = dbController.getAllLocations();
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

    private Map<String, Measurement> measurmentMapFrankfurt = new HashMap<>();

    public void switchToHome(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToFrankfurt(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("frankfurt.fxml"));
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

    @FXML
    public void initialize(){

    }

}
