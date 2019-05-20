package edu.saddleback.microservices.frontend.model.backendmodels;

public class CreateAccountObj {

    public String username;
    public String email;
    public String password;

    public CreateAccountObj(String u, String e, String p) {

        username = u;
        email = e;
        password = p;

    }

}
