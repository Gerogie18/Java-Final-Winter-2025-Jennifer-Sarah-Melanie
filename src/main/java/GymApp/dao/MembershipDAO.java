package GymApp.dao;

import GymApp.database.DatabaseConnection;
import GymApp.models.Membership;
import GymApp.models.enums.MembershipType;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Data Access Object (DAO) for handling operations related to gym memberships.
 * <p>
 * This class provides methods to perform CRUD operations on the {@code memberships} table
 * in the database, such as creating memberships, retrieving membership records,
 * and calculating membership-related statistics.
 * </p>
 *
 *  @author Melanie Adams
 *  @version 1.0
 */

public class MembershipDAO {

    public void addMembership(Membership membership) throws SQLException {
        String sql = "INSERT INTO memberships (membership_type, membership_cost, membership_description, date_purchased, member_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, membership.getMembershipType());
            pstmt.setDouble(2, membership.getMembershipCost());
            pstmt.setString(3, membership.getMembershipDescription());
            pstmt.setDate(4, Date.valueOf(membership.getMembershipStartDate()));
            pstmt.setInt(5, membership.getMemberID());
            pstmt.executeUpdate();
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    membership.setMembershipID(generatedKeys.getInt(1));
                }
            }
        }
    }

    public boolean deleteMembershipById(int membershipId) throws SQLException {
        String sql = "DELETE FROM memberships WHERE membership_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, membershipId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public boolean updateMembership(Membership membership) throws SQLException {
        String sql = "UPDATE memberships SET (membership_type = ?, membership_cost = ?, membership_description = ?, date_purchased = ?, member_id = ?) WHERE membership_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, membership.getMembershipType());
            pstmt.setDouble(2, membership.getMembershipCost());
            pstmt.setString(3, membership.getMembershipDescription());
            pstmt.setDate(4, Date.valueOf(membership.getMembershipStartDate()));
            pstmt.setInt(5, membership.getMemberID());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public List<Membership> getAllMemberships() throws SQLException {
        List<Membership> memberships = new ArrayList<>();
        String sql = "SELECT membership_id, membership_type, membership_cost, membership_description, date_purchased, member_id FROM memberships";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Membership membership = new Membership(
                            rs.getInt("membership_id"),
                            MembershipType.fromString(rs.getString("membership_type")),
                            rs.getDouble("membership_cost"),
                            rs.getString("membership_description"),
                            rs.getDate("date_purchased").toLocalDate(),
                            rs.getInt("member_id"));
                    memberships.add(membership);
                }
            }
        }
        return memberships;
    }

    public static Membership getMembershipById(int membershipId) throws SQLException {
        String sql = "SELECT * FROM memberships WHERE membership_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, membershipId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Membership(
                            membershipId,
                            MembershipType.fromString(rs.getString("membership_type")),
                            rs.getDouble("membership_cost"),
                            rs.getString("membership_description"),
                            rs.getDate("date_purchased").toLocalDate(),
                            rs.getInt("member_id"));
                } else {
                    return null;
                }
            }
        }
    }

    public List<Membership> getMembershipsByMember(int userId) throws SQLException {
        List<Membership> memberships = new ArrayList<>();
        String sql = "SELECT * FROM memberships WHERE member_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Membership membership = new Membership(
                            rs.getInt("membership_id"),
                            MembershipType.fromString(rs.getString("membership_type")),
                            rs.getDouble("membership_cost"),
                            rs.getString("membership_description"),
                            rs.getDate("date_purchased").toLocalDate(),
                            rs.getInt("member_id"));
                    memberships.add(membership);
                }
            }
        }
        return memberships;
    }
}