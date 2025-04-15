package GymApp.dao;

import GymApp.database.DatabaseConnection;
import GymApp.models.Membership;
import GymApp.models.User;

import java.sql.*;
import java.time.LocalDate;

// DAOs are responsible for handling the interactions with the database
public class MembershipDAO {

    // Here we have a method that adds a membership to the database,
    // it takes a membership object as a parameter and inserts it into the database
    // using a prepared statement
    // THIS IS JUST AN EXAMPLE FOR YOU TO LOOK AT

    public void addMembership(Membership membership) throws SQLException {
        String sql = "INSERT INTO memberships (membership_type, membership_cost, membership_description, date_purchased, member_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, membership.getMembershipType());
            pstmt.setDouble(2, membership.getMembershipCost());
            pstmt.setString(3, membership.getMembershipDescription());
            pstmt.setDate(4, Date.valueOf(membership.getMembershipStartDate()));
            pstmt.setInt(5, membership.getMemberID());
            pstmt.executeUpdate();
        }
    }

    public void deleteMembershipById(int membershipId) throws SQLException {
        String sql = "DELETE FROM memberships WHERE membership_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, membershipId);
            pstmt.executeUpdate();
        }
    }

    public void updateMembership(Membership membership) throws SQLException {
        String sql = "UPDATE memberships SET (membership_type = ?, membership_cost = ?, membership_description = ?, date_purchased = ?, member_id = ?) WHERE membership_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, membership.getMembershipType());
            pstmt.setDouble(2, membership.getMembershipCost());
            pstmt.setString(3, membership.getMembershipDescription());
            pstmt.setDate(4, Date.valueOf(membership.getMembershipStartDate()));
            pstmt.setInt(5, membership.getMemberID());
            pstmt.executeUpdate();
        }
    }

    public void getAllMemberships() throws SQLException {
        ResultSet rs = null;
        String sql = "SELECT * FROM memberships";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String membershipType = rs.getString("membership_type");
                Double cost = rs.getDouble("membership_cost");
                String desc = rs.getString("membership_description");
                LocalDate date = rs.getDate("date_purchased").toLocalDate();
                int memberID = rs.getInt("member_id");

                System.out.println("-----------------");
                System.out.println("Membership Type: " + membershipType);
                System.out.println("Cost : " + cost);
                System.out.println("Description : " + desc);
                System.out.println("Date Purchased : " + date);
                System.out.println("Member ID: " + memberID);
                System.out.println("-------------------------");
            }

        }
        ;
    }

    public ResultSet getMemberships(User user) throws SQLException {
        ResultSet rs = null;
        String sql = "SELECT * FROM memberships WHERE user_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, user.getUserId());
            rs = pstmt.executeQuery();
            System.out.println("Memberships for " + user.getUsername() + " (" + user.getUserId() + ")");
            while (rs.next()) {
                String membershipType = rs.getString("membership_type");
                Double cost = rs.getDouble("membership_cost");
                String desc = rs.getString("membership_description");
                LocalDate date = rs.getDate("date_purchased").toLocalDate();
                int memberID = rs.getInt("member_id");

                System.out.println("-----------------");
                System.out.println("Membership Type: " + membershipType);
                System.out.println("Cost : " + cost);
                System.out.println("Description : " + desc);
                System.out.println("Date Purchased : " + date);
                System.out.println("Member ID: " + memberID);
                System.out.println("-------------------------");
            }
            
        } catch (SQLException error) {
            throw new RuntimeException(error);
        }
        return rs;
    }
}