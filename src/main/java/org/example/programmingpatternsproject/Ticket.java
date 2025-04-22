package org.example.programmingpatternsproject;

import javax.print.attribute.standard.JobOriginatingUserName;
import java.util.ArrayList;
import java.util.Random;

public class Ticket {
    private int ticketId;
    private String userId;
    private String flightId;
    private String date;
    private String classOfService;
    private String status;

    public Ticket(String userId, String flightId, String date, String classOfService) {
        Random random = new Random();
        ticketId = random.nextInt(100000, 1000000);
        this.userId = userId;
        this.flightId = flightId;
        this.date = date;
        this.classOfService = classOfService;
        status = "Sent";
    }

    public Ticket(int ticketId, String userId, String flightId, String date, String classOfService, String status) {
        this.ticketId = ticketId;
        this.userId = userId;
        this.flightId = flightId;
        this.date = date;
        this.classOfService = classOfService;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", userId='" + userId + '\'' +
                ", flightId='" + flightId + '\'' +
                ", date='" + date + '\'' +
                ", classOfService='" + classOfService + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (getClass() != object.getClass()) {
            return false;
        } else {
            Ticket ticket = (Ticket) object;
            return (userId.equals(ticket.getUserId()) &&
                    flightId.equals(ticket.getFlightId()) &&
                    date.equals(ticket.getDate()) &&
                    classOfService.equals(ticket.getClassOfService()) &&
                    status.equals(ticket.status));
        }
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
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
