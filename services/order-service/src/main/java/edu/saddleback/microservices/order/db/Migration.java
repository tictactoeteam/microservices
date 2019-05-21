package edu.saddleback.microservices.order.db;

import java.sql.Connection;
import java.sql.SQLException;

public interface Migration {
    void up(Connection connection) throws SQLException;

    void down(Connection connection) throws SQLException;
}
