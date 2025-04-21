package org.example.programmingpatternsproject;

public class Flight {
    private String flightId;
    private String source;
    private String destination;

    public Flight(String flightId, String source, String destination) {
        this.flightId = flightId;
        this.source = source;
        this.destination = destination;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (getClass() != object.getClass()) {
            return false;
        } else {
            Flight flight = (Flight) object;
            return (flightId.equals(flight.getFlightId()) &&
                    source.equals(flight.getSource()) &&
                    destination.equals(flight.getDestination()));
        }
    }

    @Override
    public String toString() {
        return "Flight{" +
                "flightId='" + flightId + '\'' +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                '}';
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
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
}
