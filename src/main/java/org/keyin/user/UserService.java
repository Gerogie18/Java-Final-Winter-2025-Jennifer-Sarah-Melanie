package org.keyin.user;

import org.mindrot.jbcrypt.BCrypt;
import javax.naming.AuthenticationException;
import java.sql.SQLException;
import java.util.List;

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
            return false;
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

    //GET User List functions
    public List<User> listAllUsers() throws SQLException {
        return userDAO.getAllUsers();
    }
    public List<User> listAllAdmin() throws SQLException {
        return userDAO.getUsersByRole(User.Role.ADMIN);
    }
    public List<User> listAllTrainers() throws SQLException {
        return userDAO.getUsersByRole(User.Role.TRAINER);
    }
    public List<User> listAllMembers() throws SQLException {
        return userDAO.getUsersByRole(User.Role.MEMBER);
    }

    //Print functions
    public void printAllUsers() {
        try {
            List<User> allUsers = listAllUsers();
            if (allUsers.isEmpty()) {
                System.out.println("No users found.");
            } else {
                System.out.println("--- All Users ---");
                for (User user : allUsers) {
                    System.out.println(user.toString()); // Calling toString() on each User object
                }
                System.out.println("------------------");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving all users: " + e.getMessage());
        }
    }

    public void printAllAdmins() {
        try {
            List<User> admins = listAllAdmin();
            if (admins.isEmpty()) {
                System.out.println("No administrators found.");
            } else {
                System.out.println("--- Administrators ---");
                for (User admin : admins) {
                    System.out.println(admin.toString());
                }
                System.out.println("----------------------");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving administrators: " + e.getMessage());
        }
    }

    public void printAllTrainers() {
        try {
            List<User> trainers = listAllTrainers();
            if (trainers.isEmpty()) {
                System.out.println("No trainers found.");
            } else {
                System.out.println("--- Trainers ---");
                for (User trainer : trainers) {
                    System.out.println(trainer.toString());
                }
                System.out.println("----------------");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving trainers: " + e.getMessage());
        }
    }

    public void printAllMembers() {
        try {
            List<User> members = listAllMembers();
            if (members.isEmpty()) {
                System.out.println("No members found.");
            } else {
                System.out.println("--- Members ---");
                for (User member : members) {
                    System.out.println(member.toString());
                }
                System.out.println("---------------");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving members: " + e.getMessage());
        }
    }

    //GET Validations for creating new user
    private boolean isUsernameTaken(String username) {
        try {
            return userDAO.getUserByUsername(username) != null;
        } catch (SQLException e) {
            System.err.println("Error checking if username exists: " + e.getMessage());
            return true;
        }
    }

    private boolean isEmailTaken(String email) {
        try {
            return userDAO.getUserByEmail(email) != null;
        } catch (SQLException e) {
            System.err.println("Error checking if email exists: " + e.getMessage());
            return true;
        }
    }


}