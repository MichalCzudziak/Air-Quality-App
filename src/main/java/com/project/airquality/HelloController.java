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
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        XYChart.Series series = new XYChart.Series();
        series.getData().add(new XYChart.Data("10:00", 25));
        series.getData().add(new XYChart.Data("12:00", 27));
        series.getData().add(new XYChart.Data("14:00", 30));
        series.getData().add(new XYChart.Data("16:00", 25));

        lineChart.getData().add(series);

        XYChart.Series series2 = new XYChart.Series();
        series2.getData().add(new XYChart.Data("10:00", 13));
        series2.getData().add(new XYChart.Data("12:00", 17));
        series2.getData().add(new XYChart.Data("14:00", 24));
        series2.getData().add(new XYChart.Data("16:00", 35));

        lineChart.getData().add(series2);
    }
}