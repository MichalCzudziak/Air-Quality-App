package objects;

public class Measurement {
    private int id;
    private String timestamp;
    private double temperature;
    private double co2Level;
    private double fineDustLevel;
    private double brightnessLevel;
    private double pressureLevel;
    private double humidityLevel;
    private double airIndex;

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

    public Measurement(int id, String timestamp, double temperature, double co2Level, double fineDustLevel, double brightnessLevel, double pressureLevel, double humidityLevel, double airIndex) {
        this.id = id;
        this.timestamp = timestamp;
        this.temperature = temperature;
        this.co2Level = co2Level;
        this.fineDustLevel = fineDustLevel;
        this.brightnessLevel = brightnessLevel;
        this.pressureLevel = pressureLevel;
        this.humidityLevel = humidityLevel;
        this.airIndex = airIndex;
    }

    public Measurement(){}

}
