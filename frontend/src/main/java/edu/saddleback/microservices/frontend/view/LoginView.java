package edu.saddleback.microservices.frontend.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import edu.saddleback.microservices.frontend.controller.AppController;
import edu.saddleback.microservices.frontend.controller.backendcontrollers.CreateAccountController;
import edu.saddleback.microservices.frontend.controller.backendcontrollers.GetCartController;
import edu.saddleback.microservices.frontend.controller.backendcontrollers.LoginController;

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
    @FXML
    private TextField emailTextField;

    /**
     * Constructor
     */
    public void initialize() {

        controller = App.getController();

    }

    /**
     * Attempts to log the user in based on the entered credentials.
     */
    public void onLoginClicked() {

        if ((!usernameTextField.getText().equals("") || !emailTextField.getText().equals("")) && !passwordTextField.getText().equals("")) {
            //ATTEMPT TO LOGIN
            LoginController loginAttempt = new LoginController(usernameTextField.getText(),
                    passwordTextField.getText());

            //Login Attempt Handler
            loginAttempt.getLoggedInObservableBoolean().subscribe((onChanged) -> {

                if (onChanged) {

                    controller.setLoggedInUsername(usernameTextField.getText());
                    controller.setToken(loginAttempt.getToken());

                    System.out.println("HERE1");
                    GetCartController con = new GetCartController(controller.getToken());
                    con.getCartReceivedBoolean().subscribe((onCartReceived) -> {

                        if (onCartReceived) {

                            controller.setCart(con.getCart());
                        }

                    });
                    con.start();

                    try {
                        App.getCoordinator().showAppScene();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {

                    Platform.runLater(() -> {
                        errorText.setText("***error-invalid credentials***");
                        errorText.setVisible(true);
                    });

                }

            });

            //Attempts to login
            loginAttempt.start();

        } else {
            errorText.setText("***error-missing credentials***");
            errorText.setVisible(true);
        }

    }

    /**
     * Attempts to create an account for the entered credentials.
     */
    public void onCreateAccountClicked() {

        if (!usernameTextField.getText().equals("") && !passwordTextField.getText().equals("") && !emailTextField.getText().equals("")) {

            CreateAccountController createAccountAttempt = new CreateAccountController(usernameTextField.getText(),
                    emailTextField.getText(), passwordTextField.getText());

            //Account Created Handler
            createAccountAttempt.getAccountCreatedObservableBoolean().subscribe((isAccountCreated) -> {

                if (isAccountCreated.equals(true)) {   //SUCCESS

                    Platform.runLater(() -> {
                        errorText.setText("Account Created! Please Login");
                        errorText.setVisible(true);
                    });

                } else {                           //FAILED

                    Platform.runLater(() -> {
                        errorText.setText("***error-account already exists***");
                        errorText.setVisible(true);
                    });

                }

            });

            //Attempts to create an account
            createAccountAttempt.start();

        } else {
            errorText.setText("***error-missing credentials***");
            errorText.setVisible(true);
        }

    }

}
