package roles;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import managers.DatabaseManager;
import managers.UserManager;
import models.User;
import utils.ColorUtils;
import utils.ValidationUtils;

/**
 * Manager role class extending BaseRole.
 * Demonstrates inheritance and polymorphism with unique user management capabilities.
 * Provides functionality for user CRUD operations and contact statistics.
 *
 * @author CMPE-343 Project Group
 * @version 1.0
 */
public class Manager extends BaseRole {

    private UserManager userManager;

    /**
     * Constructor for Manager role.
     *
     * @param user The current logged-in user
     * @param dbManager The database manager instance
     * @param scanner The scanner for user input
     */
    public Manager(User user, DatabaseManager dbManager, Scanner scanner) {
        super(user, dbManager, scanner);
        this.userManager = new UserManager(dbManager);
    }

    @Override
    protected String getRoleName() {
        return "Manager";
    }

    @Override
    public void showMenu() {
        while (isLoggedIn) {
            displayMenuHeader();
            displayMenuOptions();
            displayMenuFooter();

            String choice = getMenuChoice();
            handleMenuChoice(choice);
        }
    }

    @Override
    protected void displayMenuOptions() {
        System.out.println(
            ColorUtils.colorize("  📋 MAIN MENU", ColorUtils.BRIGHT_YELLOW)
        );
        System.out.println();
        System.out.println(
            ColorUtils.colorize(
                "  1. Contact Statistics",
                ColorUtils.BRIGHT_CYAN
            )
        );
        System.out.println(
            ColorUtils.colorize("  2. List All Users", ColorUtils.WHITE)
        );
        System.out.println(
            ColorUtils.colorize(
                "  3. Add/Employ New User",
                ColorUtils.BRIGHT_GREEN
            )
        );
        System.out.println(
            ColorUtils.colorize("  4. Update Existing User", ColorUtils.YELLOW)
        );
        System.out.println(
            ColorUtils.colorize("  5. Delete/Fire User", ColorUtils.BRIGHT_RED)
        );
        System.out.println(
            ColorUtils.colorize("  6. Change Password", ColorUtils.WHITE)
        );
        System.out.println(
            ColorUtils.colorize("  7. Logout", ColorUtils.WHITE)
        );
    }

    @Override
    protected void handleMenuChoice(String choice) {
        switch (choice) {
            case "1":
                viewContactStatistics();
                break;
            case "2":
                listAllUsers();
                break;
            case "3":
                addNewUser();
                break;
            case "4":
                updateUser();
                break;
            case "5":
                deleteUser();
                break;
            case "6":
                changePassword();
                break;
            case "7":
                logout();
                break;
            default:
                displayInvalidChoice();
                break;
        }
    }

