package edu.saddleback.microservices.frontend.view;

import edu.saddleback.microservices.frontend.controller.AppController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class SceneCoordinator {

    private AppController controller;
    private Stage window;

    /**
     * Constructor
     * - Initializes the app's controller class
     * @param window
     */
    public SceneCoordinator(Stage window){

        this.window = window;
        this.controller = new AppController();

    }

    /**
     *
     * @return the app controller.
     */
    public AppController getController(){return controller;}

    /**
     * Displays the app.fxml to the Window.
     * @throws IOException
     */
    public void showAppScene() throws IOException {

        URL url = new File("src/main/res/layout/app.fxml").toURL();
        Parent layout = FXMLLoader.load(url);
        this.window.setScene(new Scene(layout));
        App.setWindowSize(600, 700);

    }

    /**
     * Displays the login.fxml to the Window.
     * @throws IOException
     */
    public void showLoginScene() throws IOException{

        URL url = new File("src/main/res/layout/login.fxml").toURL();
        Parent layout = FXMLLoader.load(url);
        this.window.setScene(new Scene(layout));
        App.setWindowSize(600, 300);

    }

}
