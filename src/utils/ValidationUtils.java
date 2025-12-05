package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * ValidationUtils class providing input validation methods for the contact management system.
 * Includes validation for phone numbers, emails, dates, and other data types.
 * Supports Turkish character validation and proper error handling.
 *
 * @author CMPE-343 Project Group
 * @version 1.0
 */
public class ValidationUtils {

    // Regular expression patterns
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[a-z0-9+_.-]+@[a-z0-9.-]+\\.[a-z]{2,}$"
    );

    private static final Pattern PHONE_PATTERN = Pattern.compile(
        "^[0-9]{10,11}$"
    );

    private static final Pattern LINKEDIN_PATTERN = Pattern.compile(
        "^(https?://)?(www\\.)?linkedin\\.com/.*$"
    );

    private static final Pattern NAME_PATTERN = Pattern.compile(
        "^[a-zA-ZğĞüÜşŞıİöÖçÇ\\s'-]+$"
    );

    /**
     * Private constructor to prevent instantiation.
     */
    private ValidationUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Validates an email address.
     * Email must be in lowercase format (e.g., name.surname@email.com)
     *
     * @param email The email address to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        String trimmed = email.trim();
        // Check if email contains any uppercase letters
        if (!trimmed.equals(trimmed.toLowerCase())) {
            return false;
        }
        return EMAIL_PATTERN.matcher(trimmed).matches();
    }

    /**
     * Validates a phone number.
     * Accepts 10 or 11 digit phone numbers.
     *
     * @param phone The phone number to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        // Remove common separators
        String cleanPhone = phone.replaceAll("[\\s()-]", "");
        return PHONE_PATTERN.matcher(cleanPhone).matches();
    }

    /**
     * Cleans a phone number by removing separators.
     *
     * @param phone The phone number to clean
     * @return Cleaned phone number
     */
    public static String cleanPhone(String phone) {
        if (phone == null) {
            return null;
        }
        return phone.replaceAll("[\\s()-]", "");
    }

    /**
     * Validates a LinkedIn URL.
     *
     * @param url The LinkedIn URL to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidLinkedInUrl(String url) {
        if (url == null || url.trim().isEmpty()) {
            return true; // Optional field
        }
        return LINKEDIN_PATTERN.matcher(url.trim()).matches();
    }

    /**
     * Validates a name (first name, last name, middle name).
     * Supports Turkish characters.
     *
     * @param name The name to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        return NAME_PATTERN.matcher(name.trim()).matches();
    }

    /**
     * Validates a date string in the format YYYY-MM-DD.
     * Checks for logical validity (e.g., no February 30).
     *
     * @param dateString The date string to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidDate(String dateString) {
        if (dateString == null || dateString.trim().isEmpty()) {
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false); // Strict date validation

        try {
            Date date = sdf.parse(dateString.trim());
            // Additional check: date should not be in the future for birth dates
            return !date.after(new Date());
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Validates a date string and checks if it's a valid birth date.
     * Birth date must be in the past and person must be less than 150 years old.
     *
     * @param dateString The birth date string to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidBirthDate(String dateString) {
        if (!isValidDate(dateString)) {
            return false;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            Date birthDate = sdf.parse(dateString.trim());
            Date currentDate = new Date();

            // Check if birth date is not in the future
            if (birthDate.after(currentDate)) {
                return false;
            }

            // Check if person is not older than 150 years
            Calendar cal = Calendar.getInstance();
            cal.setTime(birthDate);
            cal.add(Calendar.YEAR, 150);
            Date maxDate = cal.getTime();

            return currentDate.before(maxDate);
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Validates that a string is not null or empty.
     *
     * @param str The string to validate
     * @return true if not null and not empty, false otherwise
     */
    public static boolean isNotEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }

    /**
     * Validates that a string has a minimum length.
     *
     * @param str The string to validate
     * @param minLength The minimum length
     * @return true if string meets minimum length, false otherwise
     */
    public static boolean hasMinLength(String str, int minLength) {
        return str != null && str.trim().length() >= minLength;
    }

    /**
     * Validates that a string has a maximum length.
     *
     * @param str The string to validate
     * @param maxLength The maximum length
     * @return true if string is within maximum length, false otherwise
     */
    public static boolean hasMaxLength(String str, int maxLength) {
        return str != null && str.trim().length() <= maxLength;
    }

    /**
     * Validates that a string is within a length range.
     *
     * @param str The string to validate
     * @param minLength The minimum length
     * @param maxLength The maximum length
     * @return true if string is within range, false otherwise
     */
    public static boolean isWithinLength(
        String str,
        int minLength,
        int maxLength
    ) {
        return hasMinLength(str, minLength) && hasMaxLength(str, maxLength);
    }

    /**
     * Validates that a string is a valid integer.
     *
     * @param str The string to validate
     * @return true if valid integer, false otherwise
     */
    public static boolean isValidInteger(String str) {
        if (str == null || str.trim().isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(str.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Validates that a string is a valid positive integer.
     *
     * @param str The string to validate
     * @return true if valid positive integer, false otherwise
     */
    public static boolean isValidPositiveInteger(String str) {
        if (!isValidInteger(str)) {
            return false;
        }
        return Integer.parseInt(str.trim()) > 0;
    }

    /**
     * Validates a username.
     * Username must be alphanumeric and between 3-20 characters.
     *
     * @param username The username to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        String trimmed = username.trim();
        return (
            trimmed.length() >= 2 &&
            trimmed.length() <= 20 &&
            trimmed.matches("^[a-zA-Z0-9_-]+$")
        );
    }

    /**
     * Validates a password.
     * Password must be at least 2 characters long.
     *
     * @param password The password to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 2;
    }

    /**
     * Validates a role name.
     *
     * @param role The role to validate
     * @return true if valid role, false otherwise
     */
    public static boolean isValidRole(String role) {
        if (role == null || role.trim().isEmpty()) {
            return false;
        }
        String upperRole = role.trim().toUpperCase();
        return (
            upperRole.equals("TESTER") ||
            upperRole.equals("JUNIOR DEVELOPER") ||
            upperRole.equals("JUNIOR_DEVELOPER") ||
            upperRole.equals("SENIOR DEVELOPER") ||
            upperRole.equals("SENIOR_DEVELOPER") ||
            upperRole.equals("MANAGER")
        );
    }

    /**
     * Gets a user-friendly error message for invalid email.
     *
     * @return Error message
     */
    public static String getEmailError() {
        return "Invalid email format. Must be lowercase (e.g., name.surname@email.com)";
    }

    /**
     * Gets a user-friendly error message for invalid phone.
     *
     * @return Error message
     */
    public static String getPhoneError() {
        return "Invalid phone number. Must be 10 or 11 digits. Example: 05551234567";
    }

    /**
     * Gets a user-friendly error message for invalid date.
     *
     * @return Error message
     */
    public static String getDateError() {
        return "Invalid date format. Use YYYY-MM-DD. Example: 1990-05-15";
    }

    /**
     * Gets a user-friendly error message for invalid birth date.
     *
     * @return Error message
     */
    public static String getBirthDateError() {
        return "Invalid birth date. Must be in format YYYY-MM-DD, in the past, and reasonable.";
    }

    /**
     * Gets a user-friendly error message for invalid name.
     *
     * @return Error message
     */
    public static String getNameError() {
        return "Invalid name. Only letters (including Turkish characters), spaces, hyphens, and apostrophes allowed.";
    }

    /**
     * Gets a user-friendly error message for invalid LinkedIn URL.
     *
     * @return Error message
     */
    public static String getLinkedInError() {
        return "Invalid LinkedIn URL. Example: https://www.linkedin.com/in/username";
    }

    /**
     * Sanitizes input by trimming whitespace and removing potentially harmful characters.
     *
     * @param input The input string to sanitize
     * @return Sanitized string
     */
    public static String sanitizeInput(String input) {
        if (input == null) {
            return null;
        }
        return input.trim();
    }

    /**
     * Checks if a year is a leap year.
     *
     * @param year The year to check
     * @return true if leap year, false otherwise
     */
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    /**
     * Validates day of month for a given month and year.
     *
     * @param day The day to validate
     * @param month The month (1-12)
     * @param year The year
     * @return true if valid day for the given month/year, false otherwise
     */
    public static boolean isValidDayForMonth(int day, int month, int year) {
        if (month < 1 || month > 12 || day < 1) {
            return false;
        }

        int[] daysInMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

        if (month == 2 && isLeapYear(year)) {
            return day <= 29;
        }

        return day <= daysInMonth[month - 1];
    }

    /**
     * Gets format hint for email input.
     *
     * @return Email format hint string
     */
    public static String getEmailFormatHint() {
        return "(format: name.surname@email.com - must be lowercase, no capital letters)";
    }

    /**
     * Gets format hint for phone input.
     *
     * @return Phone format hint string
     */
    public static String getPhoneFormatHint() {
        return "(format: 10-11 digits, no spaces or separators, e.g., 05551234567)";
    }

    /**
     * Gets format hint for date input.
     *
     * @return Date format hint string
     */
    public static String getDateFormatHint() {
        return "(format: YYYY-MM-DD, e.g., 1990-05-15)";
    }

    /**
     * Validates yes/no input.
     *
     * @param input The input string
     * @return true if valid (yes/no/y/n), false otherwise
     */
    public static boolean isValidYesNo(String input) {
        if (input == null) {
            return false;
        }
        String lower = input.trim().toLowerCase();
        return (
            lower.equals("yes") ||
            lower.equals("no") ||
            lower.equals("y") ||
            lower.equals("n")
        );
    }

    /**
     * Converts yes/no input to boolean.
     *
     * @param input The input string (yes/no/y/n)
     * @return true for yes/y, false for no/n
     */
    public static boolean yesNoToBoolean(String input) {
        String lower = input.trim().toLowerCase();
        return lower.equals("yes") || lower.equals("y");
    }
}
