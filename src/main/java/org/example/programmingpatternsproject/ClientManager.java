package org.example.programmingpatternsproject;

import java.util.HashMap;

public class ClientManager {
    private static HashMap<String, Client> clients;

    private ClientManager() {

    }

    public static HashMap<String, Client> getClients() {
        if (clients == null) {
            clients = new HashMap<>();
        }
        return clients;
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
}
