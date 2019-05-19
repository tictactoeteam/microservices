package edu.saddleback.microservices.frontend.view;

import edu.saddleback.microservices.frontend.controller.AppController;

import java.text.DecimalFormat;

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
    private static DecimalFormat decimalFormat;

    @FXML
    private Label usernameLabel;
    @FXML
    private ListView cartList;
    @FXML
    private Label totalQuantityLabel;
    @FXML
    private Label totalCostLabel;
    @FXML
    private Label errorLabel;

    public void initialize() {

        controller = App.getController();
        decimalFormat = new DecimalFormat("#.##");
        usernameLabel.setText(controller.getLoggedInUsername());
        refreshPage();
        errorLabel.setVisible(false);

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

    /**
     * Removes one of a selected item.
     */
    public void onRemoveItemClicked() {

        if (cartList.getItems().size() > 1) {

            int selectedIndex = (int) cartList.getSelectionModel().getSelectedIndices().get(0);
            int selectedItemQuantity = controller.getCart().getCartItem(selectedIndex).getQuantity();

            if (selectedItemQuantity > 1) {

                controller.getCart().getCartItem(selectedIndex).setQuantity(selectedItemQuantity);
                //UPDATE SERVER CART
                errorLabel.setVisible(false);

            } else if (selectedItemQuantity == 0) {

                controller.getCart().remove(selectedIndex);
                //UPDATE SERVER CART
                errorLabel.setVisible(false);

            } else {

                errorLabel.setText("*error*");
                errorLabel.setVisible(true);

            }


        } else {

            errorLabel.setText("*error*");
            errorLabel.setVisible(true);

        }


    }

    private void refreshPage() {

        //Populates the table with cart items
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

        //Populates Checkout values
        totalQuantityLabel.setText(Integer.toString(controller.getCart().getTotalQuantity()));
        totalCostLabel.setText(decimalFormat.format(Double.toString(controller.getCart().getTotalCost())));

    }

}
