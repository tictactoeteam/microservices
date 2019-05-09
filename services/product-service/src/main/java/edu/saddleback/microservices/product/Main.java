package edu.saddleback.microservices.product;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import spark.Spark;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        start();
    }

    public static void start() {
        Gson gson = new Gson();

        Spark.port(8080);

        // all responses will be JSON
        Spark.before((req, res) -> {
            res.type("application/json");
        });
        //Example on how to post      Function to call           Json Conversion
        //Spark.post("/products/", ProductController::createProduct, gson::toJson);
    }
}
