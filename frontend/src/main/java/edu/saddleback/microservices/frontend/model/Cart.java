package edu.saddleback.microservices.frontend.model;

import java.util.ArrayList;

/**
 * Represents the user's cart of desired items.
 */
public class Cart{

    private ArrayList<CartItem> cartItems;

    /**
     * Constructor
     * - Initializes the cartItems arraylist.
     */
    public Cart(){
        cartItems = new ArrayList<>();}

    //Getters
    public ArrayList<CartItem> getCartItems(){return cartItems;}
    public int getSize(){return cartItems.size();}

    //Setters
    public void add(CartItem p){
        cartItems.add(p);}
    public void remove(CartItem p){
        cartItems.remove(p);}

    /**
     *
     * @return the sum of the price of all items in the cart.
     */
    public double getTotalCost(){

        double sum = 0;

        for (int i = 0; i < cartItems.size(); i++)
            sum += cartItems.get(i).getPrice();

        return sum;

    }

}
