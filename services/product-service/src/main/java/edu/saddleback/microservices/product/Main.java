package edu.saddleback.microservices.product;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import spark.Spark;

import edu.saddleback.microservices.product.controllers.ProductController;
import edu.saddleback.microservices.product.db.DbManager;
import edu.saddleback.microservices.product.db.ProductDao;
import edu.saddleback.microservices.product.model.Product;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        if (System.getenv("DO_INIT") != null && System.getenv("DO_INIT").equals("true")) {
            DbManager.init();

            if (System.getenv("DO_EXIT") != null && System.getenv("DO_EXIT").equals("true")) {
                System.exit(0);
            }
        }

        ProductDao productDao = new ProductDao();
        Product coke = new Product(UUID.randomUUID(),"Cocaine", new BigDecimal(27.03),300,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/Crack-cocaine-2-grams.jpeg" +
                        "/1024px-Crack-cocaine-2-grams.jpeg");
        Product gun = new Product(UUID.randomUUID(),"Gun", new BigDecimal(17.03),10,
                "https://www.ammoland.com/wp-content/uploads/2014/05/Smart-Guns.jpg");
        Product banana = new Product(UUID.randomUUID(),"Banana", new BigDecimal(1.03),160,
                "https://img.purch.com/w/660/aHR0cDovL3d3dy5saXZlc2NpZW5jZS5jb20vaW1hZ2VzL2kvMDAwLzA2NS8xNDkvb3JpZ2luYWwvYmFuYW5hcy5qcGc=\n");
        Product kinderEgg = new Product(UUID.randomUUID(),"Kinder Egg", new BigDecimal(441.03),15,
                "https://i0.wp.com/metro.co.uk/wp-content/uploads/2017/10/pri_55481725.jpg?quality=90&strip=all&zoom=1&resize=644%2C402&ssl=1\n");
        Product magicCroissant = new Product(UUID.randomUUID(),"Magic Croissant", new BigDecimal(27.03),344,
                "https://www.wowamazing.com/wp-content/uploads/2015/06/cutoms-confiscated-items-1.jpg");
        Product pizza = new Product(UUID.randomUUID(),"Pizza", new BigDecimal(93.03),411,
                "https://mypizzaiole.com/wp-content/uploads/2017/03/Untitled-1-36.png");
        try {
            productDao.insertProduct(coke);
            productDao.insertProduct(gun);
            productDao.insertProduct(banana);
            productDao.insertProduct(kinderEgg);
            productDao.insertProduct(pizza);
            productDao.insertProduct(magicCroissant);
        } catch (SQLException e) {
            e.printStackTrace();
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

        Spark.post("/products/", ProductController::convertDaoListToJson, gson::toJson);
        // Spark.post("/product/", ProductController::convertDaoListToJson, gson::toJson);

        //FOR KAI TO GET THE OBJECT LIST
        Spark.get("/products/", ProductController::convertDaoListToJson, gson::toJson);

        //FOR KAI TO GET AN INDIVIDUAL PRODUCT
        //Spark.get("/product", ProductController::getProductJson, gson::toJson);
    }
}
