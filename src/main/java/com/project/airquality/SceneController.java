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
    public Label avgTemperature;
    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    private Button testButton;

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

    public XYChart.Series pressureFrankfurt = new XYChart.Series();
    public XYChart.Series fineDustFrankfurt = new XYChart.Series();
    public XYChart.Series humidityFrankfurt = new XYChart.Series();
    public XYChart.Series co2Frankfurt = new XYChart.Series();
    public XYChart.Series brightnessFrankfurt = new XYChart.Series();
    public XYChart.Series airQualityFrankfurt = new XYChart.Series();

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
        if(fineCheckBox.isSelected()){
            lineChart.getData().addAll(fineDustFrankfurt);
        }
        if(!fineCheckBox.isSelected()){
            lineChart.getData().removeAll(fineDustFrankfurt);
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
        }
        if(!arqCheckbox.isSelected()){
            lineChart.getData().removeAll(airQualityFrankfurt);
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
    @FXML
    void getTestFunction(ActionEvent event) {
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
            avgTemperature.setText(Double.toString(average));
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

        if(!pressureFrankfurt.getData().isEmpty()){
            pressCheckbox.setDisable(false);
            double average = calculateAverage(pressureFrankfurt);
            avgPressure.setDisable(false);
            avgPressure.setText(Double.toString(average));
        }
    }


    // TODO Fill the hashmaps with data from database
    private Map<String, Measurement> measurmentMapFrankfurt = new HashMap<>();
    private Map<String, Measurement> measurmentMapKelsterbach = new HashMap<>();
    private Map<String, Measurement> measurmentMapUAS = new HashMap<>();
    private Map<String, Measurement> measurmentMapMaintal = new HashMap<>();

    public XYChart.Series createChart(Map<String, Measurement> map, String choice){
        XYChart.Series chart = new XYChart.Series();
        if(choice.equals("Temperature")){
            for(Map.Entry<String, Measurement> entry : map.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue().getTemperature();
                chart.getData().add(new XYChart.Data(key, value));
            }
        }
        if(choice.equals("CO2")){
            for(Map.Entry<String, Measurement> entry : map.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue().getCo2Level();
                chart.getData().add(new XYChart.Data(key, value));
            }
        }
        if(choice.equals("Fine dust")){
            for(Map.Entry<String, Measurement> entry : map.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue().getFineDustLevel();
                chart.getData().add(new XYChart.Data(key, value));
            }
        }
        if(choice.equals("Brightness")){
            for(Map.Entry<String, Measurement> entry : map.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue().getBrightnessLevel();
                chart.getData().add(new XYChart.Data(key, value));
            }
        }
        if(choice.equals("Airquality")){
            for(Map.Entry<String, Measurement> entry : map.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue().getAirIndex();
                chart.getData().add(new XYChart.Data(key, value));
            }
        }
        if(choice.equals("Pressure")){
            for(Map.Entry<String, Measurement> entry : map.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue().getPressureLevel();
                chart.getData().add(new XYChart.Data(key, value));
            }
        }
        if(choice.equals("Humidity")){
            for(Map.Entry<String, Measurement> entry : map.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue().getHumidityLevel();
                chart.getData().add(new XYChart.Data(key, value));
            }
        }
        return chart;
    }

    public double calculateAverage(Map<String, Measurement> map, String choice){
        double average = 0;
        if(choice.equals("Temperature")){
            for(Map.Entry<String, Measurement> entry : map.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue().getTemperature();
                average = average + value;
            }
            average = average / map.size();
        }
        if(choice.equals("CO2")){
            for(Map.Entry<String, Measurement> entry : map.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue().getCo2Level();
                average = average + value;
            }
            average = average / map.size();
        }
        if(choice.equals("Fine dust")){
            for(Map.Entry<String, Measurement> entry : map.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue().getFineDustLevel();
                average = average + value;
            }
            average = average / map.size();
        }
        if(choice.equals("Brightness")){
            for(Map.Entry<String, Measurement> entry : map.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue().getBrightnessLevel();
                average = average + value;
            }
            average = average / map.size();
        }
        if(choice.equals("Airquality")){
            for(Map.Entry<String, Measurement> entry : map.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue().getAirIndex();
                average = average + value;
            }
            average = average / map.size();
        }
        if(choice.equals("Pressure")){
            for(Map.Entry<String, Measurement> entry : map.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue().getPressureLevel();
                average = average + value;
            }
            average = average / map.size();
        }
        if(choice.equals("Humidity")){
            for(Map.Entry<String, Measurement> entry : map.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue().getHumidityLevel();
                average = average + value;
            }
            average = average / map.size();
        }
        return average;
    }

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


}
