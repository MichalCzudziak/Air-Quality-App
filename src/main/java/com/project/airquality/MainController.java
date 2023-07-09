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
import java.util.concurrent.atomic.AtomicReferenceArray;

import static java.lang.Math.round;

public class MainController {

    @FXML
    private Label avgAQIKelsterbach;

    @FXML
    private Label avgAQIMaintal;

    @FXML
    private Label avgAQIRodgau;

    @FXML
    private Label lastAQIMaintal;


    @FXML
    private Label lastAQIKelsterbach;


    @FXML
    private Label lastAQIRodgau;


    @FXML
    private Label actualDateKelsterbach;

    @FXML
    private Label actualDateMaintal;

    @FXML
    private Label actualDateRodgau;

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
    private TableColumn<Measurement, Integer> idColumn;


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
    private ArrayList<Measurement> rodgau = Main.allLocations.get(1).getMeasurements();
    private ArrayList<Measurement> maintal = Main.allLocations.get(2).getMeasurements();

    public Button settingsButton;
    public Label avgArq;
    private Stage stage;
    private Scene scene;
    private Parent root;

    private int year;
    private int month;
    private int day;

    private int selectedLocation;
    private LocalDateTime dateTime;
    private LocalDateTime dateTimeKelsterbach;
    private LocalDateTime dateTimeMaintal;
    private LocalDateTime dateTimeRodgau;
    private int selectedDay = 0;
    private int selectedMonth = 0;
    private int selectedYear = 0;

    private int selectedDayKelsterbach = 0;
    private int selectedMonthKelsterbach = 0;
    private int selectedYearKelsterbach = 0;

    private int selectedDayMaintal = 0;
    private int selectedMonthMaintal  = 0;
    private int selectedYearMaintal  = 0;


    private int selectedDayRodgau = 0;
    private int selectedMonthRodgau = 0;
    private int selectedYearRodgau = 0;




