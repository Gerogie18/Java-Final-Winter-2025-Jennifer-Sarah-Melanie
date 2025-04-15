package GymApp.services;
import GymApp.dao.WorkoutClassDAO;
import GymApp.models.WorkoutClass;
import GymApp.dao.UserDAO;
import GymApp.models.User;
import GymApp.models.childclasses.Trainer;
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


    public void createWorkoutClass(WorkoutClass workoutClass) throws IllegalArgumentException, SQLException {
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

        if (user.getRole() != User.Role.TRAINER) {
            throw new IllegalArgumentException("User must be registered as a trainer");
        }
        try {
            workoutClassDAO.createNewWorkoutClass(workoutClass);
        } catch (SQLException err) {
            log.warning("Failed to create class: " + workoutClass.getName() + ", trainer ID: " + workoutClass.getTrainerId() + ". Reason: " + err.getMessage());
            throw err;
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
        if (workoutClass.getStatus() == null) {
            throw new IllegalArgumentException("Workout class status must be provided.");
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

    public void listWorkoutsByTrainer(Trainer trainer) throws SQLException {
        if (trainer == null) {
            System.out.println("Provide A Trainer Please ");
        } else {
            workoutClassDAO.getWorkoutsByTrainer(trainer);
        }
    }

    public void listAllWorkouts() throws SQLException {
        workoutClassDAO.getAllWorkoutClasses();
    }
}
