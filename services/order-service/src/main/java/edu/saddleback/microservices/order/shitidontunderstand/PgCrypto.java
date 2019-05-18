package edu.saddleback.microservices.order.shitidontunderstand;

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
