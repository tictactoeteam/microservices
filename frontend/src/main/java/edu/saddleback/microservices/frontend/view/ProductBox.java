package edu.saddleback.microservices.frontend.view;

import edu.saddleback.microservices.frontend.controller.AppController;
import edu.saddleback.microservices.frontend.controller.UpdateCartController;
import edu.saddleback.microservices.frontend.model.CartItem;
import edu.saddleback.microservices.frontend.model.Product;
import javafx.application.Platform;
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

    private Product thisProduct;
    private AppController controller;
    private TextField quantityTextField;

    /**
     * Constructor
     *
     * @param product
     */
    public ProductBox(Product product) {

        thisProduct = new Product(product);
        controller = App.getController();

        HBox productBoxHBox = new HBox();
        productBoxHBox.setAlignment(Pos.CENTER);
        //Left side column
        final ImageView image = new ImageView(new Image(thisProduct.getImagePath()));

        //Right side column
        VBox productBoxInfoVBox = new VBox();
        productBoxInfoVBox.setAlignment(Pos.CENTER_LEFT);
        final Label nameLabel = new Label(thisProduct.getName());
        HBox priceHBox = new HBox();
        Label priceLabel = new Label("Price: $");
        Label priceLabelValue = new Label(" " + thisProduct.getPrice());
        priceHBox.getChildren().addAll(priceLabel, priceLabelValue);
        HBox quantityHBox = new HBox();
        quantityHBox.setAlignment(Pos.CENTER);
        final Label quantityLabel = new Label("Quantity: ");
        quantityTextField = new TextField();
        quantityTextField.setText("0");
        quantityTextField.setPrefWidth(30);//*********************************************************
        quantityHBox.getChildren().addAll(quantityLabel, quantityTextField);
        Button addToCartButton = new Button("Add to Cart");
        addToCartButton.setAlignment(Pos.CENTER);

        //Fills ProductBox with objects
        productBoxInfoVBox.getChildren().addAll(nameLabel, priceHBox, quantityHBox, addToCartButton);
        productBoxHBox.getChildren().addAll(image, productBoxInfoVBox);
        getChildren().add(productBoxHBox);

        //Handles clicking the "Add to Cart" button
        addToCartButton.setOnMouseClicked(e ->
                addToCartClicked(thisProduct, Integer.valueOf(quantityTextField.getText())));

    }

    /**
     * Handles adding a item and its chosen quantity to the user's cart.
     *
     * @param product
     * @param quantity
     */
    private void addToCartClicked(Product product, int quantity) {

        if (quantity > 0) {

            controller.getCart().add(new CartItem(product, quantity));

            UpdateCartController updateCon = new UpdateCartController(controller.getToken(), controller.getCart());
            updateCon.getCartReceivedBoolean().subscribe((onCartUpdated) -> {

                controller.setCart(updateCon.getCart());
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        quantityTextField.setText("0");
                    }
                });

            });

        }

    }

}
