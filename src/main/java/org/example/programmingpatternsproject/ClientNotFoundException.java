package org.example.programmingpatternsproject;

public class ClientNotFoundException extends Exception {
    public ClientNotFoundException() {
        super("The client with this ID cannot be found!");
    }
    public ClientNotFoundException(String message) {
        super(message);
    }
}
