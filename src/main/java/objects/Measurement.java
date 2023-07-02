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

    private int pm1AQI;
    private int pm2AQI;
    private int pm10AQI;
    private int co2AQI;


    private int dominantAQI;


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

    public int getPm1AQI() {
        return pm1AQI;
    }

    public void setPm1AQI(int pm1AQI) {
        this.pm1AQI = pm1AQI;
    }

    public int getPm2AQI() {
        return pm2AQI;
    }

    public void setPm2AQI(int pm2AQI) {
        this.pm2AQI = pm2AQI;
    }

    public int getPm10AQI() {
        return pm10AQI;
    }

    public void setPm10AQI(int pm10AQI) {
        this.pm10AQI = pm10AQI;
    }

    public int getCo2AQI() {
        return co2AQI;
    }

    public void setCo2AQI(int co2AQI) {
        this.co2AQI = co2AQI;
    }

    public int getDominantAQI() {
        return dominantAQI;
    }

    public void setDominantAQI(int dominantAQI) {
        this.dominantAQI = dominantAQI;
    }

    public void calculateAll()
    {
        calculatePM2AQI();
        calculatePM10AQI();
        calculatePM1AQI();
        calculateCO2AQI();
        calculateDominantAQI();
    }

    public void calculatePM1AQI(){
        if(this.pm1Level <=5) setPm1AQI(1);
        if(this.pm1Level >=6 && this.pm1Level <=10) setPm1AQI(2);
        if(this.pm1Level >=11 && this.pm1Level <=15) setPm1AQI(3);
        if(this.pm1Level >=16 && this.pm1Level <=30) setPm1AQI(4);
        if(this.pm1Level >=31 && this.pm1Level <=50) setPm1AQI(5);
        if(this.pm1Level > 50) setPm1AQI(6);
    }
    public void calculatePM2AQI(){
        if(this.pm2Level <=9) setPm2AQI(1);
        if(this.pm2Level >=10 && this.pm2Level <=20) setPm2AQI(2);
        if(this.pm2Level >=21 && this.pm2Level <=29) setPm2AQI(3);
        if(this.pm2Level >=30 && this.pm2Level <=49) setPm2AQI(4);
        if(this.pm2Level>=50 && this.pm2Level <=75) setPm2AQI(5);
        if(this.pm2Level> 75) setPm2AQI(6);
    }
    public void calculatePM10AQI(){
        if(this.pm10Level <=20) setPm10AQI(1);
        if(this.pm10Level  >=21 && this.pm10Level  <=30) setPm10AQI (2);
        if(this.pm10Level  >=31 && this.pm10Level  <=40) setPm10AQI (3);
        if(this.pm10Level  >=41 && this.pm10Level  <=50) setPm10AQI (4);
        if(this.pm10Level  >=51 && this.pm10Level  <=100) setPm10AQI (5);
        if(this.pm10Level  > 100) setPm10AQI (6);
  }
    public void calculateCO2AQI(){
        if(this.co2Level >=400 && this.co2Level <=650) setCo2AQI(1);
        if(this.co2Level >=651 && this.co2Level <=1500) setCo2AQI(2);
        if(this.co2Level >=1501 && this.co2Level <=2000) setCo2AQI(3);
        if(this.co2Level >=2001 && this.co2Level <=2500) setCo2AQI(4);
        if(this.co2Level >=2501 && this.co2Level <=5000) setCo2AQI(5);
        if(this.co2Level > 5000) setCo2AQI(6);
    }

    public void calculateDominantAQI(){
        setDominantAQI(Math.max(Math.max(this.pm1AQI, this.pm2AQI), Math.max(this.pm10AQI, this.co2AQI)));
    }
}