    /**
     * Displays comprehensive contact statistics.
     * Demonstrates the manager's unique capability to view system-wide analytics.
     */
    private void viewContactStatistics() {
        ColorUtils.clearScreen();
        System.out.println(
            ColorUtils.colorize(
                "╔════════════════════════════════════════════════════════════╗",
                ColorUtils.BRIGHT_CYAN
            )
        );
        System.out.println(
            ColorUtils.colorize(
                "║                CONTACT STATISTICS                          ║",
                ColorUtils.BRIGHT_CYAN
            )
        );
        System.out.println(
            ColorUtils.colorize(
                "╚════════════════════════════════════════════════════════════╝",
                ColorUtils.BRIGHT_CYAN
            )
        );
        System.out.println();

        Map<String, Object> stats = contactManager.getContactStatistics();

        if (stats.isEmpty()) {
            displayInfo("No statistics available.");
            pauseScreen();
            return;
        }

        // Basic counts
        System.out.println(
            ColorUtils.colorize(
                "═══ GENERAL STATISTICS ═══",
                ColorUtils.BRIGHT_YELLOW
            )
        );
        System.out.println();
        System.out.println(
            ColorUtils.colorize("  Total Contacts: ", ColorUtils.CYAN) +
                stats.getOrDefault("totalContacts", 0)
        );
        System.out.println(
            ColorUtils.colorize("  Contacts with LinkedIn: ", ColorUtils.CYAN) +
                stats.getOrDefault("contactsWithLinkedIn", 0)
        );
        System.out.println(
            ColorUtils.colorize(
                    "  Contacts without LinkedIn: ",
                    ColorUtils.CYAN
                ) +
                stats.getOrDefault("contactsWithoutLinkedIn", 0)
        );
        System.out.println(
            ColorUtils.colorize(
                    "  Contacts with Secondary Phone: ",
                    ColorUtils.CYAN
                ) +
                stats.getOrDefault("contactsWithSecondaryPhone", 0)
        );

        // Age statistics
        System.out.println();
        System.out.println(
            ColorUtils.colorize(
                "═══ AGE STATISTICS ═══",
                ColorUtils.BRIGHT_YELLOW
            )
        );
        System.out.println();

        if (stats.containsKey("averageAge")) {
            double avgAge = (Double) stats.get("averageAge");
            System.out.println(
                ColorUtils.colorize("  Average Age: ", ColorUtils.CYAN) +
                    String.format("%.1f years", avgAge)
            );
        }

        if (stats.containsKey("youngestContact")) {
            System.out.println(
                ColorUtils.colorize("  Youngest Contact: ", ColorUtils.CYAN) +
                    stats.get("youngestContact") +
                    " (Born: " +
                    stats.get("youngestBirthDate") +
                    ")"
            );
        }

        if (stats.containsKey("oldestContact")) {
            System.out.println(
                ColorUtils.colorize("  Oldest Contact: ", ColorUtils.CYAN) +
                    stats.get("oldestContact") +
                    " (Born: " +
                    stats.get("oldestBirthDate") +
                    ")"
            );
        }

        // Most common names
        System.out.println();
        System.out.println(
            ColorUtils.colorize(
                "═══ NAME STATISTICS ═══",
                ColorUtils.BRIGHT_YELLOW
            )
        );
        System.out.println();

        if (stats.containsKey("commonFirstNames")) {
            System.out.println(
                ColorUtils.colorize(
                    "  Most Common First Names:",
                    ColorUtils.CYAN
                )
            );
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> firstNames = (List<
                Map<String, Object>
            >) stats.get("commonFirstNames");
            for (Map<String, Object> nameInfo : firstNames) {
                System.out.println(
                    "    - " +
                        nameInfo.get("name") +
                        ": " +
                        nameInfo.get("count") +
                        " people"
                );
            }
        }

        System.out.println();
        if (stats.containsKey("commonLastNames")) {
            System.out.println(
                ColorUtils.colorize(
                    "  Most Common Last Names:",
                    ColorUtils.CYAN
                )
            );
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> lastNames = (List<
                Map<String, Object>
            >) stats.get("commonLastNames");
            for (Map<String, Object> nameInfo : lastNames) {
                System.out.println(
                    "    - " +
                        nameInfo.get("name") +
                        ": " +
                        nameInfo.get("count") +
                        " people"
                );
            }
        }

        // Birth month distribution
        if (stats.containsKey("birthMonths")) {
            System.out.println();
            System.out.println(
                ColorUtils.colorize(
                    "═══ BIRTH MONTH DISTRIBUTION ═══",
                    ColorUtils.BRIGHT_YELLOW
                )
            );
            System.out.println();

            @SuppressWarnings("unchecked")
            Map<Integer, Integer> birthMonths = (Map<
                Integer,
                Integer
            >) stats.get("birthMonths");
            String[] monthNames = {
                "",
                "January",
                "February",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December",
            };

            for (int month = 1; month <= 12; month++) {
                int count = birthMonths.getOrDefault(month, 0);
                if (count > 0) {
                    System.out.printf(
                        "  %-12s: %d contact(s)%n",
                        monthNames[month],
                        count
                    );
                }
            }
        }

        System.out.println();
        pauseScreen();
    }

    /**
     * Lists all users in the system.
     */
    private void listAllUsers() {
        ColorUtils.clearScreen();
        System.out.println(
            ColorUtils.colorize(
                "╔════════════════════════════════════════════════════════════╗",
                ColorUtils.BRIGHT_CYAN
            )
        );
        System.out.println(
            ColorUtils.colorize(
                "║                     ALL USERS                              ║",
                ColorUtils.BRIGHT_CYAN
            )
        );
        System.out.println(
            ColorUtils.colorize(
                "╚════════════════════════════════════════════════════════════╝",
                ColorUtils.BRIGHT_CYAN
            )
        );
        System.out.println();

        List<User> users = userManager.getAllUsers();

        if (users.isEmpty()) {
            displayInfo("No users found in the database.");
        } else {
            displayInfo("Total users: " + users.size());
            System.out.println();
            displayUserList(users);
        }

        pauseScreen();
    }

