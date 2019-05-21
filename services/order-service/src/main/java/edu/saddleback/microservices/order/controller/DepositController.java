package edu.saddleback.microservices.order.controller;

import java.io.IOException;
import java.sql.SQLException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import edu.saddleback.microservices.order.db.OrderDao;

public class DepositController {
    public void start() {
        try {
            RabbitController.getChannel().basicConsume("transaction-pending", false, "order-service",
                    new DefaultConsumer(RabbitController.getChannel()) {
                        @Override
                        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties props, byte[] body) {
                            JsonObject object = new JsonParser().parse(new String(body)).getAsJsonObject();

                            try {
                                OrderDao.getInstance().markPending(object.get("address").getAsString());
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    });

            RabbitController.getChannel().basicConsume("transaction-confirmed", false, "order-service",
                    new DefaultConsumer(RabbitController.getChannel()) {
                        @Override
                        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties props, byte[] body) {
                            JsonObject object = new JsonParser().parse(new String(body)).getAsJsonObject();

                            try {
                                OrderDao.getInstance().markConfirmed(object.get("address").getAsString());
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
