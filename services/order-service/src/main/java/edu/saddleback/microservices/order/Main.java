package edu.saddleback.microservices.order;

import com.google.gson.*;
import edu.saddleback.microservices.order.db.DbHandler;
import spark.Spark;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Gson gson = new Gson();

        Spark.port(8080);

        DbHandler db = new DbHandler();

        // all responses will be JSON
        Spark.before((req, res) -> {
            res.type("application/json");
        });

        Spark.get("/orders/:orderId", (request, response) ->{
            return db.getOrder(request.params("orderId"));
        }, gson::toJson);


        Spark.post("/orders", ((request, response) -> {
            return db.addOrder(request.body());
        }), gson::toJson);

        Spark.get("/orders", ((request, response) -> {
            return db.getOrders(request.body());
        }), gson::toJson);
    }
}