    /**
     * Displays a list of users in a formatted table.
     *
     * @param users The list of users to display
     */
    private void displayUserList(List<User> users) {
        System.out.println(
            ColorUtils.colorize(
                "─────────────────────────────────────────────────────────────────────────────────",
                ColorUtils.CYAN
            )
        );
        System.out.printf(
            ColorUtils.colorize(
                "%-5s %-15s %-20s %-20s %-20s%n",
                ColorUtils.BRIGHT_WHITE
            ),
            "ID",
            "Username",
            "Name",
            "Surname",
            "Role"
        );
        System.out.println(
            ColorUtils.colorize(
                "─────────────────────────────────────────────────────────────────────────────────",
                ColorUtils.CYAN
            )
        );

        for (User user : users) {
            System.out.printf(
                "%-5d %-15s %-20s %-20s %-20s%n",
                user.getUserId(),
                truncate(user.getUsername(), 15),
                truncate(user.getName(), 20),
                truncate(user.getSurname(), 20),
                truncate(user.getRole(), 20)
            );
        }

        System.out.println(
            ColorUtils.colorize(
                "─────────────────────────────────────────────────────────────────────────────────",
                ColorUtils.CYAN
            )
        );
    }

    /**
     * Displays detailed information for a single user.
     *
     * @param user The user to display
     */
    private void displayUserDetails(User user) {
        System.out.println(
            ColorUtils.colorize(
                "╔════════════════════════════════════════════════════════════╗",
                ColorUtils.BRIGHT_CYAN
            )
        );
        System.out.println(
            ColorUtils.colorize(
                "║                     USER DETAILS                           ║",
                ColorUtils.BRIGHT_CYAN
            )
        );
        System.out.println(
            ColorUtils.colorize(
                "╚════════════════════════════════════════════════════════════╝",
                ColorUtils.BRIGHT_CYAN
            )
        );
        System.out.println();
        System.out.println(
            ColorUtils.colorize("  User ID:      ", ColorUtils.CYAN) +
                user.getUserId()
        );
        System.out.println(
            ColorUtils.colorize("  Username:     ", ColorUtils.CYAN) +
                user.getUsername()
        );
        System.out.println(
            ColorUtils.colorize("  Name:         ", ColorUtils.CYAN) +
                user.getName()
        );
        System.out.println(
            ColorUtils.colorize("  Surname:      ", ColorUtils.CYAN) +
                user.getSurname()
        );
        System.out.println(
            ColorUtils.colorize("  Role:         ", ColorUtils.CYAN) +
                user.getRole()
        );
        System.out.println(
            ColorUtils.colorize("  Created At:   ", ColorUtils.CYAN) +
                user.getCreatedAt()
        );
        System.out.println();
    }

