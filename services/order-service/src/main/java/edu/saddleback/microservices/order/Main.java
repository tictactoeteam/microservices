package edu.saddleback.microservices.order;

import com.google.gson.Gson;
import spark.Spark;

import edu.saddleback.microservices.order.controller.RouteController;
import edu.saddleback.microservices.order.db.DbManager;
import edu.saddleback.microservices.order.db.OrderDao;

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

        Spark.get("/orders/:orderId", RouteController::getOrder, gson::toJson);
        Spark.post("/orders/", RouteController::createOrder, gson::toJson);


    }
}
