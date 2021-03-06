package edu.saddleback.microservices.product.db.migrations;

import java.sql.Connection;
import java.sql.SQLException;

import edu.saddleback.microservices.product.db.Migration;


public class ProductTable implements Migration {

    @Override
    public void up(Connection connection) throws SQLException {
        connection.prepareStatement("CREATE TABLE products " +
                "(id uuid PRIMARY KEY NOT NULL DEFAULT gen_random_uuid(), " +
                "name text UNIQUE NOT NULL, " +
                "price numeric(12,2) NOT NULL, " +
                "quantity int NOT NULL DEFAULT 0, " +
                "image_path text NOT NULL)").execute();

        connection.prepareStatement("CREATE INDEX idx_products_name ON products (name)").execute();
    }

    @Override
    public void down(Connection connection) throws SQLException {
        connection.prepareStatement("DROP TABLE products CASCADE").execute();
    }
}
