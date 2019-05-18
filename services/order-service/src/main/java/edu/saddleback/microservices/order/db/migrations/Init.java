package edu.saddleback.microservices.order.db.migrations;

import edu.saddleback.microservices.order.db.Migration;

import java.sql.Connection;
import java.sql.SQLException;

// NOTE - REQUIRES SUPERUSER PERM
public class Init implements Migration {
    String username = System.getenv("POSTGRES_USER");
    String password = System.getenv("POSTGRES_PASSWORD");

    @Override
    public void up(Connection connection) throws SQLException {
        connection.prepareStatement("CREATE ROLE order_perms").execute();
        connection.prepareStatement("CREATE USER " + username + " WITH PASSWORD '" + password + "'").execute();
        connection.prepareStatement("GRANT order_perms TO " + username).execute();
        connection.prepareStatement("CREATE DATABASE order OWNER " + username).execute();
    }

    @Override
    public void down(Connection connection) throws SQLException {
        connection.prepareStatement("DROP ROLE order_perms").execute();
        connection.prepareStatement("DROP USER " + username).execute();
        connection.prepareStatement("DROP DATABASE order");
    }
}
