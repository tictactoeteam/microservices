package edu.saddleback.microservices.frontend.model;

import java.math.BigDecimal;

/**
 * Represents a cart item, which is a product and its quantity chosen to be bought.
 */
public class CartItem {

    private Product product;
    private int quantity;

    /**
     * Default Constructor
     */
    public CartItem() {
        this(null, 0);
    }

    /**
     * Constructor
     *
     * @param product
     * @param quantity
     */
    public CartItem(Product product, int quantity) {

        this.product = product;
        this.quantity = quantity;

    }

    //Getters
    public int getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }

    //Setters
    public void setProduct(Product p) {
        product = p;
    }

    public void setQuantity(int q) {
        quantity = q;
    }

    /**
     * Returns the price of the cartItem, which includes the product and the amount to be purchased.
     *
     * @return
     */
    public BigDecimal getPrice() {
        return product.getPrice().multiply(new BigDecimal(quantity));
    }

    /**
     * Overrides the toString property.
     * @return
     */
    @Override
    public String toString() {
        return product + " Quantity: " + quantity;
    }

}
