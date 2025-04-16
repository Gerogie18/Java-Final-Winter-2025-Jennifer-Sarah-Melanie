package GymApp;

import GymApp.dao.WorkoutClassDAO;
import GymApp.dao.UserDAO;
import GymApp.models.WorkoutClass;
import GymApp.models.childclasses.Trainer;
import GymApp.services.UserService;
import GymApp.services.WorkoutClassService;

import java.sql.SQLException;

import static GymApp.models.WorkoutClass.Status.ACTIVE;

public class WorkoutClassTest {
    public static void main(String[] args) throws SQLException {
        // Initialize services
        UserDAO userDAO = new UserDAO();
        WorkoutClassDAO workoutDAO = new WorkoutClassDAO();

        WorkoutClassService workoutService = new WorkoutClassService(workoutDAO, userDAO);
        WorkoutClass workoutClass = new WorkoutClass("Morning Yoga", "Yoga", "Start your day with a stretch", ACTIVE, 20, 3);

        Trainer trainer = new Trainer("the trainer", "password123", "trainer2@email.com", "7095551234", "123 Main St");

        workoutService.addNewWorkoutClass(workoutClass);
}}

