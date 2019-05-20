package edu.saddleback.microservices.order.util;

public class CartObject {
    public String product;
    public int quantity;

    public CartObject(String product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

}
