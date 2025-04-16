package GymApp.services;

import GymApp.dao.MembershipDAO;
import GymApp.dao.UserDAO;
import GymApp.models.User;
import GymApp.models.WorkoutClass;
import GymApp.models.enums.Role;
import GymApp.models.Membership;
import java.util.List;
import java.util.logging.Logger;
import java.sql.SQLException;

// Service class for membership handle all the business logic
// and only uses the DAO to interact with the database it does not have methods to do so
// you can inject in your dao to use in your service. An example will be in the code
public class MembershipService {
    Logger log = Logger.getLogger(WorkoutClassService.class.getName());

    private final MembershipDAO membershipDAO;

    // When you inject in the DAO you have access to all methods in it
    // MembershipDAO membershipDAO = new MembershipDAO();this.userDAO=userDAO;

    public MembershipService(MembershipDAO membershipDAO) {
        new UserDAO();
        this.membershipDAO = membershipDAO;
    }

    // For each of your DAO methods, you want to make something like this:
    public void addMembership(Membership membership) throws SQLException {
        membershipDAO.addMembership(membership);
    }

    // public void deleteMembershipById(int membership) throws SQLException {
    // membershipDAO.deleteMembershipById(membership);
    // }

    // deletes based on membershipID
    public void deleteMembershipById(int membershipId, String userId) throws SQLException {
        if (membershipId <= 0) {
            throw new IllegalArgumentException("Invalid membership ID.");
        }

        // Membership membershipId = MembershipDAO.getMembershipById(membershipId);

        if (membershipId == null) { // for some reason this is not working, red underline
            throw new SQLException("Membership ID not found.");
        }

        // if (userRole != Role.ADMIN && workout.getTrainerId() != userId) {
        // throw new IllegalArgumentException("Trainers can only delete their own
        // classes.");
        // }
        // not sure how to edit this one, do I need it?

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

    // ? what data type is this?
    public void getAllMemberships() throws SQLException {
        membershipDAO.getAllMemberships();
    }

    // ? what data type is this?
    public void getMemberships(User user) throws SQLException {
        membershipDAO.getMemberships(user);
    }

}