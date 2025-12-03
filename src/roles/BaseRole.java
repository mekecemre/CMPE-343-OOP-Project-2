package roles;

import java.util.Scanner;
import managers.ContactManager;
import managers.DatabaseManager;
import managers.UndoManager;
import models.User;
import utils.ColorUtils;

/**
 * BaseRole abstract class serving as the foundation for all role-based menu systems.
 * Demonstrates abstraction by defining common behavior and requiring subclasses
 * to implement role-specific functionality.
 * This class showcases polymorphism through method overriding in subclasses.
 *
 * @author CMPE-343 Project Group
 * @version 1.0
 */
public abstract class BaseRole {

    /** The currently logged-in user */
    protected User currentUser;

    /** Database manager for database operations */
    protected DatabaseManager dbManager;

    /** Contact manager for contact-related operations */
    protected ContactManager contactManager;

    /** Undo manager for undo/redo functionality */
    protected UndoManager undoManager;

    /** Scanner for reading user input */
    protected Scanner scanner;

    /** Flag indicating if user is currently logged in */
    protected boolean isLoggedIn;

    /**
     * Constructor for BaseRole.
     *
     * @param user The current logged-in user
     * @param dbManager The database manager instance
     * @param scanner The scanner for user input
     */
    public BaseRole(User user, DatabaseManager dbManager, Scanner scanner) {
        this.currentUser = user;
        this.dbManager = dbManager;
        this.scanner = scanner;
        this.contactManager = new ContactManager(dbManager);
        this.undoManager = new UndoManager();
        this.isLoggedIn = true;
    }

    /**
     * Abstract method that must be implemented by each role to display their menu.
     * This demonstrates abstraction - each role will have its own implementation.
     */
    public abstract void showMenu();

    /**
     * Abstract method to get the role name for display purposes.
     *
     * @return The role name
     */
    protected abstract String getRoleName();

    /**
     * Abstract method that defines the available menu options for each role.
     * This demonstrates polymorphism - each role overrides this with their specific options.
     */
    protected abstract void displayMenuOptions();

    /**
     * Abstract method to handle menu selection for each role.
     * Each role implements their own menu handling logic.
     *
     * @param choice The user's menu choice
     */
    protected abstract void handleMenuChoice(String choice);

    /**
     * Displays the common menu header with user information.
     * This is a shared method available to all roles.
     */
    protected void displayMenuHeader() {
        ColorUtils.clearScreen();
        System.out.println(
            ColorUtils.colorize(
                "╔════════════════════════════════════════════════════════════╗",
                ColorUtils.BRIGHT_BLUE
            )
        );
        System.out.println(
            ColorUtils.colorize(
                "║     ROLE-BASED CONTACT MANAGEMENT SYSTEM                   ║",
                ColorUtils.BRIGHT_BLUE
            )
        );
        System.out.println(
            ColorUtils.colorize(
                "╚════════════════════════════════════════════════════════════╝",
                ColorUtils.BRIGHT_BLUE
            )
        );
        System.out.println();

        // Display user information with Turkish character support
        String fullName =
            currentUser.getName() + " " + currentUser.getSurname();
        String roleDisplay = getRoleName();

        System.out.println(
            ColorUtils.colorize(
                "  👤 User: " + fullName,
                ColorUtils.BRIGHT_CYAN
            )
        );
        System.out.println(
            ColorUtils.colorize(
                "  🎭 Role: " + roleDisplay,
                ColorUtils.BRIGHT_YELLOW
            )
        );
        System.out.println();
        ColorUtils.printLine(60, '─', ColorUtils.CYAN);
        System.out.println();
    }

    /**
     * Displays the common menu footer.
     */
    protected void displayMenuFooter() {
        System.out.println();
        ColorUtils.printLine(60, '─', ColorUtils.CYAN);
    }

    /**
     * Prompts the user for menu choice input.
     *
     * @return The user's choice as a string
     */
    protected String getMenuChoice() {
        System.out.print(
            ColorUtils.colorize(
                "\nEnter your choice: ",
                ColorUtils.BRIGHT_WHITE
            )
        );
        return scanner.nextLine().trim();
    }

