package edu.saddleback.microservices.frontend.controller;

import edu.saddleback.microservices.frontend.interfaces.BackendService;
import edu.saddleback.microservices.frontend.model.Cart;
import edu.saddleback.microservices.frontend.model.backendmodels.BackendServiceObject;

/**
 * Controller of the user's UI experience, holds the user's information and holds their cart, as well as holding the
 * https request methods.
 */
public class AppController {

    private String loggedInUsername;
    private String token;
    private Cart cart;
    private static BackendServiceObject backendServiceObject;
    private String selectedCoin;

    /**
     * Constructor
     */
    public AppController() {

        loggedInUsername = "";
        token = "";
        cart = new Cart();
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

    public String getSelectedCoin() {
        return selectedCoin;
    }

    //Setters
    public void setLoggedInUsername(String name) {
        loggedInUsername = name;
    }

    public void setToken(String str) {
        token = str;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setSelectedCoin(String coin) {
        this.selectedCoin = coin;
    }

    public void deleteCart() {
        cart = new Cart();
    }

}
