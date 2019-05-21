package edu.saddleback.microservices.order.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import spark.Request;
import spark.Response;

import edu.saddleback.microservices.order.db.OrderDao;
import edu.saddleback.microservices.order.db.ProductDao;
import edu.saddleback.microservices.order.util.AddressUtil;
import edu.saddleback.microservices.order.util.CartObject;
import edu.saddleback.microservices.order.util.ErrorResponse;
import edu.saddleback.microservices.order.util.Order;
import edu.saddleback.microservices.order.util.TokenUtil;


public class RouteController {
    public static JsonObject createOrder(Request request, Response response) throws ErrorResponse, SQLException {
        JsonObject body = new JsonParser().parse(request.body()).getAsJsonObject();

        Order order = new Order();

        String customerId = TokenUtil.verifyToken(request.headers("Authorization"));
        order.setCustomerId(customerId);

        ArrayList<CartObject> cart = translate(body.get("cart").getAsJsonArray());
        order.setCart(cart);

        String coin = body.get("coin").getAsString().toLowerCase();
        if (! (coin.equals("tbtc") || coin.equals("tltc") || coin.equals("tzec") || coin.equals("txlm"))) {
            throw new ErrorResponse(400, "Invalid coin", "INVALID_COIN");
        }
        order.setCoin(coin);

        order.setAddress(AddressUtil.getAddress(coin));
        order.setPrice(convertUsd(coin, ProductDao.getProductPrice(cart)));
        order.setTimestamp(new Date());

        order = OrderDao.getInstance().addOrder(order);


        JsonObject result = new JsonObject();

        result.addProperty("id", order.getId());
        result.addProperty("status", order.getStatus().toString());
        result.addProperty("coin", order.getCoin());
        result.addProperty("address", order.getAddress());
        result.addProperty("price", order.getPrice());
        result.addProperty("timestamp", order.getTimestamp().getTime());

        JsonArray array = new JsonArray();
        for (CartObject cartObject : cart) {
            JsonObject temp = new JsonObject();
            temp.addProperty("product", cartObject.product);
            temp.addProperty("quantity", cartObject.quantity);
            array.add(temp);
        }

        result.add("cart", array);

        try {
            RabbitController.getChannel().basicPublish("order", "placed", null,
                    result.toString().getBytes());
        } catch (IOException ex) {
            System.out.println("Something wen bad trying to publish rabbit message");
        }

        response.status(201);
        return result;
    }

    public static JsonObject getOrder(Request request, Response response) {
        return null;
    }


    private static ArrayList<CartObject> translate(JsonArray array) {
        int size = array.size();

        ArrayList<CartObject> list = new ArrayList<>();

        CartObject temp;
        for (int i = 0; i < size; ++i) {
            temp = new Gson().fromJson(array.get(i), CartObject.class);
            list.add(temp);
        }

        return list;
    }

    private static int getDecimals(String coin) {
        switch (coin) {
            case "tbtc" :
            case "tltc":
            case "tzec":
                return 8;
            case "txlm":
                return 7;
            default:
                return 0;
        }
    }

    private static long convertUsd(String coin, long price) {
        try {
            URL url = new URL("https://test.bitgo.com/api/v2/market/latest?coin=" + coin);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();


            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            String rez = response.toString();

            JsonObject rezJson =  new JsonParser().parse(rez).getAsJsonObject();

            double conversion = rezJson.get("marketData").getAsJsonArray().get(0).getAsJsonObject().get("currencies")
                .getAsJsonObject().get("USD").getAsJsonObject().get("last").getAsDouble();

            return (long)((price / 100.0) / conversion * Math.pow(10, getDecimals(coin)));
        } catch (IOException ex) {
            ex.printStackTrace();
            return 0;
        }





    }
}
