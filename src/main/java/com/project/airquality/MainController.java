package com.project.airquality;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import objects.Measurement;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static java.lang.Math.round;

public class MainController {

    @FXML
    private Label avgAQI;

    @FXML
    private TableColumn<Measurement, Double> pm1Column;

    @FXML
    private TableColumn<Measurement, Double> co2Column;

    @FXML
    private TableColumn<Measurement, Double> temperatureColumn;

    @FXML
    private TableColumn<Measurement, LocalDateTime> timeColumn;

    @FXML
    private TableColumn<Measurement, Double> pm10Column;

    @FXML
    private TableColumn<Measurement, Double> pm2Column;

    @FXML
    private TableView<Measurement> tableView;

    @FXML
    private Button frankfurtButton;

    @FXML
    private Button kelsterbachButton;

    @FXML
    private Button maintalButton;

    @FXML
    private Button rodgauButton;


    @FXML
    private Slider slider;

    @FXML
    private Label timeLabel;

    public Button settingsButton;
    public Label avgArq;
    private Stage stage;
    private Scene scene;
    private Parent root;




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

    private ArrayList<Measurement> measurmentList = new ArrayList<>();
    private ArrayList<Measurement> frankfurtMeasurments = new ArrayList<>();
    private ArrayList<Measurement> rodgauMeasurments = new ArrayList<>();
    private ArrayList<Measurement> maintalMeasurments = new ArrayList<>();
    private ArrayList<Measurement> kelsterbachMeasurments = new ArrayList<>();
    private Map<String, Double> tempList = new HashMap<>();
    private Map<String, Double> co2List = new HashMap<>();
    private Map<String, Double> pm1List = new HashMap<>();

    private Map<String, Double> pm2List = new HashMap<>();

    private Map<String, Double> pm10List = new HashMap<>();



    private Map<String, Double> brightnessList = new HashMap<>();
    private Map<String, Double> pressureList = new HashMap<>();
    private Map<String, Double> humidityList = new HashMap<>();

    private Map<String, Double> airqualityList = new HashMap<>();

