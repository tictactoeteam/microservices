package edu.saddleback.microservices.order;

import com.google.gson.Gson;
import edu.saddleback.microservices.order.db.DbManager;
import spark.Spark;

public class Main {
    public static void main(String[] args) throws InterruptedException {
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

        Spark.before((req, res) -> {

            res.type("application/json");
        });

        Spark.get("/orders/:orderId", (request, response) -> {

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