    /**
     * Adds a new user to the system.
     */
    private void addNewUser() {
        ColorUtils.clearScreen();
        System.out.println(
            ColorUtils.colorize(
                "╔════════════════════════════════════════════════════════════╗",
                ColorUtils.BRIGHT_GREEN
            )
        );
        System.out.println(
            ColorUtils.colorize(
                "║                  ADD NEW USER (EMPLOY)                     ║",
                ColorUtils.BRIGHT_GREEN
            )
        );
        System.out.println(
            ColorUtils.colorize(
                "╚════════════════════════════════════════════════════════════╝",
                ColorUtils.BRIGHT_GREEN
            )
        );
        System.out.println();

        // Username
        String username;
        while (true) {
            System.out.print(
                ColorUtils.colorize(
                    "Username (2-20 characters, required): ",
                    ColorUtils.CYAN
                )
            );
            username = scanner.nextLine().trim();

            if (username.isEmpty()) {
                displayError("Username is required!");
                continue;
            }

            if (!ValidationUtils.isValidUsername(username)) {
                displayError(
                    "Username must be 2-20 alphanumeric characters (underscore and hyphen allowed)."
                );
                continue;
            }

            if (userManager.usernameExists(username)) {
                displayError("Username already exists! Please choose another.");
                continue;
            }

            break;
        }

        // Password
        String password;
        while (true) {
            System.out.print(
                ColorUtils.colorize(
                    "Password (minimum 2 characters, required): ",
                    ColorUtils.CYAN
                )
            );
            password = scanner.nextLine();

            if (!ValidationUtils.isValidPassword(password)) {
                displayError("Password must be at least 2 characters long.");
                continue;
            }

            System.out.print(
                ColorUtils.colorize("Confirm Password: ", ColorUtils.CYAN)
            );
            String confirmPassword = scanner.nextLine();

            if (!password.equals(confirmPassword)) {
                displayError("Passwords do not match!");
                continue;
            }

            break;
        }

        // Name
        String name;
        while (true) {
            System.out.print(
                ColorUtils.colorize(
                    "First Name (required, Turkish characters supported): ",
                    ColorUtils.CYAN
                )
            );
            name = scanner.nextLine().trim();

            if (name.isEmpty()) {
                displayError("First name is required!");
                continue;
            }

            if (!ValidationUtils.isValidName(name)) {
                displayError(ValidationUtils.getNameError());
                continue;
            }

            break;
        }

        // Surname
        String surname;
        while (true) {
            System.out.print(
                ColorUtils.colorize(
                    "Surname (required, Turkish characters supported): ",
                    ColorUtils.CYAN
                )
            );
            surname = scanner.nextLine().trim();

            if (surname.isEmpty()) {
                displayError("Surname is required!");
                continue;
            }

            if (!ValidationUtils.isValidName(surname)) {
                displayError(ValidationUtils.getNameError());
                continue;
            }

            break;
        }

        // Role
        String role;
        while (true) {
            System.out.println();
            System.out.println(
                ColorUtils.colorize("Available Roles:", ColorUtils.YELLOW)
            );
            System.out.println("  1. Tester");
            System.out.println("  2. Junior Developer");
            System.out.println("  3. Senior Developer");
            System.out.println("  4. Manager");
            System.out.println();

            System.out.print(
                ColorUtils.colorize("Select Role (1-4): ", ColorUtils.CYAN)
            );
            String roleChoice = scanner.nextLine().trim();

            switch (roleChoice) {
                case "1":
                    role = "Tester";
                    break;
                case "2":
                    role = "Junior Developer";
                    break;
                case "3":
                    role = "Senior Developer";
                    break;
                case "4":
                    role = "Manager";
                    break;
                default:
                    displayError("Invalid role selection!");
                    continue;
            }

            break;
        }

        // Confirm addition
        System.out.println();
        System.out.println(
            ColorUtils.colorize("User Summary:", ColorUtils.YELLOW)
        );
        System.out.println("  Username: " + username);
        System.out.println("  Name: " + name + " " + surname);
        System.out.println("  Role: " + role);
        System.out.println();

        // Validate yes/no confirmation with loop
        String confirm = null;
        while (confirm == null) {
            System.out.print(
                ColorUtils.colorize(
                    "Confirm add user? (yes/no): ",
                    ColorUtils.YELLOW
                )
            );
            String input = scanner.nextLine().trim().toLowerCase();

            if (ValidationUtils.isValidYesNo(input)) {
                confirm = input;
            } else {
                displayError("Invalid input! Please enter 'yes' or 'no'.");
            }
        }

        if (!ValidationUtils.yesNoToBoolean(confirm)) {
            displayInfo("User addition cancelled.");
            pauseScreen();
            return;
        }

        // Add user to database
        if (userManager.addUser(username, password, name, surname, role)) {
            displaySuccess("User added successfully!");
        } else {
            displayError("Failed to add user. Please try again.");
        }

        pauseScreen();
    }

