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
     * @param scanner The scanner for user input
     */
    public Tester(DatabaseManager dbManager, Scanner scanner) {
        super(null, dbManager, scanner);
        this.contactManager = new ContactManager(dbManager);
    }

    /**
     * Constructor for Tester role with user.
     *
     * @param user The current logged-in user
     * @param dbManager The database manager instance
     * @param scanner The scanner for user input
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

        System.out.println(
            ColorUtils.colorize("Available fields:", ColorUtils.YELLOW)
        );
        System.out.println("  1. First Name");
        System.out.println("  2. Last Name");
        System.out.println("  3. Email");
        System.out.println("  4. Phone (Primary)");
        System.out.println("  5. Nickname");
        System.out.println("  6. LinkedIn URL");
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
            displayError("Invalid field selection!");
            pauseScreen();
            return;
        }

        System.out.print(
            ColorUtils.colorize("Enter search value: ", ColorUtils.CYAN)
        );
        String searchValue = scanner.nextLine().trim();

        if (searchValue.isEmpty()) {
            displayError("Search value cannot be empty!");
            pauseScreen();
            return;
        }

        System.out.print(
            ColorUtils.colorize(
                "Search type (1=Exact, 2=Partial): ",
                ColorUtils.CYAN
            )
        );
        String matchType = scanner.nextLine().trim();

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
    }

    /**
     * Searches contacts by multiple fields.
     */
    protected void searchByMultipleFields() {
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

        System.out.print(
            ColorUtils.colorize("Logical operator (AND/OR): ", ColorUtils.CYAN)
        );
        String logicalOp = scanner.nextLine().trim().toUpperCase();

        if (!logicalOp.equals("AND") && !logicalOp.equals("OR")) {
            logicalOp = "AND";
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
                    "Select field (or 0 to finish): ",
                    ColorUtils.CYAN
                )
            );
            String fieldChoice = scanner.nextLine().trim();

            if (fieldChoice.equals("0")) {
                addingCriteria = false;
                continue;
            }

            String fieldName = getFieldNameFromChoice(fieldChoice);
            if (fieldName == null) {
                displayError("Invalid field selection!");
                continue;
            }

            System.out.print(
                ColorUtils.colorize("Enter search value: ", ColorUtils.CYAN)
            );
            String searchValue = scanner.nextLine().trim();

            if (searchValue.isEmpty()) {
                displayError("Search value cannot be empty!");
                continue;
            }

            System.out.print(
                ColorUtils.colorize(
                    "Match type (1=Exact, 2=Partial): ",
                    ColorUtils.CYAN
                )
            );
            String matchType = scanner.nextLine().trim();

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

        if (!criteria.hasCriteria()) {
            displayWarning("No search criteria specified!");
            pauseScreen();
            return;
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
    }

    /**
     * Advanced search with user-defined multi-field queries.
     */
    protected void advancedSearch() {
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

        System.out.println(
            ColorUtils.colorize(
                "Build a custom search query:",
                ColorUtils.YELLOW
            )
        );
        System.out.println();

        System.out.print(
            ColorUtils.colorize(
                "Use AND or OR operator? (AND/OR): ",
                ColorUtils.CYAN
            )
        );
        String operator = scanner.nextLine().trim().toUpperCase();

        if (!operator.equals("AND") && !operator.equals("OR")) {
            operator = "AND";
        }

        SearchCriteria criteria = new SearchCriteria(operator);

        System.out.print(
            ColorUtils.colorize(
                "How many search conditions? (2-5): ",
                ColorUtils.CYAN
            )
        );
        String countStr = scanner.nextLine().trim();

        int conditionCount = 2;
        try {
            conditionCount = Integer.parseInt(countStr);
            if (conditionCount < 2) conditionCount = 2;
            if (conditionCount > 5) conditionCount = 5;
        } catch (NumberFormatException e) {
            conditionCount = 2;
        }

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

            System.out.print(
                ColorUtils.colorize("  Select field: ", ColorUtils.CYAN)
            );
            String fieldChoice = scanner.nextLine().trim();

            String fieldName = getFieldNameFromChoice(fieldChoice);
            if (fieldName == null) {
                displayWarning("Invalid field! Skipping...");
                continue;
            }

            System.out.print(
                ColorUtils.colorize(
                    "  Enter value to search: ",
                    ColorUtils.CYAN
                )
            );
            String value = scanner.nextLine().trim();

            if (value.isEmpty()) {
                displayWarning("Empty value! Skipping...");
                continue;
            }

            System.out.print(
                ColorUtils.colorize(
                    "  Match type (1=Exact, 2=Contains): ",
                    ColorUtils.CYAN
                )
            );
            String matchChoice = scanner.nextLine().trim();

            if (matchChoice.equals("1")) {
                criteria.addExactMatch(fieldName, value);
            } else {
                criteria.addPartialMatch(fieldName, value);
            }
        }

        if (!criteria.hasCriteria()) {
            displayWarning("No valid search criteria specified!");
            pauseScreen();
            return;
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
    }

    /**
     * Sorts contacts by a selected field.
     */
    protected void sortContacts() {
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
            ColorUtils.colorize("Select field to sort by:", ColorUtils.YELLOW)
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

        String fieldName = getFieldNameFromChoice(fieldChoice);
        if (fieldName == null) {
            displayError("Invalid field selection!");
            pauseScreen();
            return;
        }

        System.out.print(
            ColorUtils.colorize(
                "Sort order (1=Ascending, 2=Descending): ",
                ColorUtils.CYAN
            )
        );
        String orderChoice = scanner.nextLine().trim();

        boolean ascending = !orderChoice.equals("2");

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
    }

    /**
     * Converts field choice number to field name.
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
                return "contact_id".equals(choice) ? "contact_id" : null;
        }
    }

    /**
     * Truncates a string to a specified length.
     *
     * @param str The string to truncate
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
