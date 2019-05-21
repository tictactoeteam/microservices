package edu.saddleback.microservices.product.db.migrations;

import java.sql.Connection;
import java.sql.SQLException;

import edu.saddleback.microservices.product.db.Migration;


public class PgCrypto implements Migration {
    @Override
    public void up(Connection connection) throws SQLException {
        connection.prepareStatement("CREATE EXTENSION IF NOT EXISTS pgcrypto").execute();
    }

    @Override
    public void down(Connection connection) throws SQLException {
        connection.prepareStatement("DROP EXTENSION pgcrypto");
    }
}
