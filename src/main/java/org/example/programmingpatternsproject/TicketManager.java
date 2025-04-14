package org.example.programmingpatternsproject;

import java.util.ArrayList;

public class TicketManager {
    private static ArrayList<Ticket> tickets;

    private TicketManager() {

    }

    public static ArrayList<Ticket> getTickets() {
        if (tickets == null) {
            tickets = new ArrayList<>();
        }
        return tickets;
    }

    public void addTicket(Ticket ticket) {
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
}
