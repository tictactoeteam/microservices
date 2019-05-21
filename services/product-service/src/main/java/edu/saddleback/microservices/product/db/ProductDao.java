package edu.saddleback.microservices.product.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import edu.saddleback.microservices.product.model.Product;

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

        ResultSet rs = statement.executeQuery();
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
        product.setProductImage(rs.getString("image_path"));

        return product;
    }

    public void insertProduct(Product product) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO products (id, name, quantity, price, image_path)" +
                        " VALUES (?::uuid,?,?,?,?)");

        statement.setString(1,product.getProductId().toString());
        statement.setString(2,product.getProductName());
        statement.setInt(3,product.getProductQuantity());
        statement.setBigDecimal(4,product.getProductPrice());
        statement.setString(5, product.getProductImage());

        statement.executeUpdate();
    }
}
