package objects;

import java.util.ArrayList;

public class Location {
    private int id;
    private String name;
    private String address;
    private String geoCoordinates;
    private ArrayList<Measurement> measurements = new ArrayList<>();

    public Location(){}
    public Location(int id, String name, String address, String geoCoordinates) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.geoCoordinates = geoCoordinates;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGeoCoordinates() {
        return geoCoordinates;
    }

    public void setGeoCoordinates(String geoCoordinates) {
        this.geoCoordinates = geoCoordinates;
    }

    public ArrayList<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(ArrayList<Measurement> measurements) {
        this.measurements = measurements;
    }
}
