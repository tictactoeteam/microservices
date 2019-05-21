package edu.saddleback.microservices.product.db.migrations;

import java.sql.Connection;
import java.sql.SQLException;

import edu.saddleback.microservices.product.db.Migration;


// NOTE - REQUIRES SUPERUSER PERM
public class Init implements Migration {
    String username = System.getenv("POSTGRES_USER");
    String password = System.getenv("POSTGRES_PASSWORD");

    @Override
    public void up(Connection connection) throws SQLException {
        connection.prepareStatement("CREATE ROLE product_perms").execute();
        connection.prepareStatement("CREATE USER " + username + " WITH PASSWORD '" + password + "'").execute();
        connection.prepareStatement("GRANT product_perms TO " + username).execute();
        connection.prepareStatement("CREATE DATABASE product OWNER " + username).execute();
    }

    @Override
    public void down(Connection connection) throws SQLException {
        connection.prepareStatement("DROP ROLE product_perms").execute();
        connection.prepareStatement("DROP USER " + username).execute();
        connection.prepareStatement("DROP DATABASE product");
    }
}
