package edu.saddleback.microservices.product.db;

import edu.saddleback.microservices.product.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class ProductDao {

    private static Connection connection = DbManager.getConnection();

    public static Product getProductByID(String id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM products WHERE id=?"
        );
        statement.setString(1, id);

        ResultSet rs = statement.executeQuery();

        return rs.next() ? extractProduct(rs) : null;
    }

    public ArrayList<Product> getAllProducts() throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM products"
        );

        ArrayList<Product> products = new ArrayList<>();

        ResultSet rs = statement.executeQuery("SELECT * FROM products");
        while (rs.next()) {
            products.add(extractProduct(rs));
        }
        return products;
    }

    private static Product extractProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setProductID(UUID.fromString(rs.getString("id")));
        product.setProductName(rs.getString("name"));
        product.setProductQuantity(rs.getInt("quantity"));
        product.setProductPrice(rs.getBigDecimal("price"));

        return product;
    }
}
