package edu.saddleback.microservices.frontend.model.backendmodels;

/**
 * Represents a create account https send data object.
 */
public class CreateAccountObj {

    public String username;
    public String email;
    public String password;

    /**
     * Constructor
     *
     * @param u
     * @param e
     * @param p
     */
    public CreateAccountObj(String u, String e, String p) {

        username = u;
        email = e;
        password = p;

    }

}
