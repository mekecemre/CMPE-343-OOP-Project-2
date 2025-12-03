package managers;

import models.User;
import utils.PasswordHasher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * UserManager class handling all user-related database operations.
 * Provides methods for user authentication, CRUD operations, and user management.
 * Demonstrates abstraction and encapsulation principles.
 *
 * @author CMPE-343 Project Group
 * @version 1.0
 */
public class UserManager {
    private DatabaseManager dbManager;

    /**
     * Constructor for UserManager.
     *
     * @param dbManager The DatabaseManager instance for database operations
     */
    public UserManager(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    /**
     * Authenticates a user with username and password.
     * Verifies the password against the stored hash.
     *
     * @param username The username
     * @param password The plain text password
     * @return User object if authentication successful, null otherwise
     */
    public User authenticate(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password_hash");

                // Try new hash format first (with salt)
                if (PasswordHasher.verifyPassword(password, storedHash)) {
                    return extractUserFromResultSet(rs);
                }

                // Fallback to simple hash for backward compatibility
                if (PasswordHasher.verifySimpleHash(password, storedHash)) {
                    return extractUserFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Authentication error: " + e.getMessage());
        }

        return null;
    }

    /**
     * Extracts a User object from a ResultSet.
     *
     * @param rs The ResultSet containing user data
     * @return User object
     * @throws SQLException If data extraction fails
     */
    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setName(rs.getString("name"));
        user.setSurname(rs.getString("surname"));
        user.setRole(rs.getString("role"));
        user.setCreatedAt(rs.getTimestamp("created_at"));
        return user;
    }

    /**
     * Retrieves a user by user ID.
     *
     * @param userId The user ID
     * @return User object if found, null otherwise
     */
    public User getUserById(int userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractUserFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving user: " + e.getMessage());
        }

        return null;
    }

    /**
     * Retrieves a user by username.
     *
     * @param username The username
     * @return User object if found, null otherwise
     */
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractUserFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving user: " + e.getMessage());
        }

        return null;
    }

    /**
     * Retrieves all users from the database.
     *
     * @return List of all users
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY user_id";

        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                users.add(extractUserFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving users: " + e.getMessage());
        }

        return users;
    }

    /**
     * Adds a new user to the database.
     *
     * @param username The username
     * @param password The plain text password (will be hashed)
     * @param name The first name
     * @param surname The surname
     * @param role The role
     * @return true if user added successfully, false otherwise
     */
    public boolean addUser(String username, String password, String name, String surname, String role) {
        String sql = "INSERT INTO users (username, password_hash, name, surname, role) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String hashedPassword = PasswordHasher.hashPassword(password);

            pstmt.setString(1, username);
            pstmt.setString(2, hashedPassword);
            pstmt.setString(3, name);
            pstmt.setString(4, surname);
            pstmt.setString(5, role);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error adding user: " + e.getMessage());
            return false;
        }
    }

    /**
     * Updates an existing user's information.
     *
     * @param userId The user ID
     * @param username The new username
     * @param name The new first name
     * @param surname The new surname
     * @param role The new role
     * @return true if update successful, false otherwise
     */
    public boolean updateUser(int userId, String username, String name, String surname, String role) {
        String sql = "UPDATE users SET username = ?, name = ?, surname = ?, role = ? WHERE user_id = ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, name);
            pstmt.setString(3, surname);
            pstmt.setString(4, role);
            pstmt.setInt(5, userId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
            return false;
        }
    }

    /**
     * Changes a user's password.
     *
     * @param userId The user ID
     * @param newPassword The new plain text password (will be hashed)
     * @return true if password changed successfully, false otherwise
     */
    public boolean changePassword(int userId, String newPassword) {
        String sql = "UPDATE users SET password_hash = ? WHERE user_id = ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String hashedPassword = PasswordHasher.hashPassword(newPassword);

            pstmt.setString(1, hashedPassword);
            pstmt.setInt(2, userId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error changing password: " + e.getMessage());
            return false;
        }
    }

    /**
     * Deletes a user from the database.
     *
     * @param userId The user ID to delete
     * @return true if deletion successful, false otherwise
     */
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
            return false;
        }
    }

    /**
     * Checks if a username already exists in the database.
     *
     * @param username The username to check
     * @return true if username exists, false otherwise
     */
    public boolean usernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error checking username: " + e.getMessage());
        }

        return false;
    }

    /**
     * Checks if a username exists for a different user (used during updates).
     *
     * @param username The username to check
     * @param excludeUserId The user ID to exclude from the check
     * @return true if username exists for another user, false otherwise
     */
    public boolean usernameExistsForOtherUser(String username, int excludeUserId) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ? AND user_id != ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setInt(2, excludeUserId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error checking username: " + e.getMessage());
        }

        return false;
    }

    /**
     * Gets the total count of users in the database.
     *
     * @return The total number of users
     */
    public int getUserCount() {
        String sql = "SELECT COUNT(*) FROM users";

        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error counting users: " + e.getMessage());
        }

        return 0;
    }

    /**
     * Gets users by role.
     *
     * @param role The role to filter by
     * @return List of users with the specified role
     */
    public List<User> getUsersByRole(String role) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE role = ? ORDER BY user_id";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, role);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                users.add(extractUserFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving users by role: " + e.getMessage());
        }

        return users;
    }
}
