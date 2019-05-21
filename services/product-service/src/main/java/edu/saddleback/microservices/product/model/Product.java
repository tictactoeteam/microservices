package edu.saddleback.microservices.product.model;

import java.math.BigDecimal;
import java.util.UUID;

import com.google.gson.JsonObject;

public class Product {
    UUID id;
    String name;
    BigDecimal price;
    int quantity;
    String imagepath;

    public Product(UUID id, String name, BigDecimal price, int quantity, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imagepath = image;
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
        return imagepath;
    }

    public void setProductImage(String image) {
        this.imagepath = image;
    }

    public JsonObject toJson() {
        JsonObject res = new JsonObject();
        res.addProperty("id", getProductId().toString());
        res.addProperty("name", getProductName());
        res.addProperty("price", getProductPrice().toString());
        res.addProperty("quantity", getProductQuantity());
        res.addProperty("image_path", getProductImage());
        return res;
    }
}
