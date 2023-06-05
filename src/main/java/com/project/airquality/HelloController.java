package com.project.airquality;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    public LineChart lineChart;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        XYChart.Series temperatureFrankfurt = new XYChart.Series();
        XYChart.Series temperatureWiesbaden = new XYChart.Series();
        temperatureFrankfurt.setName("Frankfurt");
        temperatureWiesbaden.setName("Wiesbaden");
        temperatureWiesbaden.getData().add(new XYChart.Data("20:00", 16));
        temperatureWiesbaden.getData().add(new XYChart.Data("21:00", 20));
        temperatureWiesbaden.getData().add(new XYChart.Data("22:00", 21));
        temperatureWiesbaden.getData().add(new XYChart.Data("23:00", 31));
        temperatureWiesbaden.getData().add(new XYChart.Data("24:00", 12));

        temperatureFrankfurt.getData().add(new XYChart.Data("20:00", 15));
        temperatureFrankfurt.getData().add(new XYChart.Data("21:00", 16));
        temperatureFrankfurt.getData().add(new XYChart.Data("22:00", 17));
        temperatureFrankfurt.getData().add(new XYChart.Data("23:00", 19));
        temperatureFrankfurt.getData().add(new XYChart.Data("24:00", 21));
        lineChart.getData().add(temperatureWiesbaden);
        lineChart.getData().add(temperatureFrankfurt);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }
}