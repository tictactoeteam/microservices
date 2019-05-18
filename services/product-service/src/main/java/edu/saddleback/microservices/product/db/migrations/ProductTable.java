package edu.saddleback.microservices.product.db.migrations;

import edu.saddleback.microservices.product.db.Migration;

import java.sql.Connection;
import java.sql.SQLException;

public class ProductTable implements Migration {

    @Override
    public void up(Connection connection) throws SQLException {
        connection.prepareStatement("CREATE TABLE products"
               + "(id uuid PRIMARY KEY NOT NULL DEFAULT gen_random_uuid(), "
               + "name text UNIQUE NOT NULL,"
               + "price numeric(12,2) NOT NULL, "
               + "quantity int NOT NULL DEFAULT 0 )").execute();

        connection.prepareStatement("CREATE INDEX idx_products_name ON name (name)").execute();
    }

    @Override
    public void down(Connection connection) throws SQLException {
        connection.prepareStatement("DROP TABLE products CASCADE").execute();
    }
}
