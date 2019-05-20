package edu.saddleback.microservices.order.controller;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import spark.Request;
import spark.Response;

import edu.saddleback.microservices.order.util.CartObject;



public class RouteController {
    public static JsonObject createOrder(Request request, Response response) {
        JsonObject body = new JsonParser().parse(request.body()).getAsJsonObject();


        String customerId = body.get("token").getAsString();
        List<CartObject> cart = translate(body.get("cart").getAsJsonArray());
        String coin = body.get("coin").getAsString();

        return null;

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
