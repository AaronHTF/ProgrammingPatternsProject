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
