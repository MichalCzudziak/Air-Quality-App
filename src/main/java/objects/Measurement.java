package objects;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Measurement {
    private int id;
    private String timestamp;
    private int temperature;
    private int co2Level;
    private int pm1Level;

    private int pm2Level;
    private int pm10Level;
    private int brightnessLevel;
    private int pressureLevel;
    private int humidityLevel;

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

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public double getCo2Level() {
        return co2Level;
    }

    public void setCo2Level(int co2Level) {
        this.co2Level = co2Level;
    }

    public double getPm1Level() {
        return pm1Level;
    }

    public void setPm1Level(int pm1Level) {
        this.pm1Level = pm1Level;
    }

    public double getBrightnessLevel() {
        return brightnessLevel;
    }

    public void setBrightnessLevel(int brightnessLevel) {
        this.brightnessLevel = brightnessLevel;
    }

    public double getPm2Level() {
        return pm2Level;
    }

    public void setPm2Level(int pm2Level) {
        this.pm2Level = pm2Level;
    }

    public double getPm10Level() {
        return pm10Level;
    }

    public void setPm10Level(int pm10Level) {
        this.pm10Level = pm10Level;
    }


    public double getPressureLevel() {
        return pressureLevel;
    }

    public void setPressureLevel(int pressureLevel) {
        this.pressureLevel = pressureLevel;
    }

    public double getHumidityLevel() {
        return humidityLevel;
    }

    public void setHumidityLevel(int humidityLevel) {
        this.humidityLevel = humidityLevel;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
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

    public Measurement(int id, String timestamp, int temperature, int co2Level, int pm1Level, int brightnessLevel, int pressureLevel, int humidityLevel) {
        this.id = id;
        this.timestamp = timestamp;
        this.temperature = temperature;
        this.co2Level = co2Level;
        this.pm1Level = pm1Level;
        this.brightnessLevel = brightnessLevel;
        this.pressureLevel = pressureLevel;
        this.humidityLevel = humidityLevel;
    }

    public Measurement(int id, LocalDateTime timestamp, int temperature, int co2Level, int pm1Level, int pm2Level, int pm10Level,
                       int brightnessLevel, int pressureLevel, int humidityLevel) {
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
