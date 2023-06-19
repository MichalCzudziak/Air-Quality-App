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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import objects.Measurement;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;

public class SceneController {
    // FXML DECLARATIONS #################################################

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label actualDate;
    @FXML
    private ProgressBar arqProgressbar;
    @FXML
    private AnchorPane locMenu;

    @FXML
    private Label avgArq;

    @FXML
    private Label avgBrightness;

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

    // ###########################################################
    // DATA STRUCTURES
    // ###########################################################
    private ArrayList<Measurement> measurmentList = new ArrayList<>();

    private Map<String, Double> tempList = new HashMap<>();
    private Map<String, Double> co2List = new HashMap<>();
    private Map<String, Double> fineDustList = new HashMap<>();

    private Map<String, Double> brightnessList = new HashMap<>();
    private Map<String, Double> pressureList = new HashMap<>();
    private Map<String, Double> humidityList = new HashMap<>();

    public XYChart.Series temperatureChart = new XYChart.Series();
    public XYChart.Series pressureChart = new XYChart.Series();
    public XYChart.Series fineDustChart = new XYChart.Series();
    public XYChart.Series humidityChart = new XYChart.Series();
    public XYChart.Series co2Chart = new XYChart.Series();
    public XYChart.Series brightnessChart = new XYChart.Series();
    public XYChart.Series airQualityChart = new XYChart.Series();

    public XYChart.Series arqBoundary = new XYChart.Series();


    // ###########################################################

    // ###########################################################
    // DATA IMPORT
    // ###########################################################
    public void generateRandomMeasurements() {
        Random random = new Random();
        LocalDateTime baseTimestamp = LocalDateTime.now();

        for (int i = 0; i < 48; i++) {
            // Generate random values for each measurement attribute
            int id = i + 1;
            LocalDateTime timestamp = baseTimestamp.minusMinutes(i * 30);
            double temperature = round(random.nextDouble() * 100,1);
            double co2Level = round(random.nextDouble() * 1000,1);
            double fineDustLevel = round(random.nextDouble() * 100,1);
            double brightnessLevel = round(random.nextDouble() * 1000,1);
            double pressureLevel = round(random.nextDouble() * 2000,1);
            double humidityLevel = round(random.nextDouble() * 100,1);

            // Create a new Measurement object
            Measurement measurement = new Measurement(id, timestamp, temperature, co2Level, fineDustLevel,
                    brightnessLevel, pressureLevel, humidityLevel);

            // Add the measurement to the List
            measurmentList.add(measurement);
        }
    }

