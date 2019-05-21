package edu.saddleback.microservices.order.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.saddleback.microservices.order.util.CartObject;
import edu.saddleback.microservices.order.util.Order;
import edu.saddleback.microservices.order.util.Status;

public class OrderDao {
    private OrderDao(){

    }

    private static OrderDao instance = null;

    public static OrderDao getInstance() {
        if (instance == null) {
            instance = new OrderDao();
        }
        return instance;
    }

    private String convertCart(ArrayList<CartObject> cart) {

        String cartString = "array[";
        for (int i = 0; i < cart.size(); ++i) {
            cartString += "('" + cart.get(i).product + "', " + cart.get(i).quantity + ")";

            if (i != cart.size() - 1) {
                cartString += ", ";
            }
        }
        cartString += "]::cart_item[]";
        return cartString;

    }

    private Order extractOrder(ResultSet rs) throws SQLException {
        Order order = new Order();

        order.setId(rs.getString("id"));
        order.setCustomerId(rs.getString("custommer_id"));
        order.setStatus(Status.valueOf(rs.getString("status")));
        order.setCart(new ArrayList<>());
        order.setCoin(rs.getString("coin"));
        order.setAddress(rs.getString("address"));
        order.setPrice(rs.getLong("price"));
        order.setTimestamp(rs.getDate("timestamp"));

        return order;
    }

    public Order getOrder(String id) throws SQLException {
        Connection connection = DbManager.getConnection();

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM orders " +
                "WHERE id = ?");

        statement.setString(1, id);

        System.out.println(statement);
        ResultSet rs = statement.executeQuery();

        return rs.next() ? extractOrder(rs) : null;

    }

    public Order addOrder(Order order) throws SQLException {
        Connection connection = DbManager.getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO orders " +
                "(status, customer_id, cart, coin, address, price, timestamp) " +
                "VALUES (?::order_status, ?::uuid, " + convertCart(order.getCart()) + ", ?, ?, ?, ?)");

        statement.setString(1, order.getStatus().toString());
        statement.setString(2, order.getCustomerId());
        statement.setString(3,  order.getCoin());
        statement.setString(4, order.getAddress());
        statement.setLong(5, order.getPrice());
        statement.setDate(6, new java.sql.Date(order.getTimestamp().getTime()));

        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Invalid Order!");
        }

        String id =  statement.getGeneratedKeys().getString(1);

        return getOrder(id);

    }

    public void markPending(String address) throws SQLException {
        Connection connection = DbManager.getConnection();
        PreparedStatement statement = connection.prepareStatement("UPDATE orders SET status='PENDING'::order_status WHERE address=?");

        statement.setString(1, address);
        statement.executeUpdate();
    }

    public void markConfirmed(String address) throws SQLException {
        Connection connection = DbManager.getConnection();
        PreparedStatement statement = connection.prepareStatement("UPDATE orders SET status='PAID'::order_status WHERE address=?");

        statement.setString(1, address);
        statement.executeUpdate();
    }
}
