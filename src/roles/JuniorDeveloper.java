package roles;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;
import managers.DatabaseManager;
import models.Contact;
import models.User;
import utils.ColorUtils;
import utils.ValidationUtils;

/**
 * JuniorDeveloper role class extending Tester.
 * Demonstrates inheritance by extending Tester and adding update contact functionality.
 * Has all permissions of Tester plus the ability to update existing contacts.
 *
 * @author CMPE-343 Project Group
 * @version 1.0
 */
public class JuniorDeveloper extends Tester {

    /**
     * Constructor for JuniorDeveloper role.
     *
     * @param user The current logged-in user
     * @param dbManager The database manager instance
     * @param scanner The scanner for user input
     */
    public JuniorDeveloper(
        User user,
        DatabaseManager dbManager,
        Scanner scanner
    ) {
        super(user, dbManager, scanner);
    }

    @Override
    protected String getRoleName() {
        return "Junior Developer";
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
            ColorUtils.colorize(
                "  6. Update Existing Contact",
                ColorUtils.BRIGHT_GREEN
            )
        );
        System.out.println(
            ColorUtils.colorize("  7. Undo Last Operation", ColorUtils.YELLOW)
        );
        System.out.println(
            ColorUtils.colorize("  8. Change Password", ColorUtils.WHITE)
        );
        System.out.println(
            ColorUtils.colorize("  9. Logout", ColorUtils.WHITE)
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
                updateContact();
                break;
            case "7":
                undoLastOperation();
                break;
            case "8":
                changePassword();
                break;
            case "9":
                logout();
                break;
            default:
                displayInvalidChoice();
                break;
        }
    }

    /**
     * Updates an existing contact in the database.
     * Demonstrates polymorphism by extending base class functionality.
     */
    protected void updateContact() {
        while (true) {
            ColorUtils.clearScreen();
            System.out.println(
                ColorUtils.colorize(
                    "╔════════════════════════════════════════════════════════════╗",
                    ColorUtils.BRIGHT_GREEN
                )
            );
            System.out.println(
                ColorUtils.colorize(
                    "║                  UPDATE CONTACT                            ║",
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

            // Show all contacts first
            List<Contact> allContacts = contactManager.getAllContacts();
            if (allContacts.isEmpty()) {
                displayInfo("No contacts available to update.");
                pauseScreen();
                return;
            }

            System.out.println(
                ColorUtils.colorize("Available contacts:", ColorUtils.YELLOW)
            );
            System.out.println();
            displayContactList(allContacts);
            System.out.println();

            System.out.print(
                ColorUtils.colorize(
                    "Enter Contact ID to update (or 0 to cancel): ",
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
                    "Invalid Contact ID! Must be a number. Please try again."
                );
                pauseScreen();
                continue;
            }

            int contactId = Integer.parseInt(idInput);
            Contact existingContact = contactManager.getContactById(contactId);

            if (existingContact == null) {
                displayError(
                    "Contact with ID " +
                        contactId +
                        " not found! Please try again."
                );
                pauseScreen();
                continue;
            }

            // Display current contact details
            System.out.println();
            System.out.println(
                ColorUtils.colorize(
                    "Current Contact Information:",
                    ColorUtils.YELLOW
                )
            );
            displayContactDetails(existingContact);

            // Save previous state for undo
            Contact previousState = cloneContact(existingContact);

            System.out.println(
                ColorUtils.colorize(
                    "Enter new values (press Enter to keep current value):",
                    ColorUtils.YELLOW
                )
            );
            System.out.println();

            // First Name
            while (true) {
                System.out.print(
                    ColorUtils.colorize(
                        "First Name [" + existingContact.getFirstName() + "]: ",
                        ColorUtils.CYAN
                    )
                );
                String firstName = scanner.nextLine().trim();
                if (firstName.isEmpty()) {
                    break; // Keep current value
                }
                if (!ValidationUtils.isValidName(firstName)) {
                    displayError(
                        ValidationUtils.getNameError() + " Please try again."
                    );
                    continue;
                }
                existingContact.setFirstName(firstName);
                break;
            }

            // Middle Name
            while (true) {
                System.out.print(
                    ColorUtils.colorize(
                        "Middle Name [" +
                            (existingContact.getMiddleName() != null
                                ? existingContact.getMiddleName()
                                : "N/A") +
                            "]: ",
                        ColorUtils.CYAN
                    )
                );
                String middleName = scanner.nextLine().trim();
                if (middleName.isEmpty()) {
                    break; // Keep current value
                }
                if (!ValidationUtils.isValidName(middleName)) {
                    displayError(
                        ValidationUtils.getNameError() + " Please try again."
                    );
                    continue;
                }
                existingContact.setMiddleName(middleName);
                break;
            }

            // Last Name
            while (true) {
                System.out.print(
                    ColorUtils.colorize(
                        "Last Name [" + existingContact.getLastName() + "]: ",
                        ColorUtils.CYAN
                    )
                );
                String lastName = scanner.nextLine().trim();
                if (lastName.isEmpty()) {
                    break; // Keep current value
                }
                if (!ValidationUtils.isValidName(lastName)) {
                    displayError(
                        ValidationUtils.getNameError() + " Please try again."
                    );
                    continue;
                }
                existingContact.setLastName(lastName);
                break;
            }

            // Nickname
            System.out.print(
                ColorUtils.colorize(
                    "Nickname [" +
                        (existingContact.getNickname() != null
                            ? existingContact.getNickname()
                            : "N/A") +
                        "]: ",
                    ColorUtils.CYAN
                )
            );
            String nickname = scanner.nextLine().trim();
            if (!nickname.isEmpty()) {
                existingContact.setNickname(nickname);
            }

            // Primary Phone
            while (true) {
                System.out.print(
                    ColorUtils.colorize(
                        "Primary Phone " +
                            ValidationUtils.getPhoneFormatHint() +
                            " [" +
                            existingContact.getPhonePrimary() +
                            "]: ",
                        ColorUtils.CYAN
                    )
                );
                String phonePrimary = scanner.nextLine().trim();
                if (phonePrimary.isEmpty()) {
                    break; // Keep current value
                }
                if (!ValidationUtils.isValidPhone(phonePrimary)) {
                    displayError(
                        "Invalid phone format! " +
                            ValidationUtils.getPhoneFormatHint()
                    );
                    continue;
                }
                existingContact.setPhonePrimary(
                    ValidationUtils.cleanPhone(phonePrimary)
                );
                break;
            }

            // Secondary Phone
            while (true) {
                System.out.print(
                    ColorUtils.colorize(
                        "Secondary Phone " +
                            ValidationUtils.getPhoneFormatHint() +
                            " [" +
                            (existingContact.getPhoneSecondary() != null
                                ? existingContact.getPhoneSecondary()
                                : "N/A") +
                            "]: ",
                        ColorUtils.CYAN
                    )
                );
                String phoneSecondary = scanner.nextLine().trim();
                if (phoneSecondary.isEmpty()) {
                    break; // Keep current value
                }
                if (!ValidationUtils.isValidPhone(phoneSecondary)) {
                    displayError(
                        "Invalid phone format! " +
                            ValidationUtils.getPhoneFormatHint()
                    );
                    continue;
                }
                existingContact.setPhoneSecondary(
                    ValidationUtils.cleanPhone(phoneSecondary)
                );
                break;
            }

            // Email
            while (true) {
                System.out.print(
                    ColorUtils.colorize(
                        "Email " +
                            ValidationUtils.getEmailFormatHint() +
                            " [" +
                            existingContact.getEmail() +
                            "]: ",
                        ColorUtils.CYAN
                    )
                );
                String email = scanner.nextLine().trim();
                if (email.isEmpty()) {
                    break; // Keep current value
                }

                // Convert to lowercase for consistency
                email = email.toLowerCase();

                if (!ValidationUtils.isValidEmail(email)) {
                    displayError(
                        "Invalid email format! " +
                            ValidationUtils.getEmailFormatHint()
                    );
                    continue;
                }
                if (
                    contactManager.emailExistsForOtherContact(email, contactId)
                ) {
                    displayError(
                        "Email already exists for another contact! Please use a different email."
                    );
                    continue;
                }
                existingContact.setEmail(email);
                break;
            }

            // LinkedIn URL
            while (true) {
                System.out.print(
                    ColorUtils.colorize(
                        "LinkedIn URL [" +
                            (existingContact.getLinkedinUrl() != null
                                ? existingContact.getLinkedinUrl()
                                : "N/A") +
                            "]: ",
                        ColorUtils.CYAN
                    )
                );
                String linkedinUrl = scanner.nextLine().trim();
                if (linkedinUrl.isEmpty()) {
                    break; // Keep current value
                }
                if (!ValidationUtils.isValidLinkedInUrl(linkedinUrl)) {
                    displayError(
                        ValidationUtils.getLinkedInError() +
                            " Please try again."
                    );
                    continue;
                }
                existingContact.setLinkedinUrl(linkedinUrl);
                break;
            }

            // Birth Date
            while (true) {
                System.out.print(
                    ColorUtils.colorize(
                        "Birth Date " +
                            ValidationUtils.getDateFormatHint() +
                            " [" +
                            existingContact.getBirthDate() +
                            "]: ",
                        ColorUtils.CYAN
                    )
                );
                String birthDateStr = scanner.nextLine().trim();
                if (birthDateStr.isEmpty()) {
                    break; // Keep current value
                }
                if (!ValidationUtils.isValidBirthDate(birthDateStr)) {
                    displayError(
                        ValidationUtils.getBirthDateError() +
                            " " +
                            ValidationUtils.getDateFormatHint()
                    );
                    continue;
                }
                existingContact.setBirthDate(Date.valueOf(birthDateStr));
                break;
            }

            // Confirm update with validation
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
                continue;
            }

            // Perform update
            if (contactManager.updateContact(existingContact)) {
                // Record for undo
                undoManager.recordUpdateContact(
                    previousState,
                    "Updated contact: " + existingContact.getFullName()
                );
                displaySuccess("Contact updated successfully!");
            } else {
                displayError("Failed to update contact. Please try again.");
                pauseScreen();
                continue;
            }

            pauseScreen();

            System.out.print(
                ColorUtils.colorize(
                    "\nUpdate another contact? (y/n): ",
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
     * Clones a contact object for undo functionality.
     *
     * @param contact The contact to clone
     * @return A new Contact object with the same data
     */
    private Contact cloneContact(Contact contact) {
        Contact clone = new Contact();
        clone.setContactId(contact.getContactId());
        clone.setFirstName(contact.getFirstName());
        clone.setMiddleName(contact.getMiddleName());
        clone.setLastName(contact.getLastName());
        clone.setNickname(contact.getNickname());
        clone.setPhonePrimary(contact.getPhonePrimary());
        clone.setPhoneSecondary(contact.getPhoneSecondary());
        clone.setEmail(contact.getEmail());
        clone.setLinkedinUrl(contact.getLinkedinUrl());
        clone.setBirthDate(contact.getBirthDate());
        clone.setCreatedAt(contact.getCreatedAt());
        clone.setUpdatedAt(contact.getUpdatedAt());
        return clone;
    }

    /**
     * Undoes the last operation performed.
     */
    protected void undoLastOperation() {
        ColorUtils.clearScreen();
        System.out.println(
            ColorUtils.colorize(
                "╔════════════════════════════════════════════════════════════╗",
                ColorUtils.YELLOW
            )
        );
        System.out.println(
            ColorUtils.colorize(
                "║                    UNDO OPERATION                          ║",
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

        if (!undoManager.canUndo()) {
            displayInfo("No operations to undo.");
            pauseScreen();
            return;
        }

        // Undo operations one by one with individual confirmations
        int undoneCount = 0;

        while (undoManager.canUndo()) {
            // Show all available undo operations at each iteration
            java.util.List<String> history = undoManager.getUndoHistory();

            if (history.isEmpty()) {
                break;
            }

            System.out.println();
            System.out.println(
                ColorUtils.colorize(
                    "═══════════════════════════════════════",
                    ColorUtils.BRIGHT_CYAN
                )
            );
            System.out.println(
                ColorUtils.colorize(
                    "Available undo operations (" + history.size() + "):",
                    ColorUtils.YELLOW
                )
            );
            System.out.println();

            for (int i = history.size() - 1; i >= 0; i--) {
                System.out.println(
                    ColorUtils.colorize(
                            "  " + (history.size() - i) + ". ",
                            ColorUtils.CYAN
                        ) +
                        history.get(i)
                );
            }
            System.out.println(
                ColorUtils.colorize(
                    "═══════════════════════════════════════",
                    ColorUtils.BRIGHT_CYAN
                )
            );
            System.out.println();

            managers.UndoManager.UndoOperation operation =
                undoManager.peekLastOperation();

            System.out.println(
                ColorUtils.colorize(
                        "Next operation to undo (most recent): ",
                        ColorUtils.YELLOW
                    ) +
                    operation.getDescription()
            );
            System.out.println();

            // Ask for confirmation for this specific operation
            String confirm = null;
            while (confirm == null) {
                System.out.print(
                    ColorUtils.colorize(
                        "Undo this operation? (yes/no): ",
                        ColorUtils.YELLOW
                    )
                );
                String input = scanner.nextLine().trim().toLowerCase();

                if (utils.ValidationUtils.isValidYesNo(input)) {
                    confirm = input;
                } else {
                    displayError("Invalid input! Please enter 'yes' or 'no'.");
                }
            }

            if (utils.ValidationUtils.yesNoToBoolean(confirm)) {
                // Pop the operation and perform undo
                managers.UndoManager.UndoOperation op =
                    undoManager.popLastOperation();

                if (undoManager.undoContactOperation(op, contactManager)) {
                    displaySuccess(
                        "✓ Operation undone: " + op.getDescription()
                    );
                    undoneCount++;
                } else {
                    displayError("✗ Failed to undo: " + op.getDescription());
                }

                // Ask if user wants to continue undoing
                if (undoManager.canUndo()) {
                    System.out.println();
                    String continueUndo = null;
                    while (continueUndo == null) {
                        System.out.print(
                            ColorUtils.colorize(
                                "Continue undoing more operations? (yes/no): ",
                                ColorUtils.CYAN
                            )
                        );
                        String input = scanner.nextLine().trim().toLowerCase();

                        if (utils.ValidationUtils.isValidYesNo(input)) {
                            continueUndo = input;
                        } else {
                            displayError(
                                "Invalid input! Please enter 'yes' or 'no'."
                            );
                        }
                    }

                    if (!utils.ValidationUtils.yesNoToBoolean(continueUndo)) {
                        displayInfo("Undo process stopped.");
                        break;
                    }
                }
            } else {
                // User chose not to undo this operation - stop the process
                displayInfo(
                    "Undo cancelled. Operation remains in history and can be undone later."
                );
                break;
            }
        }

        if (undoneCount == 0) {
            displayInfo("No operations were undone.");
        } else {
            System.out.println();
            System.out.println(
                ColorUtils.colorize(
                    "═══════════════════════════════════════",
                    ColorUtils.BRIGHT_CYAN
                )
            );
            System.out.println(
                ColorUtils.colorize(
                    "Summary: " +
                        undoneCount +
                        " operation(s) undone successfully.",
                    ColorUtils.GREEN
                )
            );
            System.out.println(
                ColorUtils.colorize(
                    "═══════════════════════════════════════",
                    ColorUtils.BRIGHT_CYAN
                )
            );
        }

        pauseScreen();
    }
}
