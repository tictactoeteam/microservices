package edu.saddleback.microservices.auth.controllers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import edu.saddleback.microservices.auth.db.UserDao;
import edu.saddleback.microservices.auth.models.User;
import edu.saddleback.microservices.auth.util.ErrorResponse;
import edu.saddleback.microservices.auth.util.InvalidRequest;
import edu.saddleback.microservices.auth.util.RabbitProvider;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import spark.Request;
import spark.Response;


public class UserController {
    public static JsonObject createUser(Request request, Response response) throws ErrorResponse {
        JsonObject params = validateCreateUserParams(request.body());

        String username = params.get("username").getAsString();
        String email = params.get("email").getAsString();
        String password = params.get("password").getAsString();

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setCreatedDate(new Date());

        try {
            if (UserDao.getUserByName(username) != null || UserDao.getUserByEmail(email) != null) {
                throw new ErrorResponse(400, "An account with that username or email already exists",
                        "ACCOUNT_EXISTS");
            }

            UserDao.insertUser(user);

            user = UserDao.getUserByName(username);
        } catch (SQLException e) {
            throw new ErrorResponse(500, "An error occurred while creating your account",
                    "SERVER_ERROR");
        }

        JsonObject res = new JsonObject();
        res.addProperty("id", user.getId().toString());
        res.addProperty("username", user.getUsername());
        res.addProperty("email", user.getEmail());
        res.addProperty("createdAt", user.getCreatedDate().getTime());

        try {
            RabbitProvider.getChannel().basicPublish("user", "created", null,
                    res.toString().getBytes());
        } catch (IOException e) {
            System.err.println("Failed to publish new user to Rabbit");
            e.printStackTrace();
        }

        response.status(201);
        return res;
    }

    private static JsonObject validateCreateUserParams(String params) throws ErrorResponse {
        JsonObject body;
        try {
            body = new JsonParser().parse(params).getAsJsonObject();
        } catch (JsonSyntaxException | IllegalStateException e) {
            throw new InvalidRequest();
        }

        if (body.get("username") == null) {
            throw new InvalidRequest("username");
        }
        if (body.get("email") == null) {
            throw new InvalidRequest("email");
        }
        if (body.get("password") == null) {
            throw new InvalidRequest("password");
        }

        return body;
    }
}
