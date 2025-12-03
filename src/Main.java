import java.util.Scanner;
import managers.DatabaseManager;
import managers.UserManager;
import models.User;
import roles.*;
import utils.Animation;
import utils.ColorUtils;

/**
 * Main class serving as the entry point for the Role-Based Contact Management System.
 * Handles user authentication and initializes the appropriate role-based menu system.
 *
 * @author CMPE-343 Project Group
 * @version 1.0
 */
public class Main {

    private static Scanner scanner = new Scanner(System.in, "UTF-8");
    private static DatabaseManager dbManager;
    private static UserManager userManager;

    /**
     * Main method that initializes the system and handles the login process.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        try {
            // Set UTF-8 encoding for Turkish character support
            System.setProperty("file.encoding", "UTF-8");

            // Display startup animation
            Animation.showStartupAnimation();

            // Initialize database connection
            dbManager = new DatabaseManager();
            userManager = new UserManager(dbManager);

            // Main application loop
            boolean running = true;
            while (running) {
                User user = loginScreen();
                if (user != null) {
                    handleUserSession(user);
                } else {
                    System.out.println(
                        ColorUtils.colorize(
                            "\nExiting system...",
                            ColorUtils.YELLOW
                        )
                    );
                    running = false;
                }
            }
        } catch (Exception e) {
            System.err.println(
                ColorUtils.colorize(
                    "Critical error: " + e.getMessage(),
                    ColorUtils.RED
                )
            );
            e.printStackTrace();
        } finally {
            // Display shutdown animation
            Animation.showShutdownAnimation();

            // Cleanup resources
            if (dbManager != null) {
                dbManager.closeConnection();
            }
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    /**
     * Displays the login screen and authenticates users.
     * Continues to prompt for credentials until valid login or user exits.
     *
     * @return Authenticated User object, or null if user wants to exit
     */
    private static User loginScreen() {
        while (true) {
            displayLoginHeader();

            System.out.print(
                ColorUtils.colorize(
                    "\nUsername (or 'exit' to quit): ",
                    ColorUtils.CYAN
                )
            );
            String username = scanner.nextLine().trim();

            if (username.equalsIgnoreCase("exit")) {
                return null;
            }

            if (username.isEmpty()) {
                System.out.println(
                    ColorUtils.colorize(
                        "Username cannot be empty!",
                        ColorUtils.RED
                    )
                );
                continue;
            }

            System.out.print(
                ColorUtils.colorize("Password: ", ColorUtils.CYAN)
            );
            String password = scanner.nextLine();

            if (password.isEmpty()) {
                System.out.println(
                    ColorUtils.colorize(
                        "Password cannot be empty!",
                        ColorUtils.RED
                    )
                );
                continue;
            }

            // Authenticate user
            User user = userManager.authenticate(username, password);

            if (user != null) {
                System.out.println(
                    ColorUtils.colorize(
                        "\n✓ Login successful! Welcome, " +
                            user.getName() +
                            " " +
                            user.getSurname() +
                            "!",
                        ColorUtils.GREEN
                    )
                );
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return user;
            } else {
                System.out.println(
                    ColorUtils.colorize(
                        "\n✗ Invalid credentials! Please try again.",
                        ColorUtils.RED
                    )
                );
                System.out.println(
                    ColorUtils.colorize(
                        "Press Enter to continue...",
                        ColorUtils.YELLOW
                    )
                );
                scanner.nextLine();
            }
        }
    }

    /**
     * Displays the login screen header with system information.
     */
    private static void displayLoginHeader() {
        ColorUtils.clearScreen();
        System.out.println(
            ColorUtils.colorize(
                "╔════════════════════════════════════════════════════════════════════════╗",
                ColorUtils.BLUE
            )
        );
        System.out.println(
            ColorUtils.colorize(
                "║              ROLE-BASED CONTACT MANAGEMENT SYSTEM - LOGIN              ║",
                ColorUtils.BLUE
            )
        );
        System.out.println(
            ColorUtils.colorize(
                "╚════════════════════════════════════════════════════════════════════════╝",
                ColorUtils.BLUE
            )
        );
    }

    /**
     * Handles the user session after successful login.
     * Initializes the appropriate role-based menu based on user's role.
     *
     * @param user The authenticated user
     */
    private static void handleUserSession(User user) {
        try {
            BaseRole roleHandler = null;

            // Determine the appropriate role handler based on user's role
            switch (user.getRole().toUpperCase()) {
                case "TESTER":
                    roleHandler = new Tester(user, dbManager, scanner);
                    break;
                case "JUNIOR DEVELOPER":
                case "JUNIOR_DEVELOPER":
                    roleHandler = new JuniorDeveloper(user, dbManager, scanner);
                    break;
                case "SENIOR DEVELOPER":
                case "SENIOR_DEVELOPER":
                    roleHandler = new SeniorDeveloper(user, dbManager, scanner);
                    break;
                case "MANAGER":
                    roleHandler = new Manager(user, dbManager, scanner);
                    break;
                default:
                    System.out.println(
                        ColorUtils.colorize(
                            "Unknown role: " + user.getRole(),
                            ColorUtils.RED
                        )
                    );
                    return;
            }

            // Display role-specific menu
            if (roleHandler != null) {
                roleHandler.showMenu();
            }
        } catch (Exception e) {
            System.err.println(
                ColorUtils.colorize(
                    "Error during user session: " + e.getMessage(),
                    ColorUtils.RED
                )
            );
            e.printStackTrace();
        }
    }
}
