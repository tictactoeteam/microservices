package edu.saddleback.microservices.frontend.model;

/**
 * Represents a cart item, which is a product and its quantity.
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
    public double getPrice() {
        return product.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return product + " Quantity: " + quantity;
    }

}
