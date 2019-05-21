package edu.saddleback.microservices.frontend.view;

import java.text.DecimalFormat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import edu.saddleback.microservices.frontend.controller.AppController;
import edu.saddleback.microservices.frontend.controller.backendcontrollers.DeleteCartController;

/**
 * Controls the cart.fxml page, including showing your cart items with a checkout option.
 */
public class CartView {

    private AppController controller;

    @FXML
    private Label usernameLabel;
    @FXML
    private ListView cartList;
    @FXML
    private Label totalQuantityLabel;
    @FXML
    private Label totalCostLabel;
    @FXML
    private Label checkoutErrorText;
    @FXML
    private ChoiceBox cryptoChoiceBox;

    /**
     * Constructor
     * Initializes the cart UI with all cart values.
     */
    public void initialize() {

        controller = App.getController();
        usernameLabel.setText(controller.getLoggedInUsername());
        refreshPage();
        cryptoChoiceBox.getItems().addAll("Bitcoin (BTC)", "Litecoin (LTC)", "Zcash (ZEC)", "Lumens (XLM)");

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
     * Deletes the user's cart, emptying it.
     */
    public void onDeleteCartClicked() {

        if (controller.getCart().getSize() > 0) {

            DeleteCartController deleteCon = new DeleteCartController(controller.getToken());
            deleteCon.getCartDeletedObservableBoolean().subscribe((onCartDeleted) -> {
                controller.deleteCart();
                refreshPage();
            });

            deleteCon.start();

        }

    }

    /**
     * If checkout conditions are met, checkout scene is called and a transaction is sent to the server, generating a
     * crypto QR code.
     */
    public void onCheckoutClicked() {

        if (controller.getCart().getTotalCost() > 0 && cryptoChoiceBox.getSelectionModel().getSelectedIndex() >= 0) {
            controller.setSelectedCoin(returnCryptoAbbreviation(cryptoChoiceBox
                    .getSelectionModel().getSelectedItem().toString()));

            App.getCoordinator().showCheckoutScene();
        } else if (controller.getCart().getTotalCost() == 0) {
            checkoutErrorText.setText("Go buy something m8");
            checkoutErrorText.setVisible(true);
        } else if (cryptoChoiceBox.getSelectionModel().getSelectedIndex() == 0) {
            checkoutErrorText.setText("select a crypto currency");
            checkoutErrorText.setVisible(true);
        }

    }

    /**
     * Refreshes the UI with current cart items and values.
     */
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
        totalCostLabel.setText((String.valueOf(controller.getCart().getTotalCost())));

        //errorLabel.setVisible(false);
        //checkoutErrorText.setVisible(false);

        System.out.println("REFRESHED");

    }

    /**
     * Utility method that returns the abbreviation for the transaction https request.
     *
     * @param word
     * @return
     */
    private String returnCryptoAbbreviation(String word) {

        switch (word) {

            case "Bitcoin (BTC)":
                return "tbtc";
            case "Litecoin (LTC)":
                return "tltc";
            case "Zcash (ZEC)":
                return "tzec";
            default:
                return "txlm";

        }

    }

}
