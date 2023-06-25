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
        String query = "SELECT * FROM Außenklima.Location";
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            Location location = new Location();
            location.setId(rs.getInt("id"));
            location.setName(rs.getString("name"));
            location.setAddress(rs.getString("address"));
            location.setGeoCoordinates(rs.getString("geo_coordinates"));
            allLocations.add(location);
        }

        conn.close();
        return allLocations;
    }


    public void loadMeasurements() throws SQLException {
        Connection conn = this.getConnection();

        for (Location loc : Main.allLocations) {
            ArrayList<Measurement> allMeasurements = new ArrayList<>();
            String query = "SELECT * FROM Außenklima.Measurement WHERE location_id=" + loc.getId() ;
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Measurement measurement = new Measurement();
                measurement.setId(rs.getInt("id"));
                measurement.setTimestamp(rs.getString("date"));
                measurement.setTemperature(rs.getDouble("temperature"));
                measurement.setCo2Level(rs.getDouble("co2_level"));
                measurement.setPm1Level(rs.getDouble("fine_dust_level"));
                measurement.setBrightnessLevel(rs.getDouble("brightness_level"));
                measurement.setPressureLevel(rs.getDouble("pressure_level"));
                measurement.setHumidityLevel(rs.getDouble("humidity_level"));
                measurement.setAirIndex(rs.getDouble("air_index"));
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
            String query = "SELECT * FROM Außenklima.Measurement WHERE location_id >" + loc.getMeasurements().get(listSize -1).getId() ;
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Measurement measurement = new Measurement();
                measurement.setId(rs.getInt("id"));
                measurement.setTimestamp(rs.getString("date"));
                measurement.setTemperature(rs.getDouble("temperature"));
                measurement.setCo2Level(rs.getDouble("co2_level"));
                measurement.setPm1Level(rs.getDouble("fine_dust_level"));
                measurement.setBrightnessLevel(rs.getDouble("brightness_level"));
                measurement.setPressureLevel(rs.getDouble("pressure_level"));
                measurement.setHumidityLevel(rs.getDouble("humidity_level"));
                measurement.setAirIndex(rs.getDouble("air_index"));
                allMeasurements.add(measurement);
            }
            loc.setMeasurements(allMeasurements);
        }
        conn.close();
    }
}
