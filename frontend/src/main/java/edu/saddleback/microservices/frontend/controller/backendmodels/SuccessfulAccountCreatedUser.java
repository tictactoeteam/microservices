package edu.saddleback.microservices.frontend.controller.backendmodels;

public class SuccessfulAccountCreatedUser {

    private String id;
    private String username;
    private String email;
    private long dateCreated;
    private long lastLoginDate;

    public SuccessfulAccountCreatedUser(String id, String username, String email, long createdAt, long lastLogin) {

        this.id = id;
        this.username = username;
        this.email = email;
        dateCreated = createdAt;
        lastLoginDate = lastLogin;

    }

    //Getters
    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public long getLastLoginDate() {
        return lastLoginDate;
    }

}
