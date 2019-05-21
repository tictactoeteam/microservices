package edu.saddleback.microservices.frontend.model.backendmodels;

/**
 * Represents a login https send data object.
 */
public class LoginObj {

    public String username;
    public String password;

    /**
     * Constructor
     * @param u
     * @param p
     */
    public LoginObj(String u, String p) {

        username = u;
        password = p;

    }

}
