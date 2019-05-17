package edu.saddleback.microservices.auth.db.migrations;

import edu.saddleback.microservices.auth.db.Migration;

import java.sql.Connection;
import java.sql.SQLException;

public class UserTable implements Migration {
    @Override
    public void up(Connection connection) throws SQLException {
        connection.prepareStatement("CREATE TABLE users "
                + "(id uuid PRIMARY KEY NOT NULL DEFAULT gen_random_uuid(), "
                + "username text UNIQUE NOT NULL, "
                + "email text UNIQUE NOT NULL, "
                + "password text NOT NULL,"
                + "disabled boolean NOT NULL DEFAULT FALSE, "
                + "created_at timestamptz NOT NULL DEFAULT now(), "
                + "last_login timestamptz)").execute();
        connection.prepareStatement("CREATE INDEX idx_users_username ON users (username)").execute();
        connection.prepareStatement("CREATE INDEX idx_users_email ON users (email)").execute();
        connection.prepareStatement("CREATE INDEX idx_users_disabled ON users (disabled)").execute();
    }

    @Override
    public void down(Connection connection) throws SQLException {
        connection.prepareStatement("DROP TABLE users CASCADE").execute();
    }
}
