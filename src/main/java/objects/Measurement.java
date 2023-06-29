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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getCo2Level() {
        return co2Level;
    }

    public void setCo2Level(int co2Level) {
        this.co2Level = co2Level;
    }

    public int getPm1Level() {
        return pm1Level;
    }

    public void setPm1Level(int pm1Level) {
        this.pm1Level = pm1Level;
    }

    public int getBrightnessLevel() {
        return brightnessLevel;
    }

    public void setBrightnessLevel(int brightnessLevel) {
        this.brightnessLevel = brightnessLevel;
    }

    public int getPm2Level() {
        return pm2Level;
    }

    public void setPm2Level(int pm2Level) {
        this.pm2Level = pm2Level;
    }

    public int getPm10Level() {
        return pm10Level;
    }

    public void setPm10Level(int pm10Level) {
        this.pm10Level = pm10Level;
    }


    public int getPressureLevel() {
        return pressureLevel;
    }

    public void setPressureLevel(int pressureLevel) {
        this.pressureLevel = pressureLevel;
    }

    public int getHumidityLevel() {
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

    public Measurement(){}

    public LocalDateTime getTimestampDate() {
        return null;
    }

    public String getFormattedTimestamp() {
        return null;
    }
}
