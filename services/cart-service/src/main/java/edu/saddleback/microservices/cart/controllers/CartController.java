package edu.saddleback.microservices.cart.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import spark.Request;
import spark.Response;

import edu.saddleback.microservices.cart.model.CartItem;
import edu.saddleback.microservices.cart.util.ErrorResponse;
import edu.saddleback.microservices.cart.util.InvalidRequest;
import edu.saddleback.microservices.cart.util.TokenUtil;

public class CartController {
    private static final String REDIS_PASSWORD = System.getenv("REDIS_PASSWORD");
    private static RedisClient redisClient = RedisClient.create("redis://" + REDIS_PASSWORD + "@redis-master:6379/0");
    private static StatefulRedisConnection<String, String> connection = redisClient.connect();
    private static RedisCommands<String, String> cmd = connection.sync();

    private static Gson gson = new Gson();

    public static JsonObject getCart(Request request, Response response) throws ErrorResponse {
        String userId = TokenUtil.verifyToken(request.headers("Authorization"));

        String cartKey = "cart:" + userId;

        JsonObject res = new JsonObject();
        res.add("cart", getCartArray(cartKey));

        response.status(200);
        return res;
    }

    public static JsonObject updateCart(Request request, Response response) throws ErrorResponse {
        String userId = TokenUtil.verifyToken(request.headers("Authorization"));
        JsonObject body = new JsonParser().parse(request.body()).getAsJsonObject();

        if (!body.has("cart")) {
            throw new InvalidRequest("cart");
        }

        CartItem[] newCart;
        try {
            newCart = gson.fromJson(body.get("cart").getAsJsonArray(), CartItem[].class);
        } catch (JsonSyntaxException e) {
            throw new ErrorResponse(400, "Invalid cart format", "INVALID_CART");
        }

        for (CartItem item : newCart) {
            if (!item.isValid()) {
                throw new ErrorResponse(400, "Invalid cart item " + item.getProduct(), "INVALID_CART");
            }
        }

        String cartKey = "cart:" + userId;

        cmd.del(cartKey);
        cmd.rpush(cartKey, Arrays.stream(newCart)
                .map(CartItem::toString)
                .toArray(String[]::new));

        JsonObject res = new JsonObject();
        res.add("cart", getCartArray(cartKey));

        response.status(200);
        return res;
    }

    public static JsonObject clearCart(Request request, Response response) throws ErrorResponse {
        String userId = TokenUtil.verifyToken(request.headers("Authorization"));

        String cartKey = "cart:" + userId;
        cmd.del(cartKey);

        response.status(204);
        return null;
    }

    private static JsonArray getCartArray(String key) {
        long cartLength = cmd.llen(key);

        List<CartItem> cart = cmd.lrange(key, 0, cartLength).stream()
                .map(CartItem::new).collect(Collectors.toList());

        return gson.toJsonTree(cart, new TypeToken<ArrayList<CartItem>>() {}.getType()).getAsJsonArray();
    }
}
