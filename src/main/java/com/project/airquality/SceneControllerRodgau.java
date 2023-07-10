package com.project.airquality;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import objects.Location;
import objects.Measurement;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SceneControllerRodgau {
    /**
     *   Variables which are needed globally
     */
    @FXML
    private Parent root;
    private Stage stage;
    private Scene scene;
    private LocalDateTime dateTime;
    private int selectedDay = 0;
    private int selectedMonth = 0;
    private int selectedYear = 0;
    private int selectedCheckboxes = 0;

    /**
     *   All required FXML GUI Components declaration needed to display all the functionalities
     */

    @FXML
    private Label localTime;
    @FXML
    private RadioButton scalePM1Checkbox;
    @FXML
    private RadioButton scalePM2Checkbox;
    @FXML
    private RadioButton scalePM10Checkbox;
    @FXML
    private RadioButton scaleCO2Checkbox;
    @FXML
    private DatePicker datepicker;
    @FXML
    private Label actualDate;
    @FXML
    private Label brightnessLabel;
    @FXML
    private Label co2Label;
    @FXML
    private Label humidityLabel;
    @FXML
    private Label pressureLabel;
    @FXML
    private Label temperatureLabel;
    @FXML
    private LineChart<String, Number> lineChart;
    @FXML
    private RadioButton brighCheckbox;
    @FXML
    private RadioButton co2Checkbox;
    @FXML
    private RadioButton pm10Checkbox;
    @FXML
    private Label pm10Label;
    @FXML
    private RadioButton pm1Checkbox;
    @FXML
    private Label pm1Label;
    @FXML
    private RadioButton pm2Checkbox;
    @FXML
    private Label pm2Label;
    @FXML
    private RadioButton humCheckbox;
    @FXML
    private RadioButton pressCheckbox;
    @FXML
    private RadioButton tempCheckbox;

    /**
     *   Arraylists and maps needed to store all the information about measurements values
     */

    private ArrayList<Location> list = Main.allLocations;
    private ArrayList<Measurement> rodgau = list.get(1).getMeasurements();
    private Map<String, Integer> tempList = new HashMap<>();
    private Map<String, Integer> co2List = new HashMap<>();
    private Map<String, Integer> pm1List = new HashMap<>();
    private Map<String, Integer> pm2List = new HashMap<>();
    private Map<String, Integer> pm10List = new HashMap<>();
    private Map<String, Integer> brightnessList = new HashMap<>();
    private Map<String, Integer> pressureList = new HashMap<>();
    private Map<String, Integer> humidityList = new HashMap<>();

    /**
     *   XYCharts objects which store the information in graphs
     */

    public XYChart.Series temperatureChart = new XYChart.Series();
    public XYChart.Series pressureChart = new XYChart.Series();
    public XYChart.Series pm1Chart = new XYChart.Series();
    public XYChart.Series pm2Chart = new XYChart.Series();
    public XYChart.Series pm10Chart = new XYChart.Series();
    public XYChart.Series humidityChart = new XYChart.Series();
    public XYChart.Series co2Chart = new XYChart.Series();
    public XYChart.Series brightnessChart = new XYChart.Series();
    public XYChart.Series veryGoodAQIBoundary = new XYChart.Series<>();
    public XYChart.Series goodAQIBoundary = new XYChart.Series<>();
    public XYChart.Series okAQIBoundary = new XYChart.Series<>();
    public XYChart.Series sufficientAQIBoundary = new XYChart.Series<>();
    public XYChart.Series badAQIBoundary = new XYChart.Series<>();



    /**
     *  List gets filtered by date
     */
    public ArrayList <Measurement> filterListByDate(ArrayList<Measurement> measurements, int day, int month, int year) {
        ArrayList<Measurement> filteredList = new ArrayList<>();
        for (Measurement measurement : measurements) {
            setTime(measurement.getTimestamp());
            if (this.selectedDay == day && this.selectedMonth == month && this.selectedYear == year) {
                filteredList.add(measurement);
            }
        }
        return filteredList;
    }

    /**
     *    Setting global Time for Application
     */
    public void setTime(String timestamp) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.dateTime = LocalDateTime.parse(timestamp, formatter);
        this.selectedYear = dateTime.getYear();
        this.selectedMonth = dateTime.getMonthValue();
        this.selectedDay = dateTime.getDayOfMonth();
    }
    /**
     *   Refreshing of Charts
     */
    public void refreshChart(){
        lineChart.getData().clear();
        temperatureChart.getData().clear();
        pressureChart.getData().clear();
        humidityChart.getData().clear();
        pm1Chart.getData().clear();
        pm2Chart.getData().clear();
        pm10Chart.getData().clear();
        co2Chart.getData().clear();
        brightnessChart.getData().clear();
    }
    /**
     *   adding measurements to chart
     */
    public void addMeasurementsToChart(ArrayList<Measurement> measurements) {
        refreshChart();
        veryGoodAQIBoundary.setName("Sehr gut");
        goodAQIBoundary.setName("Gut");
        okAQIBoundary.setName("Befriedigend");
        sufficientAQIBoundary.setName("Ausreichend");
        badAQIBoundary.setName("Schlecht");

        for (Measurement measurement : measurements) {
            // Convert the timestamp to the desired format
            String timestamp = measurement.getTimestamp();
            actualDate.setText(timestamp);
            // Add the temperature data to the series
            tempList.put(timestamp, measurement.getTemperature());
            temperatureChart.getData().add(new XYChart.Data<>(timestamp, measurement.getTemperature()));
            temperatureChart.setName("Temperature");

            pressureList.put(timestamp, measurement.getPressureLevel());
            pressureChart.getData().add(new XYChart.Data<>(timestamp, measurement.getPressureLevel()));
            pressureChart.setName("Pressure");

            humidityList.put(timestamp, measurement.getHumidityLevel());
            humidityChart.getData().add(new XYChart.Data<>(timestamp, measurement.getHumidityLevel()));
            humidityChart.setName("Humidity");

            pm1List.put(timestamp, measurement.getPm1Level());
            pm1Chart.getData().add(new XYChart.Data<>(timestamp, measurement.getPm1Level()));
            pm1Chart.setName("PM 1");

            pm2List.put(timestamp, measurement.getPm2Level());
            pm2Chart.getData().add(new XYChart.Data<>(timestamp, measurement.getPm2Level()));
            pm2Chart.setName("PM 2.5");

            pm10List.put(timestamp, measurement.getPm10Level());
            pm10Chart.getData().add(new XYChart.Data<>(timestamp, measurement.getPm10Level()));
            pm10Chart.setName("PM 10");


            co2List.put(timestamp, measurement.getCo2Level());
            co2Chart.getData().add(new XYChart.Data<>(timestamp, measurement.getCo2Level()));
            co2Chart.setName("CO2");

            brightnessList.put(timestamp, measurement.getBrightnessLevel());
            brightnessChart.getData().add(new XYChart.Data<>(timestamp, measurement.getBrightnessLevel()));
            brightnessChart.setName("Brightness");

        }
    }

    /**
     *   getting highest value of measurement to set the AQI limit in the graph
     */
    public int getHighestValue(ArrayList<Measurement> measurements, String choice){
        int highestPM1 = 0;
        int highestPM2 = 0;
        int highestPM10 = 0;
        int highestCO2 = 0;

        for (Measurement measurement : measurements){
            if(highestPM1 < measurement.getPm1Level()){
                highestPM1 = measurement.getPm1Level();
            }
            if(highestPM2 < measurement.getPm2Level()){
                highestPM2 = measurement.getPm2Level();
            }
            if(highestPM10 < measurement.getPm10Level()){
                highestPM10 = measurement.getPm10Level();
            }
            if(highestCO2 < measurement.getCo2Level()){
                highestCO2 = measurement.getCo2Level();
            }
        }
        if (choice.equals("PM1")) {
            return highestPM1;
        }

        if (choice.equals("PM2")){
            return highestPM2;
        }

        if (choice.equals("PM10")) {
            return highestPM10;
        }

        if (choice.equals("CO2")) {
            return highestCO2;
        }
        return 0;
    }

    public void resetBoundaries(){
        veryGoodAQIBoundary.getData().clear();
        goodAQIBoundary.getData().clear();
        okAQIBoundary.getData().clear();
        sufficientAQIBoundary.getData().clear();
        badAQIBoundary.getData().clear();
        lineChart.getData().removeAll(veryGoodAQIBoundary);
        lineChart.getData().removeAll(goodAQIBoundary);
        lineChart.getData().removeAll(okAQIBoundary);
        lineChart.getData().removeAll(sufficientAQIBoundary);
        lineChart.getData().removeAll(badAQIBoundary);
    }

    /**
     *   creating Boundaries to show the AQI limit in the graph
     */
    public void createPM1Boundaries(ArrayList<Measurement> measurements, String choice){
        int listSize = measurements.size();
        int highestValue = getHighestValue(measurements, choice);
        if(highestValue <=5){
            for (int i = 0; i < listSize; i++) {
                veryGoodAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 5));
            }
            lineChart.getData().add(veryGoodAQIBoundary);
        }
        if(highestValue >=6 && highestValue <=10){
            for (int i = 0; i < listSize; i++) {
                veryGoodAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 5));
                goodAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 10));
            }
            lineChart.getData().add(veryGoodAQIBoundary);
            lineChart.getData().add(goodAQIBoundary);
        }
        if(highestValue >=11 && highestValue <=15){
            for (int i = 0; i < listSize; i++) {
                veryGoodAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 5));
                goodAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 10));
                okAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 15));
            }
            lineChart.getData().add(veryGoodAQIBoundary);
            lineChart.getData().add(goodAQIBoundary);
            lineChart.getData().add(okAQIBoundary);
        }
        if(highestValue >=16 && highestValue <=30){
            for (int i = 0; i < listSize; i++) {
                veryGoodAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 5));
                goodAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 10));
                okAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 15));
                sufficientAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 30));
            }
            lineChart.getData().add(veryGoodAQIBoundary);
            lineChart.getData().add(goodAQIBoundary);
            lineChart.getData().add(okAQIBoundary);
            lineChart.getData().add(sufficientAQIBoundary);
        }
        if(highestValue >=31 && highestValue <=50){
            for (int i = 0; i < listSize; i++) {
                veryGoodAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 5));
                goodAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 10));
                okAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 15));
                sufficientAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 30));
                badAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 50));
            }
            lineChart.getData().add(veryGoodAQIBoundary);
            lineChart.getData().add(goodAQIBoundary);
            lineChart.getData().add(okAQIBoundary);
            lineChart.getData().add(sufficientAQIBoundary);
            lineChart.getData().add(badAQIBoundary);
        }
    }

    public void createPM2Boundaries(ArrayList<Measurement> measurements, String choice){
        int listSize = measurements.size();
        int highestValue = getHighestValue(measurements, choice);
        if(highestValue <=9){
            for (int i = 0; i < listSize; i++) {
                veryGoodAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 9));
            }
            lineChart.getData().add(veryGoodAQIBoundary);
        }
        if(highestValue >=10 && highestValue <=20){
            for (int i = 0; i < listSize; i++) {
                veryGoodAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 9));
                goodAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 20));
            }
            lineChart.getData().add(veryGoodAQIBoundary);
            lineChart.getData().add(goodAQIBoundary);
        }
        if(highestValue >= 21 && highestValue <=29){
            for (int i = 0; i < listSize; i++) {
                veryGoodAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 9));
                goodAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 20));
                okAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 29));
            }
            lineChart.getData().add(veryGoodAQIBoundary);
            lineChart.getData().add(goodAQIBoundary);
            lineChart.getData().add(okAQIBoundary);
        }
        if(highestValue >=30 && highestValue <=49){
            for (int i = 0; i < listSize; i++) {
                veryGoodAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 9));
                goodAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 20));
                okAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 29));
                sufficientAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 49));
            }
            lineChart.getData().add(veryGoodAQIBoundary);
            lineChart.getData().add(goodAQIBoundary);
            lineChart.getData().add(okAQIBoundary);
            lineChart.getData().add(sufficientAQIBoundary);
        }
        if(highestValue >=50 && highestValue <=75){
            for (int i = 0; i < listSize; i++) {
                veryGoodAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 9));
                goodAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 20));
                okAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 29));
                sufficientAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 49));
                badAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 75));
            }
            lineChart.getData().add(veryGoodAQIBoundary);
            lineChart.getData().add(goodAQIBoundary);
            lineChart.getData().add(okAQIBoundary);
            lineChart.getData().add(sufficientAQIBoundary);
            lineChart.getData().add(badAQIBoundary);
        }
    }

    public void createPM10Boundaries(ArrayList<Measurement> measurements, String choice){
        int listSize = measurements.size();
        int highestValue = getHighestValue(measurements, choice);
        if(highestValue <=20){
            for (int i = 0; i < listSize; i++) {
                veryGoodAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 20));
            }
            lineChart.getData().add(veryGoodAQIBoundary);
        }
        if(highestValue >=21 && highestValue <=30){
            for (int i = 0; i < listSize; i++) {
                veryGoodAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 20));
                goodAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 30));
            }
            lineChart.getData().add(veryGoodAQIBoundary);
            lineChart.getData().add(goodAQIBoundary);
        }
        if(highestValue >=31 && highestValue <=40){
            for (int i = 0; i < listSize; i++) {
                veryGoodAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 20));
                goodAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 30));
                okAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 40));
            }
            lineChart.getData().add(veryGoodAQIBoundary);
            lineChart.getData().add(goodAQIBoundary);
            lineChart.getData().add(okAQIBoundary);
        }
        if(highestValue >=41 && highestValue <=50){
            for (int i = 0; i < listSize; i++) {
                veryGoodAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 20));
                goodAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 30));
                okAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 40));
                sufficientAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 50));
            }
            lineChart.getData().add(veryGoodAQIBoundary);
            lineChart.getData().add(goodAQIBoundary);
            lineChart.getData().add(okAQIBoundary);
            lineChart.getData().add(sufficientAQIBoundary);
        }
        if(highestValue >=51 && highestValue <=100){
            for (int i = 0; i < listSize; i++) {
                veryGoodAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 20));
                goodAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 30));
                okAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 40));
                sufficientAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 50));
                badAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 100));
            }
            lineChart.getData().add(veryGoodAQIBoundary);
            lineChart.getData().add(goodAQIBoundary);
            lineChart.getData().add(okAQIBoundary);
            lineChart.getData().add(sufficientAQIBoundary);
            lineChart.getData().add(badAQIBoundary);
        }
    }

    public void createCO2Boundaries(ArrayList<Measurement> measurements, String choice) {
        int listSize = measurements.size();
        int highestValue = getHighestValue(measurements, choice);
        if (highestValue <= 650) {
            for (int i = 0; i < listSize; i++) {
                veryGoodAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 650));
            }
            lineChart.getData().add(veryGoodAQIBoundary);
        }
        if (highestValue >= 651 && highestValue <= 1500) {
            for (int i = 0; i < listSize; i++) {
                veryGoodAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 650));
                goodAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 1500));
            }
            lineChart.getData().add(veryGoodAQIBoundary);
            lineChart.getData().add(goodAQIBoundary);
        }
        if (highestValue >= 1501 && highestValue <= 2000) {
            for (int i = 0; i < listSize; i++) {
                veryGoodAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 650));
                goodAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 1500));
                okAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 2000));
            }
            lineChart.getData().add(veryGoodAQIBoundary);
            lineChart.getData().add(goodAQIBoundary);
            lineChart.getData().add(okAQIBoundary);
        }
        if (highestValue >= 2001 && highestValue <= 2500) {
            for (int i = 0; i < listSize; i++) {
                veryGoodAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 650));
                goodAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 1500));
                okAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 2000));
                sufficientAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 2500));
            }
            lineChart.getData().add(veryGoodAQIBoundary);
            lineChart.getData().add(goodAQIBoundary);
            lineChart.getData().add(okAQIBoundary);
            lineChart.getData().add(sufficientAQIBoundary);
        }
        if (highestValue >= 2501 && highestValue <= 5000) {
            for (int i = 0; i < listSize; i++) {
                veryGoodAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 650));
                goodAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 1500));
                okAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 2000));
                sufficientAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 2500));
                badAQIBoundary.getData().add(new XYChart.Data<>(measurements.get(i).getTimestamp(), 5000));
            }
            lineChart.getData().add(veryGoodAQIBoundary);
            lineChart.getData().add(goodAQIBoundary);
            lineChart.getData().add(okAQIBoundary);
            lineChart.getData().add(sufficientAQIBoundary);
            lineChart.getData().add(badAQIBoundary);
        }
    }
    /**
     *   nodes get disable
     */

    public void disableNodes() {
        for (Object obj : veryGoodAQIBoundary.getData()) {
            if (obj instanceof XYChart.Data) {
                XYChart.Data<String, Number> data = (XYChart.Data<String, Number>) obj;
                data.getNode().setOpacity(0);
            }
        }
        for (Object obj : goodAQIBoundary.getData()) {
            if (obj instanceof XYChart.Data) {
                XYChart.Data<String, Number> data = (XYChart.Data<String, Number>) obj;
                data.getNode().setOpacity(0);
            }
        }
        for (Object obj : okAQIBoundary.getData()) {
            if (obj instanceof XYChart.Data) {
                XYChart.Data<String, Number> data = (XYChart.Data<String, Number>) obj;
                data.getNode().setOpacity(0);
            }
        }
        for (Object obj : sufficientAQIBoundary.getData()) {
            if (obj instanceof XYChart.Data) {
                XYChart.Data<String, Number> data = (XYChart.Data<String, Number>) obj;
                data.getNode().setOpacity(0);
            }
        }
        for (Object obj : badAQIBoundary.getData()) {
            if (obj instanceof XYChart.Data) {
                XYChart.Data<String, Number> data = (XYChart.Data<String, Number>) obj;
                data.getNode().setOpacity(0);
            }
        }
    }
    /**
     *   adding boundaries to the graph
     */
    public void setBoundaries(ArrayList<Measurement> measurements, String choice){
        if (choice.equals("PM1")) {
            createPM1Boundaries(measurements, choice);
            disableNodes();
        }
        if (choice.equals("PM2")) {
            createPM2Boundaries(measurements, choice);
            disableNodes();
        }
        if (choice.equals("PM10")) {
            createPM10Boundaries(measurements, choice);
            disableNodes();
        }
        if (choice.equals("CO2")) {
            createCO2Boundaries(measurements, choice);
            disableNodes();
        }
    }


    /**
     *   showing the tooltip to view details of selected measurement
     */
    private Tooltip tooltip = new Tooltip();

    private void enableDataPointHover(XYChart.Series<String, Number> series) {
        for (XYChart.Data<String, Number> data : series.getData()) {
            Node node = data.getNode();
            Tooltip tooltip = new Tooltip();
            tooltip.setShowDelay(Duration.ZERO);
            tooltip.setText(String.format("Time: %s\nWert: %s", data.getXValue(), data.getYValue()));
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
    /**
     *   adding functionality to allow the user to choose the measurement by clicking the measurement date
     */
    public int getTemperatureValueByClick(String clickedInput) {
        return tempList.get(clickedInput);
    }

    public int getCO2ValueByClick(String clickedInput) {
        return co2List.get(clickedInput);
    }

    public int getHumidityValueByClick(String clickedInput) {
        return humidityList.get(clickedInput);
    }

    public int getBrightnessValueByClick(String clickedInput) {
        return brightnessList.get(clickedInput);
    }

    public int getPM1ValueByClick(String clickedInput) {
        return pm1List.get(clickedInput);
    }

    public int getPM2ValueByClick(String clickedInput) {
        return pm2List.get(clickedInput);
    }

    public int getPM10ValueByClick(String clickedInput) {
        return pm10List.get(clickedInput);
    }

    public int getPressureValueByClick(String clickedInput) {
        return pressureList.get(clickedInput);
    }

    /**
     * blocking the unneeded Checkboxes for AQI
     */
    public void blockCheckboxes(){
        tempCheckbox.setDisable(true);
        brighCheckbox.setDisable(true);
        pressCheckbox.setDisable(true);
        co2Checkbox.setDisable(true);
        humCheckbox.setDisable(true);
        pm1Checkbox.setDisable(true);
        pm2Checkbox.setDisable(true);
        pm10Checkbox.setDisable(true);
    }
    /**
     *   enabling the needed checkboxes for AQI
     */
    public void enableCheckboxes(){
        tempCheckbox.setDisable(false);
        brighCheckbox.setDisable(false);
        pressCheckbox.setDisable(false);
        co2Checkbox.setDisable(false);
        humCheckbox.setDisable(false);
        pm1Checkbox.setDisable(false);
        pm2Checkbox.setDisable(false);
        pm10Checkbox.setDisable(false);
    }

    /**
     *  displaying the AQI limit in the graph
     */
    @FXML
    void showPM1Scale(ActionEvent event) {
        if(scalePM1Checkbox.isSelected()){
            setBoundaries((filterListByDate(rodgau, selectedDay, selectedMonth, selectedYear)), "PM1");
            blockCheckboxes();
        }
        if (!scalePM1Checkbox.isSelected()){
            resetBoundaries();
            enableCheckboxes();
        }
    }
    @FXML
    void showPM2Scale(ActionEvent event) {
        if(scalePM2Checkbox.isSelected()){
            setBoundaries((filterListByDate(rodgau, selectedDay, selectedMonth, selectedYear)), "PM2");
            blockCheckboxes();
        }
        if (!scalePM2Checkbox.isSelected()){
            resetBoundaries();
            enableCheckboxes();
        }
    }

    @FXML
    void showPM10Scale(ActionEvent event) {
        if(scalePM10Checkbox.isSelected()){
            setBoundaries((filterListByDate(rodgau, selectedDay, selectedMonth, selectedYear)), "PM10");
            blockCheckboxes();
        }
        if (!scalePM10Checkbox.isSelected()){
            resetBoundaries();
            enableCheckboxes();
        }
    }

    @FXML
    void showCO2Scale(ActionEvent event) {
        if(scaleCO2Checkbox.isSelected()){
            setBoundaries((filterListByDate(rodgau, selectedDay, selectedMonth, selectedYear)), "CO2");
            blockCheckboxes();
        }
        if (!scaleCO2Checkbox.isSelected()){
            resetBoundaries();
            enableCheckboxes();
        }
    }
    /**
     *  displaying the boundaries of air quality on the graph
     */
    @FXML
    public void showTemperature(ActionEvent event) {
        if (tempCheckbox.isSelected()) {
            this.selectedCheckboxes++;
            checkSelection();
            lineChart.getData().add(temperatureChart);
            enableDataPointHover(temperatureChart);
        } else {
            this.selectedCheckboxes--;
            checkSelection();
            lineChart.getData().removeAll(temperatureChart);
            disableDataPointHover(temperatureChart);
        }
    }

    @FXML
    public void showPressure(ActionEvent event) {
        if (pressCheckbox.isSelected()) {
            this.selectedCheckboxes++;
            checkSelection();
            lineChart.getData().addAll(pressureChart);
            enableDataPointHover(pressureChart);
        }
        if (!pressCheckbox.isSelected()) {
            this.selectedCheckboxes--;
            checkSelection();
            lineChart.getData().removeAll(pressureChart);
            disableDataPointHover(pressureChart);
        }
    }

    @FXML
    public void showPM1(ActionEvent event) {
        if (pm1Checkbox.isSelected()) {
            this.selectedCheckboxes++;
            checkSelection();
            lineChart.getData().addAll(pm1Chart);
            enableDataPointHover(pm1Chart);
        }
        if (!pm1Checkbox.isSelected()) {
            this.selectedCheckboxes--;
            checkSelection();
            lineChart.getData().removeAll(pm1Chart);
            scalePM1Checkbox.setSelected(false);
            scalePM1Checkbox.setDisable(true);
            resetBoundaries();
            disableDataPointHover(pm1Chart);
        }
    }


    @FXML
    public void showPM2(ActionEvent event) {
        if (pm2Checkbox.isSelected()) {
            this.selectedCheckboxes++;
            checkSelection();
            lineChart.getData().addAll(pm2Chart);
            enableDataPointHover(pm2Chart);
        }
        if (!pm2Checkbox.isSelected()) {
            this.selectedCheckboxes--;
            checkSelection();
            lineChart.getData().removeAll(pm2Chart);
            resetBoundaries();
            disableDataPointHover(pm2Chart);
        }
    }

    @FXML
    public void showPM10(ActionEvent event) {

        if (pm10Checkbox.isSelected()) {
            this.selectedCheckboxes++;
            checkSelection();
            lineChart.getData().addAll(pm10Chart);
            enableDataPointHover(pm10Chart);
            checkSelection();
        }
        if (!pm10Checkbox.isSelected()) {
            this.selectedCheckboxes--;
            checkSelection();
            lineChart.getData().removeAll(pm10Chart);
            resetBoundaries();
            disableDataPointHover(pm10Chart);
        }
    }

    @FXML
    public void showHumidity(ActionEvent event) {

        if (humCheckbox.isSelected()) {
            this.selectedCheckboxes++;
            checkSelection();
            lineChart.getData().addAll(humidityChart);
            enableDataPointHover(humidityChart);
        }
        if (!humCheckbox.isSelected()) {
            this.selectedCheckboxes--;
            checkSelection();
            lineChart.getData().removeAll(humidityChart);
            disableDataPointHover(humidityChart);

        }
    }

    @FXML
    public void showCO2(ActionEvent event) {
        if (co2Checkbox.isSelected()) {
            this.selectedCheckboxes++;
            checkSelection();
            lineChart.getData().addAll(co2Chart);
            enableDataPointHover(co2Chart);
        }
        if (!co2Checkbox.isSelected()) {
            this.selectedCheckboxes--;
            checkSelection();
            lineChart.getData().removeAll(co2Chart);
            resetBoundaries();
            disableDataPointHover(co2Chart);
        }
    }

    @FXML
    public void showBrightness(ActionEvent event) {
        if (brighCheckbox.isSelected()) {
            this.selectedCheckboxes++;
            checkSelection();
            lineChart.getData().addAll(brightnessChart);
            enableDataPointHover(brightnessChart);
        }
        if (!brighCheckbox.isSelected()) {
            this.selectedCheckboxes--;
            checkSelection();
            lineChart.getData().removeAll(brightnessChart);
            disableDataPointHover(brightnessChart);
        }
    }

    /**
     *  safety measures to prevent the user to choose multiple scales at the same time. Its not allowed to show multiple scales at the same time
     */
    public void checkSelection(){
        if (this.selectedCheckboxes == 1 && co2Checkbox.isSelected()) {
            scaleCO2Checkbox.setDisable(false);
        } else {
            scaleCO2Checkbox.setDisable(true);
            scaleCO2Checkbox.setSelected(false);
        }

        if (this.selectedCheckboxes == 1 && pm1Checkbox.isSelected()) {
            scalePM1Checkbox.setDisable(false);
        } else {
            scalePM1Checkbox.setDisable(true);
            scalePM1Checkbox.setSelected(false);
        }

        if (this.selectedCheckboxes == 1 && pm2Checkbox.isSelected()) {
            scalePM2Checkbox.setDisable(false);
        } else {
            scalePM2Checkbox.setDisable(true);
            scalePM2Checkbox.setSelected(false);
        }

        if (this.selectedCheckboxes == 1 && pm10Checkbox.isSelected()) {
            scalePM10Checkbox.setDisable(false);
        } else {
            scalePM10Checkbox.setDisable(true);
            scalePM10Checkbox.setSelected(false);
        }
    }

    /**
     *   Switching Panels
     */

    public void switchToHome(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToKelsterbach(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("kelsterbach.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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

    public void switchToRodgau(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("rodgau.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToConnectDB(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("settings.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     *  getting the last measurements from the list
     */
    public Measurement getLastMeasurement(List<Measurement> list){
        return list.get(list.size()-1);
    }

    public void checkSize(){
        if (!temperatureChart.getData().isEmpty()) {
            tempCheckbox.setDisable(false);
            temperatureLabel.setDisable(false);
        }

        if(!humidityChart.getData().isEmpty()){
            humCheckbox.setDisable(false);
            humidityLabel.setDisable(false);
        }

        if(!pm1Chart.getData().isEmpty()){
            pm1Checkbox.setDisable(false);
            pm1Label.setDisable(false);
        }

        if(!pm2Chart.getData().isEmpty()){
            pm2Checkbox.setDisable(false);
            pm2Label.setDisable(false);
        }

        if(!pm10Chart.getData().isEmpty()){
            pm10Checkbox.setDisable(false);
            pm10Label.setDisable(false);
        }

        if(!brightnessChart.getData().isEmpty()){
            brighCheckbox.setDisable(false);
            brightnessLabel.setDisable(false);
        }

        if(!co2Chart.getData().isEmpty()){
            co2Checkbox.setDisable(false);
            co2Label.setDisable(false);
        }


        if(!pressureChart.getData().isEmpty()){
            pressCheckbox.setDisable(false);
            pressureLabel.setDisable(false);
        }
    }
    /**
     *  setting the labels with the measurements
     */
    public void setLabels(ArrayList<Measurement> measurements){
        actualDate.setText(getLastMeasurement(measurements).getTimestamp());
        temperatureLabel.setText(Integer.toString(getLastMeasurement(measurements).getTemperature()));
        brightnessLabel.setText(Integer.toString(getLastMeasurement(measurements).getBrightnessLevel()));
        pressureLabel.setText(Integer.toString(getLastMeasurement(measurements).getPressureLevel()));
        co2Label.setText(Integer.toString(getLastMeasurement(measurements).getCo2Level()));
        humidityLabel.setText(Integer.toString(getLastMeasurement(measurements).getHumidityLevel()));
        pm1Label.setText(Integer.toString(getLastMeasurement(measurements).getPm1Level()));
        pm2Label.setText(Integer.toString(getLastMeasurement(measurements).getPm2Level()));
        pm10Label.setText(Integer.toString(getLastMeasurement(measurements).getPm10Level()));

    }
    /**
     *  setting the labels with the measurements by clicking the timestamp on the graph
     */
    public void setLabelsByClick(String clickedCategory){
        actualDate.setText(clickedCategory);
        temperatureLabel.setText(Integer.toString(getTemperatureValueByClick(clickedCategory)));
        brightnessLabel.setText(Integer.toString(getBrightnessValueByClick(clickedCategory)));
        pressureLabel.setText(Integer.toString(getPressureValueByClick(clickedCategory)));
        co2Label.setText(Integer.toString(getCO2ValueByClick(clickedCategory)));
        humidityLabel.setText(Integer.toString(getHumidityValueByClick(clickedCategory)));
        pm1Label.setText(Integer.toString(getPM1ValueByClick(clickedCategory)));
        pm2Label.setText(Integer.toString(getPM2ValueByClick(clickedCategory)));
        pm10Label.setText(Integer.toString(getPM10ValueByClick(clickedCategory)));
    }
    /**
     *  clearing checkboxes
     */
    public void clearCheckboxes(){
        tempCheckbox.setSelected(false);
        brighCheckbox.setSelected(false);
        pressCheckbox.setSelected(false);
        co2Checkbox.setSelected(false);
        humCheckbox.setSelected(false);
        pm1Checkbox.setSelected(false);
        pm2Checkbox.setSelected(false);
        pm10Checkbox.setSelected(false);
        scaleCO2Checkbox.setSelected(false);
        scaleCO2Checkbox.setDisable(true);
        scalePM1Checkbox.setSelected(false);
        scalePM1Checkbox.setDisable(true);
        scalePM10Checkbox.setSelected(false);
        scalePM10Checkbox.setDisable(true);
        scalePM2Checkbox.setSelected(false);
        scalePM2Checkbox.setDisable(true);
    }
    /**
     *  showing current time
     */
    public void initializeTime(ArrayList<Measurement> list){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime currentTime = LocalDateTime.now();
        String formattedTime = currentTime.format(formatter);
        localTime.setText("Current Time: \n" + formattedTime);
        setTime(getLastMeasurement(list).getTimestamp());
        datepicker.setValue(this.dateTime.toLocalDate());
    }
    /**
     *  finding the disabled dates
     */
    public ArrayList<LocalDate> findDisablesDays(ArrayList<Measurement> list){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ArrayList<LocalDate> rtr = new ArrayList<>();
        for (Measurement m : list){
            LocalDate date = LocalDate.parse(m.getTimestamp(), formatter);
            rtr.add(LocalDate.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth()));
        }
        return rtr;
    }

    @FXML
    public void initialize() {
        initializeTime(rodgau);
        setLabels(rodgau);
        List<LocalDate> disabledDates = findDisablesDays(rodgau);
        addMeasurementsToChart(filterListByDate(rodgau, selectedDay, selectedMonth, selectedYear));
        checkSize();
        // functionality to pick the date
        datepicker.setOnAction(event -> {
            resetBoundaries();
            LocalDate selectedDate = datepicker.getValue();
            this.selectedMonth = selectedDate.getMonthValue();
            this.selectedDay = selectedDate.getDayOfMonth();
            this.selectedYear = selectedDate.getYear();
            ArrayList<Measurement> filteredList = filterListByDate(rodgau, selectedDay, selectedMonth, selectedYear);
            addMeasurementsToChart(filteredList);
            initializeTime(filteredList);
            checkSize();
            clearCheckboxes();
            resetBoundaries();
            setLabels(filteredList);
            this.selectedCheckboxes = 0;
        });

        datepicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date != null && !disabledDates.contains(date)) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                } else {
                    setDisable(false);
                    setStyle("");
                }
            }

        });

        //Add click functionality to the X axis
        CategoryAxis xAxisFromChart = (CategoryAxis) lineChart.getXAxis();
        xAxisFromChart.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                double xPos = event.getX();
                int clickedDataX = xAxisFromChart.getCategories().indexOf(xAxisFromChart.getValueForDisplay(xPos).toString());
                if (clickedDataX >= 0) {
                    String clickedCategory = xAxisFromChart.getCategories().get(clickedDataX);
                    setLabelsByClick(clickedCategory);
                }
            }
        });
    }
}

