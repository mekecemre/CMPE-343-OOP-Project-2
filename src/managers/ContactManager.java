package managers;

import models.Contact;
import models.SearchCriteria;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ContactManager class handling all contact-related database operations.
 * Provides methods for CRUD operations, searching, sorting, and statistics.
 * Demonstrates abstraction, encapsulation, and data management best practices.
 *
 * @author CMPE-343 Project Group
 * @version 1.0
 */
public class ContactManager {
    private DatabaseManager dbManager;

    /**
     * Constructor for ContactManager.
     *
     * @param dbManager The DatabaseManager instance for database operations
     */
    public ContactManager(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    /**
     * Extracts a Contact object from a ResultSet.
     *
     * @param rs The ResultSet containing contact data
     * @return Contact object
     * @throws SQLException If data extraction fails
     */
    private Contact extractContactFromResultSet(ResultSet rs) throws SQLException {
        Contact contact = new Contact();
        contact.setContactId(rs.getInt("contact_id"));
        contact.setFirstName(rs.getString("first_name"));
        contact.setMiddleName(rs.getString("middle_name"));
        contact.setLastName(rs.getString("last_name"));
        contact.setNickname(rs.getString("nickname"));
        contact.setPhonePrimary(rs.getString("phone_primary"));
        contact.setPhoneSecondary(rs.getString("phone_secondary"));
        contact.setEmail(rs.getString("email"));
        contact.setLinkedinUrl(rs.getString("linkedin_url"));
        contact.setBirthDate(rs.getDate("birth_date"));
        contact.setCreatedAt(rs.getTimestamp("created_at"));
        contact.setUpdatedAt(rs.getTimestamp("updated_at"));
        return contact;
    }

    /**
     * Retrieves all contacts from the database.
     *
     * @return List of all contacts
     */
    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT * FROM contacts ORDER BY contact_id";

        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                contacts.add(extractContactFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving contacts: " + e.getMessage());
        }

        return contacts;
    }

