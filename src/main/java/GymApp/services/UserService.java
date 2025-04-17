package GymApp.services;

import GymApp.dao.UserDAO;
import GymApp.models.User;
import GymApp.models.enums.UserRole;

import javax.naming.AuthenticationException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import org.mindrot.jbcrypt.BCrypt;

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
            throw new SQLException("Email address already exists.");
        }
        // Hash the password and set it to the user object
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);
        return userDAO.createUser(user);
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
        userDAO.deleteUserById(userId);
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
        return userDAO.getAllUsers();
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
    public void printAllUsers() {
        try {
            List<User> allUsers = listAllUsers();
            if (allUsers.isEmpty()) {
                System.out.println("No users found.");
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