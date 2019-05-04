package edu.saddleback.microservices.frontend;

import edu.saddleback.microservices.frontend.view.App;
import javafx.application.Application;

/**
 * Launches the main App FXApplication, which can therefore have its methods called statically.
 */
public class Main {
    public static void main(String[] args) {

        Application.launch(App.class, args);

    }
}
