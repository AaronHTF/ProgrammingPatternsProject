package org.example.programmingpatternsproject;

import java.util.HashMap;

public class ClientManager {
    private static ClientManager clientManager;
    private HashMap<String, Client> clients;

    private ClientManager() {
        clients = new HashMap<>();
        loadClientsFromDatabase();
    }

    public static ClientManager getClients() {
        if (clientManager == null) {
            clientManager = new ClientManager();
        }
        return clientManager;
    }

    public void loadClientsFromDatabase() {
        Database db = Database.getInstance();
        HashMap<String, Client> clientsFromDatabase = new HashMap<>();
        clientsFromDatabase = db.selectClients();
        for (Client client : clientsFromDatabase.values()) {
            clients.put(client.getUserId(), client);
        }
    }

    public void addClient(Client client) {
        Database db = Database.getInstance();
        db.insertClient(client);
        clients.put(client.getUserId(), client);
    }

    public Client searchClientById(String userId) throws ClientNotFoundException {
        if (!clients.containsKey(userId)) {
            throw new ClientNotFoundException();
        }
        return clients.get(userId);
    }

    public String nextClientId() {
        int max = 0;
        for (String key : clients.keySet()) {
            int id = Integer.parseInt(key);
            if (id > max) {
                max = id;
            }
        }
        return "" + ++max;
    }

    public void displayClients() {
        for (int i = 1; i <= clients.size(); i++) {
            String id = "100000" + i;
            System.out.println(clients.get(id));
        }
    }
}
