package edu.saddleback.microservices.frontend.controller;

import edu.saddleback.microservices.frontend.model.Cart;

public class AppController {

    private String loggedInUsername;
    private Cart cart;


    public AppController(){

        loggedInUsername = "";
        cart = new Cart();

    }

    //Getters
    public String getLoggedInUsername(){return loggedInUsername;}
    public Cart getCart(){return cart;}

    //Setters
    public void setLoggedInUsername(String name){loggedInUsername = name;}


}
