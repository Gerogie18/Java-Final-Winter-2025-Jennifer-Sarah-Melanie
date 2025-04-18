package GymApp.services;
import GymApp.dao.WorkoutClassDAO;
import GymApp.models.WorkoutClass;
import GymApp.dao.UserDAO;
import GymApp.models.User;
import GymApp.models.enums.UserRole;
import java.util.List;
import java.util.logging.Logger;
import java.sql.SQLException;

/**
 * Service layer responsible for managing business logic related to workout classes.
 * <p>
 * This class handles creation, updating, deletion, and retrieval of workout classes.
 * It validates trainer roles, enforces access control, and delegates data persistence
 * to the {@link WorkoutClassDAO}. It may also use {@link UserDAO} to perform
 * user-related validations, such as verifying trainer identities.
 * </p>
 *
 * <p>
 * This service is used by controllers or menu handlers to process user actions
 * related to scheduling, managing, and displaying workout classes.
 * </p>
 *
 * @author Sarah Perry
 * @version 1.0
 */

public class WorkoutClassService {
    Logger log = Logger.getLogger(WorkoutClassService.class.getName());

    private final WorkoutClassDAO workoutClassDAO;
    private final UserDAO userDAO;

    public WorkoutClassService(WorkoutClassDAO workoutClassDAO, UserDAO userDAO) {
        this.workoutClassDAO = workoutClassDAO;
        this.userDAO = userDAO;
    }


    public void addNewWorkoutClass(WorkoutClass workoutClass) throws IllegalArgumentException, SQLException {
        // Validate workout class details
        if (workoutClass.getName() == null || workoutClass.getName().isEmpty()) {
            throw new IllegalArgumentException("Class name cannot be empty.");
        }
        if (workoutClass.getType() == null || workoutClass.getType().isEmpty()) {
            throw new IllegalArgumentException("Workout class type cannot be null or empty");
        }
        if (workoutClass.getStatus() == null) {
            throw new IllegalArgumentException("Workout class status must be provided.");
        }
        User user = userDAO.getUserById(workoutClass.getTrainerId());

        if (user.getRole() != UserRole.TRAINER) {
            throw new IllegalArgumentException("User must be registered as a trainer");
        }
        try {
            workoutClassDAO.createNewWorkoutClass(workoutClass);
            log.info("Workout class created: " + workoutClass.getName() + " (Trainer ID: " + workoutClass.getTrainerId() + ")");
        } catch (SQLException err) {
            log.warning("Failed to create class: " + workoutClass.getName() + ", trainer ID: " + workoutClass.getTrainerId() + ". Reason: " + err.getMessage());
            throw err;
        }
    }


    /**
     * updates WorkoutClass if user has permission
     * @param updatedClass
     * @param userRole
     * @param userId
     * @throws SQLException
     */

    public void updateWorkoutClass(WorkoutClass updatedClass, UserRole userRole, int userId) throws SQLException {
        //Check if updatedClass is ok
        if (updatedClass == null || updatedClass.getId() <= 0) {
            throw new IllegalArgumentException("Invalid workout class.");
        }

        // update database
        try {
            boolean isUpdated = workoutClassDAO.updateWorkoutClass(updatedClass);
            if (!isUpdated) {
                log.info("Update failed; no changes were saved for class ID: " + updatedClass.getId());
                throw new SQLException("Update failed; no record found with ID: " + updatedClass.getId());
            }
            log.info("Workout class " + updatedClass.getId() + " updated by user " + userId);
        } catch (SQLException err) {
            log.warning("Error during workout class update for class ID: " + updatedClass.getId() + " by user ID: " + userId + " – " +  err.getMessage());
            throw err;  // Rethrow to ensure the caller can react appropriately
        }
    }


    /**
     * Deletes workout class if user has permission
     * @param classId
     * @param userRole
     * @param userId
     * @throws SQLException
     */
    public void deleteWorkoutClass(int classId, UserRole userRole, int userId) throws SQLException {
        if (classId <= 0) {
            throw new IllegalArgumentException("Invalid workout class ID.");
        }

        WorkoutClass workout = workoutClassDAO.getWorkoutClassById(classId);

        if (workout == null) {
            throw new SQLException("Workout class not found.");
        }
        if (userRole != UserRole.ADMIN && workout.getTrainerId() != userId) {
            throw new IllegalArgumentException("Trainers can only delete their own classes.");
        }

        try {
            boolean isDeleted = workoutClassDAO.deleteWorkoutClass(classId);
            if (!isDeleted) {
                log.info("No workout class found with ID: " + classId + ", nothing to delete.");
                throw new SQLException("Deletion failed; no record found with ID: " + classId);
            }
            log.info("Workout class " + classId + " deleted by user " + userId);
        } catch (SQLException err) {
            log.warning("Error during workout class deletion: " + err.getMessage());
            throw err;  // Rethrow to ensure the caller can react appropriately
        }
    }


    /**
     * Retrieves a workout class by its ID
     * @param classId
     * @return
     * @throws SQLException
     */

    public WorkoutClass getWorkoutClassById(int classId) throws SQLException {
        if (classId <= 0) {
            throw new IllegalArgumentException("Invalid workout class ID.");
        }

        try {
            WorkoutClass workoutClass = workoutClassDAO.getWorkoutClassById(classId);

            if (workoutClass == null) {
                log.info("No workout class found with ID: " + classId);
                throw new SQLException("Workout class not found.");
            }
            log.info("Workout class retrieved: ID=" + classId);
            return workoutClass;
        } catch (SQLException err) {
            log.warning("Failed to retrieve workout class ID=" + classId + ": " + err.getMessage());
            throw err;  // Rethrow to ensure the caller can react appropriately
        }
    }

    /**
     * Returns a list of all the workouts associated with a trainer ID
     * @param trainerId
     * @return
     * @throws SQLException
     */

    public List<WorkoutClass> listWorkoutsByTrainer(int trainerId) throws SQLException {
        if (trainerId <= 0) {
            throw new IllegalArgumentException("Invalid trainer ID.");
        }

        try {
            List<WorkoutClass> classes = workoutClassDAO.getWorkoutsByTrainer(trainerId);

            if (classes.isEmpty()) {
                log.info("No workout classes found for trainer ID: " + trainerId);
            } else {
                log.info("Retrieved " + classes.size() + " classes for trainer ID: " + trainerId);
            }

            return classes;

        } catch (SQLException err) {
            log.warning("Error retrieving classes for trainer ID=" + trainerId + ": " + err.getMessage());
            throw err;
        }
    }


    /**
     * Returns a list of all workouts; members only see active workout classes
     * @param userRole
     * @return
     * @throws SQLException
     */
    public List<WorkoutClass> listAllWorkouts(UserRole userRole) throws SQLException {
        try {
            List<WorkoutClass> classes;

            if (userRole == UserRole.MEMBER) {
                classes = workoutClassDAO.getWorkoutClassesByStatus("ACTIVE");
                log.info("Retrieved " + classes.size() + " active classes for MEMBER view.");
            } else {
                classes = workoutClassDAO.getAllWorkoutClasses();
                log.info("Retrieved " + classes.size() + " total classes for role: " + userRole);
            }

            return classes;

        } catch (SQLException err) {
            log.warning("Error retrieving workout classes for role=" + userRole + ": " + err.getMessage());
            throw err;
        }
    }

}
