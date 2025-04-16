package GymApp.services;

import GymApp.dao.UserDAO;
import GymApp.models.User;
import GymApp.models.enums.Role;

import javax.naming.AuthenticationException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class UserService {
    Logger log = Logger.getLogger(WorkoutClassService.class.getName());
    private final UserDAO userDAO;

    //security logic
//    public boolean hasRole(int userId, String role) throws SQLException{
//        User user = userDAO.getUserById(userId);
//        return user != null && user.getRole().equals(role);
//    }

    public User login(String username, String password) throws AuthenticationException, SQLException {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            throw new AuthenticationException("Username and password must not be empty.");
        }

        User user = userDAO.getUserByUsername(username);
        if (user == null) {
            log.warning("Login failed: username '" + username + "' not found.");
            throw new AuthenticationException("Username not found.");
        }
        if (!user.getPassword().equals(password)) {
            log.warning("Login failed: incorrect password for username '" + username + "'.");
            throw new AuthenticationException("Incorrect username or password.");
        }
            log.info("User " + username + " logged in successfully.");
            return user;
    }

    //Functions for USER DAO operations
    public void createUser(User user)  throws SQLException {
        userDAO.createUser(user);
    }

    public void updateUser(User user) throws SQLException  {
        userDAO.updateUser(user);
    }

    public void deleteUser(int userId) throws SQLException  {
        userDAO.deleteUserById(userId);
    }

    public UserService(UserDAO userDAO) throws SQLException  {
        this.userDAO = userDAO;
    }

    public User searchUserByUsername(String username) throws SQLException {
        return userDAO.getUserByUsername(username);
    }

    public User searchUserByPhoneNumber(String phoneNumber) throws SQLException {
        return userDAO.getUserByPhoneNumber(phoneNumber);
    }

//    public User searchUserByAddress(String address) throws SQLException {
//        return userDAO.getUserByAddress(address);
//    }

    public User searchUserByEmail(String email) throws SQLException {
        return userDAO.getUserByEmail(email);
    }

    //GET User List functions
    public List<User> listAllUsers() throws SQLException {
        return userDAO.getAllUsers();
    }
    public List<User> listAllAdmin() throws SQLException {
        return userDAO.getUsersByRole(Role.ADMIN);
    }
    public List<User> listAllTrainers() throws SQLException {
        return userDAO.getUsersByRole(Role.TRAINER);
    }
    public List<User> listAllMembers() throws SQLException {
        return userDAO.getUsersByRole(Role.MEMBER);
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