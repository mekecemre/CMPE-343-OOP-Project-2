package models;

import java.sql.Timestamp;

/**
 * User class representing a user in the contact management system.
 * Demonstrates encapsulation by keeping fields private and providing controlled access through getters and setters.
 * This is an entity class that maps to the users table in the database.
 *
 * @author CMPE-343 Project Group
 * @version 1.0
 */
public class User {
    private int userId;
    private String username;
    private String passwordHash;
    private String name;
    private String surname;
    private String role;
    private Timestamp createdAt;

    /**
     * Default constructor for User.
     */
    public User() {
    }

    /**
     * Parameterized constructor for User.
     *
     * @param userId      Unique identifier for the user
     * @param username    Username for authentication
     * @param passwordHash Hashed password for security
     * @param name        First name of the user
     * @param surname     Last name of the user
     * @param role        Role of the user (Tester, Junior Developer, Senior Developer, Manager)
     * @param createdAt   Timestamp when the user was created
     */
    public User(int userId, String username, String passwordHash, String name, String surname, String role, Timestamp createdAt) {
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.createdAt = createdAt;
    }

    /**
     * Gets the user ID.
     *
     * @return The unique identifier of the user
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user ID.
     *
     * @param userId The unique identifier to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the username.
     *
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username The username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password hash.
     * Note: This should only be used for authentication purposes.
     *
     * @return The hashed password
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Sets the password hash.
     * Note: This should only be called with already hashed passwords.
     *
     * @param passwordHash The hashed password to set
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * Gets the first name of the user.
     *
     * @return The user's first name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the first name of the user.
     *
     * @param name The first name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the surname of the user.
     *
     * @return The user's surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname of the user.
     *
     * @param surname The surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the role of the user.
     *
     * @return The user's role
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role of the user.
     *
     * @param role The role to set (Tester, Junior Developer, Senior Developer, Manager)
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Gets the timestamp when the user was created.
     *
     * @return The creation timestamp
     */
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the creation timestamp.
     *
     * @param createdAt The creation timestamp to set
     */
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets the full name of the user (name + surname).
     *
     * @return The full name
     */
    public String getFullName() {
        return name + " " + surname;
    }

    /**
     * Returns a string representation of the User object.
     *
     * @return String representation containing user details
     */
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", role='" + role + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    /**
     * Checks if two User objects are equal based on userId.
     *
     * @param obj The object to compare with
     * @return true if the users have the same userId, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return userId == user.userId;
    }

    /**
     * Generates a hash code for the User object.
     *
     * @return The hash code based on userId
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(userId);
    }
}
