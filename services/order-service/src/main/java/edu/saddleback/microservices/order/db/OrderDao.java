package edu.saddleback.microservices.order.db;

import java.util.List;

import edu.saddleback.microservices.order.util.Order;

public class OrderDao {
    private OrderDao(){

    }

    private static OrderDao instance = null;

    public static OrderDao getInstance() {
        if (instance == null) {
            instance = new OrderDao();
        }
        return instance;
    }


    public Order getOrder(String orderId) {

        return null;
    }

    public List<Order> getAllOrders(String customerId) {

        return null;
    }

    public Order addOrder(String jsonBody) {
        
        return null;
    }
}
