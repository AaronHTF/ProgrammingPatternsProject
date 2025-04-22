package org.example.programmingpatternsproject;

import java.util.ArrayList;

public class TicketInformation {
    private int ticketId;
    private String source;
    private String destination;
    private String date;
    private String classOfService;
    private String status;

    public TicketInformation(Ticket ticket, Client client) {
        Database db = Database.getInstance();
        ArrayList<Flight> flights = db.selectFlights();
        for (Flight flight : flights) {
            if (flight.getFlightId().equals(ticket.getFlightId()) && client.getUserId().equals(ticket.getUserId())) {
                source = flight.getSource();
                destination = flight.getDestination();
                break;
            }
        }
        ticketId = ticket.getTicketId();
        date = ticket.getDate();
        classOfService = ticket.getClassOfService();
        status = ticket.getStatus();
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClassOfService() {
        return classOfService;
    }

    public void setClassOfService(String classOfService) {
        this.classOfService = classOfService;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
