package edu.saddleback.microservices.frontend.view;

import edu.saddleback.microservices.frontend.controller.AppController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 * Controls the cart.fxml page, including showing your cart items with a checkout option.
 */
public class CartView {

    private AppController controller;

    @FXML
    private Label usernameLabel;
    @FXML
    private ListView cartList;

    public void initialize() {

        controller = App.getController();
        usernameLabel.setText(controller.getLoggedInUsername());
        cartList.getItems().clear();
        ObservableList<String> cartItems = FXCollections.observableArrayList();

        if (controller.getCart().getCartItems().size() > 0) {

            for (int i = 0; i < controller.getCart().getCartItems().size(); i++) {
                cartItems.add(controller.getCart().getCartItem(i).toString());
            }

        } else {
            cartItems.add("NO CART ITEMS YET GO BUY MORE ILLEGAL SHHHIIIIIIIIIIIT");
        }

        cartList.setItems(cartItems);

    }

    /**
     * Returns the user to the main product page.
     */
    public void onShopButtonClicked() {

        try {
            App.getCoordinator().showAppScene();
        } catch (Exception e) {
            System.err.println("oof");
        }

    }

}
