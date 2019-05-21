package edu.saddleback.microservices.order.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.saddleback.microservices.order.util.CartObject;

public class ProductDao  {
    public static long getProductPrice(ArrayList<CartObject> cart) throws SQLException {
        Connection connection = DbManager.getConnection();

        PreparedStatement statement = connection.prepareStatement("SELECT SUM(price) FROM product" +
                "WHERE id IN ?");

        String[] ids = cart.stream().map((cartObject) -> cartObject.product).toArray(String[]::new);
        statement.setArray(1, connection.createArrayOf("uuid", ids));

        ResultSet rs = statement.executeQuery();

        return rs.getLong(1);
    }
}
