package org.example.programmingpatternsproject;

public class PasswordNotMatchingException extends RuntimeException {
    public PasswordNotMatchingException() {
        super("The passwords in the text boxes need don't match!");
    }
    public PasswordNotMatchingException(String message) {
        super(message);
    }
}
