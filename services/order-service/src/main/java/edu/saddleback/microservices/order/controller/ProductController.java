package edu.saddleback.microservices.order.controller;

import java.io.IOException;
import java.sql.SQLException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import edu.saddleback.microservices.order.db.ProductDao;

public class ProductController {
    public void start() {
        try {
            RabbitController.getChannel().basicConsume("product-created", false, "order-service",
                    new DefaultConsumer(RabbitController.getChannel()) {
                        @Override
                        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties props, byte[] body) {
                            JsonObject object = new JsonParser().parse(new String(body)).getAsJsonObject();

                            try {
                                ProductDao.addProduct(object.get("id").getAsString(), object.get("price").getAsNumber().doubleValue());
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
