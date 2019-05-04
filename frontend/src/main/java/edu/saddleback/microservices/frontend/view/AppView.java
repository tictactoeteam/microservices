package edu.saddleback.microservices.frontend.view;

import edu.saddleback.microservices.frontend.controller.AppController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;

/**
 * Controls the app.fxml page, including purchasing items and logging in/registering.
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
    private ImageView cartImage;
    @FXML
    private ScrollPane scrollPane;

    public void initialize(){

        controller = App.getController();

    }

    /**
     * Redirects to the login/register window.
     */
    public void onLoginRegisterClicked(){

        try {
            App.getCoordinator().showLoginScene();
        }catch (Exception e){}

    }

    /**
     * Shows the user their cart content.
     */
    public void onCartImageClicked(){



    }


}
