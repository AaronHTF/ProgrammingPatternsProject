package org.example.programmingpatternsproject;

import java.util.ArrayList;

public class DbTests {
    public static void main(String[] args) {
        Database db = Database.getInstance();
        ClientManager clientManager = ClientManager.getClients();
        TicketManager ticketManager = TicketManager.getTickets();

        //fetch clients from database
        clientManager.loadClientsFromDatabase();
        ticketManager.loadTicketsFromDatabase();

        //create clients table
//        db.createNewTable("clients", "clientId", "TEXT");
//        db.addColumn("clients", "password", "TEXT");
//        db.addColumn("clients", "clientName", "TEXT");

        //create flights table
//        db.createNewTable("flights", "flightId", "TEXT");
//        db.addColumn("flights", "source", "TEXT");
//        db.addColumn("flights", "destination", "TEXT");

        //create tickets table
//        db.createNewTable("tickets", "ticketId", "INT");
//        db.addColumn("tickets", "clientId", "TEXT");
//        db.addColumn("tickets", "flightId", "TEXT");
//        db.addColumn("tickets", "date", "TEXT");
//        db.addColumn("tickets", "classOfService", "TEXT");
//        db.addColumn("tickets", "status", "TEXT");

        //add new client to the clients table
//        db.insertClient(new Client("Darrick Marquez", "password"));
//        db.insertClient(new Client("Maricela Castillo", "password"));

        //add new flights to the flights table
//        db.insertFlight(new Flight("439875", "Montreal", "New York"));
//        db.insertFlight(new Flight("928345", "New York", "Montreal"));

        //add new tickets to the tickets table
//        db.insertTicket(new Ticket("1000001", "439875", "08/25/2025", "Economy"));
//        db.insertTicket(new Ticket("1000002", "439875", "09/13/2025", "First Class"));

        //delete client
//        db.deleteClient("1000001");
//        db.deleteClient("1000002");

        //display clients with the client manager
        clientManager.displayClients();

        //display flights
        ArrayList<Flight> flights = db.selectFlights();
        for (Flight flight : flights) {
            System.out.println(flight);
        }

        //display tickets with ticket manager
        ticketManager.displayTickets();
    }
}
