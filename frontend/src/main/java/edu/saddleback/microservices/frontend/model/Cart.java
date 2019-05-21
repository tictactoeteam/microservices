package edu.saddleback.microservices.frontend.model;

import java.math.BigDecimal;
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

        BigDecimal sum = new BigDecimal(0);
        for (int i = 0; i < cartItems.size(); i++) {

            sum.add(cartItems.get(i).getPrice());

        }

        return sum.doubleValue();

    }

    /**
     * Returns the total quantity of cart items.
     *
     * @return
     */
    public int getTotalQuantity() {

        int sum = 0;
        for (int i = 0; i < cartItems.size(); i++) {
            sum += cartItems.get(i).getQuantity();
        }

        return sum;

    }

}
