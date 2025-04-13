package org.example.programmingpatternsproject;

public class Client extends User {
    private String clientName;

    public Client(String clientName, String password) {
        super(password);
        this.clientName = clientName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
