package org.keyin.workoutclasses;
import org.keyin.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class WorkoutClassDAO {
    Logger log = Logger.getLogger(WorkoutClassDAO.class.getName());

    public void createNewWorkoutClass(WorkoutClass workoutClass)  throws SQLException{
        String sql = "INSERT INTO public.workout_classes(\n" +
                "\t class_name, class_type, class_description, class_status, class_capacity, trainer_id)\n" +
                "\t VALUES (?, ?, ?, ?, ?, ?);";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, workoutClass.getName());
            stmt.setString(2, workoutClass.getType());
            stmt.setString(3, workoutClass.getDescription());
            stmt.setString(4, workoutClass.getStatus());
            stmt.setInt(5, workoutClass.getClass_capacity());
            stmt.setInt(6, workoutClass.getTrainer());
            stmt.executeUpdate();

        } catch (SQLException err) {
            log.severe("Failed to create workout class: " + err.getMessage());
            throw new SQLException("Error creating workout class", err);
        }
    }

    public boolean updateWorkoutClass(WorkoutClass workoutClass) throws SQLException{
        String sql = "UPDATE public.workout_classes\n" +
                "\tSET class_name = ?, class_type = ?, class_description = ?, class_status = ?, class_capacity = ?, trainer_id = ?\n" +
                "\tWHERE class_id = ?;";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, workoutClass.getName());
            stmt.setString(2, workoutClass.getType());
            stmt.setString(3, workoutClass.getDescription());
            stmt.setString(4, workoutClass.getStatus());
            stmt.setInt(5, workoutClass.getClass_capacity());
            stmt.setInt(6, workoutClass.getTrainer());
            stmt.setInt(7, workoutClass.getId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException err) {
            log.severe("Failed to update workout class: " + err.getMessage());
            throw new SQLException("Error updating workout class", err);
        }
    }




    public boolean deleteWorkoutClass(int id) throws SQLException {
        String sql = "DELETE FROM public.workout_classes WHERE class_id = ?;";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException err) {
            log.severe("Failed to delete workout class: " + err.getMessage());
            throw new SQLException("Error deleting workout class", err);
        }
    }

    public void getWorkoutClassById(int id)  throws SQLException {
    }

    public void getWorkoutClassByName(String name)  throws SQLException{
    }
    public void getAllWorkoutClasses()  throws SQLException{
        String sql = "SELECT * FROM public.workout_classes;";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.executeQuery();
        } catch (SQLException error) {
            log.info("Error:" + error.getMessage());
        }
    }

}
