package edu.saddleback.microservices.frontend.controller;

import edu.saddleback.microservices.frontend.controller.backendmodels.BackendServiceObject;
import edu.saddleback.microservices.frontend.interfaces.BackendService;
import edu.saddleback.microservices.frontend.model.Cart;

public class AppController {

    private String loggedInUsername;
    private Cart cart;
    private String token;
    private static BackendServiceObject backendServiceObject;

    public AppController() {

        loggedInUsername = "";
        cart = new Cart();
        token = "";
        backendServiceObject = new BackendServiceObject();

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

    public static BackendService getBackendService() {
        return backendServiceObject.getBackendService();
    }

    //Setters
    public void setLoggedInUsername(String name) {
        loggedInUsername = name;
    }

    public void setToken(String str) {
        token = str;
    }

}
