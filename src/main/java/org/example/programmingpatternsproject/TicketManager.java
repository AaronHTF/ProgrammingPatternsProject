package org.example.programmingpatternsproject;

import java.util.ArrayList;

public class TicketManager {
    private static TicketManager ticketManager;
    private ArrayList<Ticket> tickets;

    private TicketManager() {
        tickets = new ArrayList<>();
    }

    public static TicketManager getTickets() {
        if (ticketManager == null) {
            ticketManager = new TicketManager();
        }
        return ticketManager;
    }

    public void loadTicketsFromDatabase() {
        Database db = Database.getInstance();
        ArrayList<Ticket> ticketFromDatabase;
        ticketFromDatabase = db.selectTickets();
        tickets.addAll(ticketFromDatabase);
    }

    public ArrayList<Ticket> searchTicketForClient(Client client) {
        ArrayList<Ticket> clientTicket = new ArrayList<>();
        for (Ticket ticket : tickets) {
            if (ticket.getUserId().equals(client.getUserId())) {
                clientTicket.add(ticket);
            }
        }
        return clientTicket;
    }

    public void addTicket(Ticket ticket) {
        Database db = Database.getInstance();
        db.insertTicket(ticket);
        tickets.add(ticket);
    }

    public void confirmTicket(Ticket ticket) {
        for (int i = 0; i < tickets.size(); i++) {
            if (tickets.get(i).equals(ticket)) {
                tickets.get(i).setStatus("Confirmed");
            }
        }
    }

    public void cancel(Ticket ticket) {
        for (int i = 0; i < tickets.size(); i++) {
            if (tickets.get(i).equals(ticket)) {
                tickets.get(i).setStatus("Cancelled");
            }
        }
    }

    public void displayTickets() {
        for (Ticket ticket : tickets) {
            System.out.println(ticket);
        }
    }
}
