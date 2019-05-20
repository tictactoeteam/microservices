package edu.saddleback.microservices.order.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.saddleback.microservices.order.util.CartObject;
import edu.saddleback.microservices.order.util.Order;

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


    public Order getOrder(String orderId) {

        return null;
    }

    public List<Order> getAllOrders(String customerId) {

        return null;
    }

    public Order addOrder(String customerId, List<CartObject> cart, String coin) {
        Connection connection = DbManager.getConnection();


        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO orders" +
                    "(status, customer_id, cart, coin, address, price, timestamp)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)");

            statement.setString(1, "UNPAID");
            statement.setString(2, customerId);
            statement.setString(4, coin);
            Date useful = new Date();
            statement.setString(7, useful.toString());

            String cartString = "array[";
            for (int i = 0; i < cart.size(); ++i) {
                cartString += "(" + cart.get(i).product + ", " + cart.get(i).quantity + ")";

                if (i != cart.size() - 1) {
                    cartString += ", ";
                } else {
                    cartString += "]::cart_item[]";
                }
            }

            statement.setString(3, cartString);


            ResultSet user = connection.prepareStatement("SELECT email FROM users WHERE id = " + customerId).executeQuery();
            String email = user.getString("email");

            statement.setString(5, email);


            long total = 0;
            for (CartObject cartObject : cart) {
                ResultSet price = connection.prepareStatement("SELECT price_per_unit FROM product WHERE id = " +
                        cartObject.product).executeQuery();
                long priceActual = price.getLong("price_per_unit");
                total += priceActual * cartObject.quantity;
            }

            statement.setLong(6, total);

            statement.execute();


            ResultSet thisIsDumb = connection.prepareStatement("SELECT id FROM orders WHERE " +
                    "customer_id = " + customerId + " and timestamp = " +  useful.toString()).executeQuery();
            String id = thisIsDumb.getString("id");

            Order result = new Order(id, "UNPAID", cart, coin, email, total, useful.toString());

            return result;

        } catch (SQLException ex) {
            System.out.println("Yo I think something brok");
            return null;
        }
    }
}
