package org.keyin.memberships;

import org.keyin.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// DAOs are responsible for handling the interactions with the database
public class MembershipDAO {

    // Here we have a method that adds a membership to the database,
    // it takes a membership object as a parameter and inserts it into the database
    // using a prepared statement
    // THIS IS JUST AN EXAMPLE FOR  YOU TO LOOK AT

//    public void addMemberShip() throws SQLException {
//        String sql = "INSERT INTO memberships (membershiptype, membership_price, membership_description, date_purchased, user_id) VALUES (?, ?, ?, ?, ?)";
//        try (Connection conn = DatabaseConnection.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setString(1, membership.getMembershipType());
//            pstmt.setInt(2, membership.getMembership_price())
//            pstmt.setDate(4, Date.valueOf(membership.getDatePurchased()));
//            pstmt.setInt(5,membership.getUser_id());
//            pstmt.executeUpdate();
//        }
//    }
}
