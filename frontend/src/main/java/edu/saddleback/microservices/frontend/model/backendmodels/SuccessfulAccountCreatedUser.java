package edu.saddleback.microservices.frontend.model.backendmodels;

/**
 * Represents a create account https return data object.
 */
public class SuccessfulAccountCreatedUser {

    private String id;
    private String username;
    private String email;
    private long dateCreated;
    private long lastLoginDate;

    /**
     * Constructor
     *
     * @param id
     * @param username
     * @param email
     * @param createdAt
     * @param lastLogin
     */
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
