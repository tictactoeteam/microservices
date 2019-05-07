package edu.saddleback.microservices.auth.db.migrations;

import edu.saddleback.microservices.auth.db.Migration;

import java.sql.Connection;
import java.sql.SQLException;

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