    /**
     * Handles the change password functionality common to all roles.
     * Demonstrates encapsulation - password logic is hidden within this method.
     */
    protected void changePassword() {
        ColorUtils.clearScreen();
        System.out.println(
            ColorUtils.colorize(
                "╔════════════════════════════════════════════════════════════╗",
                ColorUtils.YELLOW
            )
        );
        System.out.println(
            ColorUtils.colorize(
                "║                    CHANGE PASSWORD                         ║",
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

        // Get current password for verification
        System.out.print(
            ColorUtils.colorize(
                "Enter current password (or 'cancel' to abort): ",
                ColorUtils.CYAN
            )
        );
        String currentPassword = scanner.nextLine();

        if (currentPassword.equalsIgnoreCase("cancel")) {
            System.out.println(
                ColorUtils.colorize(
                    "\n✗ Password change cancelled.",
                    ColorUtils.YELLOW
                )
            );
            pauseScreen();
            return;
        }

        // Verify current password
        managers.UserManager userManager = new managers.UserManager(dbManager);
        User verifiedUser = userManager.authenticate(
            currentUser.getUsername(),
            currentPassword
        );

        if (verifiedUser == null) {
            System.out.println(
                ColorUtils.colorize(
                    "\n✗ Current password is incorrect!",
                    ColorUtils.RED
                )
            );
            pauseScreen();
            return;
        }

        // Get new password
        System.out.print(
            ColorUtils.colorize("Enter new password: ", ColorUtils.CYAN)
        );
        String newPassword = scanner.nextLine();

        if (newPassword.isEmpty() || newPassword.length() < 2) {
            System.out.println(
                ColorUtils.colorize(
                    "\n✗ Password must be at least 2 characters long!",
                    ColorUtils.RED
                )
            );
            pauseScreen();
            return;
        }

        // Confirm new password
        System.out.print(
            ColorUtils.colorize("Confirm new password: ", ColorUtils.CYAN)
        );
        String confirmPassword = scanner.nextLine();

        if (!newPassword.equals(confirmPassword)) {
            System.out.println(
                ColorUtils.colorize(
                    "\n✗ Passwords do not match!",
                    ColorUtils.RED
                )
            );
            pauseScreen();
            return;
        }

        // Update password
        if (userManager.changePassword(currentUser.getUserId(), newPassword)) {
            System.out.println(
                ColorUtils.colorize(
                    "\n✓ Password changed successfully!",
                    ColorUtils.GREEN
                )
            );
        } else {
            System.out.println(
                ColorUtils.colorize(
                    "\n✗ Failed to change password. Please try again.",
                    ColorUtils.RED
                )
            );
        }

        pauseScreen();
    }

    /**
     * Handles the logout functionality common to all roles.
     */
    protected void logout() {
        System.out.println();
        System.out.println(
            ColorUtils.colorize(
                "Logging out " + currentUser.getName() + "...",
                ColorUtils.YELLOW
            )
        );
        isLoggedIn = false;

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println(
            ColorUtils.colorize("✓ Logged out successfully!", ColorUtils.GREEN)
        );
    }

    /**
     * Pauses the screen until user presses Enter.
     */
    protected void pauseScreen() {
        System.out.println();
        System.out.print(
            ColorUtils.colorize(
                "Press Enter to continue...",
                ColorUtils.BRIGHT_BLACK
            )
        );
        scanner.nextLine();
    }

    /**
     * Validates if input is a valid integer.
     *
     * @param input The input string to validate
     * @return true if valid integer, false otherwise
     */
    protected boolean isValidInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Gets the current user.
     *
     * @return The current user
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Checks if the user is still logged in.
     *
     * @return true if logged in, false otherwise
     */
    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    /**
     * Displays an invalid choice message.
     */
    protected void displayInvalidChoice() {
        System.out.println();
        System.out.println(
            ColorUtils.colorize(
                "✗ Invalid choice! Please try again.",
                ColorUtils.RED
            )
        );
        pauseScreen();
    }

    /**
     * Displays a success message.
     *
     * @param message The success message to display
     */
    protected void displaySuccess(String message) {
        System.out.println();
        System.out.println(
            ColorUtils.colorize("✓ " + message, ColorUtils.GREEN)
        );
    }

    /**
     * Displays an error message.
     *
     * @param message The error message to display
     */
    protected void displayError(String message) {
        System.out.println();
        System.out.println(ColorUtils.colorize("✗ " + message, ColorUtils.RED));
    }

    /**
     * Displays a warning message.
     *
     * @param message The warning message to display
     */
    protected void displayWarning(String message) {
        System.out.println();
        System.out.println(
            ColorUtils.colorize("⚠ " + message, ColorUtils.YELLOW)
        );
    }

    /**
     * Displays an info message.
     *
     * @param message The info message to display
     */
    protected void displayInfo(String message) {
        System.out.println();
        System.out.println(
            ColorUtils.colorize("ℹ " + message, ColorUtils.CYAN)
        );
    }
}
