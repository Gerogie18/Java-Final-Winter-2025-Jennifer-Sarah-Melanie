package GymApp.dao;
import GymApp.database.DatabaseConnection;
//import org.keyin.user.User;
import GymApp.models.WorkoutClass;
import GymApp.models.childclasses.Trainer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
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
            stmt.setString(4, workoutClass.getStatusAsString());
            stmt.setInt(5, workoutClass.getClass_capacity());
            stmt.setInt(6, workoutClass.getTrainerId());
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
            stmt.setString(4, workoutClass.getStatusAsString());
            stmt.setInt(5, workoutClass.getClass_capacity());
            stmt.setInt(6, workoutClass.getTrainerId());
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



    public ResultSet getWorkoutsByTrainer(Trainer trainer) throws SQLException {
        ResultSet rs = null;
        String sql = "SELECT * FROM workout_classes where trainer_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1,trainer.getUserId());
            rs = pstmt.executeQuery();
            System.out.println("List of Workout Classes for Trainer " + trainer.getUsername()+ " " + trainer.getUserId());
            while (rs.next()) {
                int workout_id = rs.getInt("class_id");
                String name = rs.getString("class_name");
                String type = rs.getString("class_type");
                String description = rs.getString("class_description");
                String status = rs.getString("class_status");
                int capacity = rs.getInt("class_capacity");
                int trainer_id = rs.getInt("trainer_id");

                System.out.println("-----------------");
                System.out.println("Class ID: " + workout_id);
                System.out.println("Name : " + name);
                System.out.println("Type : " + type);
                System.out.println("Description : " + description);
                System.out.println("Status: " + status);
                System.out.println("Max capacity: " + capacity);
                System.out.println("-------------------------");
            }
        } catch (SQLException error) {
            throw new RuntimeException(error);
        }
        return rs;
    }


    public void getWorkoutClassByName(String name)  throws SQLException{
    }

    public void getAllWorkoutClasses()  throws SQLException{
        ResultSet rs = null;
        String sql = "SELECT * FROM public.workout_classes;";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            rs = stmt.executeQuery();
            while (rs.next()) {
                int workout_id = rs.getInt("class_id");
                String name = rs.getString("class_name");
                String type = rs.getString("class_type");
                String description = rs.getString("class_description");
                String status = rs.getString("class_status");
                int capacity = rs.getInt("class_capacity");
                int trainer_id = rs.getInt("trainer_id");

                System.out.println("-----------------");
                System.out.println("Class ID: " + workout_id);
                System.out.println("Name : " + name);
                System.out.println("Type : " + type);
                System.out.println("Description : " + description);
                System.out.println("Status: " + status);
                System.out.println("Max capacity: " + capacity);
                System.out.println("Trainer ID: " + trainer_id);
                System.out.println("-------------------------");
            }
        } catch (SQLException error) {
            log.info("Error:" + error.getMessage());
        }
    }
}
