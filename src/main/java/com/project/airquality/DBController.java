package com.project.airquality;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
public class                                                                                                                                                                                                                            DBController {
    public static Connection getConnection() {
        DBConfigManager dbConfigManager = new DBConfigManager();
        //  Database credentials
        String url = "jdbc:mysql://"+ dbConfigManager.getHost() + ":3306/" + dbConfigManager.getDatabase();
        String user = dbConfigManager.getUsername();
        String pass = dbConfigManager.getPassword();

        // JDBC driver name and database URL
        Connection con = null;
        try {
            con = DriverManager.getConnection(url, user, pass);


        } catch (SQLException e) {
            System.out.println("KEINE Verbindung: ");
            e.printStackTrace();
        }
        return con;
    }

    public static void main(String[] args) throws SQLException {
        Connection con = DBController.getConnection();

        String sql = "INSERT INTO Außenklima.Test (Name) VALUES ('Fatih')";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.execute();

        con.close();
    }
}
