package com.project.airquality;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import objects.Measurement;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.lang.Math.round;

public class MainController {

    @FXML
    private ImageView infoButton;

    @FXML
    private ImageView aqiScale;

    @FXML
    private Button showingAqi;
    @FXML
    void showScale(MouseEvent event) {
        aqiScale.setVisible(true);
    }

    @FXML
    void disableScale(MouseEvent event) {
        aqiScale.setVisible(false);
    }




    @FXML
    private Label actualDate;

    @FXML
    private DatePicker datepicker;
    @FXML
    private Label avgAQI;

    @FXML
    private TableColumn<Measurement, Integer> aqiColumn;

    @FXML
    private TableColumn<Measurement, Integer> pm1Column;

    @FXML
    private TableColumn<Measurement, Integer> co2Column;

    @FXML
    private TableColumn<Measurement, Integer> temperatureColumn;

    @FXML
    private TableColumn<Measurement, String> timeColumn;

    @FXML
    private TableColumn<Measurement, Integer> pm10Column;

    @FXML
    private TableColumn<Measurement, Integer> pm2Column;

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
    private Label localTime;

    @FXML
    private Slider slider;

    @FXML
    private Label timeLabel;

    @FXML
    private ChoiceBox<String> choiceBox;

    private ArrayList<Measurement> kelsterbach = Main.allLocations.get(0).getMeasurements();
    private ArrayList<Measurement> maintal;
    private ArrayList<Measurement> rodgau;

    public Button settingsButton;
    public Label avgArq;
    private Stage stage;
    private Scene scene;
    private Parent root;

    private int year;
    private int month;
    private int day;

    private int selectedLocation;


    public void selectLocation(int location){
        this.selectedLocation = location;
    }

    public void setTime(String timeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(timeString, formatter);
        this.year = dateTime.getYear();
        this.month = dateTime.getMonthValue();
        this.day = dateTime.getDayOfMonth();
    }



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
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private Map<String, Integer> tempList = new HashMap<>();
    private Map<String, Integer> co2List = new HashMap<>();
    private Map<String, Integer> pm1List = new HashMap<>();

    private Map<String, Integer> pm2List = new HashMap<>();

    private Map<String, Integer> pm10List = new HashMap<>();

    private Map<String, Integer> brightnessList = new HashMap<>();
    private Map<String, Integer> pressureList = new HashMap<>();
    private Map<String, Integer> humidityList = new HashMap<>();

    public void addMeasurements(List<Measurement> measurements) {
        for (Measurement measurement : measurements) {
            // Convert the timestamp to the desired format
            String timestampString = measurement.getTimestamp();
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

    public void showTooltip(int value, List<Measurement> list, Button btn){
        Tooltip tooltip = new Tooltip();
        tooltip.setShowDelay(Duration.ZERO);
        tooltip.setText("Time: " + list.get(value).getTimestamp() + "\n" +
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

    public Measurement getLastMeasurement(List<Measurement> list){
        return list.get(list.size()-1);
    }

    public String getActualTime(int value, List<Measurement> measurements){
        Measurement m = measurements.get(value);
        return m.getTimestamp();
    }


    private int selectedDay = 0;
    private int selectedMonth = 0;
    private int selectedYear = 0;

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

    public ObservableList<Measurement> getAllList(List<Measurement> measurements) {
        ObservableList<Measurement> obsList = FXCollections.observableArrayList();
        for (Measurement measurement : measurements) {
                obsList.add(measurement);
            }
        return obsList;
    }


    @FXML
    public void initialize(){



        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime currentTime = LocalDateTime.now();
        String formattedTime = currentTime.format(formatter);
        localTime.setText("Current Time: \n" + formattedTime);


        choiceBox.getItems().add(0, "Kelsterbach");
        choiceBox.getItems().add(1, "Maintal");
        choiceBox.getItems().add(0, "Rodgau");

        datepicker.setDisable(true);

        slider.setDisable(false);

        ObservableList<Measurement> observableList = getAllList(kelsterbach);



        actualDate.setText(getLastMeasurement(kelsterbach).getTimestamp());
        showTooltip(observableList.size()-1, kelsterbach, kelsterbachButton);
        setTime(getLastMeasurement(kelsterbach).getTimestamp());
        co2Column.setCellValueFactory(new PropertyValueFactory<Measurement, Integer>("co2Level"));
        temperatureColumn.setCellValueFactory(new PropertyValueFactory<Measurement, Integer>("temperature"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<Measurement, String>("timestamp"));
        pm10Column.setCellValueFactory(new PropertyValueFactory<Measurement, Integer>("pm10Level"));
        pm1Column.setCellValueFactory(new PropertyValueFactory<Measurement, Integer>("pm1Level"));
        pm2Column.setCellValueFactory(new PropertyValueFactory<Measurement, Integer>("pm2Level"));

        choiceBox.setOnAction(event -> {

            if(choiceBox.getValue().equals("Kelsterbach")){
                datepicker.setDisable(false);
                selectedLocation = 0;

            }
            if(choiceBox.getValue().equals("Maintal")){
                tableView.getItems().clear();
                selectedLocation = 1;
            }

            if(choiceBox.getValue().equals("Rodgau")){
                tableView.getItems().clear();
                selectedLocation = 2;
            }
        });

        datepicker.setOnAction(event -> {
            LocalDate selectedDate = datepicker.getValue();
            selectedMonth = selectedDate.getMonthValue();
            selectedDay = selectedDate.getDayOfMonth();
            selectedYear = selectedDate.getYear();

            if(selectedLocation == 0){
                ObservableList<Measurement> filteredList = getListByDate(kelsterbach, selectedDay, selectedMonth, selectedYear);
                setTime(getLastMeasurement(kelsterbach).getTimestamp());
                slider.setValue(filteredList.size()-1);
                slider.setMax(filteredList.size()-1);
                tableView.setItems(filteredList);
            }

        });

        ObservableList<Measurement> filteredList = getListByDate(kelsterbach, day, month, year);

        slider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                showTooltip(newValue.intValue(), filteredList, kelsterbachButton);
                changeButtonColor(newValue.intValue(), filteredList, kelsterbachButton);
                actualDate.setText(getActualTime(newValue.intValue(), filteredList));
                System.out.println(filteredList.size());
            }
        });
    }

}
