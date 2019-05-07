package edu.saddleback.microservices.auth.db;

import java.sql.Connection;
import java.sql.SQLException;

public interface Migration {
    void up(Connection connection) throws SQLException;

    void down(Connection connection) throws SQLException;
}
