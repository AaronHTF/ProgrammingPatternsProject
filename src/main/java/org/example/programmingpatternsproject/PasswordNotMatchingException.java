package org.example.programmingpatternsproject;

public class PasswordNotMatchingException extends Exception {
    public PasswordNotMatchingException() {
        super("The passwords in the text boxes don't match!");
    }
    public PasswordNotMatchingException(String message) {
        super(message);
    }
}
