package org.example.programmingpatternsproject;

import javax.print.attribute.standard.JobOriginatingUserName;

public class Ticket {
    private String userId;
    private Flight flight;
    private String date;
    private String classOfService;
    private String status;

    public Ticket(String userId, Flight flight, String date, String classOfService) {
        this.userId = userId;
        this.flight = flight;
        this.date = date;
        this.classOfService = classOfService;
        status = "Sent";
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
                    flight.equals(ticket.getFlight()) &&
                    date.equals(ticket.getDate()) &&
                    classOfService.equals(ticket.getClassOfService()) &&
                    status.equals(ticket.status));
        }
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
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
