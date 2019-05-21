package edu.saddleback.microservices.order.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.saddleback.microservices.order.util.CartObject;

public class ProductDao  {
    public static double getProductPrice(String id) throws SQLException {
        Connection connection = DbManager.getConnection();

        PreparedStatement statement = connection.prepareStatement("SELECT price FROM products " +
                "WHERE id = ?::uuid");

        statement.setString(1, id);

        ResultSet rs = statement.executeQuery();

        return rs.getDouble(1);
    }

    public static void addProduct(String id, double price) throws SQLException {
        Connection connection = DbManager.getConnection();

        PreparedStatement statement = connection.prepareStatement("INSERT INTO products (id, price) VALUES (?::uuid,?)");
        statement.setString(1, id);
        statement.setDouble(2, price);

        int rowsAffected = statement.executeUpdate();

        if (rowsAffected == 0) {
            throw new SQLException("Failed to create product " + id);
        }
    }
}
