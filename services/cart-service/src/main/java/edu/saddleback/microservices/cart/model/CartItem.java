package edu.saddleback.microservices.cart.model;

import java.util.Objects;
import java.util.UUID;

public class CartItem {
    private String product;
    private int quantity;

    public CartItem() {
        this("", 0);
    }

    public CartItem(String product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public CartItem(String fromRedis) {
        this.product = fromRedis.substring(0, fromRedis.indexOf(':'));
        this.quantity = Integer.parseInt(fromRedis.substring(fromRedis.indexOf(':') + 1));
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isValid() {
        try {
            UUID.fromString(this.product);
        } catch (IllegalArgumentException e) {
            return false;
        }

        return quantity > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return quantity == cartItem.quantity &&
                Objects.equals(product, cartItem.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, quantity);
    }

    @Override
    public String toString() {
        return this.product + ':' + this.quantity;
    }
}
