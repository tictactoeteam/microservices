package edu.saddleback.microservices.frontend.controller.backendmodels;

public class CreateAccountObject {

    public String username;
    public String email;
    public String password;

    public CreateAccountObject(String u, String e, String p) {

        username = u;
        email = e;
        password = p;

    }

}