    /**
     * Retrieves a contact by ID.
     *
     * @param contactId The contact ID
     * @return Contact object if found, null otherwise
     */
    public Contact getContactById(int contactId) {
        String sql = "SELECT * FROM contacts WHERE contact_id = ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, contactId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractContactFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving contact: " + e.getMessage());
        }

        return null;
    }

    /**
     * Adds a new contact to the database.
     *
     * @param contact The Contact object to add
     * @return The ID of the newly added contact, or -1 if failed
     */
    public int addContact(Contact contact) {
        String sql = "INSERT INTO contacts (first_name, middle_name, last_name, nickname, " +
                     "phone_primary, phone_secondary, email, linkedin_url, birth_date) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, contact.getFirstName());
            pstmt.setString(2, contact.getMiddleName());
            pstmt.setString(3, contact.getLastName());
            pstmt.setString(4, contact.getNickname());
            pstmt.setString(5, contact.getPhonePrimary());
            pstmt.setString(6, contact.getPhoneSecondary());
            pstmt.setString(7, contact.getEmail());
            pstmt.setString(8, contact.getLinkedinUrl());
            pstmt.setDate(9, contact.getBirthDate());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error adding contact: " + e.getMessage());
        }

        return -1;
    }

    /**
     * Updates an existing contact in the database.
     *
     * @param contact The Contact object with updated information
     * @return true if update successful, false otherwise
     */
    public boolean updateContact(Contact contact) {
        String sql = "UPDATE contacts SET first_name = ?, middle_name = ?, last_name = ?, " +
                     "nickname = ?, phone_primary = ?, phone_secondary = ?, email = ?, " +
                     "linkedin_url = ?, birth_date = ? WHERE contact_id = ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, contact.getFirstName());
            pstmt.setString(2, contact.getMiddleName());
            pstmt.setString(3, contact.getLastName());
            pstmt.setString(4, contact.getNickname());
            pstmt.setString(5, contact.getPhonePrimary());
            pstmt.setString(6, contact.getPhoneSecondary());
            pstmt.setString(7, contact.getEmail());
            pstmt.setString(8, contact.getLinkedinUrl());
            pstmt.setDate(9, contact.getBirthDate());
            pstmt.setInt(10, contact.getContactId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating contact: " + e.getMessage());
            return false;
        }
    }

    /**
     * Deletes a contact from the database.
     *
     * @param contactId The ID of the contact to delete
     * @return true if deletion successful, false otherwise
     */
    public boolean deleteContact(int contactId) {
        String sql = "DELETE FROM contacts WHERE contact_id = ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, contactId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting contact: " + e.getMessage());
            return false;
        }
    }

    /**
     * Deletes multiple contacts from the database.
     *
     * @param contactIds List of contact IDs to delete
     * @return The number of contacts successfully deleted
     */
    public int deleteContacts(List<Integer> contactIds) {
        if (contactIds == null || contactIds.isEmpty()) {
            return 0;
        }

        int deletedCount = 0;
        for (int contactId : contactIds) {
            if (deleteContact(contactId)) {
                deletedCount++;
            }
        }
        return deletedCount;
    }

    /**
     * Searches contacts by a single field with exact or partial match.
     *
     * @param fieldName The field name to search
     * @param value The value to search for
     * @param exactMatch true for exact match, false for partial match
     * @return List of matching contacts
     */
    public List<Contact> searchByField(String fieldName, String value, boolean exactMatch) {
        List<Contact> contacts = new ArrayList<>();
        String sql;

        if (exactMatch) {
            sql = "SELECT * FROM contacts WHERE " + fieldName + " = ?";
        } else {
            sql = "SELECT * FROM contacts WHERE " + fieldName + " LIKE ?";
        }

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (exactMatch) {
                pstmt.setString(1, value);
            } else {
                pstmt.setString(1, "%" + value + "%");
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                contacts.add(extractContactFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error searching contacts: " + e.getMessage());
        }

        return contacts;
    }

    /**
     * Searches contacts using flexible search criteria with multiple fields.
     *
     * @param criteria The SearchCriteria object containing search parameters
     * @return List of matching contacts
     */
    public List<Contact> searchByCriteria(SearchCriteria criteria) {
        List<Contact> contacts = new ArrayList<>();

        if (!criteria.hasCriteria()) {
            return getAllContacts();
        }

        String whereClause = criteria.toSQLWhereClause();
        String sql = "SELECT * FROM contacts WHERE " + whereClause;

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            List<String> values = criteria.getParameterValues();
            for (int i = 0; i < values.size(); i++) {
                pstmt.setString(i + 1, values.get(i));
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                contacts.add(extractContactFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error searching contacts with criteria: " + e.getMessage());
        }

        return contacts;
    }

    /**
     * Sorts a list of contacts by a specified field in ascending or descending order.
     *
     * @param contacts The list of contacts to sort
     * @param fieldName The field name to sort by
     * @param ascending true for ascending order, false for descending
     * @return Sorted list of contacts
     */
    public List<Contact> sortContacts(List<Contact> contacts, String fieldName, boolean ascending) {
        if (contacts == null || contacts.isEmpty()) {
            return contacts;
        }

        contacts.sort((c1, c2) -> {
            try {
                String value1 = getFieldValue(c1, fieldName);
                String value2 = getFieldValue(c2, fieldName);

                int comparison;
                if (value1 == null && value2 == null) {
                    comparison = 0;
                } else if (value1 == null) {
                    comparison = 1;
                } else if (value2 == null) {
                    comparison = -1;
                } else {
                    comparison = value1.compareTo(value2);
                }

                return ascending ? comparison : -comparison;
            } catch (Exception e) {
                return 0;
            }
        });

        return contacts;
    }

    /**
     * Gets the value of a specified field from a Contact object.
     *
     * @param contact The Contact object
     * @param fieldName The field name
     * @return The field value as a String
     */
    private String getFieldValue(Contact contact, String fieldName) {
        switch (fieldName.toLowerCase()) {
            case "contact_id":
                return String.valueOf(contact.getContactId());
            case "first_name":
                return contact.getFirstName();
            case "middle_name":
                return contact.getMiddleName();
            case "last_name":
                return contact.getLastName();
            case "nickname":
                return contact.getNickname();
            case "phone_primary":
                return contact.getPhonePrimary();
            case "phone_secondary":
                return contact.getPhoneSecondary();
            case "email":
                return contact.getEmail();
            case "linkedin_url":
                return contact.getLinkedinUrl();
            case "birth_date":
                return contact.getBirthDate() != null ? contact.getBirthDate().toString() : null;
            default:
                return "";
        }
    }

    /**
     * Gets statistical information about contacts.
     *
     * @return Map containing various statistics
     */
    public Map<String, Object> getContactStatistics() {
        Map<String, Object> stats = new HashMap<>();

        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement()) {

            // Total contacts
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM contacts");
            if (rs.next()) {
                stats.put("totalContacts", rs.getInt(1));
            }

            // Contacts with LinkedIn
            rs = stmt.executeQuery("SELECT COUNT(*) FROM contacts WHERE linkedin_url IS NOT NULL AND linkedin_url != ''");
            if (rs.next()) {
                stats.put("contactsWithLinkedIn", rs.getInt(1));
            }

            // Contacts without LinkedIn
            rs = stmt.executeQuery("SELECT COUNT(*) FROM contacts WHERE linkedin_url IS NULL OR linkedin_url = ''");
            if (rs.next()) {
                stats.put("contactsWithoutLinkedIn", rs.getInt(1));
            }

            // Most common first names
            rs = stmt.executeQuery("SELECT first_name, COUNT(*) as count FROM contacts GROUP BY first_name ORDER BY count DESC LIMIT 5");
            List<Map<String, Object>> commonFirstNames = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> nameInfo = new HashMap<>();
                nameInfo.put("name", rs.getString("first_name"));
                nameInfo.put("count", rs.getInt("count"));
                commonFirstNames.add(nameInfo);
            }
            stats.put("commonFirstNames", commonFirstNames);

            // Most common last names
            rs = stmt.executeQuery("SELECT last_name, COUNT(*) as count FROM contacts GROUP BY last_name ORDER BY count DESC LIMIT 5");
            List<Map<String, Object>> commonLastNames = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> nameInfo = new HashMap<>();
                nameInfo.put("name", rs.getString("last_name"));
                nameInfo.put("count", rs.getInt("count"));
                commonLastNames.add(nameInfo);
            }
            stats.put("commonLastNames", commonLastNames);

            // Youngest contact
            rs = stmt.executeQuery("SELECT first_name, last_name, birth_date FROM contacts ORDER BY birth_date DESC LIMIT 1");
            if (rs.next()) {
                stats.put("youngestContact", rs.getString("first_name") + " " + rs.getString("last_name"));
                stats.put("youngestBirthDate", rs.getDate("birth_date"));
            }

            // Oldest contact
            rs = stmt.executeQuery("SELECT first_name, last_name, birth_date FROM contacts ORDER BY birth_date ASC LIMIT 1");
            if (rs.next()) {
                stats.put("oldestContact", rs.getString("first_name") + " " + rs.getString("last_name"));
                stats.put("oldestBirthDate", rs.getDate("birth_date"));
            }

            // Average age
            rs = stmt.executeQuery("SELECT AVG(TIMESTAMPDIFF(YEAR, birth_date, CURDATE())) as avg_age FROM contacts");
            if (rs.next()) {
                stats.put("averageAge", rs.getDouble("avg_age"));
            }

            // Contacts by birth month
            rs = stmt.executeQuery("SELECT MONTH(birth_date) as month, COUNT(*) as count FROM contacts GROUP BY MONTH(birth_date) ORDER BY count DESC");
            Map<Integer, Integer> birthMonths = new HashMap<>();
            while (rs.next()) {
                birthMonths.put(rs.getInt("month"), rs.getInt("count"));
            }
            stats.put("birthMonths", birthMonths);

            // Contacts with secondary phone
            rs = stmt.executeQuery("SELECT COUNT(*) FROM contacts WHERE phone_secondary IS NOT NULL AND phone_secondary != ''");
            if (rs.next()) {
                stats.put("contactsWithSecondaryPhone", rs.getInt(1));
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving statistics: " + e.getMessage());
        }

        return stats;
    }

    /**
     * Gets the total count of contacts in the database.
     *
     * @return The total number of contacts
     */
    public int getContactCount() {
        String sql = "SELECT COUNT(*) FROM contacts";

        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error counting contacts: " + e.getMessage());
        }

        return 0;
    }

    /**
     * Checks if an email already exists in the database.
     *
     * @param email The email to check
     * @return true if email exists, false otherwise
     */
    public boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM contacts WHERE email = ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error checking email: " + e.getMessage());
        }

        return false;
    }

    /**
     * Checks if an email exists for a different contact (used during updates).
     *
     * @param email The email to check
     * @param excludeContactId The contact ID to exclude from the check
     * @return true if email exists for another contact, false otherwise
     */
    public boolean emailExistsForOtherContact(String email, int excludeContactId) {
        String sql = "SELECT COUNT(*) FROM contacts WHERE email = ? AND contact_id != ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setInt(2, excludeContactId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error checking email: " + e.getMessage());
        }

        return false;
    }
}
