package org.example.programmingpatternsproject;

public class Admin extends User {
    private String adminName;

    public Admin(String adminName, String password) {
        super(password);
        this.adminName = adminName;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }
}
