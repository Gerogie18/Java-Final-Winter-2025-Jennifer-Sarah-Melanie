package GymApp.services;
import GymApp.dao.WorkoutClassDAO;
import GymApp.models.WorkoutClass;
import GymApp.dao.UserDAO;
import GymApp.models.User;
import GymApp.models.enums.UserRole;
import GymApp.models.enums.WorkoutStatus;
import java.util.List;
import java.util.logging.Logger;
import java.sql.SQLException;

public class WorkoutClassService {
    Logger log = Logger.getLogger(WorkoutClassService.class.getName());

    private final WorkoutClassDAO workoutClassDAO;
    private final UserDAO userDAO;

    public WorkoutClassService(WorkoutClassDAO workoutClassDAO, UserDAO userDAO) {
        this.workoutClassDAO = workoutClassDAO;
        this.userDAO = userDAO;
    }

//Only Trainers can add a new Class; we might want to modify this so Admins can, too.
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
        } catch (SQLException err) {
            log.warning("Failed to create class: " + workoutClass.getName() + ", trainer ID: " + workoutClass.getTrainerId() + ". Reason: " + err.getMessage());
            throw err;
        }
    }



// updates WorkoutClass based on role
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


//deletes based on user role
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
            log.info("Workout class " + classId + " deleted by " + "user " + userId);
        } catch (SQLException err) {
            log.warning("Error during workout class deletion: " + err.getMessage());
            throw err;  // Rethrow to ensure the caller can react appropriately
        }
    }



    public WorkoutClass getWorkoutClassById(int classId) throws SQLException {
        if (classId <= 0) {
            throw new IllegalArgumentException("Trainer must not be null.");
        }

        WorkoutClass workoutClass = workoutClassDAO.getWorkoutClassById(classId);

        if (workoutClass == null) {
            log.info("No workout class found with ID: " + classId);
            throw new SQLException("Workout class not found.");
        }
        log.info("Workout class " + classId + " retrieved");
        return workoutClass;
    }

    public List<WorkoutClass> listWorkoutsByTrainer(int trainerId) throws SQLException {
        if (trainerId <= 0) {
            throw new IllegalArgumentException("Trainer must not be null.");
        }
        return workoutClassDAO.getWorkoutsByTrainer(trainerId);
    }

    public List<WorkoutClass> listAllWorkouts(UserRole userRole) throws SQLException {
        if (userRole == UserRole.MEMBER) {
            return workoutClassDAO.getWorkoutClassesByStatus(WorkoutStatus.active);
        } else {
            return workoutClassDAO.getAllWorkoutClasses();
        }
    }


}