    /**
     * Updates an existing user's information.
     */
    private void updateUser() {
        ColorUtils.clearScreen();
        System.out.println(
            ColorUtils.colorize(
                "╔════════════════════════════════════════════════════════════╗",
                ColorUtils.YELLOW
            )
        );
        System.out.println(
            ColorUtils.colorize(
                "║                    UPDATE USER                             ║",
                ColorUtils.YELLOW
            )
        );
        System.out.println(
            ColorUtils.colorize(
                "╚════════════════════════════════════════════════════════════╝",
                ColorUtils.YELLOW
            )
        );
        System.out.println();

        // Show all users first
        java.util.List<User> allUsers = userManager.getAllUsers();
        if (allUsers.isEmpty()) {
            displayInfo("No users available to update.");
            pauseScreen();
            return;
        }

        System.out.println(
            ColorUtils.colorize("Available users:", ColorUtils.YELLOW)
        );
        System.out.println();
        displayUserList(allUsers);
        System.out.println();

        // Loop for ID input validation
        int userId = 0;
        User existingUser = null;
        while (existingUser == null) {
            System.out.print(
                ColorUtils.colorize(
                    "Enter User ID to update (or 0 to cancel): ",
                    ColorUtils.CYAN
                )
            );
            String idInput = scanner.nextLine().trim();

            if (idInput.equals("0")) {
                displayInfo("Update cancelled.");
                pauseScreen();
                return;
            }

            if (!isValidInteger(idInput)) {
                displayError(
                    "Invalid User ID! Must be a number. Please try again."
                );
                continue;
            }

            userId = Integer.parseInt(idInput);
            existingUser = userManager.getUserById(userId);

            if (existingUser == null) {
                displayError(
                    "User with ID " + userId + " not found! Please try again."
                );
            }
        }

        // Display current user details
        System.out.println();
        System.out.println(
            ColorUtils.colorize("Current User Information:", ColorUtils.YELLOW)
        );
        displayUserDetails(existingUser);

        System.out.println(
            ColorUtils.colorize(
                "Enter new values (press Enter to keep current value):",
                ColorUtils.YELLOW
            )
        );
        System.out.println();

        // Username with validation loop
        String username = existingUser.getUsername();
        while (true) {
            System.out.print(
                ColorUtils.colorize(
                    "Username [" + existingUser.getUsername() + "]: ",
                    ColorUtils.CYAN
                )
            );
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                break; // Keep current
            }
            if (!ValidationUtils.isValidUsername(input)) {
                displayError("Invalid username format! Please try again.");
                continue;
            }
            if (userManager.usernameExistsForOtherUser(input, userId)) {
                displayError(
                    "Username already exists for another user! Please try again."
                );
                continue;
            }
            username = input;
            break;
        }

