package org.example.programmingpatternsproject;

import java.util.ArrayList;

public class Client extends User {
    private String clientName;

    public Client(String clientName, String password) {
        super(password);
        this.clientName = clientName;
    }

    public Client(String userId, String password, String clientName) {
        super(userId, password);
        this.clientName = clientName;
    }

    public ArrayList<Ticket> getTickets() {
        TicketManager ticketManager = TicketManager.getTickets();
        return ticketManager.searchTicketForClient(this);
    }

    public String toString() {
        return String.format("Client ID: %s, client name: %s, client password: %s\n", getUserId(), clientName, getPassword());
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
