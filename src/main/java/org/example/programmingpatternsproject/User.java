package org.example.programmingpatternsproject;

public class User {
    private String userId;
    private String password;
    private static int nextId = 1000001;

    public User(String password) {
        this.password = password;
        userId = String.format("%06d", nextId++);
    }

    public boolean verifyLogin(String inputPassword) {
        return password.equals(inputPassword);
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