        // Name with validation loop
        String name = existingUser.getName();
        while (true) {
            System.out.print(
                ColorUtils.colorize(
                    "First Name [" + existingUser.getName() + "]: ",
                    ColorUtils.CYAN
                )
            );
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                break; // Keep current
            }
            if (!ValidationUtils.isValidName(input)) {
                displayError(
                    ValidationUtils.getNameError() + " Please try again."
                );
                continue;
            }
            name = input;
            break;
        }

        // Surname with validation loop
        String surname = existingUser.getSurname();
        while (true) {
            System.out.print(
                ColorUtils.colorize(
                    "Surname [" + existingUser.getSurname() + "]: ",
                    ColorUtils.CYAN
                )
            );
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                break; // Keep current
            }
            if (!ValidationUtils.isValidName(input)) {
                displayError(
                    ValidationUtils.getNameError() + " Please try again."
                );
                continue;
            }
            surname = input;
            break;
        }

        // Role
        System.out.println();
        System.out.println(
            ColorUtils.colorize(
                "Current Role: " + existingUser.getRole(),
                ColorUtils.YELLOW
            )
        );
        System.out.println("  1. Tester");
        System.out.println("  2. Junior Developer");
        System.out.println("  3. Senior Developer");
        System.out.println("  4. Manager");
        System.out.println("  0. Keep current role");
        System.out.println();

        System.out.print(
            ColorUtils.colorize("Select New Role (0-4): ", ColorUtils.CYAN)
        );
        String roleChoice = scanner.nextLine().trim();

        String role = existingUser.getRole();
        switch (roleChoice) {
            case "1":
                role = "Tester";
                break;
            case "2":
                role = "Junior Developer";
                break;
            case "3":
                role = "Senior Developer";
                break;
            case "4":
                role = "Manager";
                break;
            case "0":
                // Keep current
                break;
            default:
                if (!roleChoice.isEmpty()) {
                    displayError(
                        "Invalid role selection! Keeping current role."
                    );
                }
                break;
        }

        // Confirm update with validation loop
        System.out.println();
        String confirm = null;
        while (confirm == null) {
            System.out.print(
                ColorUtils.colorize(
                    "Confirm update? (yes/no): ",
                    ColorUtils.YELLOW
                )
            );
            String input = scanner.nextLine().trim().toLowerCase();

            if (ValidationUtils.isValidYesNo(input)) {
                confirm = input;
            } else {
                displayError("Invalid input! Please enter 'yes' or 'no'.");
            }
        }

        if (!ValidationUtils.yesNoToBoolean(confirm)) {
            displayInfo("Update cancelled.");
            pauseScreen();
            return;
        }

        // Perform update
        if (userManager.updateUser(userId, username, name, surname, role)) {
            displaySuccess("User updated successfully!");
        } else {
            displayError("Failed to update user. Please try again.");
        }

        pauseScreen();
    }

    /**
     * Deletes a user from the system.
     */
    private void deleteUser() {
        ColorUtils.clearScreen();
        System.out.println(
            ColorUtils.colorize(
                "╔════════════════════════════════════════════════════════════╗",
                ColorUtils.BRIGHT_RED
            )
        );
        System.out.println(
            ColorUtils.colorize(
                "║                  DELETE USER (FIRE)                        ║",
                ColorUtils.BRIGHT_RED
            )
        );
        System.out.println(
            ColorUtils.colorize(
                "╚════════════════════════════════════════════════════════════╝",
                ColorUtils.BRIGHT_RED
            )
        );
        System.out.println();

        // Show all users first
        java.util.List<User> allUsers = userManager.getAllUsers();
        if (allUsers.isEmpty()) {
            displayInfo("No users available to delete.");
            pauseScreen();
            return;
        }

        System.out.println(
            ColorUtils.colorize("Available users:", ColorUtils.YELLOW)
        );
        System.out.println();
        displayUserList(allUsers);
        System.out.println();

        // Loop for ID input validation
        int userId = 0;
        User user = null;
        while (user == null) {
            System.out.print(
                ColorUtils.colorize(
                    "Enter User ID to delete (or 0 to cancel): ",
                    ColorUtils.CYAN
                )
            );
            String idInput = scanner.nextLine().trim();

            if (idInput.equals("0")) {
                displayInfo("Deletion cancelled.");
                pauseScreen();
                return;
            }

            if (!isValidInteger(idInput)) {
                displayError(
                    "Invalid User ID! Must be a number. Please try again."
                );
                continue;
            }

            userId = Integer.parseInt(idInput);

            // Prevent self-deletion
            if (userId == currentUser.getUserId()) {
                displayError(
                    "You cannot delete your own account! Please try again."
                );
                continue;
            }

            user = userManager.getUserById(userId);

            if (user == null) {
                displayError(
                    "User with ID " + userId + " not found! Please try again."
                );
            }
        }

        // Display user details
        System.out.println();
        System.out.println(
            ColorUtils.colorize("User to be deleted:", ColorUtils.YELLOW)
        );
        displayUserDetails(user);

        // Validate yes/no confirmation with loop
        String confirm = null;
        while (confirm == null) {
            System.out.print(
                ColorUtils.colorize(
                    "Are you sure you want to delete this user? (yes/no): ",
                    ColorUtils.RED
                )
            );
            String input = scanner.nextLine().trim().toLowerCase();

            if (ValidationUtils.isValidYesNo(input)) {
                confirm = input;
            } else {
                displayError("Invalid input! Please enter 'yes' or 'no'.");
            }
        }

        if (!ValidationUtils.yesNoToBoolean(confirm)) {
            displayInfo("Deletion cancelled.");
            pauseScreen();
            return;
        }

        // Perform deletion
        if (userManager.deleteUser(userId)) {
            displaySuccess("User deleted successfully!");
        } else {
            displayError("Failed to delete user. Please try again.");
        }

        pauseScreen();
    }

    /**
     * Truncates a string to a specified length.
     *
     * @param str The string to truncate
     * @param maxLength The maximum length
     * @return The truncated string
     */
    private String truncate(String str, int maxLength) {
        if (str == null) {
            return "";
        }
        if (str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength - 3) + "...";
    }
}
