package edu.saddleback.microservices.order.controller;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitController {
    private static String USERNAME = System.getenv("RABBIT_USERNAME");
    private static String PASSWORD = System.getenv("RABBIT_PASSWORD");

    private static Connection connection;
    private static Channel channel;

    public static Connection getConnection() {
        if (connection != null) {
            return connection;
        }

        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);
        factory.setHost("rabbitmq");

        try {
            connection = factory.newConnection();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static Channel getChannel() {
        if (channel != null) {
            return channel;
        }

        try {
            channel = getConnection().createChannel();

            channel.exchangeDeclare("order", "direct", true);
            channel.queueDeclare("order-placed", true, false, false, null);
            channel.queueDeclare("order-shipped", true, false, false, null);
            channel.queueBind("order-placed", "order", "placed");
            channel.queueBind("order-shipped", "order", "shipped");

            channel.queueDeclare("product-created", true, false, false, null);
            channel.queueDeclare("transaction-pending", true, false, false, null);
            channel.queueDeclare("transaction-confirmed", true, false, false, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return channel;
    }
}
