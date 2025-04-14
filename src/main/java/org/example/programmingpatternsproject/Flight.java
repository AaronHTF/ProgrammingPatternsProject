package org.example.programmingpatternsproject;

public class Flight {
    private String source;
    private String destination;

    public Flight(String source, String destination) {
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
            return (source.equals(flight.getSource()) &&
                    destination.equals(flight.getDestination()));
        }
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
