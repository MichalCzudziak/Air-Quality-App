package database;

import com.project.airquality.Main;
import objects.Location;
import objects.Measurement;

import java.sql.*;
import java.util.ArrayList;

public class DBController {
    public Connection getConnection(){
        DBConfigManager dbConfigManager = new DBConfigManager();
        //  Database credentials
        String url = "jdbc:mysql://"+ dbConfigManager.getHost() + ":3306/" + dbConfigManager.getDatabase();
        String user = dbConfigManager.getUsername();
        String pass = dbConfigManager.getPassword();

        // JDBC driver name and database URL
        try {
            return DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            System.out.println("KEINE Verbindung: ");
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Location> getAllLocations() throws SQLException {
        ArrayList<Location> allLocations = new ArrayList<>();
        Connection conn = this.getConnection();
        String query = "SELECT * FROM Airquality.Location";
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            Location location = new Location();
            location.setId(rs.getInt("id"));
            location.setName(rs.getString("name"));
            location.setLat(rs.getDouble("lat"));
            location.setLon(rs.getDouble("lon"));
            allLocations.add(location);
        }

        conn.close();
        return allLocations;
    }


    public void loadMeasurements() throws SQLException {
        Connection conn = this.getConnection();

        for (Location loc : Main.allLocations) {
            ArrayList<Measurement> allMeasurements = new ArrayList<>();
            String query = "SELECT * FROM Airquality.Measurement WHERE idLocation=" + loc.getId() ;
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Measurement measurement = new Measurement();
                measurement.setId(rs.getInt("id"));
                measurement.setTimestamp(rs.getString("timestamp"));
                measurement.setTemperature(rs.getInt("temperature"));
                measurement.setCo2Level(rs.getInt("co2"));
                measurement.setPm1Level(rs.getInt("pm1"));
                measurement.setBrightnessLevel(rs.getInt("lux"));
                measurement.setPressureLevel(rs.getInt("pressure")/100);
                measurement.setHumidityLevel(rs.getInt("humidity"));
                measurement.setPm2Level(rs.getInt("pm2_5"));
                measurement.setPm10Level(rs.getInt("pm10"));
                allMeasurements.add(measurement);
            }
            loc.setMeasurements(allMeasurements);
        }
        conn.close();
    }
    public void updateMeasurements() throws SQLException {
        Connection conn = this.getConnection();

        for (Location loc : Main.allLocations) {
            ArrayList<Measurement> allMeasurements = new ArrayList<>();
            int listSize = loc.getMeasurements().size();
            String query = "SELECT * FROM Airquality.Measurement WHERE idLocation >" + loc.getMeasurements().get(listSize -1).getId() ;
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Measurement measurement = new Measurement();
                measurement.setId(rs.getInt("id"));
                measurement.setTimestamp(rs.getString("timestamp"));
                measurement.setTemperature(rs.getInt("temperature"));
                measurement.setCo2Level(rs.getInt("co2"));
                measurement.setPm1Level(rs.getInt("pm1"));
                measurement.setBrightnessLevel(rs.getInt("lux"));
                measurement.setPressureLevel(rs.getInt("pressure")/100);
                measurement.setHumidityLevel(rs.getInt("humidity"));
                measurement.setPm2Level(rs.getInt("pm2_5"));
                measurement.setPm10Level(rs.getInt("pm10"));
                allMeasurements.add(measurement);
            }
            loc.setMeasurements(allMeasurements);
        }
        conn.close();
    }
}
