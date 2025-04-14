package org.example.programmingpatternsproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Database dbObject;

    private Database() {

    }

    public static Database getInstance() {
        if (dbObject == null) {
            dbObject = new Database();
        }
        return dbObject;
    }

    public Connection getConnection() {
        String DB_Path = "jdbc:sqlite:src/main/resources/database/data.db";
        Connection connection;
        try {
            connection = DriverManager.getConnection(DB_Path);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
