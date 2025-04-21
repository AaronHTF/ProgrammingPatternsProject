package org.example.programmingpatternsproject;

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

        //add new client to the clients table
//        clientManager.addClient(new Client("Darrick Marquez", "password"));
//        clientManager.addClient(new Client("Maricela Castillo", "password"));
//        db.insertClient(new Client("Darrick Marquez", "password"));
//        db.insertClient(new Client("Maricela Castillo", "password"));

        //delete client
//        db.deleteClient("1000001");
//        db.deleteClient("1000002");

        //display clients with the client manager
        clientManager.displayClients();
    }
}
