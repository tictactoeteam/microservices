package edu.saddleback.microservices.frontend;

import javafx.application.Application;

import edu.saddleback.microservices.frontend.view.App;

/**
 * Launches the main App FXApplication, which can therefore have its methods called statically.
 */
public class Main {
    public static void main(String[] args) {

        Application.launch(App.class, args);

    }
}
