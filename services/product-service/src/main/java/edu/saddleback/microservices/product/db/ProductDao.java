package edu.saddleback.microservices.product.db;

import edu.saddleback.microservices.product.model.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ProductDao {

    private static Connection connection = DbManager.getConnection();
    List<Product> listProduct = new ArrayList<>();

    public static Product getProductByID(UUID id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM products WHERE id=?"
        );
        statement.setString(1, id.toString());

        ResultSet rs = statement.executeQuery();

        return rs.next() ? extractProduct(rs) : null;
    }

    public List<Product> getAllProducts() throws SQLException{
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM products WHERE id=?"
        );

        ResultSet rs = statement.executeQuery("SELECT * FROM products");
        while(rs.next()){
            Product product = new Product();
            product.setProductID(UUID.fromString(rs.getString("id")));
            product.setProductName(rs.getString("name"));
            product.setProductPrice(rs.getString("price"));
            product.setProductQuantity(rs.getString("quantity");
        }
        return listProduct;
    }


    private static Product extractProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setProductID(UUID.fromString(rs.getString("id")));
        product.setProductName(rs.getString("name"));
        product.setProductQuantity(rs.getString("quantity"));
        product.setProductPrice(rs.getString("price"));

        return product;
    }
}
