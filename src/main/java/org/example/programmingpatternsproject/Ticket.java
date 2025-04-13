package org.example.programmingpatternsproject;

public class Ticket {
    private String userId;
    private Flight flight;
    private String date;
    private String classOfService;

    public Ticket(String userId, Flight flight, String date, String classOfService) {
        this.userId = userId;
        this.flight = flight;
        this.date = date;
        this.classOfService = classOfService;
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
}
