package org.example.programmingpatternsproject;

import java.util.ArrayList;

public class DbTests {
    public static void main(String[] args) {
        Database db = Database.getInstance();
        ClientManager clientManager = ClientManager.getClients();

        //fetch clients from database
        clientManager.loadClientsFromDatabase();

        //create clients table
//        db.createNewTable("clients", "clientId", "TEXT");
//        db.addColumn("clients", "password", "TEXT");
//        db.addColumn("clients", "clientName", "TEXT");

        //create flights table
//        db.createNewTable("flights", "flightId", "TEXT");
//        db.addColumn("flights", "source", "TEXT");
//        db.addColumn("flights", "destination", "TEXT");

        //create tickets table
        db.createNewTable("tickets", "ticketId", "INT");
        db.addColumn("tickets", "clientId", "TEXT");
        db.addColumn("tickets", "flightId", "TEXT");
        db.addColumn("tickets", "date", "TEXT");
        db.addColumn("tickets", "classOfService", "TEXT");
        db.addColumn("tickets", "status", "TEXT");

        //add new client to the clients table
//        db.insertClient(new Client("Darrick Marquez", "password"));
//        db.insertClient(new Client("Maricela Castillo", "password"));

        //add new flights to the flights table
//        db.insertFlights(new Flight("439875", "Montreal", "New York"));
//        db.insertFlights(new Flight("928345", "New York", "Montreal"));

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
    }
}
