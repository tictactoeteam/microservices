package edu.saddleback.microservices.frontend.model;

import java.util.ArrayList;

/**
 * Represents the user's cart of desired items.
 */
public class Cart {

    private ArrayList<CartItem> cartItems;

    /**
     * Constructor
     * - Initializes the cartItems array list.
     */
    public Cart() {
        cartItems = new ArrayList<>();
    }

    //Getters
    public ArrayList<CartItem> getCartItems() {
        return cartItems;
    }

    public CartItem getCartItem(int index) {
        return cartItems.get(index);
    }

    public int getSize() {
        return cartItems.size();
    }

    //Setters
    public void add(CartItem p) {
        cartItems.add(p);
    }

    public void remove(int index) {
        cartItems.remove(index);
    }

    /**
     * @return the sum of the price of all items in the cart.
     */
    public double getTotalCost() {

        double sum = 0;
        for (int i = 0; i < cartItems.size(); i++) {

            sum += cartItems.get(i).getPrice();

        }

        return sum;

    }

    public int getTotalQuantity() {

        int sum = 0;
        for( int i = 0; i < cartItems.size(); i++)
            sum += cartItems.get(i).getQuantity();

        return sum;

    }

}
