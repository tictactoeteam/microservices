package edu.saddleback.microservices.order.db;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.sql.*;
import java.util.Date;

public class OrderDAO {
    private OrderDAO(){

    }

    private static OrderDAO instance = null;

    public static OrderDAO getInstance(){
        if (instance == null)
            instance = new OrderDAO();
        return instance;
    }

    public JsonObject getOrder(String orderId){
        Connection connection = DbManager.getConnection();

        try {
            ResultSet rs = connection.prepareStatement("SELECT * FROM orders WHERE id = " + orderId).executeQuery();
            java.sql.Array array = rs.getArray("cart");

            array.getBaseTypeName();

        } catch (SQLException ex) {
            System.out.println("boom");
        }
        return null;
    }

    public JsonObject getAllOrders(String jsonBody){
        return null;
    }

    public JsonObject addOrder(String jsonBody){
        JsonParser parser = new JsonParser();

        JsonObject requestBody = (JsonObject)parser.parse(jsonBody);

        JsonArray cart = requestBody.get("cart").getAsJsonArray();
        String coin = requestBody.get("coin").getAsString();


        Connection connection = DbManager.getConnection();

        long total = 0;
        String uuid = "";
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO orders (status, customer_id, cart, coin, address, price)"
                            + "VALUES(?, ?, ?, ?, ?, ?)");

            statement.setString(1, "UNPAID");
            statement.setString(2, "123");
            statement.setString(3, cart.getAsString());
            statement.setString(4, coin);
            statement.setString(5, "123@gmail.com");


            long temp = 0;
            for (int i=0; i<cart.size(); ++i){
               ResultSet rs = connection.prepareStatement("SELECT * FROM product WHERE name = "
                       + cart.get(i).getAsJsonObject().get("product)")).executeQuery();
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
