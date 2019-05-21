package edu.saddleback.microservices.frontend.model;

/**
 * Represents a product, with name, image, and price.
 */
public class Product {

    private String productID;
    private String name;
    private String imagePath;
    private double price;
    private int quantity;

    /**
     * Constructor
     *
     * @param name
     * @param imagePath
     * @param price
     */
    public Product(String productID, String name, String imagePath, double price, int quantity) {

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
        this("", "", "", 0.00, -1);
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

    public int getQuantity() {
        return quantity;
    }

    //Setters
    public void setQuantity(int num) {
        quantity = num;
    }

    @Override
    public String toString() {
        return name;
    }

}
