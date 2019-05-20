package edu.saddleback.microservices.order.controller;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import spark.Request;
import spark.Response;

import edu.saddleback.microservices.order.db.OrderDao;
import edu.saddleback.microservices.order.util.CartObject;
import edu.saddleback.microservices.order.util.Order;


public class RouteController {
    public static JsonObject createOrder(Request request, Response response) {
        JsonObject body = new JsonParser().parse(request.body()).getAsJsonObject();


        String customerId = body.get("token").getAsString();
        List<CartObject> cart = translate(body.get("cart").getAsJsonArray());
        String coin = body.get("coin").getAsString();


        Order order = OrderDao.getInstance().addOrder(customerId, cart, coin);

        JsonObject result = new JsonObject();

        result.addProperty("id", order.id);
        result.addProperty("status", order.status);
        result.addProperty("coin", order.coin);
        result.addProperty("address", order.address);
        result.addProperty("price", order.price);
        result.addProperty("timestamp", order.timestamp);

        JsonArray array = new JsonArray();
        for (CartObject cartObject : cart) {
            JsonObject temp = new JsonObject();
            temp.addProperty("product", cartObject.product);
            temp.addProperty("quantity", cartObject.quantity);
            array.add(temp);
        }

        result.add("cart", array);

        return result;
    }



    private static List<CartObject> translate(JsonArray array) {
        int size = array.size();


        List<CartObject> list = new ArrayList<>();

        CartObject temp;
        for (int i = 0; i < size; ++i) {
            temp = new Gson().fromJson(array.get(i), CartObject.class);
            list.add(temp);
        }

        return list;
    }
}