    public void selectLocation(int location){
        this.selectedLocation = location;
    }
    public void setTime(String timestamp) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.dateTime = LocalDateTime.parse(timestamp, formatter);
        this.selectedDay = dateTime.getDayOfMonth();
        this.selectedMonth = dateTime.getMonthValue();
        this.selectedYear = dateTime.getYear();
    }

    public void setTime(String timestampKelsterbach, String timestampMaintal, String timestampRodgau) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.dateTimeKelsterbach = LocalDateTime.parse(timestampKelsterbach, formatter);
        this.selectedYearKelsterbach = dateTimeKelsterbach.getYear();
        this.selectedMonthKelsterbach = dateTimeKelsterbach.getMonthValue();
        this.selectedDayKelsterbach = dateTimeKelsterbach.getDayOfMonth();
        this.dateTimeMaintal = LocalDateTime.parse(timestampMaintal, formatter);
        this.selectedYearMaintal = dateTimeMaintal.getYear();
        this.selectedMonthMaintal = dateTimeMaintal.getMonthValue();
        this.selectedDayMaintal = dateTimeMaintal.getDayOfMonth();
        this.dateTimeRodgau = LocalDateTime.parse(timestampRodgau, formatter);
        this.selectedYearRodgau = dateTimeRodgau.getYear();
        this.selectedMonthRodgau = dateTimeRodgau.getMonthValue();
        this.selectedDayRodgau = dateTimeRodgau.getDayOfMonth();
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
        Parent root = FXMLLoader.load(getClass().getResource("rodgau.fxml"));
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


    public void calculateAQI(ArrayList<Measurement>list){
        for(Measurement m: list){
            m.calculateAll();
        }
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
        int size = measurements.size();
        int sum = 0;
        for (Measurement measurement : measurements) {
            sum = sum + measurement.getDominantAQI();
        }
        return sum / size;
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

    public void changeButtonColor(Measurement m, Button btn){
        if (m.getDominantAQI() == 1) {
            btn.setStyle("-fx-background-color: #0411E7;");
        }
        if (m.getDominantAQI() == 2) {
            btn.setStyle("-fx-background-color: #046AE7;");
        }
        if (m.getDominantAQI() == 3) {
            btn.setStyle("-fx-background-color: #04C9E7;");
        }
        if (m.getDominantAQI() == 4) {
            btn.setStyle("-fx-background-color: #CCB532;");
        }
        if (m.getDominantAQI() == 5) {
            btn.setStyle("-fx-background-color: #E9830E;");
        }
        if (m.getDominantAQI() == 6) {
            btn.setStyle("-fx-background-color: #CE2903;");
        }


    }

    public void showTooltip(int value, Measurement m, Button btn){
        Tooltip tooltip = new Tooltip();
        tooltip.setShowDelay(Duration.ZERO);
        tooltip.setText("Time: " + m.getTimestamp() + "\n" +
                "AQI: " + m.getDominantAQI() + "\n" +
                "PM1: " + m.getPm1Level() + "\n" +
                "PM2.5: " + m.getPm2Level() + "\n" +
                "PM10: " + m.getPm10Level() + "\n" +
                "CO2: " + m.getCo2Level() + "\n"

        );
        tooltip.setStyle("-fx-font-size: 14px"); // Set the font size
        Tooltip.install(btn, tooltip);
    }

    public ObservableList<Measurement> convertList(ArrayList<Measurement> measurements){
        ObservableList<Measurement> obsList = FXCollections.observableArrayList();
        obsList.addAll(measurements);
        return obsList;
    }

    public Measurement getLastMeasurement(List<Measurement> list){
        return list.get(list.size()-1);
    }

    public String getActualTime(int value, List<Measurement> measurements){
        Measurement m = measurements.get(value-1);
        return m.getTimestamp();
    }


    public ObservableList<Measurement> getListByDate(List<Measurement> measurements, int day, int month, int year) {
        ObservableList<Measurement> obsList = FXCollections.observableArrayList();
        for (Measurement measurement : measurements) {
            //setTime(measurement.getTimestamp());
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

    public ArrayList <Measurement> filterListByDate(List<Measurement> measurements, int day, int month, int year) {
        ArrayList<Measurement> filteredList = new ArrayList<>();
        for (Measurement measurement : measurements) {
            setTime(measurement.getTimestamp());
            if (this.selectedDay == day && this.selectedMonth == month && this.selectedYear == year) {
                filteredList.add(measurement);
            }
        }
        return filteredList;
    }

    public ArrayList<LocalDate> findDisablesDays(ArrayList<Measurement> list){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ArrayList<LocalDate> rtr = new ArrayList<>();
        for (Measurement m : list){
            LocalDate date = LocalDate.parse(m.getTimestamp(), formatter);
            rtr.add(LocalDate.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth()));
        }
        return rtr;
    }

    public void setPM1TableColor(){
        pm1Column.setCellFactory(column -> new TableCell<Measurement, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && item <= 5) {
                    setText(item.toString());
                    setStyle("-fx-background-color: rgba(4, 17, 231, 0.5); -fx-text-fill: white;");
                }
                if (item != null && item >= 6 && item <=10) {
                    setText(item.toString());
                    setStyle("-fx-background-color: rgba(4, 106, 231, 0.5); -fx-text-fill: white;");
                }
                if (item != null && item >= 11 && item <=15) {
                    setText(item.toString());
                    setStyle("-fx-background-color: rgba(4, 201, 231, 0.5); -fx-text-fill: white;");
                }
                if (item != null && item >= 16 && item <=30) {
                    setText(item.toString());
                    setStyle("-fx-background-color: rgba(204, 181, 50, 0.5); -fx-text-fill: white;");
                }
                if (item != null && item >= 31 && item <=50) {
                    setText(item.toString());
                    setStyle("-fx-background-color: rgba(233, 131, 14, 0.5); -fx-text-fill: white;");
                }
                if (item != null && item >50){
                    setText(item.toString());
                    setStyle("-fx-background-color: rgba(206, 41, 3, 0.5); -fx-text-fill: white;");
                }
            }
        });
    }
    public void setPM2TableColor(){
        pm2Column.setCellFactory(column -> new TableCell<Measurement, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && item <= 9) {
                    setText(item.toString());
                    setStyle("-fx-background-color: rgba(4, 17, 231, 0.5); -fx-text-fill: white;");
                }
                if (item != null && item >= 10 && item <=20) {
                    setText(item.toString());
                    setStyle("-fx-background-color: rgba(4, 106, 231, 0.5); -fx-text-fill: white;");
                }
                if (item != null && item >= 21 && item <=29) {
                    setText(item.toString());
                    setStyle("-fx-background-color: rgba(4, 201, 231, 0.5); -fx-text-fill: white;");
                }
                if (item != null && item >= 30 && item <=49) {
                    setText(item.toString());
                    setStyle("-fx-background-color: rgba(204, 181, 50, 0.5); -fx-text-fill: white;");
                }
                if (item != null && item >= 50 && item <=75) {
                    setText(item.toString());
                    setStyle("-fx-background-color: rgba(233, 131, 14, 0.5); -fx-text-fill: white;");
                }
                if (item != null && item >75){
                    setText(item.toString());
                    setStyle("-fx-background-color: rgba(206, 41, 3, 0.5); -fx-text-fill: white;");
                }
            }
        });
    }
    public void setPM10TableColor(){

        pm10Column.setCellFactory(column -> new TableCell<Measurement, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && item <= 20) {
                    setText(item.toString());
                    setStyle("-fx-background-color: rgba(4, 17, 231, 0.5); -fx-text-fill: white;");
                }
                if (item != null && item >= 21 && item <=30) {
                    setText(item.toString());
                    setStyle("-fx-background-color: rgba(4, 106, 231, 0.5); -fx-text-fill: white;");
                }
                if (item != null && item >= 31 && item <=40) {
                    setText(item.toString());
                    setStyle("-fx-background-color: rgba(4, 201, 231, 0.5); -fx-text-fill: white;");
                }
                if (item != null && item >= 41 && item <=50) {
                    setText(item.toString());
                    setStyle("-fx-background-color: rgba(204, 181, 50, 0.5); -fx-text-fill: white;");
                }
                if (item != null && item >= 51 && item <=100) {
                    setText(item.toString());
                    setStyle("-fx-background-color: rgba(233, 131, 14, 0.5); -fx-text-fill: white;");
                }
                if (item != null && item >100){
                    setText(item.toString());
                    setStyle("-fx-background-color: rgba(206, 41, 3, 0.5); -fx-text-fill: white;");
                }
            }
        });
    }

    public void setCO2TableColor(){
        co2Column.setCellFactory(column -> new TableCell<Measurement, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && item >= 400 &&item <= 650) {
                    setText(item.toString());
                    setStyle("-fx-background-color: rgba(4, 17, 231, 0.5); -fx-text-fill: white;");
                }
                if (item != null && item >= 651 && item <=1500) {
                    setText(item.toString());
                    setStyle("-fx-background-color: rgba(4, 106, 231, 0.5); -fx-text-fill: white;");
                }
                if (item != null && item >= 1501 && item <=2000) {
                    setText(item.toString());
                    setStyle("-fx-background-color: rgba(4, 201, 231, 0.5); -fx-text-fill: white;");
                }
                if (item != null && item >= 2001 && item <=2500) {
                    setText(item.toString());
                    setStyle("-fx-background-color: rgba(204, 181, 50, 0.5); -fx-text-fill: white;");
                }
                if (item != null && item >= 2501 && item <=5000) {
                    setText(item.toString());
                    setStyle("-fx-background-color: rgba(233, 131, 14, 0.5); -fx-text-fill: white;");
                }
                if (item != null && item >5000){
                    setText(item.toString());
                    setStyle("-fx-background-color: rgba(206, 41, 3, 0.5); -fx-text-fill: white;");
                }
            }
        });
    }

    public void setAQITableColor(){
        aqiColumn.setCellFactory(column -> new TableCell<Measurement, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && item == 1) {
                    setText(item.toString());
                    setStyle("-fx-background-color: rgba(4, 17, 231, 0.5); -fx-text-fill: white;");
                }
                if (item != null && item == 2) {
                    setText(item.toString());
                    setStyle("-fx-background-color: rgba(4, 106, 231, 0.5); -fx-text-fill: white;");
                }
                if (item != null && item == 3) {
                    setText(item.toString());
                    setStyle("-fx-background-color: rgba(4, 201, 231, 0.5); -fx-text-fill: white;");
                }
                if (item != null && item == 4) {
                    setText(item.toString());
                    setStyle("-fx-background-color: rgba(204, 181, 50, 0.5); -fx-text-fill: white;");
                }
                if (item != null && item == 5) {
                    setText(item.toString());
                    setStyle("-fx-background-color: rgba(233, 131, 14, 0.5); -fx-text-fill: white;");
                }
                if (item != null && item == 6){
                    setText(item.toString());
                    setStyle("-fx-background-color: rgba(206, 41, 3, 0.5); -fx-text-fill: white;");
                }
            }
        });
    }

    public void clearTableColumns() {
        idColumn.setCellValueFactory(null);
        co2Column.setCellValueFactory(null);
        temperatureColumn.setCellValueFactory(null);
        timeColumn.setCellValueFactory(null);
        pm10Column.setCellValueFactory(null);
        pm1Column.setCellValueFactory(null);
        pm2Column.setCellValueFactory(null);
        aqiColumn.setCellValueFactory(null);
    }

    public void setSlider(ArrayList<Measurement> list){
        slider.setMin(0);
        slider.setValue(list.size()-1);
        slider.setMax(list.size()-1);
        slider.setBlockIncrement(1);
        slider.setMinorTickCount(0);
        slider.setMajorTickUnit(1);
        slider.setSnapToTicks(true);
        slider.setDisable(false);
    }
    public Measurement getMeasurementFromList(ArrayList<Measurement> list,int index){
        return list.get(index);
    }

    @FXML
    public void initialize(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime currentTime = LocalDateTime.now();
        String formattedTime = currentTime.format(formatter);
        localTime.setText("Current Time: \n" + formattedTime);

        choiceBox.getItems().add(0, "Kelsterbach");
        choiceBox.getItems().add(1, "Maintal");
        choiceBox.getItems().add(2, "Rodgau");
        slider.setDisable(true);

        calculateAQI(kelsterbach);
        calculateAQI(maintal);
        calculateAQI(rodgau);

        changeButtonColor(getLastMeasurement(kelsterbach), kelsterbachButton);
        changeButtonColor(getLastMeasurement(maintal), maintalButton);
        changeButtonColor(getLastMeasurement(rodgau), rodgauButton);

        lastAQIKelsterbach.setText("Last Measured AQI: " + Integer.toString(getLastMeasurement(kelsterbach).getDominantAQI()));
        actualDateKelsterbach.setText(getLastMeasurement(kelsterbach).getTimestamp());
        lastAQIMaintal.setText("Last Measured AQI: " + Integer.toString(getLastMeasurement(maintal).getDominantAQI()));
        actualDateMaintal.setText(getLastMeasurement(maintal).getTimestamp());
        lastAQIRodgau.setText("Last Measured AQI: " + Integer.toString(getLastMeasurement(rodgau).getDominantAQI()));
        actualDateRodgau.setText(getLastMeasurement(rodgau).getTimestamp());

        List<LocalDate> disabledDatesKelsterbach = findDisablesDays(kelsterbach);
        List<LocalDate> disabledDatesRodgau = findDisablesDays(rodgau);
        List<LocalDate> disabledDatesMaintal = findDisablesDays(maintal);


        datepicker.setDisable(true);
        datepicker.setValue(null);

        setTime(getLastMeasurement(kelsterbach).getTimestamp(), getLastMeasurement(maintal).getTimestamp(), getLastMeasurement(rodgau).getTimestamp());

        ArrayList<Measurement> filteredListKelsterbach = filterListByDate(kelsterbach, selectedDayKelsterbach, selectedMonthKelsterbach, selectedYearKelsterbach);
        ArrayList<Measurement> filteredListMaintal = filterListByDate(maintal, selectedDayMaintal, selectedMonthMaintal, selectedYearMaintal);
        ArrayList<Measurement> filteredListRodgau = filterListByDate(rodgau, selectedDayRodgau, selectedMonthRodgau, selectedYearRodgau);

        avgAQIKelsterbach.setText("Day Average AQI: " + Integer.toString(calculateAvgAQI(filteredListKelsterbach)));
        avgAQIMaintal.setText("Day Average AQI: " + Integer.toString(calculateAvgAQI(filteredListMaintal)));
        avgAQIRodgau.setText("Day Average AQI: " + Integer.toString(calculateAvgAQI(filteredListRodgau)));


        showTooltip(filteredListKelsterbach.size()-1, getLastMeasurement(filteredListKelsterbach), kelsterbachButton);
        showTooltip(filteredListMaintal.size()-1, getLastMeasurement(filteredListMaintal), maintalButton);
        showTooltip(filteredListRodgau.size()-1, getLastMeasurement(filteredListRodgau), rodgauButton);

        choiceBox.setOnAction(event -> {
            if(choiceBox.getValue().equals("Kelsterbach")){
                datepicker.setDisable(false);
                tableView.getItems().clear();
                selectedLocation = 0;

            }
            if(choiceBox.getValue().equals("Maintal")){
                datepicker.setDisable(false);
                tableView.getItems().clear();
                selectedLocation = 1;
            }

            if(choiceBox.getValue().equals("Rodgau")){
                datepicker.setDisable(false);
                tableView.getItems().clear();
                selectedLocation = 2;
            }
        });

        setPM1TableColor();
        setPM2TableColor();
        setPM10TableColor();
        setCO2TableColor();
        setAQITableColor();

        idColumn.setCellValueFactory(new PropertyValueFactory<Measurement, Integer>("id"));
        co2Column.setCellValueFactory(new PropertyValueFactory<Measurement, Integer>("co2Level"));
        temperatureColumn.setCellValueFactory(new PropertyValueFactory<Measurement, Integer>("temperature"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<Measurement, String>("timestamp"));
        pm10Column.setCellValueFactory(new PropertyValueFactory<Measurement, Integer>("pm10Level"));
        pm1Column.setCellValueFactory(new PropertyValueFactory<Measurement, Integer>("pm1Level"));
        pm2Column.setCellValueFactory(new PropertyValueFactory<Measurement, Integer>("pm2Level"));
        aqiColumn.setCellValueFactory(new PropertyValueFactory<Measurement, Integer>("dominantAQI"));


        datepicker.setOnAction(event -> {
            if(selectedLocation == 0){
                ObservableList<Measurement> lastList = tableView.getItems();

                LocalDate selectedDate = datepicker.getValue();
                selectedMonthKelsterbach= selectedDate.getMonthValue();
                selectedDayKelsterbach = selectedDate.getDayOfMonth();
                selectedYearKelsterbach = selectedDate.getYear();
                ArrayList<Measurement> filteredList = filterListByDate(kelsterbach, selectedDayKelsterbach, selectedMonthKelsterbach, selectedYearKelsterbach);
                ObservableList<Measurement> observableList = convertList(filteredList);

                setSlider(filteredList);
                actualDateKelsterbach.setText(getLastMeasurement(filteredList).getTimestamp());
                lastAQIKelsterbach.setText("Last Measured AQI: " + Integer.toString(getLastMeasurement(filteredList).getDominantAQI()));
                avgAQIKelsterbach.setText("Day Average AQI: " + Integer.toString(calculateAvgAQI(filteredList)));

                showTooltip(filteredList.size()-1, getLastMeasurement(filteredList), kelsterbachButton);
                changeButtonColor(getLastMeasurement(filteredList), kelsterbachButton);

                slider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                    int index = newValue.intValue();
                    if (index >= 0 && index < observableList.size() && selectedLocation == 0) {
                        Measurement measurement = observableList.get(index);
                        showTooltip(index, measurement, kelsterbachButton);
                        changeButtonColor(measurement, kelsterbachButton);
                        actualDateKelsterbach.setText(measurement.getTimestamp());
                        lastAQIKelsterbach.setText("Last Measured AQI: " + Integer.toString(measurement.getDominantAQI()));
                    }
                });
                tableView.getItems().removeAll(lastList);
                tableView.refresh();
                tableView.getItems().addAll(observableList);
            }

            if(selectedLocation == 1){
                ObservableList<Measurement> lastList = tableView.getItems();

                LocalDate selectedDate = datepicker.getValue();
                selectedMonthMaintal= selectedDate.getMonthValue();
                selectedDayMaintal = selectedDate.getDayOfMonth();
                selectedYearMaintal = selectedDate.getYear();
                ArrayList<Measurement> filteredList = filterListByDate(maintal, selectedDayMaintal, selectedMonthMaintal, selectedYearMaintal);
                ObservableList<Measurement> observableList = convertList(filteredList);

                setSlider(filteredList);
                actualDateMaintal.setText(getLastMeasurement(filteredList).getTimestamp());
                lastAQIMaintal.setText("Last Measured AQI: " + Integer.toString(getLastMeasurement(filteredList).getDominantAQI()));
                avgAQIMaintal.setText("Day Average AQI: " + Integer.toString(calculateAvgAQI(filteredList)));

                showTooltip(filteredList.size()-1, getLastMeasurement(filteredList), maintalButton);
                changeButtonColor(getLastMeasurement(filteredList), maintalButton);

                slider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                    int index = newValue.intValue();
                    if (index >= 0 && index < observableList.size() && selectedLocation == 1) {
                        Measurement measurement = observableList.get(index);
                        showTooltip(index, measurement, maintalButton);
                        changeButtonColor(measurement, maintalButton);
                        actualDateMaintal.setText(measurement.getTimestamp());
                        lastAQIMaintal.setText("Last Measured AQI: " + Integer.toString(measurement.getDominantAQI()));
                    }
                });
                tableView.getItems().removeAll(lastList);
                tableView.refresh();
                tableView.getItems().addAll(observableList);
            }

            if(selectedLocation == 2){
                ObservableList<Measurement> lastList = tableView.getItems();

                LocalDate selectedDate = datepicker.getValue();
                selectedMonthRodgau= selectedDate.getMonthValue();
                selectedDayRodgau = selectedDate.getDayOfMonth();
                selectedYearRodgau = selectedDate.getYear();
                ArrayList<Measurement> filteredList = filterListByDate(rodgau, selectedDayRodgau, selectedMonthRodgau, selectedYearRodgau);
                ObservableList<Measurement> observableList = convertList(filteredList);

                setSlider(filteredList);
                actualDateRodgau.setText(getLastMeasurement(filteredList).getTimestamp());
                lastAQIRodgau.setText("Last Measured AQI: " + Integer.toString(getLastMeasurement(filteredList).getDominantAQI()));
                avgAQIRodgau.setText("Day Average AQI: " + Integer.toString(calculateAvgAQI(filteredList)));

                showTooltip(filteredList.size()-1, getLastMeasurement(filteredList), rodgauButton);
                changeButtonColor(getLastMeasurement(filteredList), rodgauButton);

                slider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                    int index = newValue.intValue();
                    if (index >= 0 && index < observableList.size() && selectedLocation == 2) {
                        Measurement measurement = observableList.get(index);
                        showTooltip(index, measurement, rodgauButton);
                        changeButtonColor(measurement, rodgauButton);
                        actualDateRodgau.setText(measurement.getTimestamp());
                        lastAQIRodgau.setText("Last Measured AQI: " + Integer.toString(measurement.getDominantAQI()));
                    }
                });
                tableView.getItems().removeAll(lastList);
                tableView.refresh();
                tableView.getItems().addAll(observableList);
            }

        });
        datepicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (selectedLocation == 0) {
                    if (date != null && !disabledDatesKelsterbach.contains(date)) {
                        setDisable(true);
                        setStyle("-fx-background-color: #ffc0cb;");
                    } else {
                        setDisable(false);
                        setStyle("");
                    }
                }
                if (selectedLocation == 1) {
                    if (date != null && !disabledDatesMaintal.contains(date)) {
                        setDisable(true);
                        setStyle("-fx-background-color: #ffc0cb;");
                    } else {
                        setDisable(false);
                        setStyle("");
                    }
                }
                if (selectedLocation == 2) {
                    if (date != null && !disabledDatesRodgau.contains(date)) {
                        setDisable(true);
                        setStyle("-fx-background-color: #ffc0cb;");
                    } else {
                        setDisable(false);
                        setStyle("");
                    }
                }

            }
        });


    }

}
