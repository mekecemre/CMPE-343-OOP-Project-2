package models;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Contact class representing a contact record in the contact management system.
 * Demonstrates encapsulation by keeping fields private and providing controlled access through getters and setters.
 * This is an entity class that maps to the contacts table in the database.
 *
 * @author CMPE-343 Project Group
 * @version 1.0
 */
public class Contact {
    private int contactId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String nickname;
    private String phonePrimary;
    private String phoneSecondary;
    private String email;
    private String linkedinUrl;
    private Date birthDate;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    /**
     * Default constructor for Contact.
     */
    public Contact() {
    }

    /**
     * Parameterized constructor for Contact.
     *
     * @param contactId      Unique identifier for the contact
     * @param firstName      First name of the contact
     * @param middleName     Middle name of the contact (optional)
     * @param lastName       Last name of the contact
     * @param nickname       Nickname of the contact
     * @param phonePrimary   Primary phone number
     * @param phoneSecondary Secondary phone number (optional)
     * @param email          Email address
     * @param linkedinUrl    LinkedIn profile URL (optional)
     * @param birthDate      Birth date of the contact
     * @param createdAt      Timestamp when the contact was created
     * @param updatedAt      Timestamp when the contact was last updated
     */
    public Contact(int contactId, String firstName, String middleName, String lastName,
                   String nickname, String phonePrimary, String phoneSecondary,
                   String email, String linkedinUrl, Date birthDate,
                   Timestamp createdAt, Timestamp updatedAt) {
        this.contactId = contactId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.phonePrimary = phonePrimary;
        this.phoneSecondary = phoneSecondary;
        this.email = email;
        this.linkedinUrl = linkedinUrl;
        this.birthDate = birthDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Gets the contact ID.
     *
     * @return The unique identifier of the contact
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Sets the contact ID.
     *
     * @param contactId The unique identifier to set
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Gets the first name of the contact.
     *
     * @return The first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the contact.
     *
     * @param firstName The first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the middle name of the contact.
     *
     * @return The middle name (may be null)
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Sets the middle name of the contact.
     *
     * @param middleName The middle name to set
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * Gets the last name of the contact.
     *
     * @return The last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the contact.
     *
     * @param lastName The last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the nickname of the contact.
     *
     * @return The nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Sets the nickname of the contact.
     *
     * @param nickname The nickname to set
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Gets the primary phone number.
     *
     * @return The primary phone number
     */
    public String getPhonePrimary() {
        return phonePrimary;
    }

    /**
     * Sets the primary phone number.
     *
     * @param phonePrimary The primary phone number to set
     */
    public void setPhonePrimary(String phonePrimary) {
        this.phonePrimary = phonePrimary;
    }

    /**
     * Gets the secondary phone number.
     *
     * @return The secondary phone number (may be null)
     */
    public String getPhoneSecondary() {
        return phoneSecondary;
    }

    /**
     * Sets the secondary phone number.
     *
     * @param phoneSecondary The secondary phone number to set
     */
    public void setPhoneSecondary(String phoneSecondary) {
        this.phoneSecondary = phoneSecondary;
    }

    /**
     * Gets the email address.
     *
     * @return The email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address.
     *
     * @param email The email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the LinkedIn profile URL.
     *
     * @return The LinkedIn URL (may be null)
     */
    public String getLinkedinUrl() {
        return linkedinUrl;
    }

    /**
     * Sets the LinkedIn profile URL.
     *
     * @param linkedinUrl The LinkedIn URL to set
     */
    public void setLinkedinUrl(String linkedinUrl) {
        this.linkedinUrl = linkedinUrl;
    }

    /**
     * Gets the birth date.
     *
     * @return The birth date
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * Sets the birth date.
     *
     * @param birthDate The birth date to set
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Gets the creation timestamp.
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
     * Gets the last update timestamp.
     *
     * @return The last update timestamp
     */
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets the last update timestamp.
     *
     * @param updatedAt The last update timestamp to set
     */
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Gets the full name of the contact (firstName + middleName + lastName).
     *
     * @return The full name
     */
    public String getFullName() {
        StringBuilder fullName = new StringBuilder(firstName);
        if (middleName != null && !middleName.isEmpty()) {
            fullName.append(" ").append(middleName);
        }
        fullName.append(" ").append(lastName);
        return fullName.toString();
    }

    /**
     * Returns a string representation of the Contact object.
     *
     * @return String representation containing contact details
     */
    @Override
    public String toString() {
        return "Contact{" +
                "contactId=" + contactId +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nickname='" + nickname + '\'' +
                ", phonePrimary='" + phonePrimary + '\'' +
                ", phoneSecondary='" + phoneSecondary + '\'' +
                ", email='" + email + '\'' +
                ", linkedinUrl='" + linkedinUrl + '\'' +
                ", birthDate=" + birthDate +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    /**
     * Checks if two Contact objects are equal based on contactId.
     *
     * @param obj The object to compare with
     * @return true if the contacts have the same contactId, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Contact contact = (Contact) obj;
        return contactId == contact.contactId;
    }

    /**
     * Generates a hash code for the Contact object.
     *
     * @return The hash code based on contactId
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(contactId);
    }
}
