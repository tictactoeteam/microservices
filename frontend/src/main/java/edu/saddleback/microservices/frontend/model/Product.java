package edu.saddleback.microservices.frontend.model;

/**
 * Represents a product, with name, image, and price.
 */
public class Product {

    private String productID;
    private String name;
    private String imagePath;
    private double price;

    /**
     * Constructor
     *
     * @param name
     * @param imagePath
     * @param price
     */
    public Product(String productID, String name, String imagePath, double price) {

        this.productID = productID;
        this.name = name;
        this.imagePath = imagePath;
        this.price = price;

    }

    /**
     * Copy Constructor
     *
     * @param tmpProduct
     */
    public Product(Product tmpProduct) {

        this(tmpProduct.productID, tmpProduct.name, tmpProduct.imagePath, tmpProduct.price);

    }

    /**
     * Default Constructor
     */
    public Product() {
        this("", "", "", 0.00);
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

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name;
    }

}
