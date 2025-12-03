package managers;

import java.util.Stack;
import models.Contact;
import models.User;

/**
 * UndoManager class providing undo functionality for contact and user operations.
 * Implements the Memento pattern to save and restore previous states.
 * Maintains a history of operations that can be undone.
 *
 * @author CMPE-343 Project Group
 * @version 1.0
 */
public class UndoManager {

    private Stack<UndoOperation> undoStack;
    private static final int MAX_UNDO_LEVELS = 10;

    /**
     * Enum representing the type of operation that can be undone.
     */
    public enum OperationType {
        /** Operation type for adding a new contact */
        ADD_CONTACT,
        /** Operation type for updating an existing contact */
        UPDATE_CONTACT,
        /** Operation type for deleting a contact */
        DELETE_CONTACT,
        /** Operation type for adding a new user */
        ADD_USER,
        /** Operation type for updating an existing user */
        UPDATE_USER,
        /** Operation type for deleting a user */
        DELETE_USER,
    }

    /**
     * Inner class representing a single undo operation.
     */
    public static class UndoOperation {

        private OperationType type;
        private Contact contactSnapshot;
        private User userSnapshot;
        private int affectedId;
        private String description;

        /**
         * Constructor for contact-related undo operations.
         *
         * @param type The type of operation
         * @param contact The contact snapshot
         * @param affectedId The ID of the affected entity
         * @param description A description of the operation
         */
        public UndoOperation(
            OperationType type,
            Contact contact,
            int affectedId,
            String description
        ) {
            this.type = type;
            this.contactSnapshot = contact;
            this.affectedId = affectedId;
            this.description = description;
        }

        /**
         * Constructor for user-related undo operations.
         *
         * @param type The type of operation
         * @param user The user snapshot
         * @param affectedId The ID of the affected entity
         * @param description A description of the operation
         */
        public UndoOperation(
            OperationType type,
            User user,
            int affectedId,
            String description
        ) {
            this.type = type;
            this.userSnapshot = user;
            this.affectedId = affectedId;
            this.description = description;
        }

        /**
         * Gets the operation type.
         *
         * @return The operation type
         */
        public OperationType getType() {
            return type;
        }

        /**
         * Gets the contact snapshot.
         *
         * @return The contact snapshot
         */
        public Contact getContactSnapshot() {
            return contactSnapshot;
        }

        /**
         * Gets the user snapshot.
         *
         * @return The user snapshot
         */
        public User getUserSnapshot() {
            return userSnapshot;
        }

        /**
         * Gets the affected ID.
         *
         * @return The affected ID
         */
        public int getAffectedId() {
            return affectedId;
        }

        /**
         * Gets the description.
         *
         * @return The description
         */
        public String getDescription() {
            return description;
        }
    }

    /**
     * Constructor for UndoManager.
     */
    public UndoManager() {
        this.undoStack = new Stack<>();
    }

    /**
     * Records a contact addition operation.
     *
     * @param contactId The ID of the added contact
     * @param description A description of the operation
     */
    public void recordAddContact(int contactId, String description) {
        pushOperation(
            new UndoOperation(
                OperationType.ADD_CONTACT,
                (Contact) null,
                contactId,
                description
            )
        );
    }

    /**
     * Records a contact update operation with the previous state.
     *
     * @param previousState The previous state of the contact
     * @param description A description of the operation
     */
    public void recordUpdateContact(Contact previousState, String description) {
        pushOperation(
            new UndoOperation(
                OperationType.UPDATE_CONTACT,
                previousState,
                previousState.getContactId(),
                description
            )
        );
    }

    /**
     * Records a contact deletion operation with the deleted contact's data.
     *
     * @param deletedContact The deleted contact
     * @param description A description of the operation
     */
    public void recordDeleteContact(
        Contact deletedContact,
        String description
    ) {
        pushOperation(
            new UndoOperation(
                OperationType.DELETE_CONTACT,
                deletedContact,
                deletedContact.getContactId(),
                description
            )
        );
    }

    /**
     * Records a user addition operation.
     *
     * @param userId The ID of the added user
     * @param description A description of the operation
     */
    public void recordAddUser(int userId, String description) {
        pushOperation(
            new UndoOperation(
                OperationType.ADD_USER,
                (User) null,
                userId,
                description
            )
        );
    }

    /**
     * Records a user update operation with the previous state.
     *
     * @param previousState The previous state of the user
     * @param description A description of the operation
     */
    public void recordUpdateUser(User previousState, String description) {
        pushOperation(
            new UndoOperation(
                OperationType.UPDATE_USER,
                previousState,
                previousState.getUserId(),
                description
            )
        );
    }

