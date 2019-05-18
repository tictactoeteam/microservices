package edu.saddleback.microservices.order.db;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.saddleback.microservices.order.shitidontunderstand.DbManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbHandler {


    public JsonObject getOrder(String orderId) {
        JsonObject rez = new JsonObject();
        try {
            //DO DB STUFF HERE!!

            Connection connection = DbManager.getConnection();
            ResultSet rs = connection.prepareStatement("SELECT * FROM orders WHERE id = " + orderId).executeQuery();

            rez.addProperty("id", rs.getString("id"));
            rez.addProperty("status", rs.getString("status"));
            rez.addProperty("coin", rs.getString("coin"));
            rez.addProperty("address", rs.getString("address"));


            rez.addProperty("something", "Henlo Worudo");
        } catch (SQLException ex) {
            rez.addProperty("error", "Yo, I think there might be a problem with getting your order man");
            rez.addProperty("code", "ORDER_NOT_FOUND");
        } finally {
            return rez;
        }
    }


    public JsonObject getOrders(String parsableJson) {
        return new JsonObject();
    }

    public JsonObject addOrder(String parsableJson) {
        JsonObject rez = new JsonObject();
        try {
            Connection connection = DbManager.getConnection();

            Gson g = new Gson();
            JsonObject param = g.fromJson(parsableJson, JsonObject.class);

            ResultSet rs = connection.prepareStatement(
                    "SELECT*FROM users WHERE id = " + param.get("customer_id"))
                    .executeQuery();
            if (rs.getString("name") == null) {
                throw new Exception();
            }

            ResultSet rs2 = connection.prepareStatement(
                    "SELECT*FROM products WHERE id = " + param.get("product_id"))
                    .executeQuery();

            connection.prepareStatement(
                    "INSERT INTO orders (status, customer_id, cart, coin, address, price)"
                    + "VALUES (PENDING," + param.get("customer_id")
                            + ",NULL, BITCOIN???,peepoo@gmail.com,100)")
                    .execute();


            ResultSet rs3 = connection.prepareStatement(
                    "SELECT * FROM orders WHERE customer_id = " + param.get("customer_id)"))
                    .executeQuery();

            String orderId = rs3.getString("id");

            //DO DB STUFF HERE!!
            JsonObject body = new JsonParser().parse(parsableJson).getAsJsonObject();
            rez.addProperty("order_id", orderId);
            rez.addProperty("order_status", "PENDING");
        } catch (Exception ex) {
            rez.addProperty("error", "Yo, there might be a problem with adding your order man");
            rez.addProperty("code", "ERROR_SOMETHING_BAD_HAPPENED_LOL");
        } finally {
            return rez;
        }
    }
}
