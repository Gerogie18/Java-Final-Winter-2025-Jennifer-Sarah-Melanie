package org.keyin.user;

import org.keyin.database.DatabaseConnection;

import java.sql.*;

public class UserDAO {

    //CREATE user
    public void createUser(User user) throws SQLException {
        String sql = "INSERT INTO users (user_id, user_name, user_password, user_email, user_phone_number, user_address, user_role) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getUsername());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getEmail());
            pstmt.setInt(5, user.getPhoneNumber());
            pstmt.setString(6, user.getAddress());
            pstmt.setString(7, user.getRole().toString());
            pstmt.executeUpdate();
        }
    }

    //DELETE user
    public void deleteUserById(String userId) throws SQLException {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            pstmt.executeUpdate();
        }
    }

    //SET user
    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE users SET user_name = ?, user_password = ?, user_email = ?, user_phone_number = ?, user_address = ?, user_role = ? WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getEmail());
            pstmt.setInt(4, user.getPhoneNumber());
            pstmt.setString(5, user.getAddress());
            pstmt.setString(6, user.getRole().toString());
            pstmt.setString(7, user.getUserId());
            pstmt.executeUpdate();
        }
    }

    //GET search for users
    public User getUserByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getString("user_id"),
                            rs.getString("user_name"),
                            rs.getString("user_password"),
                            rs.getString("user_email"),
                            rs.getInt("user_phone_number"),
                            rs.getString("user_address"),
                            User.Role.valueOf(rs.getString("user_role"))
                    );
                }
            }
        }
        return null;
    }

    public User getUserByPhoneNumber(int phoneNumber) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_phone_number = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, phoneNumber);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getString("user_id"),
                            rs.getString("user_name"),
                            rs.getString("user_password"),
                            rs.getString("user_email"),
                            rs.getInt("user_phone_number"),
                            rs.getString("user_address"),
                            User.Role.valueOf(rs.getString("user_role"))
                    );
                }
            }
        }
        return null;
    }

    public User getUserByAddress(String address) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_address = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, address);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getString("user_id"),
                            rs.getString("user_name"),
                            rs.getString("user_password"),
                            rs.getString("user_email"),
                            rs.getInt("user_phone_number"),
                            rs.getString("user_address"),
                            User.Role.valueOf(rs.getString("user_role"))
                    );
                }
            }
        }
        return null;
    }

    public User getUserByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getString("user_id"),
                            rs.getString("user_name"),
                            rs.getString("user_password"),
                            rs.getString("user_email"),
                            rs.getInt("user_phone_number"),
                            rs.getString("user_address"),
                            User.Role.valueOf(rs.getString("user_role"))
                    );
                }
            }
        }
        return null;
    }
}