    public double round(double value, int decimalPlaces) {
        if (decimalPlaces < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(decimalPlaces, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public void addMeasurementsToChart(List<Measurement> measurements) {
        for (Measurement measurement : measurements) {
            // Convert the timestamp to the desired format
            String timestampString = measurement.getFormattedTimestamp();
            actualDate.setText(timestampString);
            // Add the temperature data to the series
            tempList.put(timestampString, measurement.getTemperature());
            temperatureChart.getData().add(new XYChart.Data<>(timestampString, measurement.getTemperature()));
            temperatureChart.setName("Temperature");

            pressureList.put(timestampString, measurement.getPressureLevel());
            pressureChart.getData().add(new XYChart.Data<>(timestampString, measurement.getPressureLevel()));
            pressureChart.setName("Pressure");


            humidityList.put(timestampString, measurement.getHumidityLevel());
            humidityChart.getData().add(new XYChart.Data<>(timestampString, measurement.getHumidityLevel()));
            humidityChart.setName("Humidity");

            fineDustList.put(timestampString, measurement.getFineDustLevel());
            fineDustChart.getData().add(new XYChart.Data<>(timestampString, measurement.getFineDustLevel()));
            fineDustChart.setName("FineDust");

            co2List.put(timestampString, measurement.getCo2Level());
            co2Chart.getData().add(new XYChart.Data<>(timestampString, measurement.getCo2Level()));
            co2Chart.setName("CO2");

            brightnessList.put(timestampString, measurement.getBrightnessLevel());
            brightnessChart.getData().add(new XYChart.Data<>(timestampString, measurement.getBrightnessLevel()));
            brightnessChart.setName("Brightness");
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

    public static void sortByAscendingTime(List<Measurement> measurements) {
        measurements.sort(new Comparator<Measurement>() {
            @Override
            public int compare(Measurement m1, Measurement m2) {
                LocalDateTime time1 = m1.getTimestampDate();
                LocalDateTime time2 = m2.getTimestampDate();
                return time1.compareTo(time2);
            }
        });
    }



    // ###########################################################
    // GRAPH OPTIONS
    // ###########################################################
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

    public double findTemperatureValueByClick(String userInput) {
        return tempList.get(userInput);
    }

    public double findCO2ValueByClick(String userInput) {
        return co2List.get(userInput);
    }

    public double findHumidityValueByClick(String userInput) {
        return humidityList.get(userInput);
    }

    public double findBrightnessValueByClick(String userInput) {
        return brightnessList.get(userInput);
    }

    public double findFineDustValueByClick(String userInput) {
        return fineDustList.get(userInput);
    }

    public double findPressureValueByClick(String userInput) {
        return pressureList.get(userInput);
    }


    // ###########################################################
    // SHOWING CONTROLS
    // ###########################################################
    @FXML
    public void showTemperature(ActionEvent event) {
        if (tempCheckbox.isSelected()) {
            lineChart.getData().add(temperatureChart);
            enableDataPointHover(temperatureChart);
        } else {
            lineChart.getData().removeAll(temperatureChart);
            disableDataPointHover(temperatureChart);
        }
    }

    @FXML
    public void showPressure(ActionEvent event) {
        if (pressCheckbox.isSelected()) {
            lineChart.getData().addAll(pressureChart);
            enableDataPointHover(pressureChart);
        }
        if (!pressCheckbox.isSelected()) {
            lineChart.getData().removeAll(pressureChart);
            disableDataPointHover(pressureChart);
        }
    }

    @FXML
    public void showFineDust(ActionEvent event) {
        if (fineCheckBox.isSelected()) {
            lineChart.getData().addAll(fineDustChart);
            enableDataPointHover(fineDustChart);
        }
        if (!fineCheckBox.isSelected()) {
            lineChart.getData().removeAll(fineDustChart);
            disableDataPointHover(fineDustChart);
        }
    }

    @FXML
    public void showHumidity(ActionEvent event) {
        if (humCheckbox.isSelected()) {
            lineChart.getData().addAll(humidityChart);
            enableDataPointHover(humidityChart);
        }
        if (!humCheckbox.isSelected()) {
            lineChart.getData().removeAll(humidityChart);
            disableDataPointHover(humidityChart);
        }
    }

    @FXML
    public void showCO2(ActionEvent event) {
        if (co2Checkbox.isSelected()) {
            lineChart.getData().addAll(co2Chart);
            enableDataPointHover(co2Chart);
        }
        if (!co2Checkbox.isSelected()) {
            lineChart.getData().removeAll(co2Chart);
            disableDataPointHover(co2Chart);
        }
    }

    @FXML
    public void showBrightness(ActionEvent event) {
        if (brighCheckbox.isSelected()) {
            lineChart.getData().addAll(brightnessChart);
            enableDataPointHover(brightnessChart);
        }
        if (!brighCheckbox.isSelected()) {
            lineChart.getData().removeAll(brightnessChart);
            disableDataPointHover(brightnessChart);
        }
    }

    @FXML
    public void showAirquality(ActionEvent event) {
        if (arqCheckbox.isSelected()) {
            lineChart.getData().addAll(airQualityChart);
            lineChart.getData().addAll(arqBoundary);
            for (Object obj : arqBoundary.getData()) {
                if (obj instanceof XYChart.Data) {
                    XYChart.Data<String, Number> data = (XYChart.Data<String, Number>) obj;
                    data.getNode().setOpacity(0);
                }
            }
        }
        if (!arqCheckbox.isSelected()) {
            lineChart.getData().removeAll(airQualityChart);
            lineChart.getData().removeAll(arqBoundary);
        }
    }


    // ###########################################################
    // SWITCHING CONTROLS
    // ###########################################################

    public void switchToHome(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToFrankfurt(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("frankfurt.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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


    @FXML
    public void initialize() {
        generateRandomMeasurements();
        sortByAscendingTime(measurmentList);
        addMeasurementsToChart(measurmentList);

        CategoryAxis xAxisFromChart = (CategoryAxis) lineChart.getXAxis();

        // Add click functionality to the X axis
        xAxisFromChart.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                double xPos = event.getX();
                int clickedDataX = xAxisFromChart.getCategories().indexOf(xAxisFromChart.getValueForDisplay(xPos).toString());
                if (clickedDataX >= 0) {
                    String clickedCategory = xAxisFromChart.getCategories().get(clickedDataX);
                    actualDate.setText(clickedCategory);
                    avgTemperature.setText(Double.toString(findTemperatureValueByClick(clickedCategory)));
                    avgBrightness.setText(Double.toString(findBrightnessValueByClick(clickedCategory)));
                    avgPressure.setText(Double.toString(findPressureValueByClick(clickedCategory)));
                    avgCO2.setText(Double.toString(findCO2ValueByClick(clickedCategory)));
                    avgHumidity.setText(Double.toString(findHumidityValueByClick(clickedCategory)));
                    avgFinedust.setText(Double.toString(findFineDustValueByClick(clickedCategory)));
                }
            }
        });

        if (!temperatureChart.getData().isEmpty()) {
            tempCheckbox.setDisable(false);
            double average = calculateAverage(temperatureChart);
            avgTemperature.setDisable(false);
            avgTemperature.setText(Double.toString(average) + " C");
        }


        if(!humidityChart.getData().isEmpty()){
            humCheckbox.setDisable(false);
            double average = calculateAverage(humidityChart);
            avgHumidity.setDisable(false);
            avgHumidity.setText(Double.toString(average));
        }

        if(!fineDustChart.getData().isEmpty()){
            fineCheckBox.setDisable(false);
            double average = calculateAverage(fineDustChart);
            avgFinedust.setDisable(false);
            avgFinedust.setText(Double.toString(average));
        }

        if(!brightnessChart.getData().isEmpty()){
            brighCheckbox.setDisable(false);
            double average = calculateAverage(brightnessChart);
            avgBrightness.setDisable(false);
            avgBrightness.setText(Double.toString(average));
        }

        if(!co2Chart.getData().isEmpty()){
            co2Checkbox.setDisable(false);
            double average = calculateAverage(co2Chart);
            avgCO2.setDisable(false);
            avgCO2.setText(Double.toString(average));
        }


        if(!pressureChart.getData().isEmpty()){
            pressCheckbox.setDisable(false);
            double average = calculateAverage(pressureChart);
            avgPressure.setDisable(false);
            avgPressure.setText(Double.toString(average) + " HPA");
        }
    }

}
