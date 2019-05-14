package edu.saddleback.microservices.frontend.controller;

import edu.saddleback.microservices.frontend.model.Cart;

public class AppController {

    private String loggedInUsername;
    private Cart cart;
    private String token;


    public AppController() {

        loggedInUsername = "";
        cart = new Cart();
        token = "";

    }

    //Getters
    public String getLoggedInUsername() {
        return loggedInUsername;
    }

    public Cart getCart() {
        return cart;
    }

    public String getToken() {
        return token;
    }

    //Setters
    public void setLoggedInUsername(String name) {
        loggedInUsername = name;
    }

    public void setToken(String str) {
        token = str;
    }

}
