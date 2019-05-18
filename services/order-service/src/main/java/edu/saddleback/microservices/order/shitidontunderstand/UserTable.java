package edu.saddleback.microservices.order.shitidontunderstand;

import java.sql.Connection;
import java.sql.SQLException;

public class UserTable implements Migration {
    @Override
    public void up(Connection connection) throws SQLException {
        connection.prepareStatement("CREATE TABLE users "
                + "(id uuid PRIMARY KEY NOT NULL DEFAULT gen_random_uuid(), "
                + "username text UNIQUE NOT NULL, "
                + "email text UNIQUE NOT NULL)").execute();
        connection.prepareStatement("CREATE INDEX idx_users_username ON users (username)").execute();
        connection.prepareStatement("CREATE INDEX idx_users_email ON users (email)").execute();


        connection.prepareStatement("CREATE TABLE products "
                + "(id uuid PRIMARY KEY NOT NULL DEFAULT gen_random_uuid(), "
                + "product_name text UNIQUE NOT NULL"
                + "price_per_unit bigint NOT NULL)").execute();

        connection.prepareStatement("CREATE TABLE orders "
                + "(id uuid PRIMARY KEY NOT NULL DEFAULT gen_random_uuid(), "
                + "product uuid FOREIGN KEY NOT NULL, "
                + "status string NOT NULL, "
                + "customer_id FOREIGN KEY NOT NULL, "
                + "coin varchar NOT NULL, "
                + "address varchar NOT NULL, "
                + "timestamp timestamptz DEFAULT now()").execute();
    }

    @Override
    public void down(Connection connection) throws SQLException {
        connection.prepareStatement("DROP TABLE users CASCADE").execute();
        connection.prepareStatement("DROP TABLE status_enum CASCADE").execute();
        connection.prepareStatement("DROP TABLE products CASCADE").execute();
        connection.prepareStatement("DROP TABLE orders CASCADE").execute();
    }
}
