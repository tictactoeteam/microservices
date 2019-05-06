package edu.saddleback.microservices.frontend.view;

import edu.saddleback.microservices.frontend.controller.AppController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Controls the login.fxml page, including registering or logging in a user.
 */
public class LoginView {

    private AppController controller;

    @FXML
    private Label errorText;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;

    public void initialize() {

        controller = App.getController();

    }

    /**
     * Attempts to log the user in based on the entered credentials.
     */
    public void onLoginClicked() {

        if (!usernameTextField.getText().equals("") && !passwordTextField.getText().equals("")) {
            //ATTEMPT TO LOGIN


            //SUCCESS
            controller.setLoggedInUsername(usernameTextField.getText());
            try {
                App.getCoordinator().showAppScene();
            } catch (Exception e) {
            }

        } else {
            errorText.setText("***error-missing credentials***");
            errorText.setVisible(true);
        }

    }

    /**
     * Attempts to create an account for the entered credentials.
     */
    public void onCreateAccountClicked() {

        String username = usernameTextField.getText();
        if (!username.equals("") && !passwordTextField.getText().equals("")) {
            //ATTEMPT TO CREATE AN ACCOUNT


            //SUCCESS
            controller.setLoggedInUsername(usernameTextField.getText());
            try {
                App.getCoordinator().showAppScene();
            } catch (Exception e) {
            }

        }

    }

}
