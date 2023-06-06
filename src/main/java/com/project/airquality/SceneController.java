package com.project.airquality;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SceneController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label arqAvg;

    @FXML
    private Label brigAvg;

    @FXML
    private Label co2Avg;

    @FXML
    private Label fineAvg;

    @FXML
    private LineChart lineChart;

    @FXML
    private Label pressAvg;

    @FXML
    private Label tempAvg;

    @FXML
    private Label wetAvg;

    @FXML
    private Button testButton;

    @FXML
    private AreaChart areaChart;
    @FXML
    void getTestFunction(ActionEvent event) {
        XYChart.Series temperatureFrankfurt = new XYChart.Series();
        temperatureFrankfurt.setName("Temperature");
        temperatureFrankfurt.getData().add(new XYChart.Data("20:00", 20.0));
        temperatureFrankfurt.getData().add(new XYChart.Data("21:00", 19.0));
        temperatureFrankfurt.getData().add(new XYChart.Data("22:00", 18.2));
        temperatureFrankfurt.getData().add(new XYChart.Data("23:00", 14.1));
        temperatureFrankfurt.getData().add(new XYChart.Data("24:00", 10.0));

        XYChart.Series pressureFrankfurt = new XYChart.Series();
        pressureFrankfurt.getData().add(new XYChart.Data("20:00", 995));
        pressureFrankfurt.getData().add(new XYChart.Data("21:00", 995.02));
        pressureFrankfurt.getData().add(new XYChart.Data("22:00", 996.10));
        pressureFrankfurt.getData().add(new XYChart.Data("23:00", 1002));
        pressureFrankfurt.getData().add(new XYChart.Data("24:00", 1000.003));



        XYChart.Series series1 = new XYChart.Series();
        series1.setName("John");
        series1.getData().add(new XYChart.Data("Monday", 3));
        series1.getData().add(new XYChart.Data("Tuesday", 4));
        series1.getData().add(new XYChart.Data("Wednesday", 3));
        series1.getData().add(new XYChart.Data("Thursday", 5));
        series1.getData().add(new XYChart.Data("Friday", 4));
        series1.getData().add(new XYChart.Data("Saturday", 10));
        series1.getData().add(new XYChart.Data("Sunday", 12));

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Jane");
        series2.getData().add(new XYChart.Data("Monday", 1));
        series2.getData().add(new XYChart.Data("Tuesday", 3));
        series2.getData().add(new XYChart.Data("Wednesday", 4));

        series2.getData().add(new XYChart.Data("Thursday", 3));
        series2.getData().add(new XYChart.Data("Friday", 3));
        series2.getData().add(new XYChart.Data("Saturday", 5));
        series2.getData().add(new XYChart.Data("Sunday", 4));
        areaChart.getData().addAll(pressureFrankfurt);
    }

    @FXML
    void getDataFunction(ActionEvent event) {
        measurmentMapFrankfurt = initializeRandomData();
        XYChart.Series temperatureFrankfurt = createChart(measurmentMapFrankfurt, "Temperature");
        temperatureFrankfurt.setName("Temperature");

        XYChart.Series co2Frankfurt = createChart(measurmentMapFrankfurt, "CO2");
        co2Frankfurt.setName("CO2");
        XYChart.Series fineDustFrankfurt = createChart(measurmentMapFrankfurt, "Fine dust");
        fineDustFrankfurt.setName("PM");
        XYChart.Series pressureFrankfurt = createChart(measurmentMapFrankfurt, "Pressure");
        pressureFrankfurt.setName("Pressure");
        XYChart.Series wetnessFrankfurt = createChart(measurmentMapFrankfurt, "Wetness");
        wetnessFrankfurt.setName("Wetness");
        XYChart.Series brightnessFrankfurt = createChart(measurmentMapFrankfurt, "Wetness");
        brightnessFrankfurt.setName("Brightness");
        XYChart.Series airqualityFrankfurt = createChart(measurmentMapFrankfurt, "Airquality");
        airqualityFrankfurt.setName("ARQ Index");

        lineChart.getData().add(temperatureFrankfurt);
        lineChart.getData().add(co2Frankfurt);
        lineChart.getData().add(fineDustFrankfurt);
        lineChart.getData().add(pressureFrankfurt);
        lineChart.getData().add(wetnessFrankfurt);
        lineChart.getData().add(brightnessFrankfurt);
        lineChart.getData().add(airqualityFrankfurt);

        arqAvg.setText(Double.toString(calculateAverage(measurmentMapFrankfurt, "Airquality")));
        brigAvg.setText(Double.toString(calculateAverage(measurmentMapFrankfurt, "Brightness")));
        co2Avg.setText(Double.toString(calculateAverage(measurmentMapFrankfurt, "CO2")));
        wetAvg.setText(Double.toString(calculateAverage(measurmentMapFrankfurt, "Wetness")));
        tempAvg.setText(Double.toString(calculateAverage(measurmentMapFrankfurt, "Temperature")));
        fineAvg.setText(Double.toString(calculateAverage(measurmentMapFrankfurt, "Fine Dust")));
        pressAvg.setText(Double.toString(calculateAverage(measurmentMapFrankfurt, "Pressure")));

    }

    private Map<String, Measurment> measurmentMapFrankfurt = new HashMap<>();
    private Map<String, Measurment> measurmentMapKelsterbach = new HashMap<>();
    private Map<String, Measurment> measurmentMapUAS = new HashMap<>();
    private Map<String, Measurment> measurmentMapMaintal = new HashMap<>();
    private static final DecimalFormat df = new DecimalFormat("0.00");
    public static double nextDoubleBetween2(double min, double max) {
        return (new Random().nextDouble() * (max - min)) + min;
    }
    public Map<String,Measurment> initializeRandomData(){
        Map<String,Measurment> returnMap = new HashMap<>();
        int hour = 0;
        for (int i = 1; i <= 24; i++){
            double randomTemperature = 30.0;
            double randomCO2 = 20;
            double randomFineDust = 60;
            double randomBrightness = 70;
            double randomWetness = 300;
            double randomAirquality = 600;
            double randomPressure = 100;
            returnMap.put(Integer.toString(hour), new Measurment(Integer.toString(hour), randomTemperature+i, randomCO2+i, randomFineDust+i, randomBrightness+i, randomPressure+i, randomWetness+i, randomAirquality+i));
            hour +=1;
        }
        return returnMap;
    }

    public XYChart.Series createChart(Map<String, Measurment> map, String choice){
        XYChart.Series chart = new XYChart.Series();
        if(choice.equals("Temperature")){
            for(Map.Entry<String, Measurment> entry : map.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue().getTemperature();
                chart.getData().add(new XYChart.Data(key, value));
            }
        }
        if(choice.equals("CO2")){
            for(Map.Entry<String, Measurment> entry : map.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue().getCo2Level();
                chart.getData().add(new XYChart.Data(key, value));
            }
        }
        if(choice.equals("Fine dust")){
            for(Map.Entry<String, Measurment> entry : map.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue().getFineDustLevel();
                chart.getData().add(new XYChart.Data(key, value));
            }
        }
        if(choice.equals("Brightness")){
            for(Map.Entry<String, Measurment> entry : map.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue().getBrightnessLevel();
                chart.getData().add(new XYChart.Data(key, value));
            }
        }
        if(choice.equals("Airquality")){
            for(Map.Entry<String, Measurment> entry : map.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue().getAirIndex();
                chart.getData().add(new XYChart.Data(key, value));
            }
        }
        if(choice.equals("Pressure")){
            for(Map.Entry<String, Measurment> entry : map.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue().getPressureLevel();
                chart.getData().add(new XYChart.Data(key, value));
            }
        }
        if(choice.equals("Wetness")){
            for(Map.Entry<String, Measurment> entry : map.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue().getWetLevel();
                chart.getData().add(new XYChart.Data(key, value));
            }
        }
        return chart;
    }

    public double calculateAverage(Map<String, Measurment> map, String choice){
        double average = 0;
        if(choice.equals("Temperature")){
            for(Map.Entry<String, Measurment> entry : map.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue().getTemperature();
                average = average + value;
            }
            average = average / map.size();
        }
        if(choice.equals("CO2")){
            for(Map.Entry<String, Measurment> entry : map.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue().getCo2Level();
                average = average + value;
            }
            average = average / map.size();
        }
        if(choice.equals("Fine dust")){
            for(Map.Entry<String, Measurment> entry : map.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue().getFineDustLevel();
                average = average + value;
            }
            average = average / map.size();
        }
        if(choice.equals("Brightness")){
            for(Map.Entry<String, Measurment> entry : map.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue().getBrightnessLevel();
                average = average + value;
            }
            average = average / map.size();
        }
        if(choice.equals("Airquality")){
            for(Map.Entry<String, Measurment> entry : map.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue().getAirIndex();
                average = average + value;
            }
            average = average / map.size();
        }
        if(choice.equals("Pressure")){
            for(Map.Entry<String, Measurment> entry : map.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue().getPressureLevel();
                average = average + value;
            }
            average = average / map.size();
        }
        if(choice.equals("Wetness")){
            for(Map.Entry<String, Measurment> entry : map.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue().getWetLevel();
                average = average + value;
            }
            average = average / map.size();
        }
        return average;
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

}
