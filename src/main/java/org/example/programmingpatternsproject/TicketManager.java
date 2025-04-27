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

    public void deleteTicketById(int id) {
        Ticket ticket = searchTicketById(id);
        Database db = Database.getInstance();
        db.deleteTicket(ticket.getTicketId());
        tickets.remove(ticket);
    }

    public void updateTicket(int ticketId, String clientId, String flightId, String date, String classOfService, String status) {
        Database db = Database.getInstance();
        Ticket ticket = searchTicketById(ticketId);
        ticket.setTicketId(ticketId);
        ticket.setUserId(clientId);
        ticket.setFlightId(flightId);
        ticket.setDate(date);
        ticket.setClassOfService(classOfService);
        ticket.setStatus(status);
        db.updateTicket(ticketId, clientId, flightId, date, classOfService, status);
    }

    public Ticket searchTicketById(int id) {
        return tickets.stream().filter(ticket -> ticket.getTicketId() == id).findAny().orElse(null);
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

    public List<Ticket> filterByFlight(Flight flight) {
        return tickets.stream().filter(ticket -> ticket.getSource().equals(flight.getSource()) && ticket.getDestination().equals(flight.getDestination())).toList();
    }

    public List<Ticket> filterBySource(Flight flight) {
        return tickets.stream().filter(ticket -> ticket.getSource().equals(flight.getSource())).toList();
    }

    public List<Ticket> filterByDestination(Flight flight) {
        return tickets.stream().filter(ticket -> ticket.getDestination().equals(flight.getDestination())).toList();
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
