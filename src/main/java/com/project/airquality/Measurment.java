package com.project.airquality;

public class Measurment {
    private String timestamp;
    private double temperature;
    private double co2Level;
    private double fineDustLevel;
    private double brightnessLevel;
    private double pressureLevel;
    private double wetLevel;
    private double airIndex;

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getCo2Level() {
        return co2Level;
    }

    public void setCo2Level(double co2Level) {
        this.co2Level = co2Level;
    }

    public double getFineDustLevel() {
        return fineDustLevel;
    }

    public void setFineDustLevel(double fineDustLevel) {
        this.fineDustLevel = fineDustLevel;
    }

    public double getBrightnessLevel() {
        return brightnessLevel;
    }

    public void setBrightnessLevel(double brightnessLevel) {
        this.brightnessLevel = brightnessLevel;
    }

    public double getPressureLevel() {
        return pressureLevel;
    }

    public void setPressureLevel(double pressureLevel) {
        this.pressureLevel = pressureLevel;
    }

    public double getWetLevel() {
        return wetLevel;
    }

    public void setWetLevel(double wetLevel) {
        this.wetLevel = wetLevel;
    }

    public double getAirIndex() {
        return airIndex;
    }

    public void setAirIndex(double airIndex) {
        this.airIndex = airIndex;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Measurment(String timestamp, double temperature, double co2Level, double fineDustLevel, double brightnessLevel, double pressureLevel, double wetLevel, double airIndex) {
        this.timestamp = timestamp;
        this.temperature = temperature;
        this.co2Level = co2Level;
        this.fineDustLevel = fineDustLevel;
        this.brightnessLevel = brightnessLevel;
        this.pressureLevel = pressureLevel;
        this.wetLevel = wetLevel;
        this.airIndex = airIndex;
    }


}
