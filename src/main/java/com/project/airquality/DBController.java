package com.project.airquality;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
public class                                                                                                                                                                                                                            DBController {
    public static Connection getConnection() throws SQLException {

        //  Database credentials
        String url = "jdbc:mysql://172.104.152.221:3306/Außenklima";
        String user = "admin";
        String pass = "FraUAS2023.";

        // JDBC driver name and database URL
        Connection con = null;
        try {
            con = (Connection) DriverManager.getConnection(url, user, pass);


        } catch (SQLException e) {
            System.out.println("KEINE Verbindung");
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
