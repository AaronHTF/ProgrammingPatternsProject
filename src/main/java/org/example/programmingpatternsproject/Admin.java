package org.example.programmingpatternsproject;

public class Admin extends User {
    private static Admin admin;

    public Admin() {
        super("1000000", "admin");
    }

    public static Admin getAdmin() {
        if (admin == null) {
            admin = new Admin();
        }
        return admin;
    }
}
