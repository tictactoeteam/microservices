package edu.saddleback.microservices.order.controller;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

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

            channel.exchangeDeclare("user", "direct", true);
            channel.queueDeclare("user-created", true, false, false, null);
            channel.queueBind("user-created", "user", "created");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return channel;
    }
}
