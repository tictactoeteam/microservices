package edu.saddleback.microservices.auth.models;

import java.util.Date;
import java.util.UUID;

import org.mindrot.jbcrypt.BCrypt;

public class User {
    private UUID id;
    private String username;
    private String email;
    private String hashedPassword;
    private boolean disabled;
    private Date createdDate;
    private Date lastLogin;

    public User() {
        this(UUID.randomUUID(), "", "", "", false, null, null);
    }

    public User(UUID id, String username, String email, String password,
                boolean disabled, Date createdDate, Date lastLogin) {
        this.id = id;
        this.username = username;
        this.email = email;
        setPassword(password);
        this.disabled = disabled;
        this.createdDate = createdDate;
        this.lastLogin = lastLogin;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean checkPassword(String password) {
        return BCrypt.checkpw(password, this.hashedPassword);
    }

    public void setPassword(String password) {
        this.hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }
}
