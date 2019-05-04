package edu.saddleback.microservices.frontend.view;

import edu.saddleback.microservices.frontend.controller.AppController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

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
    private ScrollPane scrollPane;

    public void initialize(){

        controller = App.getController();

        if(!controller.getLoggedInUsername().equals("")){

            loggedInLabel.setVisible(true);
            usernameLabel.setVisible(true);
            usernameLabel.setText(controller.getLoggedInUsername());
            loginRegisterButton.setText("Logout");

        }else{

            loggedInLabel.setVisible(false);
            usernameLabel.setVisible(false);
            usernameLabel.setText("");
            loginRegisterButton.setText("Login/Register");

        }

    }

    /**
     * Redirects to the login/register window.
     */
    public void onLoginRegisterClicked(){

        if(!controller.getLoggedInUsername().equals("")){

            //LOGOUT HERE
            controller.setLoggedInUsername("");
            try {
                App.getCoordinator().showAppScene();
            }catch (Exception e){}


        }else{

            try {
                App.getCoordinator().showLoginScene();
            }catch (Exception e){}

        }



    }

    /**
     * Shows the user their cart content.
     */
    public void onCartImageClicked(){

        if(!controller.getLoggedInUsername().equals("")){

            try {
                App.getCoordinator().showCartScene();
            }catch (Exception e){}

        }else{

            try {
                App.getCoordinator().showLoginScene();
            }catch (Exception e){}

        }

    }

}
