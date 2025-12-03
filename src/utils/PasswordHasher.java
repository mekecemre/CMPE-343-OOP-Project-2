package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * PasswordHasher class providing secure password hashing functionality.
 * Uses SHA-256 algorithm with salt for enhanced security.
 * Demonstrates best practices for password storage and verification.
 *
 * @author CMPE-343 Project Group
 * @version 1.0
 */
public class PasswordHasher {
    private static final String ALGORITHM = "SHA-256";
    private static final int SALT_LENGTH = 16;

    /**
     * Private constructor to prevent instantiation.
     */
    private PasswordHasher() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Generates a random salt for password hashing.
     *
     * @return A byte array containing the random salt
     */
    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    /**
     * Hashes a password with a given salt using SHA-256.
     *
     * @param password The plain text password to hash
     * @param salt The salt to use in hashing
     * @return The hashed password as a byte array
     * @throws NoSuchAlgorithmException If the SHA-256 algorithm is not available
     */
    private static byte[] hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(ALGORITHM);
        md.update(salt);
        return md.digest(password.getBytes());
    }

    /**
     * Hashes a password and returns the combined salt and hash as a Base64 encoded string.
     * The format is: Base64(salt) + ":" + Base64(hash)
     *
     * @param password The plain text password to hash
     * @return The salted and hashed password as a Base64 encoded string
     */
    public static String hashPassword(String password) {
        try {
            byte[] salt = generateSalt();
            byte[] hash = hashPassword(password, salt);

            String saltBase64 = Base64.getEncoder().encodeToString(salt);
            String hashBase64 = Base64.getEncoder().encodeToString(hash);

            return saltBase64 + ":" + hashBase64;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password: " + e.getMessage(), e);
        }
    }

    /**
     * Verifies a password against a stored hash.
     * Extracts the salt from the stored hash, hashes the provided password with it,
     * and compares the results.
     *
     * @param password The plain text password to verify
     * @param storedHash The stored hash in format "salt:hash" (Base64 encoded)
     * @return true if the password matches, false otherwise
     */
    public static boolean verifyPassword(String password, String storedHash) {
        try {
            String[] parts = storedHash.split(":");
            if (parts.length != 2) {
                return false;
            }

            byte[] salt = Base64.getDecoder().decode(parts[0]);
            byte[] storedHashBytes = Base64.getDecoder().decode(parts[1]);

            byte[] computedHash = hashPassword(password, salt);

            return MessageDigest.isEqual(computedHash, storedHashBytes);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Simple hash function for backward compatibility or testing.
     * Uses SHA-256 without salt. NOT RECOMMENDED for production use.
     *
     * @param password The plain text password to hash
     * @return The hashed password as a hex string
     */
    public static String simpleHash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password: " + e.getMessage(), e);
        }
    }

    /**
     * Verifies a password against a simple hash (without salt).
     * For backward compatibility or testing only.
     *
     * @param password The plain text password to verify
     * @param storedHash The stored simple hash
     * @return true if the password matches, false otherwise
     */
    public static boolean verifySimpleHash(String password, String storedHash) {
        String computedHash = simpleHash(password);
        return computedHash.equals(storedHash);
    }
}
