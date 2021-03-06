package edu.saddleback.microservices.product.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import spark.Request;
import spark.Response;

import edu.saddleback.microservices.product.db.ProductDao;
import edu.saddleback.microservices.product.model.Product;
import edu.saddleback.microservices.product.util.RabbitProvider;
import static edu.saddleback.microservices.product.util.RabbitProvider.getChannel;


public class ProductController {
    //convert from DAO to JSON
    ProductDao productDao = new ProductDao();

    public static JsonArray convertDaoListToJson(Request request, Response response) {
        ArrayList<Product> productList = new ArrayList<>();
        ProductDao productDaos = new ProductDao();
        Gson gson = new GsonBuilder().create();

        JsonArray jsonProductList = gson.toJsonTree(productList).getAsJsonArray();

        {
            try {
                productList = productDaos.getAllProducts();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < productList.size(); i++) {

            Product product = new Product();

            product = productList.get(i);
            product.setProductID(productList.get(i).getProductId());
            product.setProductName(productList.get(i).getProductName());
            product.setProductQuantity(productList.get(i).getProductQuantity());
            product.setProductPrice(productList.get(i).getProductPrice());

            JsonObject res = product.toJson();
            jsonProductList.add(res);
        }
        return jsonProductList;
    }

    //Need to convert one DAO query to Json
    public static JsonObject convertDaoToJson(Product someProduct) {
        JsonObject res = new JsonObject();
        res.addProperty("id", someProduct.getProductId().toString());
        res.addProperty("name", someProduct.getProductName());
        res.addProperty("price", someProduct.getProductPrice().toString());
        res.addProperty("quantity", someProduct.getProductQuantity());
        res.addProperty("image_path", someProduct.getProductImage());
        return res;
    }

    public static JsonObject getProductJson(String uuid, Request request, Response response) {
        JsonObject res = new JsonObject();
        Product someProduct = new Product();
        ProductDao productdao = new ProductDao();

        try {
            someProduct = productdao.getProductByID(uuid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        res = convertDaoToJson(someProduct);

        return res;
    }

    public static void productPurchased() {
        try {
            RabbitProvider.getChannel().basicConsume("order-placed", false, "productTag",
                    new DefaultConsumer(RabbitProvider.getChannel()) {
                        @Override
                        public void handleDelivery(String consumerTag,
                                                   Envelope envelope,
                                                   AMQP.BasicProperties properties,
                                                   byte[] body)
                                throws IOException {
                            String routingKey = envelope.getRoutingKey();
                            String contentType = properties.getContentType();
                            long deliveryTag = envelope.getDeliveryTag();

                            JsonParser parser = new JsonParser();
                            JsonObject json = parser.parse(body.toString()).getAsJsonObject();

                            //go through the json array here?
                            JsonArray cartArray = json.get("cart").getAsJsonArray();

                            for (int i = 0; i < cartArray.size(); i++) {
                                String productName = cartArray.getAsString();
                                System.out.println(productName + " was received");

                            }

                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
