package edu.saddleback.microservices.order.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
