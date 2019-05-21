package edu.saddleback.microservices.product.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Product {
    UUID id;
    String name;
    BigDecimal price;
    int quantity;
    String imagePath;

    public Product(UUID id, String name, BigDecimal price, int quantity, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imagePath = image;
    }

    public Product() {
        this(UUID.randomUUID(), "", BigDecimal.ZERO, 0, "");
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

    public void setProductQuantity(int productQuantity) {
        this.quantity = productQuantity;
    }

    public int getProductQuantity() {
        return quantity;
    }

    public void setProductPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getProductPrice() {
        return price;
    }

    public String getProductImage() {
        return imagePath;
    }

    public void setProductImage(String image) {
        this.imagePath = image;
    }
}
