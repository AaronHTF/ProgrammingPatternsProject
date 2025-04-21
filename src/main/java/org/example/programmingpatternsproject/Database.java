package org.example.programmingpatternsproject;

import com.google.gson.Gson;

import java.sql.*;

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

    public void createNewTable(String tableName, String primaryKeyColumnName, String primaryKeyColumnType) {
        String sql = """
                CREATE TABLE IF NOT EXISTS ?(
                ? ? PRIMARY KEY
                )
                """;

        try {
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, tableName);
            pstmt.setString(2, primaryKeyColumnName);
            pstmt.setString(3, primaryKeyColumnType);
            pstmt.execute();

            pstmt.close();
            conn.close();
            System.out.println("Table created successfully!");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addColumn(String tableName, String columnName, String columnType) {
        String sql = "AlTER TABLE ? ADD COLUMN ? ?";

        try {
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, tableName);
            pstmt.setString(2, columnName);
            pstmt.setString(3, columnType);
            pstmt.execute();

            pstmt.close();
            conn.close();
            System.out.println("Column inserted successfully");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertClient(Client client) {
        String sql = "INSERT INTO clients(userId, password, clientName) VALUES (?, ?, ?)";

        try {
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, client.getUserId());
            pstmt.setString(2, client.getPassword());
            pstmt.setString(3, client.getClientName());
            pstmt.execute();

            pstmt.close();
            conn.close();
            System.out.println("Client inserted successfully");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void selectJson() {
        String sql = """
                SELECT json_object(
                'userId', userId,
                'password', password,
                'clientName', clientName
                ) AS json_result
                FROM clients;
                """;

        ClientManager clientManager = ClientManager.getClients();
        Gson gson = new Gson();

        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String jsonResults = rs.getString("json_result");
                Client client = gson.fromJson(jsonResults, Client.class);
                clientManager.addClient(client);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
