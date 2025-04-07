package org.keyin.user;

import org.mindrot.jbcrypt.BCrypt;

//*
// This is the parent class for all users, There are 3 types of users: Trainer, Member, and Admin
//
// *//
public class User {
    private static int UserId = 1;

    private final String userId;
    private String username;
    private String password;
    private String email;
    private int phoneNumber;
    private String address;
    private Role role;

    //enum for roles
    public enum Role {
        ADMIN, TRAINER, MEMBER
    }

    //Constructor
    public User(String username, String password, String email, int phoneNumber, String address, Role role) {
        //update userID
        this.userId = String.format("%07d", UserId);
        UserId++;

        this.username = username;
        this.password = hashPassword(password);
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = role;
    }

    //Getters and Setters
    public String getUserId() {
        return userId;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    //Hash password using BCrypt
    private String hashPassword (String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    //Verify password using BCrypt
    public boolean verifyPassword (String password) {
        return BCrypt.checkpw(password, this.password);
    }

    //toString
    @java.lang.Override
    public java.lang.String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", address='" + address + '\'' +
                ", role=" + role +
                '}';
    }
}