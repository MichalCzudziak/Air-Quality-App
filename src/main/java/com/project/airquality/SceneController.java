package com.project.airquality;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import objects.Location;
import objects.Measurement;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SceneController {
    // FXML DECLARATIONS #################################################

    private Stage stage;
    private Scene scene;
    private Parent root;

    private int year;
    private int month;
    private int day;

    private LocalDateTime dateTime;

    private int selectedDay = 0;
    private int selectedMonth = 0;
    private int selectedYear = 0;

    @FXML
    private DatePicker datepicker;

    @FXML
    private Label actualDate;
    @FXML
    private ProgressBar arqProgressbar;
    @FXML
    private AnchorPane locMenu;

    @FXML
    private Label arqLabel;

    @FXML
    private Label brightnessLabel;

    @FXML
    private Label co2Label;

    @FXML
    private Label finedustLabel;

    @FXML
    private Label humidityLabel;

    @FXML
    private Label pressureLabel;

    @FXML
    private Label temperatureLabel;
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

    // ###########################################################
    // DATA STRUCTURES
    // ###########################################################
    private ArrayList<Measurement> measurmentList = new ArrayList<>();

    private ArrayList<Location> list = Main.allLocations;

    private ArrayList<Measurement> kelsterbach = list.get(0).getMeasurements();


    private Map<String, Integer> tempList = new HashMap<>();
    private Map<String, Integer> co2List = new HashMap<>();
    private Map<String, Integer> pm1List = new HashMap<>();

    private Map<String, Integer> pm2List = new HashMap<>();

    private Map<String, Integer> pm10List = new HashMap<>();



    private Map<String, Integer> brightnessList = new HashMap<>();
    private Map<String, Integer> pressureList = new HashMap<>();
    private Map<String, Integer> humidityList = new HashMap<>();


    public XYChart.Series temperatureChart = new XYChart.Series();
    public XYChart.Series pressureChart = new XYChart.Series();
    public XYChart.Series pm1Chart = new XYChart.Series();
    public XYChart.Series pm2Chart = new XYChart.Series();
    public XYChart.Series pm10Chart = new XYChart.Series();
    public XYChart.Series humidityChart = new XYChart.Series();
    public XYChart.Series co2Chart = new XYChart.Series();
    public XYChart.Series brightnessChart = new XYChart.Series();
    public XYChart.Series airQualityChart = new XYChart.Series();
    public XYChart.Series arqBoundary = new XYChart.Series();

    public XYChart.Series veryGoodAQIBoundary = new XYChart.Series<>();
    public XYChart.Series goodAQIBoundary = new XYChart.Series<>();
    public XYChart.Series okAQIBoundary = new XYChart.Series<>();

    public XYChart.Series ausreichendAQIBoundary= new XYChart.Series<>();
    public XYChart.Series badAQIBoundary = new XYChart.Series<>();




    // ###########################################################

    // ###########################################################
    // DATA IMPORT
    // ###########################################################
    public ObservableList<Measurement> getListByDate(List<Measurement> measurements, int day, int month, int year) {
        ObservableList<Measurement> obsList = FXCollections.observableArrayList();
        for (Measurement measurement : measurements) {
            setTime(measurement.getTimestamp());
            if (this.day == day && this.month == month && this.year == year) {
                obsList.add(measurement);
            }
        }
        return obsList;
    }

    public void setTime(String timeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.dateTime = LocalDateTime.parse(timeString, formatter);
        this.year = dateTime.getYear();
        this.month = dateTime.getMonthValue();
        this.day = dateTime.getDayOfMonth();
    }


    public double round(double value, int decimalPlaces) {
        if (decimalPlaces < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(decimalPlaces, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public void clearNode(XYChart.Series<String, Integer> series){
            for(XYChart.Data<String, Integer> data : series.getData()){
                Node node = data.getNode();
                if(node != null){
                    node.setVisible(false);
                    node.setManaged(false);
                }
            }
    }


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
    public void addMeasurementsToChart(List<Measurement> measurements) {
        refreshChart();
        for (Measurement measurement : measurements) {
            // Convert the timestamp to the desired format
            String timestampString = measurement.getTimestamp();
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

            pm1List.put(timestampString, measurement.getPm1Level());
            pm1Chart.getData().add(new XYChart.Data<>(timestampString, measurement.getPm1Level()));
            pm1Chart.setName("PM 1");

            pm2List.put(timestampString, measurement.getPm2Level());
            pm2Chart.getData().add(new XYChart.Data<>(timestampString, measurement.getPm2Level()));
            pm2Chart.setName("PM 2.5");

            pm10List.put(timestampString, measurement.getPm10Level());
            pm10Chart.getData().add(new XYChart.Data<>(timestampString, measurement.getPm10Level()));
            pm10Chart.setName("PM 10");


            co2List.put(timestampString, measurement.getCo2Level());
            co2Chart.getData().add(new XYChart.Data<>(timestampString, measurement.getCo2Level()));
            co2Chart.setName("CO2");

            brightnessList.put(timestampString, measurement.getBrightnessLevel());
            brightnessChart.getData().add(new XYChart.Data<>(timestampString, measurement.getBrightnessLevel()));
            brightnessChart.setName("Brightness");

        }
    }

    public void clearBoundaries(){
        lineChart.getData().clear();
        veryGoodAQIBoundary.getData().clear();
        goodAQIBoundary.getData().clear();
        okAQIBoundary.getData().clear();
        ausreichendAQIBoundary.getData().clear();
        badAQIBoundary.getData().clear();
    }

    public void createBoundaries(List<Measurement> measurements, String choice){
        int listSize = measurements.size();
        if(choice.equals("PM1")){
            for(int i = 0; i < listSize; i++){
                veryGoodAQIBoundary.getData().add((new XYChart.Data<>(measurements.get(i).getTimestamp(),5)));
                goodAQIBoundary.getData().add((new XYChart.Data<>(measurements.get(i).getTimestamp(),10)));
                okAQIBoundary.getData().add((new XYChart.Data<>(measurements.get(i).getTimestamp(),15)));
                ausreichendAQIBoundary.getData().add((new XYChart.Data<>(measurements.get(i).getTimestamp(),30)));
                badAQIBoundary.getData().add((new XYChart.Data<>(measurements.get(i).getTimestamp(),50)));
            }

            clearNode(veryGoodAQIBoundary);
            lineChart.getData().add(veryGoodAQIBoundary);
            lineChart.getData().add(goodAQIBoundary);
            lineChart.getData().add(okAQIBoundary);
            lineChart.getData().add(ausreichendAQIBoundary);
            lineChart.getData().add(badAQIBoundary);
        }
        if(choice.equals("PM2")){
            for(int i = 0; i < listSize; i++){
                veryGoodAQIBoundary.getData().add(i, 9);
                goodAQIBoundary.getData().add(i, 20);
                okAQIBoundary.getData().add(i, 29);
                ausreichendAQIBoundary.getData().add(i, 49);
                badAQIBoundary.getData().add(i, 75);
            }
        }
        if(choice.equals("PM10")){
            for(int i = 0; i < listSize; i++){
                veryGoodAQIBoundary.getData().add(i, 20);
                goodAQIBoundary.getData().add(i, 30);
                okAQIBoundary.getData().add(i, 40);
                ausreichendAQIBoundary.getData().add(i, 50);
                badAQIBoundary.getData().add(i, 100);
            }
        }
        if(choice.equals("CO2")){
            for(int i = 0; i < listSize; i++){
                veryGoodAQIBoundary.getData().add(i, 650);
                goodAQIBoundary.getData().add(i, 1500);
                okAQIBoundary.getData().add(i, 2000);
                ausreichendAQIBoundary.getData().add(i, 2500);
                badAQIBoundary.getData().add(i, 5000);
            }
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

    public int findTemperatureValueByClick(String userInput) {
        return tempList.get(userInput);
    }

    public int findCO2ValueByClick(String userInput) {
        return co2List.get(userInput);
    }

    public int findHumidityValueByClick(String userInput) {
        return humidityList.get(userInput);
    }

    public int findBrightnessValueByClick(String userInput) {
        return brightnessList.get(userInput);
    }

    public int findPM1ValueByClick(String userInput) {
        return pm1List.get(userInput);
    }

    public int findPM2ValueByClick(String userInput) {
        return pm2List.get(userInput);
    }

    public int findPM10ValueByClick(String userInput) {
        return pm10List.get(userInput);
    }

    public int findPressureValueByClick(String userInput) {
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
    public void showPM1(ActionEvent event) {
        if (pm1Checkbox.isSelected()) {
            lineChart.getData().addAll(pm1Chart);
            createBoundaries(convertList(getListByDate(kelsterbach, this.day, this.month, this.year)), "PM1");
            for (Object obj : veryGoodAQIBoundary.getData()) {
                if (obj instanceof XYChart.Data) {
                    XYChart.Data<String, Number> data = (XYChart.Data<String, Number>) obj;
                    data.getNode().setOpacity(0);
                }
            }
            enableDataPointHover(pm1Chart);
        }
        if (!pm1Checkbox.isSelected()) {
            lineChart.getData().removeAll(pm1Chart);
            clearBoundaries();
            disableDataPointHover(pm1Chart);
        }
    }

    @FXML
    public void showPM2(ActionEvent event) {
        if (pm2Checkbox.isSelected()) {
            lineChart.getData().addAll(pm2Chart);
            enableDataPointHover(pm2Chart);
        }
        if (!pm2Checkbox.isSelected()) {
            lineChart.getData().removeAll(pm2Chart);
            disableDataPointHover(pm2Chart);
        }
    }

    @FXML
    public void showPM10(ActionEvent event) {
        if (pm10Checkbox.isSelected()) {
            lineChart.getData().addAll(pm10Chart);
            enableDataPointHover(pm10Chart);
        }
        if (!pm10Checkbox.isSelected()) {
            lineChart.getData().removeAll(pm10Chart);
            disableDataPointHover(pm10Chart);
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
        Parent root = FXMLLoader.load(getClass().getResource("maintal.fxml"));
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

    // ID 0 = Kelsterbach

    public Measurement getLastMeasurement(List<Measurement> list){
        return list.get(list.size()-1);
    }

    public ObservableList<Measurement> convertList(List<Measurement> measurements){
        ObservableList<Measurement> obsList = FXCollections.observableArrayList();
        obsList.addAll(measurements);
        return obsList;
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

    public void setLabels(){
        actualDate.setText(getLastMeasurement(kelsterbach).getTimestamp());
        temperatureLabel.setText(Integer.toString(getLastMeasurement(kelsterbach).getTemperature()));
        brightnessLabel.setText(Integer.toString(getLastMeasurement(kelsterbach).getBrightnessLevel()));
        pressureLabel.setText(Integer.toString(getLastMeasurement(kelsterbach).getPressureLevel()));
        co2Label.setText(Integer.toString(getLastMeasurement(kelsterbach).getCo2Level()));
        humidityLabel.setText(Integer.toString(getLastMeasurement(kelsterbach).getHumidityLevel()));
        pm1Label.setText(Integer.toString(getLastMeasurement(kelsterbach).getPm1Level()));
        pm2Label.setText(Integer.toString(getLastMeasurement(kelsterbach).getPm2Level()));
        pm10Label.setText(Integer.toString(getLastMeasurement(kelsterbach).getPm10Level()));
    }

    public void setLabelsClick(String clickedCategory){
        actualDate.setText(clickedCategory);
        temperatureLabel.setText(Integer.toString(findTemperatureValueByClick(clickedCategory)));
        brightnessLabel.setText(Integer.toString(findBrightnessValueByClick(clickedCategory)));
        pressureLabel.setText(Integer.toString(findPressureValueByClick(clickedCategory)));
        co2Label.setText(Integer.toString(findCO2ValueByClick(clickedCategory)));
        humidityLabel.setText(Integer.toString(findHumidityValueByClick(clickedCategory)));
        pm1Label.setText(Integer.toString(findPM1ValueByClick(clickedCategory)));
        pm2Label.setText(Integer.toString(findPM2ValueByClick(clickedCategory)));
        pm10Label.setText(Integer.toString(findPM10ValueByClick(clickedCategory)));
    }

    @FXML
    private Label localTime;

    public void clearCheckboxes(){
        tempCheckbox.setSelected(false);
        brighCheckbox.setSelected(false);
        pressCheckbox.setSelected(false);
        co2Checkbox.setSelected(false);
        humCheckbox.setSelected(false);
        pm1Checkbox.setSelected(false);
        pm2Checkbox.setSelected(false);
        pm10Checkbox.setSelected(false);
    }

    @FXML
    public void initialize() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime currentTime = LocalDateTime.now();
        String formattedTime = currentTime.format(formatter);
        localTime.setText("Current Time: \n" + formattedTime);
        setLabels();
        setTime(getLastMeasurement(kelsterbach).getTimestamp());
        datepicker.setValue(this.dateTime.toLocalDate());
        ObservableList<Measurement> list = convertList(getListByDate(kelsterbach, this.day, this.month, this.year));
        addMeasurementsToChart(list);
        checkSize();

        datepicker.setOnAction(event -> {
            LocalDate selectedDate = datepicker.getValue();
            selectedMonth = selectedDate.getMonthValue();
            selectedDay = selectedDate.getDayOfMonth();
            selectedYear = selectedDate.getYear();
            ObservableList<Measurement> filteredList = getListByDate(kelsterbach, selectedDay, selectedMonth, selectedYear);
            addMeasurementsToChart(filteredList);
            checkSize();
            clearCheckboxes();
        });
        //sortByAscendingTime(measurmentList);
        CategoryAxis xAxisFromChart = (CategoryAxis) lineChart.getXAxis();
        // Add click functionality to the X axis
        xAxisFromChart.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                double xPos = event.getX();
                int clickedDataX = xAxisFromChart.getCategories().indexOf(xAxisFromChart.getValueForDisplay(xPos).toString());
                if (clickedDataX >= 0) {
                    String clickedCategory = xAxisFromChart.getCategories().get(clickedDataX);
                    setLabelsClick(clickedCategory);
                }
            }
        });

    }

}
