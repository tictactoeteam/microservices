package edu.saddleback.microservices.frontend.view;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;

import edu.saddleback.microservices.frontend.controller.AppController;

/**
 * This starts the application, loads the window and sets the stage with the main application page.
 */
public class App extends Application {

    private static SceneCoordinator coordinator;
    private static Stage Window;

    /**
     * Creates the scene coordinator and shows the App Scene.
     *
     * @param args
     */
    public static void main(String[] args) {

        coordinator = new SceneCoordinator(Window);

        try {
            coordinator.showAppScene();
        } catch (IOException e) {
            System.out.println("FAILED TO LOAD app.fxml");
        }
    }

    /**
     * Creates the window assigns it to the scene coordinator, showing the App Scene.
     *
     * @param window
     */
    @Override
    public void start(Stage window) {

        Window = window;
        Window.setTitle("Silk Road");
        Window.setWidth(600);
        Window.setHeight(700);
        Window.show();
        Window.setResizable(false);
        coordinator = new SceneCoordinator(Window);

        try {
            coordinator.showAppScene();
        } catch (IOException e) {
            System.out.println("FAILED TO LOAD app.fxml");
        }

    }

    /**
     * Allows the game controller to be accessed by each scene.
     *
     * @return
     */
    public static AppController getController() {
        return coordinator.getController();
    }

    /**
     * Allows the scene controller to be called from the scene views.
     *
     * @return
     */
    public static SceneCoordinator getCoordinator() {
        return coordinator;
    }

    /**
     * Allows for the window sizes to be changes.
     *
     * @param width
     * @param height
     */
    public static void setWindowSize(double width, double height) {

        Window.setWidth(width);
        Window.setHeight(height);

    }

    /**
     * Kills the application on exit request.
     */
    @Override
    public void stop() {
        System.exit(1);
    }

}
