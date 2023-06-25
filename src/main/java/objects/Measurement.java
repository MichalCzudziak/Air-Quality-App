package objects;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Measurement {
    private int id;
    private String timestamp;
    private double temperature;
    private double co2Level;
    private double pm1Level;

    private double pm2Level;



    private double pm10Level;
    private double brightnessLevel;
    private double pressureLevel;
    private double humidityLevel;
    private double airIndex;

    private LocalDateTime timestampDate;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public double getPm1Level() {
        return pm1Level;
    }

    public void setPm1Level(double pm1Level) {
        this.pm1Level = pm1Level;
    }

    public double getBrightnessLevel() {
        return brightnessLevel;
    }

    public void setBrightnessLevel(double brightnessLevel) {
        this.brightnessLevel = brightnessLevel;
    }

    public double getPm2Level() {
        return pm2Level;
    }

    public void setPm2Level(double pm2Level) {
        this.pm2Level = pm2Level;
    }

    public double getPm10Level() {
        return pm10Level;
    }

    public void setPm10Level(double pm10Level) {
        this.pm10Level = pm10Level;
    }


    public double getPressureLevel() {
        return pressureLevel;
    }

    public void setPressureLevel(double pressureLevel) {
        this.pressureLevel = pressureLevel;
    }

    public double getHumidityLevel() {
        return humidityLevel;
    }

    public void setHumidityLevel(double humidityLevel) {
        this.humidityLevel = humidityLevel;
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

    public double calculateArqIndex() {
        return 20.0;
    }

    public String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd HH:mm", Locale.GERMAN);
        return this.timestampDate.format(formatter);
    }

    public LocalDateTime getTimestampDate() {
        return timestampDate;
    }

    public void setTimestampDate(LocalDateTime timestampDate) {
        this.timestampDate = timestampDate;
    }

    public Measurement(int id, String timestamp, double temperature, double co2Level, double pm1Level, double brightnessLevel, double pressureLevel, double humidityLevel) {
        this.id = id;
        this.timestamp = timestamp;
        this.temperature = temperature;
        this.co2Level = co2Level;
        this.pm1Level = pm1Level;
        this.brightnessLevel = brightnessLevel;
        this.pressureLevel = pressureLevel;
        this.humidityLevel = humidityLevel;
        this.airIndex = calculateArqIndex();
    }

    public Measurement(int id, LocalDateTime timestamp, double temperature, double co2Level, double pm1Level, double pm2Level, double pm10Level,
                       double brightnessLevel, double pressureLevel, double humidityLevel) {
        this.id = id;
        this.timestampDate = timestamp;
        this.temperature = temperature;
        this.co2Level = co2Level;
        this.pm1Level = pm1Level;
        this.pm2Level = pm2Level;
        this.pm10Level = pm10Level;
        this.brightnessLevel = brightnessLevel;
        this.pressureLevel = pressureLevel;
        this.humidityLevel = humidityLevel;
    }

    public Measurement(){}

}
