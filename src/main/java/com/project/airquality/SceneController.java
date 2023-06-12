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
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import objects.Measurement;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SceneController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ProgressBar arqProgressbar;
    @FXML
    private AnchorPane locMenu;

    @FXML
    private Label avgArq;

    @FXML
    private Label avgBrigthness;

    @FXML
    private Label avgCO2;

    @FXML
    private Label avgFinedust;

    @FXML
    private Label avgHumidity;

    @FXML
    private Label avgPressure;

    @FXML
    private Label avgTemperature;
    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    private Button dbConnectButton;

    @FXML
    private Button settingsButton;

    @FXML
    private TextField textDBName;

    @FXML
    private TextField textDBURL;

    @FXML
    private TextField textUsername;

    @FXML
    private PasswordField textPassword;

    @FXML
    private RadioButton arqCheckbox;

    @FXML
    private RadioButton brighCheckbox;

    @FXML
    private RadioButton co2Checkbox;

    @FXML
    private RadioButton fineCheckBox;

    @FXML
    private RadioButton humCheckbox;

    @FXML
    private RadioButton pressCheckbox;

    @FXML
    private RadioButton tempCheckbox;

    private Map<String, Measurement> measurmentMapFrankfurt = new HashMap<>();
    private Map<String, Measurement> measurmentMapKelsterbach = new HashMap<>();
    private Map<String, Measurement> measurmentMapUAS = new HashMap<>();
    private Map<String, Measurement> measurmentMapMaintal = new HashMap<>();

    public XYChart.Series pressureFrankfurt = new XYChart.Series();
    public XYChart.Series fineDustFrankfurt = new XYChart.Series();
    public XYChart.Series humidityFrankfurt = new XYChart.Series();
    public XYChart.Series co2Frankfurt = new XYChart.Series();
    public XYChart.Series brightnessFrankfurt = new XYChart.Series();
    public XYChart.Series airQualityFrankfurt = new XYChart.Series();

    public XYChart.Series fineDustFrankfurtPM1 = new XYChart.Series();
    public XYChart.Series fineDustFrankfurtPM2 = new XYChart.Series();
    public XYChart.Series fineDustFrankfurtPM3 = new XYChart.Series();

    public XYChart.Series arqBoundary = new XYChart.Series();

    public XYChart.Series temperatureFrankfurt = new XYChart.Series();

    private Tooltip tooltip = new Tooltip();

    @FXML
    public void showTemperature(ActionEvent event) {
        if (tempCheckbox.isSelected()) {
            lineChart.getData().add(temperatureFrankfurt);
            enableDataPointHover(temperatureFrankfurt);
        } else {
            lineChart.getData().removeAll(temperatureFrankfurt);
            disableDataPointHover(temperatureFrankfurt);
        }
    }
    private void enableDataPointHover(XYChart.Series<String, Number> series) {
        for (XYChart.Data<String, Number> data : series.getData()) {
            Node node = data.getNode();
            Tooltip tooltip = new Tooltip();
            tooltip.setShowDelay(Duration.ZERO);
            tooltip.setText(String.format("Uhr: %s\nWert: %s", data.getXValue(), data.getYValue()));
            tooltip.setStyle("-fx-font-size: 14px"); // Set the font size
            Tooltip.install(node, tooltip);
            node.setOnMouseEntered(e -> tooltip.show(node, e.getScreenX(), e.getScreenY() + 10));
            node.setOnMouseExited(e -> tooltip.hide());
        }
    }

    private void disableDataPointHover(XYChart.Series<String, Number> series) {
        for (XYChart.Data<String, Number> data : series.getData()) {
            Node node = data.getNode();
            Tooltip.uninstall(node, tooltip);
            node.setOnMouseEntered(null);
            node.setOnMouseExited(null);
        }
    }
    @FXML
    public void showPressure(ActionEvent event) {
        if(pressCheckbox.isSelected()){
            lineChart.getData().addAll(pressureFrankfurt);
            enableDataPointHover(pressureFrankfurt);
        }
        if(!pressCheckbox.isSelected()){
            lineChart.getData().removeAll(pressureFrankfurt);
            disableDataPointHover(pressureFrankfurt);
        }
    }
    @FXML
    public void showFineDust(ActionEvent event) {
        if (fineCheckBox.isSelected()) {
            lineChart.getData().addAll(fineDustFrankfurtPM1);
            lineChart.getData().addAll(fineDustFrankfurtPM2);
            lineChart.getData().addAll(fineDustFrankfurtPM3);
            enableDataPointHover(fineDustFrankfurtPM1);
            enableDataPointHover(fineDustFrankfurtPM2);
            enableDataPointHover(fineDustFrankfurtPM3);
        }
        if (!fineCheckBox.isSelected()) {
            lineChart.getData().removeAll(fineDustFrankfurtPM1);
            lineChart.getData().removeAll(fineDustFrankfurtPM2);
            lineChart.getData().removeAll(fineDustFrankfurtPM3);
            enableDataPointHover(fineDustFrankfurtPM1);
            enableDataPointHover(fineDustFrankfurtPM2);
            enableDataPointHover(fineDustFrankfurtPM3);
        }
    }
    @FXML
    public void showHumidity(ActionEvent event) {
        if (humCheckbox.isSelected()) {
            lineChart.getData().addAll(humidityFrankfurt);
        }
        if (!humCheckbox.isSelected()) {
            lineChart.getData().removeAll(humidityFrankfurt);
        }
    }
    @FXML
    public void showCO2(ActionEvent event) {
        if (co2Checkbox.isSelected()) {
            lineChart.getData().addAll(co2Frankfurt);
        }
        if (!co2Checkbox.isSelected()) {
            lineChart.getData().removeAll(co2Frankfurt);
        }
    }
    @FXML
    public void showBrightness(ActionEvent event) {
        if (brighCheckbox.isSelected()) {
            lineChart.getData().addAll(brightnessFrankfurt);
        }
        if (!brighCheckbox.isSelected()) {
            lineChart.getData().removeAll(brightnessFrankfurt);
        }
    }
    @FXML
    public void showAirquality(ActionEvent event) {
        if(arqCheckbox.isSelected()){
            lineChart.getData().addAll(airQualityFrankfurt);
            lineChart.getData().addAll(arqBoundary);
            for (Object obj : arqBoundary.getData()) {
                if (obj instanceof XYChart.Data) {
                    XYChart.Data<String, Number> data = (XYChart.Data<String, Number>) obj;
                    data.getNode().setOpacity(0);
                }
            }
        }
        if(!arqCheckbox.isSelected()){
            lineChart.getData().removeAll(airQualityFrankfurt);
            lineChart.getData().removeAll(arqBoundary);
        }
    }

    public double calculateAverage(XYChart.Series<String, Number> chart) {
        double sum = 0.0;
        int count = 0;

        for (XYChart.Data<String, Number> data : chart.getData()) {
            double value = data.getYValue().doubleValue();
            sum += value;
            count++;
        }
        double average = sum / count;
        average = Math.round(average * 10.0) / 10.0;
        return average;
    }

    // TEST DATA

    // TODO Create realistic sample data

    // TODO Fill the hashmaps with data from database


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

        temperatureFrankfurt.setName("Temperature");
        temperatureFrankfurt.getData().add(new XYChart.Data("00:00", 20.0));
        temperatureFrankfurt.getData().add(new XYChart.Data("01:00", 19.0));
        temperatureFrankfurt.getData().add(new XYChart.Data("02:00", 18.2));
        temperatureFrankfurt.getData().add(new XYChart.Data("03:00", 14.1));
        temperatureFrankfurt.getData().add(new XYChart.Data("04:00", 20.0));
        temperatureFrankfurt.getData().add(new XYChart.Data("05:00", 19.0));
        temperatureFrankfurt.getData().add(new XYChart.Data("06:00", 18.2));
        temperatureFrankfurt.getData().add(new XYChart.Data("07:00", 14.1));
        temperatureFrankfurt.getData().add(new XYChart.Data("08:00", 21.0));
        temperatureFrankfurt.getData().add(new XYChart.Data("09:00", 19.0));
        temperatureFrankfurt.getData().add(new XYChart.Data("10:00", 18.2));
        temperatureFrankfurt.getData().add(new XYChart.Data("11:00", 14.1));
        temperatureFrankfurt.getData().add(new XYChart.Data("12:00", 10.0));
        temperatureFrankfurt.getData().add(new XYChart.Data("13:00", 20.0));
        temperatureFrankfurt.getData().add(new XYChart.Data("14:00", 19.0));
        temperatureFrankfurt.getData().add(new XYChart.Data("15:00", 18.2));
        temperatureFrankfurt.getData().add(new XYChart.Data("16:00", 14.1));
        temperatureFrankfurt.getData().add(new XYChart.Data("17:00", 20.0));
        temperatureFrankfurt.getData().add(new XYChart.Data("18:00", 19.0));
        temperatureFrankfurt.getData().add(new XYChart.Data("19:00", 18.2));
        temperatureFrankfurt.getData().add(new XYChart.Data("20:00", 14.1));
        temperatureFrankfurt.getData().add(new XYChart.Data("21:00", 14.1));
        temperatureFrankfurt.getData().add(new XYChart.Data("22:00", 14.1));
        temperatureFrankfurt.getData().add(new XYChart.Data("23:00", 14.1));

        if(!temperatureFrankfurt.getData().isEmpty()){
            tempCheckbox.setDisable(false);
            double average = calculateAverage(temperatureFrankfurt);
            avgTemperature.setDisable(false);
            avgTemperature.setText(Double.toString(average) + " C");
        }

        pressureFrankfurt.setName("Pressure");
        pressureFrankfurt.getData().add(new XYChart.Data("00:00", 995));
        pressureFrankfurt.getData().add(new XYChart.Data("01:00", 996));
        pressureFrankfurt.getData().add(new XYChart.Data("02:00", 1002));
        pressureFrankfurt.getData().add(new XYChart.Data("03:00", 970));
        pressureFrankfurt.getData().add(new XYChart.Data("04:00", 1003));
        pressureFrankfurt.getData().add(new XYChart.Data("05:00", 1002));
        pressureFrankfurt.getData().add(new XYChart.Data("06:00", 1001));
        pressureFrankfurt.getData().add(new XYChart.Data("07:00", 1005));
        pressureFrankfurt.getData().add(new XYChart.Data("08:00", 1001));
        pressureFrankfurt.getData().add(new XYChart.Data("09:00", 1002));
        pressureFrankfurt.getData().add(new XYChart.Data("10:00", 1050));
        pressureFrankfurt.getData().add(new XYChart.Data("11:00", 1005));
        pressureFrankfurt.getData().add(new XYChart.Data("12:00", 999));
        pressureFrankfurt.getData().add(new XYChart.Data("13:00", 998));
        pressureFrankfurt.getData().add(new XYChart.Data("14:00", 996));
        pressureFrankfurt.getData().add(new XYChart.Data("15:00", 1002));
        pressureFrankfurt.getData().add(new XYChart.Data("16:00", 1002));
        pressureFrankfurt.getData().add(new XYChart.Data("17:00", 1001));
        pressureFrankfurt.getData().add(new XYChart.Data("18:00", 996));
        pressureFrankfurt.getData().add(new XYChart.Data("19:00", 997));
        pressureFrankfurt.getData().add(new XYChart.Data("20:00", 998));
        pressureFrankfurt.getData().add(new XYChart.Data("21:00", 999));
        pressureFrankfurt.getData().add(new XYChart.Data("22:00", 1001));
        pressureFrankfurt.getData().add(new XYChart.Data("23:00", 1002));


        airQualityFrankfurt.setName("ARQ");
        airQualityFrankfurt.getData().add(new XYChart.Data("00:00", 20));
        airQualityFrankfurt.getData().add(new XYChart.Data("01:00", 40));
        airQualityFrankfurt.getData().add(new XYChart.Data("02:00", 60));
        airQualityFrankfurt.getData().add(new XYChart.Data("03:00", 55));
        airQualityFrankfurt.getData().add(new XYChart.Data("04:00", 10));
        airQualityFrankfurt.getData().add(new XYChart.Data("05:00", 25));

        arqBoundary.setName("MAX. ARQ LEVEL");
        arqBoundary.getData().add(new XYChart.Data("00:00", 50));
        arqBoundary.getData().add(new XYChart.Data("01:00", 50));
        arqBoundary.getData().add(new XYChart.Data("02:00", 50));
        arqBoundary.getData().add(new XYChart.Data("03:00", 50));
        arqBoundary.getData().add(new XYChart.Data("04:00", 50));
        arqBoundary.getData().add(new XYChart.Data("05:00", 50));
        arqBoundary.getData().add(new XYChart.Data("06:00", 50));
        arqBoundary.getData().add(new XYChart.Data("07:00", 50));
        arqBoundary.getData().add(new XYChart.Data("08:00", 50));
        arqBoundary.getData().add(new XYChart.Data("09:00", 50));
        arqBoundary.getData().add(new XYChart.Data("10:00", 50));
        arqBoundary.getData().add(new XYChart.Data("11:00", 50));
        arqBoundary.getData().add(new XYChart.Data("12:00", 50));
        arqBoundary.getData().add(new XYChart.Data("13:00", 50));
        arqBoundary.getData().add(new XYChart.Data("14:00", 50));
        arqBoundary.getData().add(new XYChart.Data("15:00", 50));
        arqBoundary.getData().add(new XYChart.Data("16:00", 50));
        arqBoundary.getData().add(new XYChart.Data("17:00", 50));
        arqBoundary.getData().add(new XYChart.Data("18:00", 50));
        arqBoundary.getData().add(new XYChart.Data("19:00", 50));
        arqBoundary.getData().add(new XYChart.Data("20:00", 50));
        arqBoundary.getData().add(new XYChart.Data("21:00", 50));
        arqBoundary.getData().add(new XYChart.Data("22:00", 50));
        arqBoundary.getData().add(new XYChart.Data("23:00", 50));

        fineDustFrankfurtPM1.setName("PM1.0");
        fineDustFrankfurtPM1.getData().add(new XYChart.Data("00:00", 5));
        fineDustFrankfurtPM1.getData().add(new XYChart.Data("01:00", 6));
        fineDustFrankfurtPM1.getData().add(new XYChart.Data("02:00", 6));
        fineDustFrankfurtPM1.getData().add(new XYChart.Data("03:00", 5));
        fineDustFrankfurtPM1.getData().add(new XYChart.Data("04:00", 6));
        fineDustFrankfurtPM1.getData().add(new XYChart.Data("05:00", 7));
        fineDustFrankfurtPM1.getData().add(new XYChart.Data("06:00", 6));
        fineDustFrankfurtPM1.getData().add(new XYChart.Data("07:00", 6));
        fineDustFrankfurtPM1.getData().add(new XYChart.Data("08:00", 6));
        fineDustFrankfurtPM1.getData().add(new XYChart.Data("09:00", 7));
        fineDustFrankfurtPM1.getData().add(new XYChart.Data("10:00", 8));
        fineDustFrankfurtPM1.getData().add(new XYChart.Data("11:00", 9));
        fineDustFrankfurtPM1.getData().add(new XYChart.Data("12:00", 8));
        fineDustFrankfurtPM1.getData().add(new XYChart.Data("13:00", 8));
        fineDustFrankfurtPM1.getData().add(new XYChart.Data("14:00", 7));
        fineDustFrankfurtPM1.getData().add(new XYChart.Data("15:00", 6));
        fineDustFrankfurtPM1.getData().add(new XYChart.Data("16:00", 5));
        fineDustFrankfurtPM1.getData().add(new XYChart.Data("17:00", 6));
        fineDustFrankfurtPM1.getData().add(new XYChart.Data("18:00", 4));
        fineDustFrankfurtPM1.getData().add(new XYChart.Data("19:00", 4));
        fineDustFrankfurtPM1.getData().add(new XYChart.Data("20:00", 3));
        fineDustFrankfurtPM1.getData().add(new XYChart.Data("21:00", 3));
        fineDustFrankfurtPM1.getData().add(new XYChart.Data("22:00", 2));
        fineDustFrankfurtPM1.getData().add(new XYChart.Data("23:00", 3));

        if(!fineDustFrankfurtPM1.getData().isEmpty()){
            fineCheckBox.setDisable(false);
            double average = calculateAverage(fineDustFrankfurtPM1);
            avgFinedust.setDisable(false);
            avgFinedust.setText(Double.toString(average));
        }


        fineDustFrankfurtPM2.setName("PM2.5");
        fineDustFrankfurtPM2.getData().add(new XYChart.Data("00:00", 7));
        fineDustFrankfurtPM2.getData().add(new XYChart.Data("01:00", 7));
        fineDustFrankfurtPM2.getData().add(new XYChart.Data("02:00", 7));
        fineDustFrankfurtPM2.getData().add(new XYChart.Data("03:00", 7));
        fineDustFrankfurtPM2.getData().add(new XYChart.Data("04:00", 8));
        fineDustFrankfurtPM2.getData().add(new XYChart.Data("05:00", 5));
        fineDustFrankfurtPM2.getData().add(new XYChart.Data("06:00", 6));
        fineDustFrankfurtPM2.getData().add(new XYChart.Data("07:00", 8));
        fineDustFrankfurtPM2.getData().add(new XYChart.Data("08:00", 8));
        fineDustFrankfurtPM2.getData().add(new XYChart.Data("09:00", 9));
        fineDustFrankfurtPM2.getData().add(new XYChart.Data("10:00", 4));
        fineDustFrankfurtPM2.getData().add(new XYChart.Data("11:00", 5));
        fineDustFrankfurtPM2.getData().add(new XYChart.Data("12:00", 6));
        fineDustFrankfurtPM2.getData().add(new XYChart.Data("13:00", 6));
        fineDustFrankfurtPM2.getData().add(new XYChart.Data("14:00", 4));
        fineDustFrankfurtPM2.getData().add(new XYChart.Data("15:00", 5));
        fineDustFrankfurtPM2.getData().add(new XYChart.Data("16:00", 6));
        fineDustFrankfurtPM2.getData().add(new XYChart.Data("17:00", 5));
        fineDustFrankfurtPM2.getData().add(new XYChart.Data("18:00", 7));
        fineDustFrankfurtPM2.getData().add(new XYChart.Data("19:00", 7));
        fineDustFrankfurtPM2.getData().add(new XYChart.Data("20:00", 7));
        fineDustFrankfurtPM2.getData().add(new XYChart.Data("21:00", 3));
        fineDustFrankfurtPM2.getData().add(new XYChart.Data("22:00", 2));
        fineDustFrankfurtPM2.getData().add(new XYChart.Data("23:00", 3));

        if(!fineDustFrankfurtPM2.getData().isEmpty()){
            fineCheckBox.setDisable(false);
            double average = calculateAverage(fineDustFrankfurtPM2);
            avgFinedust.setDisable(false);
            avgFinedust.setText(Double.toString(average));
        }

        fineDustFrankfurtPM3.setName("PM10");
        fineDustFrankfurtPM3.getData().add(new XYChart.Data("00:00", 8));
        fineDustFrankfurtPM3.getData().add(new XYChart.Data("01:00", 8));
        fineDustFrankfurtPM3.getData().add(new XYChart.Data("02:00", 9));
        fineDustFrankfurtPM3.getData().add(new XYChart.Data("03:00", 8));
        fineDustFrankfurtPM3.getData().add(new XYChart.Data("04:00", 9));
        fineDustFrankfurtPM3.getData().add(new XYChart.Data("05:00", 8));
        fineDustFrankfurtPM3.getData().add(new XYChart.Data("06:00", 9));
        fineDustFrankfurtPM3.getData().add(new XYChart.Data("07:00", 10));
        fineDustFrankfurtPM3.getData().add(new XYChart.Data("08:00", 10));
        fineDustFrankfurtPM3.getData().add(new XYChart.Data("09:00", 11));
        fineDustFrankfurtPM3.getData().add(new XYChart.Data("10:00", 12));
        fineDustFrankfurtPM3.getData().add(new XYChart.Data("11:00", 10));
        fineDustFrankfurtPM3.getData().add(new XYChart.Data("12:00", 9));
        fineDustFrankfurtPM3.getData().add(new XYChart.Data("13:00", 10));
        fineDustFrankfurtPM3.getData().add(new XYChart.Data("14:00", 10));
        fineDustFrankfurtPM3.getData().add(new XYChart.Data("15:00", 12));
        fineDustFrankfurtPM3.getData().add(new XYChart.Data("16:00", 15));
        fineDustFrankfurtPM3.getData().add(new XYChart.Data("17:00", 16));
        fineDustFrankfurtPM3.getData().add(new XYChart.Data("18:00", 12));
        fineDustFrankfurtPM3.getData().add(new XYChart.Data("19:00", 9));
        fineDustFrankfurtPM3.getData().add(new XYChart.Data("20:00", 8));
        fineDustFrankfurtPM3.getData().add(new XYChart.Data("21:00", 5));
        fineDustFrankfurtPM3.getData().add(new XYChart.Data("22:00", 6));
        fineDustFrankfurtPM3.getData().add(new XYChart.Data("23:00", 4));

        if(!fineDustFrankfurtPM3.getData().isEmpty()){
            fineCheckBox.setDisable(false);
            double average = calculateAverage(fineDustFrankfurtPM3);
            avgFinedust.setDisable(false);
            avgFinedust.setText(Double.toString(average));
        }

        if(!airQualityFrankfurt.getData().isEmpty()){
            arqCheckbox.setDisable(false);
            double average = calculateAverage(airQualityFrankfurt);
            avgArq.setDisable(false);
            avgArq.setText(Double.toString(average));
        }



        if(!pressureFrankfurt.getData().isEmpty()){
            pressCheckbox.setDisable(false);
            double average = calculateAverage(pressureFrankfurt);
            avgPressure.setDisable(false);
            avgPressure.setText(Double.toString(average) + " HPA");
        }
    }

}
