package edu.saddleback.microservices.auth.controllers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import edu.saddleback.microservices.auth.db.UserDao;
import edu.saddleback.microservices.auth.models.User;
import edu.saddleback.microservices.auth.util.ErrorResponse;
import edu.saddleback.microservices.auth.util.InvalidRequest;
import edu.saddleback.microservices.auth.util.TokenUtil;
import java.sql.SQLException;
import spark.Request;
import spark.Response;

public class SessionController {
    public static JsonObject createSession(Request request, Response response) throws ErrorResponse {
        JsonObject body = validateCreateSessionParams(request.body());

        User user;

        try {
            if (body.get("username") != null) {
                user = UserDao.getUserByName(body.get("username").getAsString());
            } else {
                user = UserDao.getUserByEmail(body.get("email").getAsString());
            }
        } catch (SQLException e) {
            throw new ErrorResponse(500, "An error occurred while logging in", "SERVER_ERROR");
        }

        if (user == null || user.isDisabled()) {
            throw new ErrorResponse(400, "Invalid username or password", "BAD_LOGIN");
        }

        if (user.checkPassword(body.get("password").getAsString())) {
            JsonObject res = new JsonObject();
            res.addProperty("token", TokenUtil.createToken(user.getId().toString()));

            return res;
        } else {
            throw new ErrorResponse(400, "Invalid username or password", "BAD_LOGIN");
        }
    }

    private static JsonObject validateCreateSessionParams(String params) throws ErrorResponse {
        JsonObject body;
        try {
            body = new JsonParser().parse(params).getAsJsonObject();
        } catch (JsonSyntaxException | IllegalStateException e) {
            throw new InvalidRequest();
        }

        if (body.get("username") == null && body.get("email") == null) {
            throw new ErrorResponse(400, "Must specify username or email", "INVALID_REQUEST");
        }

        if (body.get("password") == null) {
            throw new InvalidRequest("password");
        }

        return body;
    }
}
