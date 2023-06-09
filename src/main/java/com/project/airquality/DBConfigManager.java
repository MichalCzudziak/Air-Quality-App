package com.project.airquality;

import java.io.*;
import java.util.Properties;

public class DBConfigManager {
    private String host;
    private String username;
    private String password;
    private String database;
    private Properties properties = new Properties();

    DBConfigManager(){
        try(InputStream inputStream = new FileInputStream("config.properties")){
            properties.load(inputStream);

            this.host = properties.getProperty("host");
            this.username = properties.getProperty("username");
            this.password = properties.getProperty("password");
            this.database = properties.getProperty("database");

        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public DBConfigManager(String host, String username, String password, String database) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.database = database;
    }

    public String getHost() {
        return host;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDatabase() {
        return database;
    }

    public boolean saveConfig(){
        properties.setProperty("host", host);
        properties.setProperty("username", username);
        properties.setProperty("password", password);
        properties.setProperty("database", database);

        try (OutputStream outputStream = new FileOutputStream("config.properties")){
            properties.store(outputStream, null);
            return true;
        } catch (IOException e){
            System.out.println("Fehler");
            return false;
        }
    }
}
