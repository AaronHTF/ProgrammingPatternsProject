package org.example.programmingpatternsproject;

import java.util.ArrayList;
import java.util.List;

public class TicketManager {
    private static TicketManager ticketManager;
    private ArrayList<Ticket> tickets;

    private TicketManager() {
        tickets = new ArrayList<>();
        loadTicketsFromDatabase();
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

    public void deny(Ticket ticket) {
        for (int i = 0; i < tickets.size(); i++) {
            if (tickets.get(i).equals(ticket)) {
                tickets.get(i).setStatus("Denied");
            }
        }
    }

    public List<Ticket> filterByStatus(String status) {
        return tickets.stream().filter(ticket -> ticket.getStatus().equals(status)).toList();
    }

    public List<Ticket> filterByClassOfService(String classOfService) {
        return tickets.stream().filter(ticket -> ticket.getClassOfService().equals(classOfService)).toList();
    }

    public void sortByDate() {
        tickets.sort((t1, t2) -> t1.getDateInDateFormat().compareTo(t2.getDateInDateFormat()));
    }

    public void displayTickets() {
        for (Ticket ticket : tickets) {
            System.out.println(ticket);
        }
    }

    public void reloadTickets() {
        tickets = new ArrayList<>();
        loadTicketsFromDatabase();
    }
}
