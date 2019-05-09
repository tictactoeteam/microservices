package edu.saddleback.microservices.product.model;

import java.util.UUID;

public class Product {
    UUID id;
    String name;
    String price;
    String quantity;

    public Product(UUID id, String name, String price, String quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Product() {
        this(UUID.randomUUID(), "", "", "");
    }


    public void setProductName(String product) {
        this.name = product;
    }

    public String getProductName() {
        return name;
    }

    public void setProductID(UUID productID) {
        this.id = productID;
    }

    public UUID getProductId() {
        return id;
    }

    public void setProductQuantity(String productQuantity) {
        this.quantity = productQuantity;
    }

    public String getProductQuantity() {
        return quantity;
    }

    public void setProductPrice(String price) {
        this.price = price;
    }

    public String getProductPrice() {
        return price;
    }
}