    public ArrayList<Measurement> generateRandomMeasurements() {
        Random random = new Random();
        ArrayList<Measurement> list = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            // Generate random values for each measurement attribute
            int id = i + 1;
            LocalTime time = LocalTime.of(i, random.nextInt(60)); // Generate random minutes for each hour
            LocalDateTime timestamp = LocalDateTime.of(LocalDate.now(), time);
            int temperature = (int) round(random.nextInt() * 100, 1);
            int co2Level = (int) (400 + round(random.nextInt() * (2000-400), 1));
            int pm1Level = (int) (0 + round(random.nextInt() * (35-0), 1));
            int pm2Level = (int) (0 + round(random.nextInt() * (53-0), 1));
            int pm10Level = (int) round(random.nextInt() * 100, 1);
            int brightnessLevel = (int) round(random.nextInt() * 1000, 1);
            int pressureLevel = (int) round(random.nextInt() * 2000, 1);
            int humidityLevel = (int) round(random.nextInt() * 100, 1);

            // Create a new Measurement object
            Measurement measurement = new Measurement(id, timestamp, temperature, co2Level, pm1Level, pm2Level, pm10Level,
                    brightnessLevel, pressureLevel, humidityLevel);

            // Add the measurement to the List
            list.add(measurement);
        }
        return list;
    }

    public void addMeasurements(List<Measurement> measurements) {
        for (Measurement measurement : measurements) {
            // Convert the timestamp to the desired format
            String timestampString = measurement.getFormattedTimestamp();
            // Add the temperature data to the series
            tempList.put(timestampString, measurement.getTemperature());
            pressureList.put(timestampString, measurement.getPressureLevel());
            humidityList.put(timestampString, measurement.getHumidityLevel());
            pm1List.put(timestampString, measurement.getPm1Level());
            pm2List.put(timestampString, measurement.getPm2Level());
            pm10List.put(timestampString, measurement.getPm10Level());
            co2List.put(timestampString, measurement.getCo2Level());
            brightnessList.put(timestampString, measurement.getBrightnessLevel());
        }
    }

    public double round(double value, int decimalPlaces) {
        if (decimalPlaces < 0) throw new IllegalArgumentException();
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(decimalPlaces, RoundingMode.HALF_UP);
        return bd.doubleValue();
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

    public int getAQIPM1(Measurement m){
        if(m.getPm1Level() <=5) return 1;
        if(m.getPm1Level() >=6 && m.getPm1Level() <=10) return 2;
        if(m.getPm1Level() >=11 && m.getPm1Level() <=15) return 3;
        if(m.getPm1Level() >=16 && m.getPm1Level() <=30) return 4;
        if(m.getPm1Level() >=31 && m.getPm1Level() <=50) return 5;
        if(m.getPm1Level() > 50) return 6;
        return 0;
    }

    public int getAQIPM2(Measurement m){
        if(m.getPm2Level() <=9) return 1;
        if(m.getPm2Level() >=10 && m.getPm2Level() <=20) return 2;
        if(m.getPm2Level() >=21 && m.getPm2Level() <=29) return 3;
        if(m.getPm2Level() >=30 && m.getPm2Level() <=49) return 4;
        if(m.getPm2Level() >=50 && m.getPm2Level() <=75) return 5;
        if(m.getPm2Level() > 75) return 6;
        return 0;
    }

    public int getAQIPM10(Measurement m){
        if(m.getPm10Level() <=20) return 1;
        if(m.getPm10Level() >=21 && m.getPm10Level() <=30) return 2;
        if(m.getPm10Level() >=31 && m.getPm10Level() <=40) return 3;
        if(m.getPm10Level()>=41 && m.getPm10Level() <=50) return 4;
        if(m.getPm10Level() >=51 && m.getPm10Level() <=100) return 5;
        if(m.getPm10Level() > 100) return 6;
        return 0;
    }

    public int getAQIPMCO2(Measurement m){
        if(m.getCo2Level() >= 400 && m.getCo2Level() <= 650) return 1;
        if(m.getCo2Level() >=651 && m.getCo2Level() <=1500) return 2;
        if(m.getCo2Level() >=1501 && m.getCo2Level() <=2000) return 3;
        if(m.getCo2Level()>=2001 && m.getCo2Level() <=2500) return 4;
        if(m.getCo2Level()>=2501 && m.getCo2Level() <=5000) return 5;
        if(m.getCo2Level() > 5000) return 6;
        return 0;
    }

    public int calculateAvgAQI(List<Measurement> measurements) {
        int sumPM1 = 0;
        int sumPM2 = 0;
        int sumPM10 = 0;
        int sumCO2 = 0;
        int count = 0;
        for (Measurement measurement : measurements) {
            sumPM1 += getAQIPM1(measurement);
            sumPM2 += getAQIPM2(measurement);
            sumPM10 += getAQIPM10(measurement);
            sumCO2 += getAQIPMCO2(measurement);
            count++;
        }
        sumPM1 = sumPM1 / count;
        sumPM2 = sumPM2 / count;
        sumPM10 = sumPM10 / count;
        sumCO2 = sumCO2 / count;
        return Math.max(Math.max(sumPM1, sumPM2), Math.max(sumPM10, sumCO2));
    }

    public void changeButtonColorAvg(int max, Button btn){
        if (max == 1) {
            btn.setStyle("-fx-background-color: #0411E7;");
        }
        if (max == 2) {
            btn.setStyle("-fx-background-color: #046AE7;");
        }
        if (max == 3) {
            btn.setStyle("-fx-background-color: #04C9E7;");
        }
        if (max == 4) {
            btn.setStyle("-fx-background-color: #CCB532;");
        }
        if (max == 5) {
            btn.setStyle("-fx-background-color: #E9830E;");
        }
        if (max == 6) {
            btn.setStyle("-fx-background-color: #CE2903;");
        }
    }

    public void changeButtonColor(int value, List<Measurement> measurements, Button btn){
        Measurement m = measurements.get(value);
        int pm1Index = getAQIPM1(m);
        int pm10Index = getAQIPM10(m);
        int co2Index = getAQIPMCO2(m);
        int max = Math.max(Math.max(pm1Index, pm10Index), co2Index);

        if (max == 1) {
            btn.setStyle("-fx-background-color: #0411E7;");
        }
        if (max == 2) {
            btn.setStyle("-fx-background-color: #046AE7;");
        }
        if (max == 3) {
            btn.setStyle("-fx-background-color: #04C9E7;");
        }
        if (max == 4) {
            btn.setStyle("-fx-background-color: #CCB532;");
        }
        if (max == 5) {
            btn.setStyle("-fx-background-color: #E9830E;");
        }
        if (max == 6) {
            btn.setStyle("-fx-background-color: #CE2903;");
        }


    }

    public void showTooltip(int value, ArrayList<Measurement> list, Button btn){
        Tooltip tooltip = new Tooltip();
        tooltip.setShowDelay(Duration.ZERO);
        tooltip.setText("Time: " + list.get(value).getFormattedTimestamp() + "\n" +
                "AQI: \n" +
                "PM1: " + list.get(value).getPm1Level() + "\n" +
                "PM2.5: " + list.get(value).getPm2Level() + "\n" +
                "PM10: " + list.get(value).getPm10Level() + "\n" +
                "CO2: " + list.get(value).getCo2Level() + "\n"

        );
        tooltip.setStyle("-fx-font-size: 14px"); // Set the font size
        Tooltip.install(btn, tooltip);
    }

    public ObservableList<Measurement> convertList(List<Measurement> measurements){
        ObservableList<Measurement> obsList = FXCollections.observableArrayList();
        obsList.addAll(measurements);
        return obsList;
    }




    @FXML
    public void initialize(){
        LocalDateTime baseTimestamp = LocalDateTime.now();
        frankfurtMeasurments = generateRandomMeasurements();
        kelsterbachMeasurments = generateRandomMeasurements();
        maintalMeasurments = generateRandomMeasurements();
        rodgauMeasurments = generateRandomMeasurements();

        sortByAscendingTime(frankfurtMeasurments);
        sortByAscendingTime(kelsterbachMeasurments);
        sortByAscendingTime(maintalMeasurments);
        sortByAscendingTime(rodgauMeasurments);

        addMeasurements(frankfurtMeasurments);
        addMeasurements(kelsterbachMeasurments);
        addMeasurements(maintalMeasurments);
        addMeasurements(rodgauMeasurments);

        slider.setValue(baseTimestamp.getHour());
        timeLabel.setText(frankfurtMeasurments.get(baseTimestamp.getHour()).getFormattedTimestamp());
        showTooltip(baseTimestamp.getHour(), frankfurtMeasurments, frankfurtButton);
        showTooltip(baseTimestamp.getHour(), rodgauMeasurments, rodgauButton);
        showTooltip(baseTimestamp.getHour(), maintalMeasurments, maintalButton);
        showTooltip(baseTimestamp.getHour(), kelsterbachMeasurments, kelsterbachButton);

        slider.setMax(baseTimestamp.getHour());
        co2Column.setCellValueFactory(new PropertyValueFactory<Measurement, Double>("co2Level"));
        temperatureColumn.setCellValueFactory(new PropertyValueFactory<Measurement, Double>("temperature"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<Measurement, LocalDateTime>("timestampDate"));
        pm10Column.setCellValueFactory(new PropertyValueFactory<Measurement, Double>("pm10Level"));
        pm1Column.setCellValueFactory(new PropertyValueFactory<Measurement, Double>("pm1Level"));
        pm2Column.setCellValueFactory(new PropertyValueFactory<Measurement, Double>("pm2Level"));
        tableView.setItems(convertList(frankfurtMeasurments));

        changeButtonColorAvg(calculateAvgAQI(frankfurtMeasurments), frankfurtButton);
        changeButtonColorAvg(calculateAvgAQI(kelsterbachMeasurments), kelsterbachButton);
        changeButtonColorAvg(calculateAvgAQI(maintalMeasurments), maintalButton);
        changeButtonColorAvg(calculateAvgAQI(rodgauMeasurments), rodgauButton);


        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                showTooltip(newValue.intValue(), frankfurtMeasurments, frankfurtButton);
                showTooltip(newValue.intValue(), rodgauMeasurments, rodgauButton);
                showTooltip(newValue.intValue(), maintalMeasurments, maintalButton);
                showTooltip(newValue.intValue(), kelsterbachMeasurments, kelsterbachButton);

                changeButtonColor(newValue.intValue(), frankfurtMeasurments, frankfurtButton);
                changeButtonColor(newValue.intValue(), kelsterbachMeasurments, kelsterbachButton);
                changeButtonColor(newValue.intValue(), maintalMeasurments, maintalButton);
                changeButtonColor(newValue.intValue(), kelsterbachMeasurments, rodgauButton);

                timeLabel.setText(frankfurtMeasurments.get(newValue.intValue()).getFormattedTimestamp());
            }
        });
    }

}
