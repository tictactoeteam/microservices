package edu.saddleback.microservices.product;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import spark.Spark;

import edu.saddleback.microservices.product.controllers.ProductController;
import edu.saddleback.microservices.product.db.DbManager;
import edu.saddleback.microservices.product.model.Product;


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

        // all responses will be JSON
        Spark.before((req, res) -> {
            res.type("application/json");
        });

        Spark.get("/ping/", (req, res) -> "Product service is online");
        //Example on how to post      Function to call           Json Conversion
        //15672 docker compose reference to check rabbitmq
        //Spark.post("/products/", ProductController::createProduct, gson::toJson);

        Spark.post("/product/", ProductController::convertDaoListToJson, gson::toJson);
        // Spark.post("/product/", ProductController::convertDaoListToJson, gson::toJson);

        //FOR KAI TO GET THE OBJECT LIST
        Spark.get("/product/", ProductController::convertDaoListToJson, gson::toJson);

        //FOR KAI TO GET AN INDIVIDUAL PRODUCT
        //Spark.get("/product", ProductController::getProductJson, gson::toJson);
    }
}
