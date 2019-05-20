package edu.saddleback.microservices.frontend.view;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import edu.saddleback.microservices.frontend.controller.AppController;

public class SceneCoordinator {

    private static AppController controller;
    private Stage window;

    /**
     * Constructor
     * - Initializes the app's controller class
     *
     * @param window
     */
    public SceneCoordinator(Stage window) {

        this.window = window;
        this.controller = new AppController();

    }

    /**
     * @return the app controller.
     */
    public AppController getController() {
        return controller;
    }

    /**
     * Displays the app.fxml to the Window.
     *
     * @throws IOException
     */
    public void showAppScene() throws IOException {

        Platform.runLater(() -> {
            URL url = null;
            try {
                url = new File("src/main/res/layout/app.fxml").toURL();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Parent layout = null;
            try {
                layout = FXMLLoader.load(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.window.setScene(new Scene(layout));
            App.setWindowSize(600, 700);
        });

    }

    /**
     * Displays the login.fxml to the Window.
     *
     * @throws IOException
     */
    public void showLoginScene() throws IOException {

        URL url = new File("src/main/res/layout/login.fxml").toURL();
        Parent layout = FXMLLoader.load(url);
        this.window.setScene(new Scene(layout));
        App.setWindowSize(600, 350);

    }

    /**
     * Displays the login.fxml to the Window.
     *
     * @throws IOException
     */
    public void showCartScene() {

        URL url = null;
        try {
            url = new File("src/main/res/layout/cart.fxml").toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Parent layout = null;
        try {
            layout = FXMLLoader.load(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.window.setScene(new Scene(layout));
        App.setWindowSize(600, 600);

    }

}
