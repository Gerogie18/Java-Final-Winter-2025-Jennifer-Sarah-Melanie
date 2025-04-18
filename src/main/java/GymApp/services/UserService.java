package GymApp.services;

import GymApp.dao.UserDAO;
import GymApp.models.User;
import GymApp.models.enums.UserRole;

import javax.naming.AuthenticationException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Service layer responsible for user-related operations such as registration,
 * login, deletion, and user role filtering.
 * <p>
 * Handles input validation, password hashing, and delegates data persistence
 * and queries to the {@link UserDAO}.
 * </p>
 *
 *  @author Jennifer Lyver
 *  @version 1.0
 */

public class UserService {
    Logger log = Logger.getLogger(WorkoutClassService.class.getName());
    private final UserDAO userDAO;

    public UserService (UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User login(String email, String password) throws AuthenticationException, SQLException {
        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            throw new AuthenticationException("Email and password must not be empty.");
        }
        email = email.trim().toLowerCase();

        User user = userDAO.getUserByEmail(email);
        if (user == null) {
            log.warning("Login failed - no user for this email");
            throw new AuthenticationException("Username not found.");
        }
        if (!BCrypt.checkpw(password, user.getPassword())) {
            log.warning("Login failed: incorrect credentials");
            throw new AuthenticationException("Incorrect email or password.");
        }
            log.info(user.getUsername() + ":" + email + " logged in successfully.");
            return user;
    }


    //Functions for USER DAO operations

    public int createUser(User user)  throws SQLException {
        // Check if email is taken before creating the user
        if (isEmailTaken(user.getEmail())) {
            log.warning("User creation failed: Email already exists");
            throw new SQLException("Email address already exists.");
        }
        // Hash the password and set it to the user object
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);
        try {
            int newUserId = userDAO.createUser(user);
            log.info("New user created: ID=" + newUserId + ", Role=" + user.getRole() + ", Email=" + user.getEmail());
            return newUserId;
        } catch (SQLException e) {
            log.warning("Database error during user creation (email: " + user.getEmail() + "): " + e.getMessage());
            throw e;
        }
    }

    public void updateUser(User user) throws SQLException  {
        // Need to fetch the existing user to compare emails
        User existingUser = userDAO.getUserById(user.getUserId());
        if (existingUser != null && !existingUser.getEmail().equals(user.getEmail()) && isEmailTaken(user.getEmail())) {
            throw new SQLException("Email address already exists for another user.");
        }
        // Hash the password if it has been changed (and is not already a hash)
        if (user.getPassword() != null && !user.getPassword().startsWith("$2a$")) {
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            user.setPassword(hashedPassword);
        }
        userDAO.updateUser(user);
    }

    public void deleteUser(int userId) throws SQLException  {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID.");
        }

        try {
            boolean deleted = userDAO.deleteUserById(userId);
            if (!deleted) {
                log.info("No user found with ID=" + userId + ". Nothing to delete.");
                throw new SQLException("User not found. No deletion performed.");
            }
            log.info("User ID=" + userId + " successfully deleted.");
        } catch (SQLException e) {
            log.warning("Error deleting user ID=" + userId + ": " + e.getMessage());
            throw e;
        }
    }

    public User searchUserByUsername(String username) throws SQLException {
        return userDAO.getUserByUsername(username);
    }

    public User searchUserByPhoneNumber(String phoneNumber) throws SQLException {
        return userDAO.getUserByPhoneNumber(phoneNumber);
    }

    public User searchUserByEmail(String email) throws SQLException {
        return userDAO.getUserByEmail(email);
    }

    //GET User List functions
    public List<User> listAllUsers() throws SQLException {

        try {
            List<User> users = userDAO.getAllUsers();
            log.info("Retrieved list of all users.");
            return users;
        } catch (SQLException e) {
            log.warning("Database error while retrieving users: " + e.getMessage());
            throw e;
        }

    }


    public List<User> listAllAdmin() throws SQLException {
        return userDAO.getUsersByRole(UserRole.ADMIN);
    }
    public List<User> listAllTrainers() throws SQLException {
        return userDAO.getUsersByRole(UserRole.TRAINER);
    }
    public List<User> listAllMembers() throws SQLException {
        return userDAO.getUsersByRole(UserRole.MEMBER);
    }



    //Print functions
    public void printAllUsers() throws SQLException{
            List<User> allUsers = listAllUsers();
            if (allUsers.isEmpty()) {
                System.out.println("No users found.");
                log.info("Attempted to print users, but none found.");
            } else {
                System.out.println("--- All Users ---");
                System.out.println("----------------------------------------------------------------------------------");
                System.out.println(String.format("%-8s | %-5s | %-15s | %-25s | %-12s | %-20s",
                        "ROLE", "ID", "USERNAME", "EMAIL", "PHONE", "ADDRESS"));
                System.out.println("----------------------------------------------------------------------------------");
                for (User user : allUsers) {
                    System.out.println(user.toString());
                }
                System.out.println("----------------------------------------------------------------------------------");
            }
    }

    public void printAllAdmins() {
        try {
            List<User> admins = listAllAdmin();
            if (admins.isEmpty()) {
                System.out.println("No administrators found.");
            } else {
                System.out.println("--- Administrators ---");
                System.out.println("----------------------------------------------------------------------------------");
                System.out.println(String.format("%-8s | %-5s | %-15s | %-25s | %-12s | %-20s",
                        "ROLE", "ID", "USERNAME", "EMAIL", "PHONE", "ADDRESS"));
                System.out.println("----------------------------------------------------------------------------------");
                for (User admin : admins) {
                    System.out.println(admin.toString());
                }
                System.out.println("----------------------------------------------------------------------------------");
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
                System.out.println("----------------------------------------------------------------------------------");
                System.out.println(String.format("%-8s | %-5s | %-15s | %-25s | %-12s | %-20s",
                        "ROLE", "ID", "USERNAME", "EMAIL", "PHONE", "ADDRESS"));
                System.out.println("----------------------------------------------------------------------------------");
                for (User trainer : trainers) {
                    System.out.println(trainer.toString());
                }
                System.out.println("----------------------------------------------------------------------------------");
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
                System.out.println("----------------------------------------------------------------------------------");
                System.out.println(String.format("%-8s | %-5s | %-15s | %-25s | %-12s | %-20s",
                        "ROLE", "ID", "USERNAME", "EMAIL", "PHONE", "ADDRESS"));
                System.out.println("----------------------------------------------------------------------------------");
                for (User member : members) {
                    System.out.println(member.toString());
                }
                System.out.println("----------------------------------------------------------------------------------");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving members: " + e.getMessage());
        }
    }

    //GET Validations for creating new user
    public boolean isEmailTaken(String email) {
        try {
            return userDAO.getUserByEmail(email) != null;
        } catch (SQLException e) {
            System.err.println("Error checking if email exists: " + e.getMessage());
            return true;
        }
    }

}