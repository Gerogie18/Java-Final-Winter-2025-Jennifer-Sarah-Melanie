package GymApp.dao;
import GymApp.database.DatabaseConnection;
import GymApp.models.WorkoutClass;
import GymApp.models.enums.Status;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class WorkoutClassDAO {
    public void createNewWorkoutClass(WorkoutClass workoutClass) throws SQLException {
        String sql = "INSERT INTO public.workout_classes(\n" +
                "\t class_name, class_type, class_description, class_status, class_capacity, trainer_id)\n" +
                "\t VALUES (?, ?, ?, ?, ?, ?);";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, workoutClass.getName());
            stmt.setString(2, workoutClass.getType());
            stmt.setString(3, workoutClass.getDescription());
            stmt.setString(4, workoutClass.getStatusAsString());
            stmt.setInt(5, workoutClass.getClass_capacity());
            stmt.setInt(6, workoutClass.getTrainerId());

            //check if update was successfull
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted == 0) {
                throw new SQLException("Creating workout class failed; no rows affected.");
            }

            //get workshop ID
            try (var rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    workoutClass.setId(generatedId);  //set workoutClass id
                } else {
                    throw new SQLException("Creating workout class failed; no ID obtained.");
                }
            }
        }
    }

    public boolean updateWorkoutClass(WorkoutClass workoutClass) throws SQLException {
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
        }
    }


    public boolean deleteWorkoutClass(int id) throws SQLException {
        String sql = "DELETE FROM public.workout_classes WHERE class_id = ?;";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public WorkoutClass getWorkoutClassById(int classId) throws SQLException {

        String sql = "SELECT * FROM workout_classes WHERE class_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, classId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new WorkoutClass(
                            classId,
                            rs.getString("class_name"),
                            rs.getString("class_type"),
                            rs.getString("class_description"),
                            Status.fromString(rs.getString("class_status")),
                            rs.getInt("class_capacity"),
                            rs.getInt("trainer_id")
                    );
                } else {
                    return null;
                }
            }
        }
    }


    public List<WorkoutClass> getWorkoutsByTrainer(int trainerId) throws SQLException {
        List<WorkoutClass> workoutClasses = new ArrayList<>();
        String sql = "SELECT * FROM workout_classes WHERE trainer_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, trainerId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    WorkoutClass workoutClass = new WorkoutClass(
                            rs.getInt("class_id"),
                            rs.getString("class_name"),
                            rs.getString("class_type"),
                            rs.getString("class_description"),
                            Status.fromString(rs.getString("class_status")),  // safer version of valueOf
                            rs.getInt("class_capacity"),
                            trainerId
                    );
                    workoutClasses.add(workoutClass);
                }
            }
        }

        return workoutClasses;
    }



    public List<WorkoutClass> getAllWorkoutClasses() throws SQLException {
        List<WorkoutClass> workoutClasses = new ArrayList<>();
        String sql = "SELECT * FROM public.workout_classes;";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    workoutClasses.add(new WorkoutClass(
                            rs.getInt("class_id"),
                            rs.getString("class_name"),
                            rs.getString("class_type"),
                            rs.getString("class_description"),
                            Status.valueOf(rs.getString("class_status")),
                            rs.getInt("class_capacity"),
                            rs.getInt("trainer_id")
                    ));
                }
            }

        }
        return workoutClasses;
    }



    public List<WorkoutClass> getWorkoutClassesByStatus(Status status) throws SQLException {
        List<WorkoutClass> classes = new ArrayList<>();
        String sql = "SELECT * FROM workout_classes WHERE class_status = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status.toString());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    classes.add(new WorkoutClass(
                            rs.getInt("class_id"),
                            rs.getString("class_name"),
                            rs.getString("class_type"),
                            rs.getString("class_description"),
                            Status.fromString(rs.getString("class_status")),
                            rs.getInt("class_capacity"),
                            rs.getInt("trainer_id")
                    ));
                }
            }
        }
        return classes;
    }
}
