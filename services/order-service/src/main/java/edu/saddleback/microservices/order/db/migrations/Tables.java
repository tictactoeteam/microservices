package edu.saddleback.microservices.order.db.migrations;

import java.sql.Connection;
import java.sql.SQLException;

import edu.saddleback.microservices.order.db.Migration;

public class Tables implements Migration {
    @Override
    public void up(Connection connection) throws SQLException {
        connection.prepareStatement("CREATE TABLE users " +
                "(id uuid PRIMARY KEY NOT NULL DEFAULT gen_random_uuid(), " +
                "username text UNIQUE NOT NULL, " +
                "email text UNIQUE NOT NULL)").execute();
        connection.prepareStatement("CREATE INDEX idx_users_username ON users (username)").execute();
        connection.prepareStatement("CREATE INDEX idx_users_email ON users (email)").execute();

        connection.prepareStatement("CREATE TABLE products " +
                "(id uuid PRIMARY KEY NOT NULL DEFAULT gen_random_uuid(), " +
                "price numeric(12,2) NOT NULL)").execute();

        connection.prepareStatement("CREATE TYPE order_status AS ENUM ('UNPAID', 'PENDING', 'PAID')").execute();

        connection.prepareStatement("CREATE TYPE cart_item AS " +
                "(product uuid, " +
                "quantity int )").execute();

        connection.prepareStatement("CREATE TABLE orders (" +
                "id uuid PRIMARY KEY NOT NULL DEFAULT gen_random_uuid(), " +
                "status order_status NOT NULL, " +
                "customer_id uuid NOT NULL REFERENCES users(id), " +
                "cart cart_item[] NOT NULL, " +
                "coin text NOT NULL, " +
                "address text NOT NULL, " +
                "price bigint NOT NULL, " +
                "timestamp timestamptz DEFAULT now())").execute();
        connection.prepareStatement("CREATE INDEX idx_orders_status ON orders(status)").execute();
        connection.prepareStatement("CREATE INDEX idx_orders_timestamp ON orders(timestamp)").execute();
    }

    @Override
    public void down(Connection connection) throws SQLException {
        connection.prepareStatement("DROP TABLE users CASCADE").execute();
        connection.prepareStatement("DROP TABLE products CASCADE").execute();
        connection.prepareStatement("DROP TYPE order_status CASCADE").execute();
        connection.prepareStatement("DROP TYPE cart_item CASCADE").execute();
        connection.prepareStatement("DROP TABLE orders CASCADE").execute();
    }
}
