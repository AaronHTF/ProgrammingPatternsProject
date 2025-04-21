package org.example.programmingpatternsproject;

import java.util.HashMap;

public class ClientManager {
    private static ClientManager clientManager;
    private HashMap<String, Client> clients;

    private ClientManager() {
        clients = new HashMap<>();
    }

    public static ClientManager getClients() {
        if (clientManager == null) {
            clientManager = new ClientManager();
        }
        return clientManager;
    }

    public void loadClientsFromDatabase() {
        Database db = Database.getInstance();
        db.selectClients();
    }

    public void addClient(Client client) {
        clients.put(client.getUserId(), client);
    }

    public Client searchClientById(String userId) throws ClientNotFoundException {
        if (!clients.containsKey(userId)) {
            throw new ClientNotFoundException();
        }
        return clients.get(userId);
    }

    public void displayClients() {
        for (int i = 1; i <= clients.size(); i++) {
            String id = "100000" + i;
            System.out.println(clients.get(id));
        }
    }
}
