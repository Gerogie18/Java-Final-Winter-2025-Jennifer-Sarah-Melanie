package GymApp.models;

import GymApp.models.enums.UserRole;

/**
 * Represents a user in the gym system.
 * <p>
 * A user can be an admin, trainer, or member. This model stores their profile
 * information, including login credentials and role assignment.
 * </p>
 */

public class User {
    private int userId;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String address;
    private UserRole userRole;


    //Constructors
    public User(int userId, String username, String password, String email, String phoneNumber, String address, UserRole userRole) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.userRole = userRole;
    }
    public User(String username, String password, String email, String phoneNumber, String address, UserRole userRole) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.userRole = userRole;
    }

    //Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {this.password = password; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserRole getRole() {
        return userRole;
    }

    public void setRole(UserRole userRole) {
        this.userRole = userRole;
    }

    //toString
    @java.lang.Override
    public java.lang.String toString() {
        return String.format("%-8s | %-5d | %-15s | %-25s | %-12s | %-20s",
                userRole, userId, username, email, phoneNumber, address);
    }
}