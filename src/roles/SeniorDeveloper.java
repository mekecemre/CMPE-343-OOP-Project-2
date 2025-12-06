package roles;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import managers.DatabaseManager;
import models.Contact;
import models.User;
import utils.ColorUtils;
import utils.ValidationUtils;

/**
 * SeniorDeveloper role class extending JuniorDeveloper.
 * Demonstrates inheritance by extending JuniorDeveloper and adding add/delete contact functionality.
 * Has all permissions of JuniorDeveloper plus the ability to add and delete contacts.
 *
 * @author CMPE-343 Project Group
 * @version 1.0
 */
public class SeniorDeveloper extends JuniorDeveloper {

    /**
     * Constructor for SeniorDeveloper role.
     *
     * @param user The current logged-in user
     * @param dbManager The database manager instance
     * @param scanner The scanner for user input
     */
    public SeniorDeveloper(
        User user,
        DatabaseManager dbManager,
        Scanner scanner
    ) {
        super(user, dbManager, scanner);
    }

    @Override
    protected String getRoleName() {
        return "Senior Developer";
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
            ColorUtils.colorize("  7. Add New Contact", ColorUtils.BRIGHT_GREEN)
        );
        System.out.println(
            ColorUtils.colorize(
                "  8. Add Multiple Contacts",
                ColorUtils.BRIGHT_GREEN
            )
        );
        System.out.println(
            ColorUtils.colorize("  9. Delete Contact", ColorUtils.BRIGHT_RED)
        );
        System.out.println(
            ColorUtils.colorize(
                " 10. Delete Multiple Contacts",
                ColorUtils.BRIGHT_RED
            )
        );
        System.out.println(
            ColorUtils.colorize(" 11. Undo Last Operation", ColorUtils.YELLOW)
        );
        System.out.println(
            ColorUtils.colorize(" 12. Change Password", ColorUtils.WHITE)
        );
        System.out.println(
            ColorUtils.colorize(" 13. Logout", ColorUtils.WHITE)
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
                addNewContact();
                break;
            case "8":
                addMultipleContacts();
                break;
            case "9":
                deleteContact();
                break;
            case "10":
                deleteMultipleContacts();
                break;
            case "11":
                undoLastOperation();
                break;
            case "12":
                changePassword();
                break;
            case "13":
                logout();
                break;
            default:
                displayInvalidChoice();
                break;
        }
    }

    /**
     * Adds a new contact to the database.
     * Demonstrates polymorphism by extending base class functionality.
     */
    protected void addNewContact() {
        ColorUtils.clearScreen();
        System.out.println(
            ColorUtils.colorize(
                "╔════════════════════════════════════════════════════════════╗",
                ColorUtils.BRIGHT_GREEN
            )
        );
        System.out.println(
            ColorUtils.colorize(
                "║                    ADD NEW CONTACT                         ║",
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

        Contact newContact = new Contact();

        // First Name (Required)
        while (true) {
            System.out.print(
                ColorUtils.colorize("First Name (required): ", ColorUtils.CYAN)
            );
            String firstName = scanner.nextLine().trim();

            if (firstName.isEmpty()) {
                displayError("First name is required!");
                continue;
            }

            if (!ValidationUtils.isValidName(firstName)) {
                displayError(ValidationUtils.getNameError());
                continue;
            }

            newContact.setFirstName(firstName);
            break;
        }

        // Middle Name (Optional)
        while (true) {
            System.out.print(
                ColorUtils.colorize(
                    "Middle Name (optional, press Enter to skip): ",
                    ColorUtils.CYAN
                )
            );
            String middleName = scanner.nextLine().trim();
            if (middleName.isEmpty()) {
                break; // Skip optional field
            }
            if (!ValidationUtils.isValidName(middleName)) {
                displayError(ValidationUtils.getNameError());
                continue;
            }
            newContact.setMiddleName(middleName);
            break;
        }

        // Last Name (Required)
        while (true) {
            System.out.print(
                ColorUtils.colorize("Last Name (required): ", ColorUtils.CYAN)
            );
            String lastName = scanner.nextLine().trim();

            if (lastName.isEmpty()) {
                displayError("Last name is required!");
                continue;
            }

            if (!ValidationUtils.isValidName(lastName)) {
                displayError(ValidationUtils.getNameError());
                continue;
            }

            newContact.setLastName(lastName);
            break;
        }

        // Nickname (Optional)
        System.out.print(
            ColorUtils.colorize("Nickname (optional): ", ColorUtils.CYAN)
        );
        String nickname = scanner.nextLine().trim();
        if (!nickname.isEmpty()) {
            newContact.setNickname(nickname);
        }

        // Primary Phone (Required)
        while (true) {
            System.out.print(
                ColorUtils.colorize(
                    "Primary Phone " +
                        ValidationUtils.getPhoneFormatHint() +
                        " (required): ",
                    ColorUtils.CYAN
                )
            );
            String phonePrimary = scanner.nextLine().trim();

            if (phonePrimary.isEmpty()) {
                displayError("Primary phone cannot be empty!");
                continue;
            }

            if (!ValidationUtils.isValidPhone(phonePrimary)) {
                displayError(
                    "Invalid phone format! " +
                        ValidationUtils.getPhoneFormatHint()
                );
                continue;
            }

            newContact.setPhonePrimary(
                ValidationUtils.cleanPhone(phonePrimary)
            );
            break;
        }

        // Secondary Phone (Optional)
        while (true) {
            System.out.print(
                ColorUtils.colorize(
                    "Secondary Phone " +
                        ValidationUtils.getPhoneFormatHint() +
                        " (optional, press Enter to skip): ",
                    ColorUtils.CYAN
                )
            );
            String phoneSecondary = scanner.nextLine().trim();

            if (phoneSecondary.isEmpty()) {
                break; // Skip optional field
            }

            if (!ValidationUtils.isValidPhone(phoneSecondary)) {
                displayError(
                    "Invalid phone format! " +
                        ValidationUtils.getPhoneFormatHint()
                );
                continue;
            }

            newContact.setPhoneSecondary(
                ValidationUtils.cleanPhone(phoneSecondary)
            );
            break;
        }

        // Email (Required)
        while (true) {
            System.out.print(
                ColorUtils.colorize(
                    "Email " +
                        ValidationUtils.getEmailFormatHint() +
                        " (required): ",
                    ColorUtils.CYAN
                )
            );
            String email = scanner.nextLine().trim();

            if (email.isEmpty()) {
                displayError("Email cannot be empty!");
                continue;
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

            if (contactManager.emailExists(email)) {
                displayError(
                    "Email already exists in the system! Please use a different email."
                );
                continue;
            }

            newContact.setEmail(email);
            break;
        }

        // LinkedIn URL (Optional)
        while (true) {
            System.out.print(
                ColorUtils.colorize(
                    "LinkedIn URL (optional): ",
                    ColorUtils.CYAN
                )
            );
            String linkedinUrl = scanner.nextLine().trim();
            if (linkedinUrl.isEmpty()) {
                break; // Skip optional field
            }
            if (!ValidationUtils.isValidLinkedInUrl(linkedinUrl)) {
                displayError(ValidationUtils.getLinkedInError());
                continue;
            }
            newContact.setLinkedinUrl(linkedinUrl);
            break;
        }

        // Birth Date (Required)
        while (true) {
            System.out.print(
                ColorUtils.colorize(
                    "Birth Date (YYYY-MM-DD, required): ",
                    ColorUtils.CYAN
                )
            );
            String birthDateStr = scanner.nextLine().trim();

            if (birthDateStr.isEmpty()) {
                displayError("Birth date is required!");
                continue;
            }

            if (!ValidationUtils.isValidBirthDate(birthDateStr)) {
                displayError(ValidationUtils.getBirthDateError());
                continue;
            }

            newContact.setBirthDate(Date.valueOf(birthDateStr));
            break;
        }

        // Confirm addition
        System.out.println();
        System.out.println(
            ColorUtils.colorize("Contact Summary:", ColorUtils.YELLOW)
        );
        System.out.println("  Name: " + newContact.getFullName());
        System.out.println("  Phone: " + newContact.getPhonePrimary());
        System.out.println("  Email: " + newContact.getEmail());
        System.out.println();

        // Validate yes/no confirmation with loop
        String confirm = null;
        while (confirm == null) {
            System.out.print(
                ColorUtils.colorize(
                    "Confirm add? (yes/no): ",
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
            displayInfo("Addition cancelled.");
            pauseScreen();
            return;
        }

        // Add contact to database
        int newId = contactManager.addContact(newContact);
        if (newId > 0) {
            undoManager.recordAddContact(
                newId,
                "Added contact: " + newContact.getFullName()
            );
            displaySuccess("Contact added successfully! (ID: " + newId + ")");
        } else {
            displayError("Failed to add contact. Please try again.");
        }

        pauseScreen();
    }

    /**
     * Adds multiple contacts in batch mode.
     */
    protected void addMultipleContacts() {
        ColorUtils.clearScreen();
        System.out.println(
            ColorUtils.colorize(
                "╔════════════════════════════════════════════════════════════╗",
                ColorUtils.BRIGHT_GREEN
            )
        );
        System.out.println(
            ColorUtils.colorize(
                "║                ADD MULTIPLE CONTACTS                       ║",
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

        System.out.print(
            ColorUtils.colorize(
                "How many contacts do you want to add? (1-20, or 0 to cancel): ",
                ColorUtils.CYAN
            )
        );
        String countStr = scanner.nextLine().trim();

        if (countStr.equals("0")) {
            displayInfo("Operation cancelled.");
            pauseScreen();
            return;
        }

        if (!isValidInteger(countStr)) {
            displayError("Invalid number!");
            pauseScreen();
            return;
        }

        int count = Integer.parseInt(countStr);
        if (count < 1 || count > 20) {
            displayError("Please enter a number between 1 and 20.");
            pauseScreen();
            return;
        }

        int successCount = 0;
        int failCount = 0;

        for (int i = 1; i <= count; i++) {
            System.out.println();
            System.out.println(
                ColorUtils.colorize(
                    "═══ Contact " + i + " of " + count + " ═══",
                    ColorUtils.BRIGHT_CYAN
                )
            );
            System.out.println();

            Contact contact = new Contact();

            // Simplified input for batch mode
            System.out.print("First Name: ");
            String firstName = scanner.nextLine().trim();
            if (
                firstName.isEmpty() || !ValidationUtils.isValidName(firstName)
            ) {
                displayWarning("Invalid first name, skipping...");
                failCount++;
                continue;
            }
            contact.setFirstName(firstName);

            System.out.print("Last Name: ");
            String lastName = scanner.nextLine().trim();
            if (lastName.isEmpty() || !ValidationUtils.isValidName(lastName)) {
                displayWarning("Invalid last name, skipping...");
                failCount++;
                continue;
            }
            contact.setLastName(lastName);

            System.out.print("Phone: ");
            String phone = scanner.nextLine().trim();
            if (phone.isEmpty() || !ValidationUtils.isValidPhone(phone)) {
                displayWarning("Invalid phone, skipping...");
                failCount++;
                continue;
            }
            contact.setPhonePrimary(ValidationUtils.cleanPhone(phone));

            System.out.print("Email: ");
            String email = scanner.nextLine().trim();
            if (email.isEmpty() || !ValidationUtils.isValidEmail(email)) {
                displayWarning("Invalid email, skipping...");
                failCount++;
                continue;
            }
            contact.setEmail(email);

            System.out.print("Birth Date (YYYY-MM-DD): ");
            String birthDate = scanner.nextLine().trim();
            if (
                birthDate.isEmpty() ||
                !ValidationUtils.isValidBirthDate(birthDate)
            ) {
                displayWarning("Invalid birth date, skipping...");
                failCount++;
                continue;
            }
            contact.setBirthDate(Date.valueOf(birthDate));

            // Add contact
            int newId = contactManager.addContact(contact);
            if (newId > 0) {
                undoManager.recordAddContact(
                    newId,
                    "Batch added contact: " + contact.getFullName()
                );
                successCount++;
                System.out.println(
                    ColorUtils.colorize(
                        "✓ Contact added (ID: " + newId + ")",
                        ColorUtils.GREEN
                    )
                );
            } else {
                failCount++;
                System.out.println(
                    ColorUtils.colorize(
                        "✗ Failed to add contact",
                        ColorUtils.RED
                    )
                );
            }
        }

        System.out.println();
        System.out.println(
            ColorUtils.colorize(
                "═══════════════════════════════════════",
                ColorUtils.BRIGHT_CYAN
            )
        );
        System.out.println(
            ColorUtils.colorize("Batch Add Complete!", ColorUtils.BRIGHT_GREEN)
        );
        System.out.println("  Successfully added: " + successCount);
        System.out.println("  Failed: " + failCount);
        System.out.println(
            ColorUtils.colorize(
                "═══════════════════════════════════════",
                ColorUtils.BRIGHT_CYAN
            )
        );

        pauseScreen();
    }

    /**
     * Deletes a contact from the database.
     */
    protected void deleteContact() {
        while (true) {
            ColorUtils.clearScreen();
            System.out.println(
                ColorUtils.colorize(
                    "╔════════════════════════════════════════════════════════════╗",
                    ColorUtils.BRIGHT_RED
                )
            );
            System.out.println(
                ColorUtils.colorize(
                    "║                   DELETE CONTACT                           ║",
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

            // Show all contacts first
            List<Contact> allContacts = contactManager.getAllContacts();
            if (allContacts.isEmpty()) {
                displayInfo("No contacts available to delete.");
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
                    "Enter Contact ID to delete (or 0 to cancel): ",
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
                    "Invalid Contact ID! Must be a number. Please try again."
                );
                pauseScreen();
                continue;
            }

            int contactId = Integer.parseInt(idInput);
            Contact contact = contactManager.getContactById(contactId);

            if (contact == null) {
                displayError(
                    "Contact with ID " +
                        contactId +
                        " not found! Please try again."
                );
                pauseScreen();
                continue;
            }

            // Display contact details
            System.out.println();
            System.out.println(
                ColorUtils.colorize("Contact to be deleted:", ColorUtils.YELLOW)
            );
            displayContactDetails(contact);

            // Validate yes/no confirmation with loop
            String confirm = null;
            while (confirm == null) {
                System.out.print(
                    ColorUtils.colorize(
                        "Are you sure you want to delete this contact? (yes/no): ",
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
                continue;
            }

            // Perform deletion
            if (contactManager.deleteContact(contactId)) {
                undoManager.recordDeleteContact(
                    contact,
                    "Deleted contact: " + contact.getFullName()
                );
                displaySuccess("Contact deleted successfully!");
            } else {
                displayError("Failed to delete contact. Please try again.");
                pauseScreen();
                continue;
            }

            pauseScreen();

            System.out.print(
                ColorUtils.colorize(
                    "\nDelete another contact? (y/n): ",
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
     * Deletes multiple contacts from the database.
     */
    protected void deleteMultipleContacts() {
        ColorUtils.clearScreen();
        System.out.println(
            ColorUtils.colorize(
                "╔════════════════════════════════════════════════════════════╗",
                ColorUtils.BRIGHT_RED
            )
        );
        System.out.println(
            ColorUtils.colorize(
                "║              DELETE MULTIPLE CONTACTS                      ║",
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

        // Show all contacts first
        List<Contact> allContacts = contactManager.getAllContacts();
        if (allContacts.isEmpty()) {
            displayInfo("No contacts available to delete.");
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
                "Enter Contact IDs separated by commas (e.g., 1,2,3), or press Enter to cancel: ",
                ColorUtils.CYAN
            )
        );
        String idsInput = scanner.nextLine().trim();

        if (idsInput.isEmpty()) {
            displayInfo("Deletion cancelled.");
            pauseScreen();
            return;
        }

        String[] idStrings = idsInput.split(",");
        List<Integer> contactIds = new ArrayList<>();
        List<Contact> contactsToDelete = new ArrayList<>();

        for (String idStr : idStrings) {
            idStr = idStr.trim();
            if (!isValidInteger(idStr)) {
                displayWarning("Skipping invalid ID: " + idStr);
                continue;
            }

            int contactId = Integer.parseInt(idStr);
            Contact contact = contactManager.getContactById(contactId);

            if (contact == null) {
                displayWarning(
                    "Contact with ID " + contactId + " not found, skipping..."
                );
                continue;
            }

            contactIds.add(contactId);
            contactsToDelete.add(contact);
        }

        if (contactIds.isEmpty()) {
            displayError("No valid contacts found for deletion!");
            pauseScreen();
            return;
        }

        System.out.println();
        System.out.println(
            ColorUtils.colorize(
                "Contacts to be deleted (" + contactIds.size() + "):",
                ColorUtils.YELLOW
            )
        );
        for (Contact contact : contactsToDelete) {
            System.out.println(
                "  - [" +
                    contact.getContactId() +
                    "] " +
                    contact.getFullName() +
                    " (" +
                    contact.getEmail() +
                    ")"
            );
        }

        System.out.println();

        // Validate yes/no confirmation with loop
        String confirm = null;
        while (confirm == null) {
            System.out.print(
                ColorUtils.colorize(
                    "Are you sure you want to delete these " +
                        contactIds.size() +
                        " contacts? (yes/no): ",
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

        // Perform deletions
        int deletedCount = 0;
        for (Contact contact : contactsToDelete) {
            if (contactManager.deleteContact(contact.getContactId())) {
                undoManager.recordDeleteContact(
                    contact,
                    "Batch deleted contact: " + contact.getFullName()
                );
                deletedCount++;
            }
        }

        System.out.println();
        if (deletedCount == contactIds.size()) {
            displaySuccess(
                "All " + deletedCount + " contacts deleted successfully!"
            );
        } else {
            displayWarning(
                "Deleted " +
                    deletedCount +
                    " out of " +
                    contactIds.size() +
                    " contacts."
            );
        }

        pauseScreen();
    }
}
