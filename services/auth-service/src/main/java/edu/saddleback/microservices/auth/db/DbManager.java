package edu.saddleback.microservices.auth.db;

import edu.saddleback.microservices.auth.db.migrations.Init;
import edu.saddleback.microservices.auth.db.migrations.PgCrypto;
import edu.saddleback.microservices.auth.db.migrations.UserTable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DbManager {
    private static final Migration[] migrations = {
        new UserTable()
    };

    private static String url = System.getenv("POSTGRES_URL");
    private static String user = System.getenv("POSTGRES_USER");
    private static String password = System.getenv("POSTGRES_PASSWORD");

    public static Connection getConnection() {
        return createConnection(url, user, password);
    }

    public static void init() {
        String suUrl = System.getenv("POSTGRES_SU_URL");
        String superuser = System.getenv("POSTGRES_SUPERUSER");
        String suPassword = System.getenv("POSTGRES_SU_PASSWORD");

        try {
            Connection connection = createConnection(suUrl, superuser, suPassword);
            new Init().up(connection);

            Connection authConnection = createConnection(url, superuser, suPassword);
            new PgCrypto().up(authConnection);
        } catch (SQLException e) {
            System.out.println("Database auth already exists");
        }
    }

    private static Connection createConnection(String url, String user, String password) {
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);

        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(url, props);
        } catch (SQLException e) {
            System.err.println("Failed to connect to database");
            e.printStackTrace();
            System.exit(1);
        } catch (ClassNotFoundException e) {
            System.err.println("Missing postgres driver");
            System.exit(1);
        }

        return null;
    }

    public static void migrate() {
        Connection connection = getConnection();

        for (int i = lastMigration() + 1; i < migrations.length; i++) {
            System.out.println("Running migration #" + i);
            try {
                connection.prepareStatement("BEGIN TRANSACTION").execute();
                migrations[i].up(connection);

                if (i == 0) {
                    connection.prepareStatement("INSERT INTO migrations (last_migration) VALUES (0)")
                            .execute();
                } else {
                    connection.prepareStatement("UPDATE migrations SET last_migration = last_migration + 1")
                            .execute();
                }

                connection.prepareStatement("COMMIT").execute();
            } catch (SQLException e) {
                System.err.println("Failed to run migration");
                e.printStackTrace();
                try {
                    connection.prepareStatement("ROLLBACK").execute();
                    System.out.println("Rolled back failed migration");
                } catch (SQLException e2) {
                    System.err.println("Failed to rollback bad migration");
                    e2.printStackTrace();
                    System.exit(1);
                }
            }
        }
    }

    private static int lastMigration() {
        Connection connection = getConnection();

        try {
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS migrations (last_migration int)").execute();

            ResultSet rs = connection.prepareStatement("SELECT last_migration FROM migrations").executeQuery();

            return rs.next() ? rs.getInt("last_migration") : -1;
        } catch (SQLException e) {
            return -1;
        }
    }
}
