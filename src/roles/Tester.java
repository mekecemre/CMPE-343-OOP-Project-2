package roles;

import java.util.List;
import java.util.Scanner;
import managers.ContactManager;
import managers.DatabaseManager;
import models.Contact;
import models.SearchCriteria;
import utils.ColorUtils;

/**
 * Tester role class extending BaseRole.
 * Demonstrates inheritance and polymorphism by overriding abstract methods.
 * Provides functionality for viewing, searching, and sorting contacts.
 *
 * @author CMPE-343 Project Group
 * @version 1.0
 */
public class Tester extends BaseRole {

    /**
     * Constructor for Tester role.
     *
     * @param dbManager The database manager instance
     * @param scanner   The scanner for user input
     */
    public Tester(DatabaseManager dbManager, Scanner scanner) {
        super(null, dbManager, scanner);
        this.contactManager = new ContactManager(dbManager);
    }

    /**
     * Constructor for Tester role with user.
     *
     * @param user      The current logged-in user
     * @param dbManager The database manager instance
     * @param scanner   The scanner for user input
     */
    public Tester(
        models.User user,
        DatabaseManager dbManager,
        Scanner scanner
    ) {
        super(user, dbManager, scanner);
    }

    @Override
    protected String getRoleName() {
        return "Tester";
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
            ColorUtils.colorize("  1. List All Contacts", ColorUtils.WHITE)
        );
        System.out.println(
            ColorUtils.colorize(
                "  2. Search Contacts by Single Field",
                ColorUtils.WHITE
            )
        );
        System.out.println(
            ColorUtils.colorize(
                "  3. Search Contacts by Multiple Fields",
                ColorUtils.WHITE
            )
        );
        System.out.println(
            ColorUtils.colorize(
                "  4. Advanced Search (User-Defined Query)",
                ColorUtils.WHITE
            )
        );
        System.out.println(
            ColorUtils.colorize("  5. Sort Contact Results", ColorUtils.WHITE)
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
                listAllContacts();
                break;
            case "2":
                searchBySingleField();
                break;
            case "3":
                searchByMultipleFields();
                break;
            case "4":
                advancedSearch();
                break;
            case "5":
                sortContacts();
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
     * Lists all contacts in the database.
     */
    protected void listAllContacts() {
        ColorUtils.clearScreen();
        System.out.println(
            ColorUtils.colorize(
                "╔════════════════════════════════════════════════════════════╗",
                ColorUtils.BRIGHT_CYAN
            )
        );
        System.out.println(
            ColorUtils.colorize(
                "║                    ALL CONTACTS                            ║",
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

        List<Contact> contacts = contactManager.getAllContacts();

        if (contacts.isEmpty()) {
            displayInfo("No contacts found in the database.");
        } else {
            displayInfo("Total contacts: " + contacts.size());
            System.out.println();
            displayContactList(contacts);
        }

        pauseScreen();
    }

    /**
     * Displays a list of contacts in a formatted table.
     *
     * @param contacts The list of contacts to display
     */
    protected void displayContactList(List<Contact> contacts) {
        System.out.println(
            ColorUtils.colorize(
                "─────────────────────────────────────────────────────────────────────────────────",
                ColorUtils.CYAN
            )
        );
        System.out.printf(
            ColorUtils.colorize(
                "%-5s %-15s %-15s %-15s %-15s %-25s%n",
                ColorUtils.BRIGHT_WHITE
            ),
            "ID",
            "First Name",
            "Last Name",
            "Phone",
            "Email",
            "Birth Date"
        );
        System.out.println(
            ColorUtils.colorize(
                "─────────────────────────────────────────────────────────────────────────────────",
                ColorUtils.CYAN
            )
        );

        for (Contact contact : contacts) {
            System.out.printf(
                "%-5d %-15s %-15s %-15s %-25s %-15s%n",
                contact.getContactId(),
                truncate(contact.getFirstName(), 15),
                truncate(contact.getLastName(), 15),
                truncate(contact.getPhonePrimary(), 15),
                truncate(contact.getEmail(), 25),
                contact.getBirthDate().toString()
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
     * Displays detailed information for a single contact.
     *
     * @param contact The contact to display
     */
    protected void displayContactDetails(Contact contact) {
        System.out.println(
            ColorUtils.colorize(
                "╔════════════════════════════════════════════════════════════╗",
                ColorUtils.BRIGHT_CYAN
            )
        );
        System.out.println(
            ColorUtils.colorize(
                "║                   CONTACT DETAILS                          ║",
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
            ColorUtils.colorize("  Contact ID:       ", ColorUtils.CYAN) +
                contact.getContactId()
        );
        System.out.println(
            ColorUtils.colorize("  First Name:       ", ColorUtils.CYAN) +
                contact.getFirstName()
        );
        System.out.println(
            ColorUtils.colorize("  Middle Name:      ", ColorUtils.CYAN) +
                (contact.getMiddleName() != null
                    ? contact.getMiddleName()
                    : "N/A")
        );
        System.out.println(
            ColorUtils.colorize("  Last Name:        ", ColorUtils.CYAN) +
                contact.getLastName()
        );
        System.out.println(
            ColorUtils.colorize("  Nickname:         ", ColorUtils.CYAN) +
                (contact.getNickname() != null ? contact.getNickname() : "N/A")
        );
        System.out.println(
            ColorUtils.colorize("  Primary Phone:    ", ColorUtils.CYAN) +
                contact.getPhonePrimary()
        );
        System.out.println(
            ColorUtils.colorize("  Secondary Phone:  ", ColorUtils.CYAN) +
                (contact.getPhoneSecondary() != null
                    ? contact.getPhoneSecondary()
                    : "N/A")
        );
        System.out.println(
            ColorUtils.colorize("  Email:            ", ColorUtils.CYAN) +
                contact.getEmail()
        );
        System.out.println(
            ColorUtils.colorize("  LinkedIn URL:     ", ColorUtils.CYAN) +
                (contact.getLinkedinUrl() != null
                    ? contact.getLinkedinUrl()
                    : "N/A")
        );
        System.out.println(
            ColorUtils.colorize("  Birth Date:       ", ColorUtils.CYAN) +
                contact.getBirthDate()
        );
        System.out.println(
            ColorUtils.colorize("  Created At:       ", ColorUtils.CYAN) +
                contact.getCreatedAt()
        );
        System.out.println(
            ColorUtils.colorize("  Updated At:       ", ColorUtils.CYAN) +
                contact.getUpdatedAt()
        );
        System.out.println();
    }

    /**
     * Searches contacts by a single field.
     */
    protected void searchBySingleField() {
        while (true) {
            ColorUtils.clearScreen();
            System.out.println(
                ColorUtils.colorize(
                    "╔════════════════════════════════════════════════════════════╗",
                    ColorUtils.BRIGHT_CYAN
                )
            );
            System.out.println(
                ColorUtils.colorize(
                    "║              SEARCH BY SINGLE FIELD                        ║",
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

            // Show all contacts first for reference
            List<Contact> allContacts = contactManager.getAllContacts();
            if (allContacts.isEmpty()) {
                displayInfo("No contacts available to search.");
                pauseScreen();
                return;
            }

            System.out.println(
                ColorUtils.colorize(
                    "All contacts (for reference):",
                    ColorUtils.YELLOW
                )
            );
            System.out.println();
            displayContactList(allContacts);
            System.out.println();

            System.out.println(
                ColorUtils.colorize("Available fields:", ColorUtils.YELLOW)
            );
            System.out.println("  1. First Name");
            System.out.println("  2. Last Name");
            System.out.println("  3. Email");
            System.out.println("  4. Phone (Primary)");
            System.out.println("  5. Nickname");
            System.out.println("  6. Birth Date");
            System.out.println("  7. LinkedIn URL");
            System.out.println("  0. Cancel");
            System.out.println();

            System.out.print(
                ColorUtils.colorize("Select field to search: ", ColorUtils.CYAN)
            );
            String fieldChoice = scanner.nextLine().trim();

            if (fieldChoice.equals("0")) {
                return;
            }

            String fieldName = getFieldNameFromChoice(fieldChoice);
            if (fieldName == null) {
                displayError("Invalid field selection! Please try again.");
                pauseScreen();
                continue;
            }

            if (fieldName.equals("birth_date")) {
                System.out.print(
                    ColorUtils.colorize(
                        "Enter date (For Exact Search (YYYY-MM-DD)): ",
                        ColorUtils.CYAN
                    )
                );
            } else {
                System.out.print(
                    ColorUtils.colorize("Enter search value: ", ColorUtils.CYAN)
                );
            }
            String searchValue = scanner.nextLine().trim();

            if (searchValue.isEmpty()) {
                displayError("Search value cannot be empty!");
                pauseScreen();
                continue;
            }

            System.out.print(
                ColorUtils.colorize(
                    "Search type (1=Exact, 2=Partial): ",
                    ColorUtils.CYAN
                )
            );
            String matchType = scanner.nextLine().trim();

            if (!matchType.equals("1") && !matchType.equals("2")) {
                displayError("Invalid match type! Please enter 1 or 2.");
                pauseScreen();
                continue;
            }

            boolean exactMatch = matchType.equals("1");

            List<Contact> results = contactManager.searchByField(
                fieldName,
                searchValue,
                exactMatch
            );

            System.out.println();
            if (results.isEmpty()) {
                displayInfo("No contacts found matching your search.");
            } else {
                displaySuccess("Found " + results.size() + " contact(s):");
                System.out.println();
                displayContactList(results);
            }

            pauseScreen();

            System.out.print(
                ColorUtils.colorize(
                    "\nPerform another search? (y/n): ",
                    ColorUtils.CYAN
                )
            );
            String again = scanner.nextLine().trim().toLowerCase();
            if (!again.equals("y")) {
                break;
            }
        }
    }

    /**
     * Searches contacts by multiple fields.
     */
    protected void searchByMultipleFields() {
        while (true) {
            ColorUtils.clearScreen();
            System.out.println(
                ColorUtils.colorize(
                    "╔════════════════════════════════════════════════════════════╗",
                    ColorUtils.BRIGHT_CYAN
                )
            );
            System.out.println(
                ColorUtils.colorize(
                    "║             SEARCH BY MULTIPLE FIELDS                      ║",
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

            // Show all contacts first for reference
            List<Contact> allContacts = contactManager.getAllContacts();
            if (allContacts.isEmpty()) {
                displayInfo("No contacts available to search.");
                pauseScreen();
                return;
            }

            System.out.println(
                ColorUtils.colorize(
                    "All contacts (for reference):",
                    ColorUtils.YELLOW
                )
            );
            System.out.println();
            displayContactList(allContacts);
            System.out.println();

            System.out.print(
                ColorUtils.colorize(
                    "Logical operator (AND/OR, or 0 to cancel): ",
                    ColorUtils.CYAN
                )
            );
            String logicalOp = scanner.nextLine().trim().toUpperCase();

            if (logicalOp.equals("0")) {
                displayInfo("Search cancelled.");
                pauseScreen();
                return;
            }

            if (!logicalOp.equals("AND") && !logicalOp.equals("OR")) {
                displayError("Invalid operator! Please enter AND or OR.");
                pauseScreen();
                continue;
            }

            SearchCriteria criteria = new SearchCriteria(logicalOp);
            boolean addingCriteria = true;
            while (addingCriteria) {
                System.out.println();
                System.out.println(
                    ColorUtils.colorize("Available fields:", ColorUtils.YELLOW)
                );
                System.out.println("  1. First Name");
                System.out.println("  2. Last Name");
                System.out.println("  3. Email");
                System.out.println("  4. Phone (Primary)");
                System.out.println("  5. Nickname");
                System.out.println("  6. Birth Date");
                System.out.println();

                System.out.print(
                    ColorUtils.colorize(
                        "Select field (or 0 to finish/cancel): ",
                        ColorUtils.CYAN
                    )
                );
                String fieldChoice = scanner.nextLine().trim();

                if (fieldChoice.equals("0")) {
                    if (criteria.getCriteriaCount() == 0) {
                        displayInfo("Search cancelled.");
                        pauseScreen();
                        return;
                    }
                    break;
                }

                String fieldName = getFieldNameFromChoice(fieldChoice);
                if (fieldName == null) {
                    displayError("Invalid field selection! Please try again.");
                    continue;
                }

                System.out.print(
                    ColorUtils.colorize("Enter search value: ", ColorUtils.CYAN)
                );
                String searchValue = scanner.nextLine().trim();

                if (searchValue.isEmpty()) {
                    displayError(
                        "Search value cannot be empty! Please try again."
                    );
                    continue;
                }

                System.out.print(
                    ColorUtils.colorize(
                        "Match type (1=Exact, 2=Partial): ",
                        ColorUtils.CYAN
                    )
                );
                String matchType = scanner.nextLine().trim();

                if (!matchType.equals("1") && !matchType.equals("2")) {
                    displayError("Invalid match type! Please enter 1 or 2.");
                    continue;
                }

                if (matchType.equals("1")) {
                    criteria.addExactMatch(fieldName, searchValue);
                } else {
                    criteria.addPartialMatch(fieldName, searchValue);
                }

                displaySuccess("Criterion added!");

                if (criteria.getCriteriaCount() >= 2) {
                    System.out.print(
                        ColorUtils.colorize(
                            "Add another criterion? (y/n): ",
                            ColorUtils.CYAN
                        )
                    );
                    String more = scanner.nextLine().trim().toLowerCase();
                    if (!more.equals("y")) {
                        addingCriteria = false;
                    }
                }
            }

            if (criteria.getCriteriaCount() < 2) {
                displayError("Need at least 2 search criteria!");
                pauseScreen();
                continue;
            }

            List<Contact> results = contactManager.searchByCriteria(criteria);

            System.out.println();
            System.out.println(
                ColorUtils.colorize(
                    "Search criteria: " + criteria.toString(),
                    ColorUtils.YELLOW
                )
            );
            System.out.println();

            if (results.isEmpty()) {
                displayInfo("No contacts found matching your search.");
            } else {
                displaySuccess("Found " + results.size() + " contact(s):");
                System.out.println();
                displayContactList(results);
            }

            pauseScreen();

            System.out.print(
                ColorUtils.colorize(
                    "\nPerform another search? (y/n): ",
                    ColorUtils.CYAN
                )
            );
            String again = scanner.nextLine().trim().toLowerCase();
            if (!again.equals("y")) {
                break;
            }
        }
    }

    /**
     * Advanced search with user-defined multi-field queries.
     */
    protected void advancedSearch() {
        while (true) {
            ColorUtils.clearScreen();
            System.out.println(
                ColorUtils.colorize(
                    "╔════════════════════════════════════════════════════════════╗",
                    ColorUtils.BRIGHT_CYAN
                )
            );
            System.out.println(
                ColorUtils.colorize(
                    "║                  ADVANCED SEARCH                           ║",
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

            // Show all contacts first for reference
            List<Contact> allContacts = contactManager.getAllContacts();
            if (allContacts.isEmpty()) {
                displayInfo("No contacts available to search.");
                pauseScreen();
                return;
            }

            System.out.println(
                ColorUtils.colorize(
                    "All contacts (for reference):",
                    ColorUtils.YELLOW
                )
            );
            System.out.println();
            displayContactList(allContacts);
            System.out.println();

            System.out.println(
                ColorUtils.colorize(
                    "Build a custom search query:",
                    ColorUtils.YELLOW
                )
            );
            System.out.println();

            System.out.print(
                ColorUtils.colorize(
                    "Use AND or OR operator? (AND/OR, or 0 to cancel): ",
                    ColorUtils.CYAN
                )
            );
            String operator = scanner.nextLine().trim().toUpperCase();

            if (operator.equals("0")) {
                displayInfo("Search cancelled.");
                pauseScreen();
                return;
            }

            if (!operator.equals("AND") && !operator.equals("OR")) {
                displayError("Invalid operator! Please enter AND or OR.");
                pauseScreen();
                continue;
            }

            SearchCriteria criteria = new SearchCriteria(operator);

            int conditionCount = 0;
            while (true) {
                System.out.print(
                    ColorUtils.colorize(
                        "How many search conditions? (2-5, or 0 to cancel): ",
                        ColorUtils.CYAN
                    )
                );
                String countStr = scanner.nextLine().trim();

                if (countStr.equals("0")) {
                    displayInfo("Search cancelled.");
                    pauseScreen();
                    return;
                }

                try {
                    conditionCount = Integer.parseInt(countStr);
                    if (conditionCount < 2 || conditionCount > 5) {
                        displayError(
                            "Number must be between 2 and 5! Please try again."
                        );
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    displayError(
                        "Invalid number! Please enter a number between 2 and 5."
                    );
                }
            }

            int validConditions = 0;
            for (int i = 1; i <= conditionCount; i++) {
                System.out.println();
                System.out.println(
                    ColorUtils.colorize(
                        "Condition " + i + ":",
                        ColorUtils.BRIGHT_YELLOW
                    )
                );

                System.out.println("  1. First Name    4. Phone");
                System.out.println("  2. Last Name     5. Email");
                System.out.println("  3. Nickname      6. Birth Date");

                String fieldName = null;
                while (fieldName == null) {
                    System.out.print(
                        ColorUtils.colorize("  Select field: ", ColorUtils.CYAN)
                    );
                    String fieldChoice = scanner.nextLine().trim();

                    fieldName = getFieldNameFromChoice(fieldChoice);
                    if (fieldName == null) {
                        displayError("  Invalid field! Please select 1-6.");
                    }
                }

                String value = null;
                while (value == null || value.isEmpty()) {
                    System.out.print(
                        ColorUtils.colorize(
                            "  Enter value to search: ",
                            ColorUtils.CYAN
                        )
                    );
                    value = scanner.nextLine().trim();

                    if (value.isEmpty()) {
                        displayError(
                            "  Value cannot be empty! Please try again."
                        );
                    }
                }

                String matchChoice = null;
                while (matchChoice == null) {
                    System.out.print(
                        ColorUtils.colorize(
                            "  Match type (1=Exact, 2=Contains): ",
                            ColorUtils.CYAN
                        )
                    );
                    matchChoice = scanner.nextLine().trim();

                    if (!matchChoice.equals("1") && !matchChoice.equals("2")) {
                        displayError("  Invalid choice! Please enter 1 or 2.");
                        matchChoice = null;
                    }
                }

                if (matchChoice.equals("1")) {
                    criteria.addExactMatch(fieldName, value);
                } else {
                    criteria.addPartialMatch(fieldName, value);
                }
                validConditions++;
            }

            if (validConditions < 2) {
                displayError("Need at least 2 valid search criteria!");
                pauseScreen();
                continue;
            }

            List<Contact> results = contactManager.searchByCriteria(criteria);

            System.out.println();
            System.out.println(
                ColorUtils.colorize(
                    "═══════════════════════════════════════════════════════════",
                    ColorUtils.BRIGHT_BLUE
                )
            );
            System.out.println(
                ColorUtils.colorize(
                    "Query: " + criteria.toString(),
                    ColorUtils.YELLOW
                )
            );
            System.out.println(
                ColorUtils.colorize(
                    "═══════════════════════════════════════════════════════════",
                    ColorUtils.BRIGHT_BLUE
                )
            );
            System.out.println();

            if (results.isEmpty()) {
                displayInfo("No contacts found matching your query.");
            } else {
                displaySuccess("Found " + results.size() + " contact(s):");
                System.out.println();
                displayContactList(results);
            }

            pauseScreen();

            System.out.print(
                ColorUtils.colorize(
                    "\nPerform another search? (y/n): ",
                    ColorUtils.CYAN
                )
            );
            String again = scanner.nextLine().trim().toLowerCase();
            if (!again.equals("y")) {
                break;
            }
        }
    }

    /**
     * Sorts contacts by a selected field.
     */
    protected void sortContacts() {
        while (true) {
            ColorUtils.clearScreen();
            System.out.println(
                ColorUtils.colorize(
                    "╔════════════════════════════════════════════════════════════╗",
                    ColorUtils.BRIGHT_CYAN
                )
            );
            System.out.println(
                ColorUtils.colorize(
                    "║                    SORT CONTACTS                           ║",
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

            List<Contact> contacts = contactManager.getAllContacts();

            if (contacts.isEmpty()) {
                displayInfo("No contacts to sort.");
                pauseScreen();
                return;
            }

            System.out.println(
                ColorUtils.colorize(
                    "Select field to sort by:",
                    ColorUtils.YELLOW
                )
            );
            System.out.println("  1. Contact ID");
            System.out.println("  2. First Name");
            System.out.println("  3. Last Name");
            System.out.println("  4. Email");
            System.out.println("  5. Phone (Primary)");
            System.out.println("  6. Birth Date");
            System.out.println("  0. Cancel");
            System.out.println();

            System.out.print(
                ColorUtils.colorize("Select field: ", ColorUtils.CYAN)
            );
            String fieldChoice = scanner.nextLine().trim();

            if (fieldChoice.equals("0")) {
                return;
            }

            String fieldName = getSortFieldNameFromChoice(fieldChoice);
            if (fieldName == null) {
                displayError("Invalid field selection! Please select 1-6.");
                pauseScreen();
                continue;
            }

            System.out.print(
                ColorUtils.colorize(
                    "Sort order (1=Ascending, 2=Descending): ",
                    ColorUtils.CYAN
                )
            );
            String orderChoice = scanner.nextLine().trim();

            if (!orderChoice.equals("1") && !orderChoice.equals("2")) {
                displayError("Invalid sort order! Please enter 1 or 2.");
                pauseScreen();
                continue;
            }

            boolean ascending = orderChoice.equals("1");

            List<Contact> sortedContacts = contactManager.sortContacts(
                contacts,
                fieldName,
                ascending
            );

            System.out.println();
            displaySuccess(
                "Contacts sorted by " +
                    fieldName +
                    " (" +
                    (ascending ? "Ascending" : "Descending") +
                    ")"
            );
            System.out.println();
            displayContactList(sortedContacts);

            pauseScreen();

            System.out.print(
                ColorUtils.colorize("\nSort again? (y/n): ", ColorUtils.CYAN)
            );
            String again = scanner.nextLine().trim().toLowerCase();
            if (!again.equals("y")) {
                break;
            }
        }
    }

    /**
     * Converts field choice number to field name for search operations.
     *
     * @param choice The choice number
     * @return The field name, or null if invalid
     */
    protected String getFieldNameFromChoice(String choice) {
        switch (choice) {
            case "1":
                return "first_name";
            case "2":
                return "last_name";
            case "3":
                return "email";
            case "4":
                return "phone_primary";
            case "5":
                return "nickname";
            case "6":
                return "birth_date";
            case "7":
                return "linkedin_url";
            default:
                return null;
        }
    }

    /**
     * Converts field choice number to field name for sort operations.
     *
     * @param choice The choice number
     * @return The field name, or null if invalid
     */
    protected String getSortFieldNameFromChoice(String choice) {
        switch (choice) {
            case "1":
                return "contact_id";
            case "2":
                return "first_name";
            case "3":
                return "last_name";
            case "4":
                return "email";
            case "5":
                return "phone_primary";
            case "6":
                return "birth_date";
            default:
                return null;
        }
    }

    /**
     * Truncates a string to a specified length.
     *
     * @param str       The string to truncate
     * @param maxLength The maximum length
     * @return The truncated string
     */
    protected String truncate(String str, int maxLength) {
        if (str == null) {
            return "";
        }
        if (str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength - 3) + "...";
    }
}
