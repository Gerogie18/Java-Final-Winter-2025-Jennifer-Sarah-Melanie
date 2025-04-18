package GymApp.services;

import GymApp.dao.MembershipDAO;
import GymApp.dao.UserDAO;
import GymApp.models.Membership;
import java.util.List;
import java.util.logging.Logger;
import java.sql.SQLException;

/**
 * Service layer for managing gym memberships.
 * <p>
 * Handles operations like creating memberships, retrieving user membership history,
 * and calculating total membership costs. Delegates data access to the {@link MembershipDAO}.
 * </p>
 *
 *  @author Melanie Adams
 *  @version 1.0
 */
public class MembershipService {
    Logger log = Logger.getLogger(MembershipService.class.getName());

    private final MembershipDAO membershipDAO;

    public MembershipService(MembershipDAO membershipDAO) {
        new UserDAO();
        this.membershipDAO = membershipDAO;
    }


    //adds new membership
    public void addMembership(Membership membership) throws SQLException {
        membershipDAO.addMembership(membership);
    }


    // deletes based on membershipID
    public void deleteMembershipById(int membershipId, String userId) throws SQLException {
        if (membershipId <= 0) {
            throw new IllegalArgumentException("Invalid membership ID.");
        }

        try {
            boolean isDeleted = membershipDAO.deleteMembershipById(membershipId);
            if (!isDeleted) {
                log.info("No membership found with ID: " + membershipId + ", nothing to delete.");
                throw new SQLException("Deletion failed; no record found with ID: " + membershipId);
            }
            log.info("Membership " + membershipId + " deleted by " + "user " + userId);
        } catch (SQLException err) {
            log.warning("Error during membership deletion: " + err.getMessage());
            throw err; // Rethrow to ensure the caller can react appropriately
        }
    }

    public void updateMembership(Membership membership) throws SQLException {
        membershipDAO.updateMembership(membership);
    }

    public List<Membership> getAllMemberships() throws SQLException {
        return membershipDAO.getAllMemberships();
    }

    public void printAllMemberships() throws SQLException{
        try {
            List<Membership> memberships = getAllMemberships();
            if (memberships.isEmpty()) {
                System.out.println("No memberships found.");
            } else {
                System.out.println("--- All Memberships ---");
                System.out.println(
                        "--------------------------------------------------------------------------------------------------");
                System.out.println(String.format("%-12s | %-15s | %-30s | %-10s | %-15s | %-8s",
                        "MEMBERSHIP ID", "TYPE", "DESCRIPTION", "COST", "START DATE", "MEMBER ID"));
                System.out.println(
                        "--------------------------------------------------------------------------------------------------");
                for (Membership membership : memberships) {
                    System.out.println(membership.toString());
                }
                System.out.println(
                        "--------------------------------------------------------------------------------------------------");
            }
        } catch (SQLException e) {
            log.warning("Error retrieving memberships: " + e.getMessage());
        }
    }

    public double calculateAnnualRevenue(int year) throws SQLException {
        List<Membership> memberships = getAllMemberships();
        double annualRevenue = 0;
        for (Membership membership : memberships) {
            if (membership.getMembershipStartDate().getYear() == year) {
                annualRevenue += membership.getMembershipCost();
            }
        }
        return annualRevenue;
    }

    public double calculateMembershipCosts(int memberId) throws SQLException {
        List<Membership> memberships = getMembershipsByMember(memberId);
        double totalCost = 0;
        for (Membership membership : memberships) {
                totalCost += membership.getMembershipCost();
        }
        return totalCost;
    }


    public List<Membership> getMembershipsByMember(int userId) throws SQLException {
        if (userId <= 0) {
            throw new IllegalArgumentException("Member ID must not be null.");
        }
        return membershipDAO.getMembershipsByMember(userId);
    }

    public void printMembershipByMember(int userId) {
        try {
            List<Membership> memberships = getMembershipsByMember(userId);
            if (memberships.isEmpty()) {
                System.out.println("No memberships found.");
            } else {
                System.out.println("--- Memberships for " + userId + " ---");
                System.out.println(
                        "--------------------------------------------------------------------------------------------------");
                System.out.println(String.format("%-12s | %-15s | %-30s | %-10s | %-15s | %-8s",
                        "MEMBERSHIP ID", "TYPE", "DESCRIPTION", "COST", "START DATE", "MEMBER ID"));
                System.out.println(
                        "--------------------------------------------------------------------------------------------------");
                for (Membership membership : memberships) {
                    System.out.println(membership.toString());
                }
                System.out.println(
                        "--------------------------------------------------------------------------------------------------");
            }
        } catch (SQLException e) {
            log.warning("Error retrieving memberships: " + e.getMessage());
        }
    }
}