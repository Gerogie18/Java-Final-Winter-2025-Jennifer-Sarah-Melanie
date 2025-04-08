package org.keyin.user;

import org.mindrot.jbcrypt.BCrypt;
import javax.naming.AuthenticationException;
public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) throws AuthenticationException {
        this.userDAO = userDAO;
    }

    // security logic
    public boolean userHasRole(int userId, User.Role role) throws SQLException {
        User user = userDAO.getUserById(userId);
        return user != null && user.getRole().equals(role);
    }

    public boolean userLogin(String username, String password) throws AuthenticationException {
        User user = null;
        try {
            user = userDAO.getUserByUsername(username);
        } catch (SQLException e) {
            // Decide how to handle database errors.
            // Returning false might be appropriate, or you could log the error.
            System.err.println("Database error during login: " + e.getMessage());
            return false;
        }

        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            return true;
        } else {
            throw new AuthenticationException("Invalid username or password");
        }
    }

    // Functions for USER DAO operations
    public boolean createUser(User user) {
        if (user == null) {
            System.out.println("User is null, please provide a valid user");
            return false;
        }

        // Validation checks
        if (isUsernameTaken(user.getUsername())) {
            System.out.println("Username already exists. Please choose a different username.");
            return false;
        }

        if (isEmailTaken(user.getEmail())) {
            System.out.println("Email already exists. Please use a different email.");
            return false;
        }

        String phoneNumber = String.valueOf(user.getPhoneNumber());
        if (!phoneNumber.matches("\\d{10}")) { // Assuming North American 10-digit phone numbers
            System.out.println("Invalid phone number. Please enter a 10-digit number starting with 1 followed by the area code.");
            return false;
        }

        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(8));
        user.setPassword(hashedPassword);

        try {
            userDAO.createUser(user);
            System.out.println("User created successfully");
            return true;
        } catch (SQLException e) {
            // Check for specific SQL exceptions like unique constraint violations
            if (e.getMessage().contains("UNIQUE constraint failed: users.user_name")) {
                System.err.println("Error: Username already exists.");
            } else if (e.getMessage().contains("UNIQUE constraint failed: users.user_email")) {
                System.err.println("Error: Email already exists.");
            } else {
                System.err.println("Error creating user: " + e.getMessage());
            }
            return false;
        }
    }

    public boolean updateUser(User user) {
        try {
            userDAO.updateUser(user);
            System.out.println("User updated successfully");
            return true;
        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteUser(int userId) {
        try {
            userDAO.deleteUserById(userId);
            System.out.println("User with ID " + userId + " deleted successfully");
            return true;
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
            return false;
        }
    }
    public boolean deleteUser(User user) {
        if (user == null) {
            System.out.println("Cannot delete a null user.");
            return false;
        }
        return deleteUser(user.getUserId());
    }

    public User searchUserByUsername(String username) throws SQLException {
        return userDAO.getUserByUsername(username);
    }

    public User searchUserByPhoneNumber(int phoneNumber) throws SQLException {
        return userDAO.getUserByPhoneNumber(phoneNumber);
    }

    public User searchUserByEmail(String email) throws SQLException {
        return userDAO.getUserByEmail(email);
    }
}
