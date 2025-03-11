package org.keyin.workoutclasses;

import java.sql.SQLException;

public class WorkoutClassService {
    // Inject in the DAO to be able to use it in the service
    private WorkoutClassDAO workoutDAO;

    public void addNewWorkoutClass(WorkoutClass workoutClass) throws SQLException {
        System.out.println("Adding new workout class");
        workoutDAO.addWorkoutClass(workoutClass);
    }
}
