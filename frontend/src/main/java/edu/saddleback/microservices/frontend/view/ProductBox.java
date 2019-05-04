package edu.saddleback.microservices.frontend.view;

import edu.saddleback.microservices.frontend.model.Product;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Represents a FX box that holds the data about a product, including its name, price, and and image of it.
 */
public class ProductBox extends Pane {

    private TextField quantityTextField;

    /**
     * Constructor
     * @param product
     */
    public ProductBox(Product product){

        HBox productBoxHBox = new HBox();
        productBoxHBox.setAlignment(Pos.CENTER);
        //Left side column
        ImageView image = new ImageView(new Image(product.getImagePath()));

        //Right side column
        VBox productBoxInfoVBox = new VBox();
        productBoxInfoVBox.setAlignment(Pos.CENTER_LEFT);
        Label nameLabel = new Label(product.getName());
        HBox priceHBox = new HBox();
        Label priceLabel = new Label("Price: $");
        Label priceLabelValue = new Label(" " + product.getPrice());
        priceHBox.getChildren().addAll(priceLabel, priceLabelValue);
        HBox quantityHBox = new HBox();
        quantityHBox.setAlignment(Pos.CENTER);
        Label quantityLabel = new Label("Quantity: ");
        quantityTextField = new TextField();
        quantityTextField.setPrefWidth(30);//*********************************************************
        quantityHBox.getChildren().addAll(quantityLabel, quantityTextField);
        Button addToCartButton = new Button("Add to Cart");
        addToCartButton.setAlignment(Pos.CENTER);

        //Fills ProductBox with objects
        productBoxInfoVBox.getChildren().addAll(nameLabel, priceHBox, quantityHBox, addToCartButton);
        productBoxHBox.getChildren().addAll(image, productBoxInfoVBox);
        getChildren().add(productBoxHBox);

    }

    //Getter
    public int getQuantityTextFieldQuantity(){return Integer.valueOf(quantityTextField.getText());}

}