    /**
     * Records a user deletion operation with the deleted user's data.
     *
     * @param deletedUser The deleted user
     * @param description A description of the operation
     */
    public void recordDeleteUser(User deletedUser, String description) {
        pushOperation(
            new UndoOperation(
                OperationType.DELETE_USER,
                deletedUser,
                deletedUser.getUserId(),
                description
            )
        );
    }

    /**
     * Pushes an operation onto the undo stack.
     * Maintains a maximum stack size by removing oldest operations.
     *
     * @param operation The operation to push
     */
    private void pushOperation(UndoOperation operation) {
        if (undoStack.size() >= MAX_UNDO_LEVELS) {
            undoStack.remove(0); // Remove oldest operation
        }
        undoStack.push(operation);
    }

    /**
     * Gets the last operation without removing it from the stack.
     *
     * @return The last operation, or null if stack is empty
     */
    public UndoOperation peekLastOperation() {
        if (undoStack.isEmpty()) {
            return null;
        }
        return undoStack.peek();
    }

    /**
     * Pops and returns the last operation from the stack.
     *
     * @return The last operation, or null if stack is empty
     */
    public UndoOperation popLastOperation() {
        if (undoStack.isEmpty()) {
            return null;
        }
        return undoStack.pop();
    }

    /**
     * Checks if there are operations that can be undone.
     *
     * @return true if undo stack is not empty, false otherwise
     */
    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    /**
     * Gets the number of operations in the undo stack.
     *
     * @return The size of the undo stack
     */
    public int getUndoStackSize() {
        return undoStack.size();
    }

    /**
     * Clears all undo operations.
     */
    public void clearUndoStack() {
        undoStack.clear();
    }

    /**
     * Performs the undo operation for contacts.
     *
     * @param operation The operation to undo
     * @param contactManager The ContactManager to perform the undo
     * @return true if undo successful, false otherwise
     */
    public boolean undoContactOperation(
        UndoOperation operation,
        ContactManager contactManager
    ) {
        if (operation == null || contactManager == null) {
            return false;
        }

        try {
            switch (operation.getType()) {
                case ADD_CONTACT:
                    // Undo add by deleting the contact
                    return contactManager.deleteContact(
                        operation.getAffectedId()
                    );
                case UPDATE_CONTACT:
                    // Undo update by restoring previous state
                    Contact previousState = operation.getContactSnapshot();
                    if (previousState != null) {
                        return contactManager.updateContact(previousState);
                    }
                    return false;
                case DELETE_CONTACT:
                    // Undo delete by re-adding the contact
                    Contact deletedContact = operation.getContactSnapshot();
                    if (deletedContact != null) {
                        int newId = contactManager.addContact(deletedContact);
                        return newId > 0;
                    }
                    return false;
                default:
                    return false;
            }
        } catch (Exception e) {
            System.err.println(
                "Error during undo operation: " + e.getMessage()
            );
            return false;
        }
    }

    /**
     * Performs the undo operation for users.
     *
     * @param operation The operation to undo
     * @param userManager The UserManager to perform the undo
     * @return true if undo successful, false otherwise
     */
    public boolean undoUserOperation(
        UndoOperation operation,
        UserManager userManager
    ) {
        if (operation == null || userManager == null) {
            return false;
        }

        try {
            switch (operation.getType()) {
                case ADD_USER:
                    // Undo add by deleting the user
                    return userManager.deleteUser(operation.getAffectedId());
                case UPDATE_USER:
                    // Undo update by restoring previous state
                    User previousState = operation.getUserSnapshot();
                    if (previousState != null) {
                        return userManager.updateUser(
                            previousState.getUserId(),
                            previousState.getUsername(),
                            previousState.getName(),
                            previousState.getSurname(),
                            previousState.getRole()
                        );
                    }
                    return false;
                case DELETE_USER:
                    // Undo delete by re-adding the user
                    User deletedUser = operation.getUserSnapshot();
                    if (deletedUser != null) {
                        // Note: Cannot restore exact password hash, would need special handling
                        return userManager.addUser(
                            deletedUser.getUsername(),
                            "resetpassword", // Temporary password
                            deletedUser.getName(),
                            deletedUser.getSurname(),
                            deletedUser.getRole()
                        );
                    }
                    return false;
                default:
                    return false;
            }
        } catch (Exception e) {
            System.err.println(
                "Error during undo operation: " + e.getMessage()
            );
            return false;
        }
    }

    /**
     * Gets a list of all undo operation descriptions.
     *
     * @return List of operation descriptions
     */
    public java.util.List<String> getUndoHistory() {
        java.util.List<String> history = new java.util.ArrayList<>();
        for (UndoOperation op : undoStack) {
            history.add(op.getDescription());
        }
        return history;
    }
}
