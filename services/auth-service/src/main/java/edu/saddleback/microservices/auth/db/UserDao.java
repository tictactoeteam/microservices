package edu.saddleback.microservices.auth.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import edu.saddleback.microservices.auth.models.User;

public class UserDao {
    private static Connection connection = DbManager.getConnection();

    public static void insertUser(User user) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO users (username, email, password, disabled, created_at, last_login)" +
                        "VALUES (?,?,?,?,?,?)");

        Date createdAt = user.getCreatedDate() == null ? null : new Date(user.getCreatedDate().getTime());
        Date lastLogin = user.getLastLogin() == null ? null : new Date(user.getLastLogin().getTime());

        statement.setString(1, user.getUsername());
        statement.setString(2, user.getEmail());
        statement.setString(3, user.getHashedPassword());
        statement.setBoolean(4, user.isDisabled());
        statement.setDate(5, createdAt);
        statement.setDate(6, lastLogin);

        int updatedRows = statement.executeUpdate();

        if (updatedRows == 0) {
            throw new SQLException("User violated constraints");
        }
    }

    public static User getUserById(UUID id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM users WHERE id=?"
        );
        statement.setString(1, id.toString());

        ResultSet rs = statement.executeQuery();

        return rs.next() ? extractUser(rs) : null;
    }

    public static User getUserByName(String username) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM users WHERE username=?"
        );
        statement.setString(1, username);

        ResultSet rs = statement.executeQuery();

        return rs.next() ? extractUser(rs) : null;
    }

    public static User getUserByEmail(String email) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM users WHERE email=?"
        );
        statement.setString(1, email);

        ResultSet rs = statement.executeQuery();

        return rs.next() ? extractUser(rs) : null;
    }

    public static User getUserByNameOrEmail(String identifier) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM users WHERE username=? OR email=?"
        );
        statement.setString(1, identifier);
        statement.setString(2, identifier);

        ResultSet rs = statement.executeQuery();

        return rs.next() ? extractUser(rs) : null;
    }

    private static User extractUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(UUID.fromString(rs.getString("id")));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setHashedPassword(rs.getString("password"));
        user.setDisabled(rs.getBoolean("disabled"));
        user.setCreatedDate(rs.getDate("created_at"));
        user.setLastLogin(rs.getDate("last_login"));

        return user;
    }
}
