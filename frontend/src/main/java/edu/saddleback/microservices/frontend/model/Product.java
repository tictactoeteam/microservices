package edu.saddleback.microservices.frontend.model;

import java.math.BigDecimal;

/**
 * Represents a product, with ID, name, image, price, and total quantity.
 */
public class Product {

    private String productID;
    private String name;
    private String imagePath;
    private BigDecimal price;
    private int quantity;

    /**
     * Constructor
     *
     * @param name
     * @param imagePath
     * @param price
     */
    public Product(String productID, String name, String imagePath, BigDecimal price, int quantity) {

        this.productID = productID;
        this.name = name;
        this.imagePath = imagePath;
        this.price = price;
        this.quantity = quantity;

    }

    /**
     * Copy Constructor
     *
     * @param tmpProduct
     */
    public Product(Product tmpProduct) {

        this(tmpProduct.productID, tmpProduct.name, tmpProduct.imagePath, tmpProduct.price, tmpProduct.quantity);

    }

    /**
     * Default Constructor
     */
    public Product() {
        this("", "", "", BigDecimal.valueOf(0.00), -1);
    }

    //Getters
    public String getProductID() {
        return productID;
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    //Setters
    public void setQuantity(int num) {
        quantity = num;
    }

    /**
     * Overrides the toString property.
     * @return
     */
    @Override
    public String toString() {
        return name;
    }

}
