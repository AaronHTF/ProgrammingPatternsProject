package org.example.programmingpatternsproject;

public class WrongPasswordException extends Exception {
    public WrongPasswordException() {
        super("The password you entered is incorrect. Please try again.");
    }
    public WrongPasswordException(String message) {
        super(message);
    }
}
