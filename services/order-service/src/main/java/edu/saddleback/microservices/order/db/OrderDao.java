package edu.saddleback.microservices.order.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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


    // doesn't work!
    public JsonObject getOrder(String orderId) {
        Connection connection = DbManager.getConnection();
        
        try {
            ResultSet rs = connection.prepareStatement("SELECT * FROM orders WHERE id = " + orderId).executeQuery();

            String id = rs.getString("id");
            String status = rs.getString("status");
            String cartJsonString = rs.getString("cart");
            JsonArray cartJson =  (JsonArray)new JsonParser().parse(cartJsonString);
            String coin = rs.getString("coin");
            String address = rs.getString("address");
            long price = rs.getLong("price");
            Timestamp timestamp = rs.getTimestamp("timestamp");


            JsonObject response = new JsonObject();

            response.addProperty("id", id);
            response.addProperty("status", status);
            response.add("cart", cartJson);
            response.addProperty("coin", coin);
            response.addProperty("address", address);
            response.addProperty("price", price);
            response.addProperty("timestamp", timestamp.toString());

            return response;

        } catch (SQLException ex) {
            System.out.println("boom");
        }
        return null;
    }

    public JsonObject getAllOrders(String customerId) {
        Connection connection = DbManager.getConnection();
        JsonObject response = new JsonObject();
        JsonArray orders = new JsonArray();

        try {
            ResultSet rs = connection.prepareStatement("SELECT id FROM orders WHERE customer_id = " + customerId).executeQuery();

            int size = 0;
            if (rs != null) {
                rs.last();    // moves cursor to the last row
                size = rs.getRow(); // get row id
            }

            rs.first();

            response = new JsonObject();

            orders = new JsonArray(size);

            for (int i = 0; i < size; ++i) {
                orders.add(getOrder(rs.getString("id")));
            }

        } catch (SQLException ex) {
            System.out.println("oooo");
        }

        response.add("orders", orders);
        return response;
    }

    public JsonObject addOrder(String jsonBody) {
        JsonParser parser = new JsonParser();

        JsonObject requestBody = (JsonObject)parser.parse(jsonBody);

        JsonArray cart = requestBody.get("cart").getAsJsonArray();
        String coin = requestBody.get("coin").getAsString();


        Connection connection = DbManager.getConnection();

        long total = 0;
        String uuid = "defaultIg";
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO orders " +
                    "(status, customer_id, cart, coin, address, price)" +
                    "VALUES(?, ?, ?, ?, ?, ?)");

            statement.setString(1, "UNPAID");
            statement.setString(2, "123");
            statement.setString(3, cart.toString());
            statement.setString(4, coin);
            statement.setString(5, "123@gmail.com");


            long temp = 0;
            for (int i = 0; i < cart.size(); ++i) {
                ResultSet rs = connection.prepareStatement("SELECT * FROM product WHERE name = " +
                        cart.get(i).getAsJsonObject().get("product)")).executeQuery();
                temp = rs.getLong("price_per_unit");
                temp *= cart.get(i).getAsJsonObject().get("quantity").getAsInt();
                total += temp;
            }

            statement.setLong(6, total);

            statement.execute();

            ResultSet rs = connection.prepareStatement("SELECT * FROM orders WHERE cart = "  + cart.getAsString()).executeQuery();
            uuid = rs.getString("id");



        } catch (SQLException ex) {
            System.out.println("waaaaah");
            return null;
        }


        JsonObject response = new JsonObject();

        response.addProperty("id", uuid);
        response.addProperty("status", "UNPAID");
        response.add("cart", cart);
        response.addProperty("coin", coin);
        response.addProperty("address", "123@gmail.com");
        response.addProperty("price", total);
        response.addProperty("timestamp", new Date().toString());

        return response;

    }
}
