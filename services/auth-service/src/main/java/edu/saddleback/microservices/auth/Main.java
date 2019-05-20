package edu.saddleback.microservices.auth;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import spark.Spark;

import edu.saddleback.microservices.auth.controllers.SessionController;
import edu.saddleback.microservices.auth.controllers.UserController;
import edu.saddleback.microservices.auth.db.DbManager;
import edu.saddleback.microservices.auth.util.ErrorResponse;

public class Main {
    public static void main(String[] args) {
        if (System.getenv("DO_INIT") != null && System.getenv("DO_INIT").equals("true")) {
            DbManager.init();

            if (System.getenv("DO_EXIT") != null && System.getenv("DO_EXIT").equals("true")) {
                System.exit(0);
            }
        }

        DbManager.migrate();

        start();
    }

    public static void start() {
        Gson gson = new Gson();

        Spark.port(8080);

        // all responses will be JSON
        Spark.before((req, res) -> {
            res.type("application/json");
        });

        Spark.get("/ping/", (req, res) -> "Auth service is online!");
        Spark.post("/users/", UserController::createUser, gson::toJson);
        Spark.post("/session/", SessionController::createSession, gson::toJson);

        // handle custom exceptions
        Spark.exception(ErrorResponse.class, (exception, req, res) -> {
            JsonObject response = new JsonObject();
            response.addProperty("error", exception.getError());
            response.addProperty("code", exception.getCode());

            res.status(exception.getStatus());
            res.body(response.toString());
        });

        Spark.exception(Exception.class, (exception, req, res) -> {
            System.err.println("Exception - returning 500");
            exception.printStackTrace();

            JsonObject response = new JsonObject();
            response.addProperty("error", "Internal server error");
            response.addProperty("code", "SERVER_ERROR");

            res.status(500);
            res.body(response.toString());
        });
    }
}
