package edu.saddleback.microservices.frontend.view;

import edu.saddleback.microservices.frontend.controller.AppController;
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
