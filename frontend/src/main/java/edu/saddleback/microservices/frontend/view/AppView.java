package edu.saddleback.microservices.frontend.view;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import edu.saddleback.microservices.frontend.controller.AppController;
import edu.saddleback.microservices.frontend.controller.backendcontrollers.GetAllProductsController;
import edu.saddleback.microservices.frontend.model.Product;

/**
 * Controls the app.fxml page, including adding items to the cart and logging in/registering.
 */
public class AppView {

    private AppController controller;

    @FXML
    private Button loginRegisterButton;
    @FXML
    private Label loggedInLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private ScrollPane scrollPane;

    /**
     * Constructor
     * Initializes the logged in username label and fills the scroll pane with all ProductBoxs.
     */
    public void initialize() {

        controller = App.getController();

        if (!controller.getLoggedInUsername().equals("")) {

            loggedInLabel.setVisible(true);
            usernameLabel.setVisible(true);
            usernameLabel.setText(controller.getLoggedInUsername());
            loginRegisterButton.setText("Logout");

        } else {

            loggedInLabel.setVisible(false);
            usernameLabel.setVisible(false);
            usernameLabel.setText("");
            loginRegisterButton.setText("Login/Register");

        }

        GetAllProductsController productsController = new GetAllProductsController();
        productsController.getProductsRecievedBoolean().subscribe((isProductsReceived) -> {

            if (isProductsReceived.equals(true)) { //Success

                //Generates the product boxes
                List<Product> products = productsController.getProducts();
                ArrayList<ProductBox> productBoxes = new ArrayList<>();
                for (int i = 0; i < products.size(); i++) {
                    productBoxes.add(new ProductBox(products.get(i)));
                }

                VBox productBoxVBox = new VBox();
                productBoxVBox.setAlignment(Pos.CENTER);
                for (int i = 0; i < productBoxes.size(); i++) {
                    productBoxVBox.getChildren().add(productBoxes.get(i));
                }

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        scrollPane.setContent(productBoxVBox);
                    }
                });

            } else {                                //Failed

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        scrollPane.setContent(new Label("***error-cannot reach product service***"));
                    }
                });

            }

        });

        productsController.start();

    }

    /**
     * Redirects to the login/register window.
     */
    public void onLoginRegisterClicked() {

        if (!controller.getLoggedInUsername().equals("")) {

            controller.setLoggedInUsername("");
            controller.setToken("");
            try {
                App.getCoordinator().showAppScene();
            } catch (Exception e) {
                System.err.println("oof");
            }

        } else {

            try {
                App.getCoordinator().showLoginScene();
            } catch (Exception e) {
                System.err.println("oof");
            }

        }

    }

    /**
     * Shows the user their cart content.
     */
    public void onCartImageClicked() {

        if (!controller.getLoggedInUsername().equals("")) {

            try {
                App.getCoordinator().showCartScene();
            } catch (Exception e) {
                System.err.println("oof");
            }

        } else {

            try {
                App.getCoordinator().showLoginScene();
            } catch (Exception e) {
                System.err.println("oof");
            }

        }

    }

}
