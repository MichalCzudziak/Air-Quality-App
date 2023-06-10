package com.project.airquality;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import objects.Measurement;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
public class mainController {
    public Button settingsButton;
    public Label avgArq;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ProgressBar arqProgressbar;

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
    public void switchToKelsterbach(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("kelsterbach.fxml"));
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
    public void switchToUAS(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("uas.fxml"));
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

    public void setBarColor(double average){
        if (average < 50) {
            arqProgressbar.setStyle("-fx-accent: green");
        } else {
            arqProgressbar.setStyle("-fx-accent: red");
        }
    }

    @FXML
    public void initialize(){
        // SELECT * FROM db
        Measurement m1 = new Measurement(1, "20:00", 24.1, 30, 40, 70, 1000, 80);
        measurmentMapFrankfurt.put(m1.getTimestamp(), m1);
        double average = measurmentMapFrankfurt.get("20:00").getAirIndex();
        arqProgressbar.setProgress(average/100);
        setBarColor(average+100);
    }

}
