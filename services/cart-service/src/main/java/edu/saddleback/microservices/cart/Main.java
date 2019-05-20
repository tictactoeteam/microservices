package edu.saddleback.microservices.cart;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import spark.Spark;

import edu.saddleback.microservices.cart.controllers.CartController;
import edu.saddleback.microservices.cart.util.ErrorResponse;

public class Main {
    public static void main(String[] args) {
        Gson gson = new Gson();

        Spark.port(8080);

        // all responses will be JSON
        Spark.before((req, res) -> {
            res.type("application/json");
        });

        Spark.get("/ping/", (req, res) -> "Cart service is online!");
        Spark.get("/cart/", CartController::getCart, gson::toJson);
        Spark.put("/cart/", CartController::updateCart, gson::toJson);
        Spark.delete("/cart/", CartController::clearCart, gson::toJson);

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
