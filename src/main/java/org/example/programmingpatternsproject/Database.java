package org.example.programmingpatternsproject;

import com.google.gson.Gson;

import java.sql.*;
import java.util.ArrayList;

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

    public void insertFlight(Flight flight) {
        String sql = "INSERT INTO flights(flightId, source, destination) VALUES (?, ?, ?)";

        try {
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, flight.getFlightId());
            pstmt.setString(2, flight.getSource());
            pstmt.setString(3, flight.getDestination());
            pstmt.execute();

            pstmt.close();
            conn.close();
            System.out.println("Flight inserted successfully");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertTicket(Ticket ticket) {
        String sql = "INSERT INTO tickets(ticketId, clientId, flightId, date, classOfService, status) VALUES (?, ?, ?, ?, ?, ?)";
        TicketManager ticketManager = TicketManager.getTickets();

        try {
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, ticket.getTicketId());
            pstmt.setString(2, ticket.getUserId());
            pstmt.setString(3, ticket.getFlightId());
            pstmt.setString(4, ticket.getDate());
            pstmt.setString(5, ticket.getClassOfService());
            pstmt.setString(6, ticket.getStatus());
            pstmt.execute();
            ticketManager.addTicket(ticket);

            pstmt.close();
            conn.close();
            System.out.println("Ticket inserted successfully");
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

    public ArrayList<Flight> selectFlights() {
        String sql = "SELECT * FROM flights";
        ArrayList<Flight> flights = new ArrayList<>();

        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String flightId = rs.getString("flightId");
                String source = rs.getString("source");
                String destination = rs.getString("destination");
                flights.add(new Flight(flightId, source, destination));
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return flights;
    }

    public void selectTickets() {
        String sql = "SELECT * FROM tickets";

        TicketManager ticketManager = TicketManager.getTickets();

        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int ticketId = rs.getInt("ticketId");
                String clientId = rs.getString("clientId");
                String flightId = rs.getString("flightId");
                String date = rs.getString("date");
                String classOfService = rs.getString("classOfService");
                String status = rs.getString("status");
                ticketManager.addTicket(new Ticket(ticketId, clientId, flightId, date, classOfService, status));
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
