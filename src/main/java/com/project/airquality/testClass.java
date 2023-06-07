package com.project.airquality;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class testClass extends Application {

    private LineChart<Number, Number> lineChart;
    private NumberAxis xAxis;
    private NumberAxis yAxis;
    private double lastMouseX;
    private double lastMouseY;

    @Override
    public void start(Stage primaryStage) {
        // Create a line chart
        xAxis = new NumberAxis();
        yAxis = new NumberAxis();
        lineChart = new LineChart<>(xAxis, yAxis);

        // Add data to the line chart
        lineChart.getData().add(createSampleSeries());

        // Create buttons
        Button zoomInButton = new Button("Zoom In");
        Button zoomOutButton = new Button("Zoom Out");
        Button resetButton = new Button("Reset Zoom");

        // Set button event handlers
        zoomInButton.setOnAction(event -> zoomIn());
        zoomOutButton.setOnAction(event -> zoomOut());
        resetButton.setOnAction(event -> resetZoom());

        // Create a BorderPane and add the line chart and buttons to it
        BorderPane root = new BorderPane();
        root.setCenter(lineChart);
        root.setBottom(zoomInButton);
        root.setMargin(zoomInButton, new Insets(5));
        root.setRight(zoomOutButton);
        root.setMargin(zoomOutButton, new Insets(5));
        root.setLeft(resetButton);
        root.setMargin(resetButton, new Insets(5));

        // Create a scene and set it on the stage
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Register mouse event handlers for panning
        lineChart.setOnMousePressed(event -> handleMousePressed(event));
        lineChart.setOnMouseDragged(event -> handleMouseDragged(event));

        // Register scroll event handler for zooming
        lineChart.setOnScroll(event -> handleScroll(event));
    }

    private void zoomIn() {
        xAxis.setAutoRanging(false);
        yAxis.setAutoRanging(false);
        xAxis.setLowerBound(xAxis.getLowerBound() + 1);
        xAxis.setUpperBound(xAxis.getUpperBound() - 1);
        yAxis.setLowerBound(yAxis.getLowerBound() + 1);
        yAxis.setUpperBound(yAxis.getUpperBound() - 1);
    }

    private void zoomOut() {
        xAxis.setAutoRanging(false);
        yAxis.setAutoRanging(false);
        xAxis.setLowerBound(xAxis.getLowerBound() - 1);
        xAxis.setUpperBound(xAxis.getUpperBound() + 1);
        yAxis.setLowerBound(yAxis.getLowerBound() - 1);
        yAxis.setUpperBound(yAxis.getUpperBound() + 1);
    }

    private void resetZoom() {
        xAxis.setAutoRanging(true);
        yAxis.setAutoRanging(true);
    }

    private void handleMousePressed(javafx.scene.input.MouseEvent event) {
        lastMouseX = event.getX();
        lastMouseY = event.getY();
    }

    private void handleMouseDragged(javafx.scene.input.MouseEvent event) {
        double deltaX = event.getX() - lastMouseX;
        double deltaY = event.getY() - lastMouseY;

        double xAxisScale = xAxis.getScale();
        double yAxisScale = yAxis.getScale();

        double xAxisOffset = deltaX / xAxisScale;
        double yAxisOffset = deltaY / yAxisScale;

        xAxis.setLowerBound(xAxis.getLowerBound() - xAxisOffset);
        xAxis.setUpperBound(xAxis.getUpperBound() - xAxisOffset);
        yAxis.setLowerBound(yAxis.getLowerBound() - yAxisOffset);
        yAxis.setUpperBound(yAxis.getUpperBound() - yAxisOffset);

        lastMouseX = event.getX();
        lastMouseY = event.getY();
    }

    private void handleScroll(ScrollEvent event) {
        double zoomFactor = 1.1;
        double deltaY = event.getDeltaY();

        if (deltaY < 0) {
            zoomOut();
        } else {
            zoomIn();
        }

        event.consume();
    }

    private LineChart.Series<Number, Number> createSampleSeries() {
        LineChart.Series<Number, Number> series = new LineChart.Series<>();
        series.getData().add(new LineChart.Data<>(1, 5));
        series.getData().add(new LineChart.Data<>(2, 7));
        series.getData().add(new LineChart.Data<>(3, 3));
        series.getData().add(new LineChart.Data<>(4, 2));
        series.getData().add(new LineChart.Data<>(5, 8));
        return series;
    }

    public static void main(String[] args) {
        launch(args);
    }
}