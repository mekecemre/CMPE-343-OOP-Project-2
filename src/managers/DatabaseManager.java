package managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DatabaseManager class handling database connections and operations.
 * Manages MySQL database connectivity and provides connection pooling.
 * Demonstrates encapsulation and resource management best practices.
 *
 * @author CMPE-343 Project Group
 * @version 1.0
 */
public class DatabaseManager {
    // Database connection parameters
    private static final String DB_URL = "jdbc:mysql://localhost:3306/contact_management";
    private static final String DB_USER = "myuser";
    private static final String DB_PASSWORD = "1234";

    private Connection connection;

    /**
     * Constructor that initializes the database connection.
     * Loads the MySQL JDBC driver and establishes a connection.
     *
     * @throws SQLException If connection cannot be established
     */
    public DatabaseManager() throws SQLException {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection with UTF-8 encoding for Turkish character support
            connection = DriverManager.getConnection(
                DB_URL + "?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC",
                DB_USER,
                DB_PASSWORD
            );

            System.out.println("Database connection established successfully.");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found: " + e.getMessage(), e);
        } catch (SQLException e) {
            throw new SQLException("Failed to connect to database: " + e.getMessage(), e);
        }
    }

    /**
     * Gets the active database connection.
     *
     * @return The database Connection object
     * @throws SQLException If connection is closed or invalid
     */
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            // Reconnect if connection is closed
            connection = DriverManager.getConnection(
                DB_URL + "?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC",
                DB_USER,
                DB_PASSWORD
            );
        }
        return connection;
    }

    /**
     * Tests if the database connection is valid.
     *
     * @return true if connection is valid, false otherwise
     */
    public boolean isConnectionValid() {
        try {
            return connection != null && !connection.isClosed() && connection.isValid(2);
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Closes the database connection.
     * Should be called when the application is shutting down.
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }

    /**
     * Executes a SQL update statement (INSERT, UPDATE, DELETE).
     *
     * @param sql The SQL statement to execute
     * @return The number of rows affected
     * @throws SQLException If the SQL statement fails
     */
    public int executeUpdate(String sql) throws SQLException {
        try (Statement stmt = getConnection().createStatement()) {
            return stmt.executeUpdate(sql);
        }
    }

    /**
     * Begins a database transaction.
     *
     * @throws SQLException If transaction cannot be started
     */
    public void beginTransaction() throws SQLException {
        getConnection().setAutoCommit(false);
    }

    /**
     * Commits the current database transaction.
     *
     * @throws SQLException If commit fails
     */
    public void commit() throws SQLException {
        getConnection().commit();
        getConnection().setAutoCommit(true);
    }

    /**
     * Rolls back the current database transaction.
     *
     * @throws SQLException If rollback fails
     */
    public void rollback() throws SQLException {
        try {
            getConnection().rollback();
            getConnection().setAutoCommit(true);
        } catch (SQLException e) {
            System.err.println("Error during rollback: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Creates the database schema if it doesn't exist.
     * Creates the users and contacts tables with appropriate constraints.
     *
     * @throws SQLException If schema creation fails
     */
    public void createSchemaIfNotExists() throws SQLException {
        String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
            "user_id INT PRIMARY KEY AUTO_INCREMENT, " +
            "username VARCHAR(50) UNIQUE NOT NULL, " +
            "password_hash VARCHAR(255) NOT NULL, " +
            "name VARCHAR(100) NOT NULL, " +
            "surname VARCHAR(100) NOT NULL, " +
            "role VARCHAR(50) NOT NULL, " +
            "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
            ") CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci";

        String createContactsTable = "CREATE TABLE IF NOT EXISTS contacts (" +
            "contact_id INT PRIMARY KEY AUTO_INCREMENT, " +
            "first_name VARCHAR(100) NOT NULL, " +
            "middle_name VARCHAR(100), " +
            "last_name VARCHAR(100) NOT NULL, " +
            "nickname VARCHAR(100), " +
            "phone_primary VARCHAR(20) NOT NULL, " +
            "phone_secondary VARCHAR(20), " +
            "email VARCHAR(150) NOT NULL, " +
            "linkedin_url VARCHAR(255), " +
            "birth_date DATE NOT NULL, " +
            "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
            "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" +
            ") CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci";

        try (Statement stmt = getConnection().createStatement()) {
            stmt.executeUpdate(createUsersTable);
            stmt.executeUpdate(createContactsTable);
            System.out.println("Database schema verified/created successfully.");
        }
    }

    /**
     * Gets database metadata information.
     *
     * @return String containing database information
     */
    public String getDatabaseInfo() {
        try {
            return "Database: " + connection.getMetaData().getDatabaseProductName() +
                   " " + connection.getMetaData().getDatabaseProductVersion();
        } catch (SQLException e) {
            return "Unable to retrieve database information";
        }
    }
}
