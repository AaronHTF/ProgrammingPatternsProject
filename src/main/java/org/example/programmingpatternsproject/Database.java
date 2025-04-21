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
                CREATE TABLE IF NOT EXISTS %s(
                %s %s PRIMARY KEY
                )
                """.formatted(tableName, primaryKeyColumnName, primaryKeyColumnType);

        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            stmt.execute(sql);

            stmt.close();
            conn.close();
            System.out.println("Table created successfully!");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addColumn(String tableName, String columnName, String columnType) {
        String sql = "AlTER TABLE %s ADD COLUMN %s %s".formatted(tableName, columnName, columnType);

        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

            stmt.close();
            conn.close();
            System.out.println("Column inserted successfully");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertClient(Client client) {
        String sql = "INSERT INTO clients(clientId, password, clientName) VALUES (?, ?, ?)";
        ClientManager clientManager = ClientManager.getClients();

        try {
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, client.getUserId());
            pstmt.setString(2, client.getPassword());
            pstmt.setString(3, client.getClientName());
            pstmt.execute();
            clientManager.addClient(client);

            pstmt.close();
            conn.close();
            System.out.println("Client inserted successfully");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteClient(String id) {
        String sql = "DELETE FROM clients WHERE clientId = ?";

        try {
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            int rowsDeleted = pstmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Client with ID " + id + " was deleted successfully.");
            } else {
                System.out.println("No client with the provided ID exists");
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void selectClients() {
        String sql = "SELECT * FROM clients";

        ClientManager clientManager = ClientManager.getClients();

        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String clientId = rs.getString("clientId");
                String password = rs.getString("password");
                String clientName = rs.getString("clientName");
                clientManager.addClient(new Client(clientId, password, clientName));
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
