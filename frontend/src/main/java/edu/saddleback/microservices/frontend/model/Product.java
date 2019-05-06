package edu.saddleback.microservices.frontend.model;

/**
 * Represents a product, with name, image, and price.
 */
public class Product {

    private String name;
    private String imagePath;
    private double price;

    /**
     * Constructor
     * @param name
     * @param imagePath
     * @param price
     */
    public Product(String name, String imagePath, double price){

        this.name = name;
        this.imagePath = imagePath;
        this.price = price;

    }

    /**
     * Copy Constructor
     * @param tmpProduct
     */
    public Product(Product tmpProduct){

        this(tmpProduct.getName(), tmpProduct.getImagePath(), tmpProduct.getPrice());

    }

    /**
     * Default Constructor
     */
    public Product(){
        this("", "", 0.00);
    }

    //Getters
    public String getName(){
        return name;
    }
    public String getImagePath(){
        return imagePath;
    }
    public double getPrice(){
        return price;
    }

    //Setters
    public void setName(String str){
        name = str;
    }
    public void setImagePath(String str){
        imagePath = str;
    }
    public void setPrice(double d){
        price = d;
    }

}
