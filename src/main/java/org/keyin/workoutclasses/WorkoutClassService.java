package org.keyin.workoutclasses;
import org.keyin.user.UserDAO;
import org.keyin.user.User;
import java.util.Arrays;
import java.util.HashSet;
import java.util.logging.Logger;
import java.sql.SQLException;

public class WorkoutClassService {
    Logger log = Logger.getLogger(WorkoutClassService.class.getName());
    // Inject in the DAO to be able to use it in the service
    private WorkoutClassDAO workoutClassDAO;
    private UserDAO userDAO;

    public WorkoutClassService(WorkoutClassDAO workoutClassDAO) {
        this.workoutClassDAO = workoutClassDAO;
        this.userDAO = userDAO;
    }


    // Need to add more validation (e.g., about trainer)
    public void createWorkoutClass(WorkoutClass workoutClass) throws IllegalArgumentException, SQLException {
        // Validate workout class details
        if (workoutClass.getName() == null || workoutClass.getName().isEmpty()) {
            throw new IllegalArgumentException("Class name cannot be empty.");
        }
        if (workoutClass.getType() == null || workoutClass.getType().isEmpty()) {
            throw new IllegalArgumentException("Workout class type cannot be null or empty");
        }
        if (!new HashSet<>(Arrays.asList("CANCELLED", "ACTIVE", "INACTIVE")).contains(workoutClass.getStatus())) {
            throw new IllegalArgumentException("Workout class status must be active, inactive, or cancelled");
        }
        User user = userDAO.getUserById(workoutClass.getTrainer());
        User.Role userRole = User.getRole(user);
        if (!"trainer".equals(userRole)) {
            throw new IllegalArgumentException("User must be registered as a trainer");
        }
        try {
            workoutClassDAO.createNewWorkoutClass(workoutClass);
        } catch (SQLException err) {
            log.warning("Error during workout class creation: " + err.getMessage());
            throw err;  // should we add different details here?
        }
    }
    // Need to update validation here
    public void updateWorkoutClass(WorkoutClass workoutClass) throws IllegalArgumentException, SQLException {
        if (workoutClass.getId() <= 0) {
            throw new IllegalArgumentException("Invalid workout class ID.");
        }
        if (workoutClass.getName() == null || workoutClass.getName().isEmpty()) {
            throw new IllegalArgumentException("Workout class name cannot be null or empty");
        }
        if (workoutClass.getType() == null || workoutClass.getType().isEmpty()) {
            throw new IllegalArgumentException("Workout class type cannot be null or empty");
        }
        if (!new HashSet<>(Arrays.asList("CANCELLED", "ACTIVE", "INACTIVE")).contains(workoutClass.getStatus())) {
            throw new IllegalArgumentException("Workout class status must be active, inactive, or cancelled");
        }

        boolean updated = workoutClassDAO.updateWorkoutClass(workoutClass);
        if (!updated) {
            log.info("No workout class was updated; possibly does not exist.");
            throw new SQLException("Update failed; no workout class found with ID: " + workoutClass.getId());
        }
    }

    //Could add if classID not an int or something else for validation
    public void deleteWorkoutClass(int classId) throws IllegalArgumentException, SQLException {
        if (classId <= 0) {
            throw new IllegalArgumentException("Invalid workout class ID.");
        }
        try {
            boolean isDeleted = workoutClassDAO.deleteWorkoutClass(classId);
            if (!isDeleted) {
                log.info("No workout class found with ID: " + classId + ", nothing to delete.");
                throw new SQLException("Deletion failed; no record found with ID: " + classId);
            }
        } catch (SQLException err) {
            log.warning("Error during workout class deletion: " + err.getMessage());
            throw err;  // Rethrow to ensure the caller can react appropriately
        }
    }
}
