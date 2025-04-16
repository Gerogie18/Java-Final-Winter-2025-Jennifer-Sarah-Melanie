package GymApp.services;

import GymApp.models.WorkoutClass;
import GymApp.models.enums.Role;
import GymApp.models.enums.Status;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class MenuActions {
    private static final Logger log = Logger.getLogger(MenuActions.class.getName());


    // Workouts
    public static void addWorkout(Scanner scanner, int userId, WorkoutClassService workoutService) {
        System.out.println("Enter workout class name: ");
        String name = scanner.nextLine();

        System.out.println("Enter workout type: ");
        String type = scanner.nextLine();

        System.out.println("Enter description: ");
        String description = scanner.nextLine();

        System.out.println("Enter description: ");
        int capacity = scanner.nextInt();
        scanner.nextLine();

        WorkoutClass workoutClass = new WorkoutClass();
        workoutClass.setName(name);
        workoutClass.setType(type);
        workoutClass.setDescription(description);
        workoutClass.setStatus(Status.active);
        workoutClass.setClass_capacity(capacity);
        workoutClass.setTrainerByID(userId);

        try {
            workoutService.addNewWorkoutClass(workoutClass);
            System.out.println("Workout class added successfully!");
        } catch (IllegalArgumentException err) {
            System.out.println("Input error: " + err.getMessage());
        } catch (SQLException err) {
            System.out.println("Sorry! We couldn’t add the workout class right now.");
            log.warning("Error while adding workout class: " + err.getMessage());
        }
    }


    public static void deleteWorkout(Scanner scanner, int userId, Role userRole, WorkoutClassService workoutService) {
        // Initialize variables outside of while loop
        WorkoutClass workout = null;
        int workoutId = -1;

        // Keep prompting until a valid class is entered
        while (workout == null) {
            System.out.print("Enter the ID of the workout class you want to delete: ");

            // Only permit integers
            if (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid numeric ID.");
                scanner.nextLine(); // Clear invalid input
                continue;
            }

            workoutId = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            try {
                workout = workoutService.getWorkoutClassById(workoutId);
            } catch (IllegalArgumentException | SQLException e) {
                System.out.println("Workout class not found. Please try again.");
            }
        }

        System.out.println("Are you sure you want to delete this class? (y/n)");
        String confirm = scanner.nextLine();
        if (!confirm.equalsIgnoreCase("y")) {
            System.out.println("Deletion cancelled.");
            return;
        }

        try {
            workoutService.deleteWorkoutClass(workoutId, userRole, userId);
            System.out.println("Workout class deleted successfully!");
        } catch (IllegalArgumentException err) {
            System.out.println("Input error: " + err.getMessage());
        } catch (SQLException err) {
            System.out.println("Sorry! We couldn’t add the workout class right now.");
            err.printStackTrace();
        }
    }


    public static void updateWorkout(Scanner scanner, int userId, Role userRole, WorkoutClassService workoutService) {
        // Initialize variables outside of while loop
        WorkoutClass workout = null;
        int classId = -1;

        // Keep prompting until a valid class is entered
        while (workout == null) {
            System.out.print("Enter the ID of the workout class you want to update: ");

            // Only permit integers
            if (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid numeric ID.");
                scanner.nextLine(); // Clear invalid input
                continue;
            }

            classId = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            try {
                workout = workoutService.getWorkoutClassById(classId);
            } catch (IllegalArgumentException | SQLException e) {
                System.out.println("Workout class not found. Please try again.");
            }
        }

        // get User Input
        // Allow user to leave fields empty if they don't want to change anything
        System.out.println("Leave field empty to keep the current value.");

        System.out.println("Current name: " + workout.getName());
        System.out.print("New name: ");
        String name = scanner.nextLine();
        if (!name.isBlank()) workout.setName(name);

        System.out.println("Current type: " + workout.getType());
        System.out.print("New type: ");
        String type = scanner.nextLine();
        if (!type.isBlank()) workout.setType(type);

        System.out.println("Current description: " + workout.getDescription());
        System.out.print("New description: ");
        String description = scanner.nextLine();
        if (!description.isBlank()) workout.setDescription(description);

        System.out.println("Current capacity: " + workout.getClass_capacity());
        System.out.print("New capacity: ");
        String capacityInput = scanner.nextLine();
        if (!capacityInput.isBlank()) {
            int capacity = Integer.parseInt(capacityInput);
            workout.setClass_capacity(capacity);
        }

        System.out.println("Current status: " + workout.getStatus());
        System.out.print("New status (active, cancelled, inactive): ");
        String statusInput = scanner.nextLine();
        if (!statusInput.isBlank()) {
            workout.setStatus(Status.fromString(statusInput));
        }

        // Commit update
        try {
            workoutService.updateWorkoutClass(workout, userRole, userId);
            System.out.println("Workout class updated successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Input error: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Database error while updating class.");
            e.printStackTrace();
        }
    }

    public static void viewMyClasses(int trainerId, WorkoutClassService workoutService) {
        try {
            List<WorkoutClass> myClasses = workoutService.listWorkoutsByTrainer(trainerId);

            if (myClasses.isEmpty()) {
                System.out.println("You are not currently teaching any workout classes.");
                return;
            }

            System.out.println("\nYour Workout Classes:");
            System.out.println("--------------------------------------------------");

            for (WorkoutClass wc : myClasses) {
                System.out.println("Class ID:     " + wc.getId());
                System.out.println("Name:         " + wc.getName());
                System.out.println("Type:         " + wc.getType());
                System.out.println("Description:  " + wc.getDescription());
                System.out.println("Status:       " + wc.getStatus());
                System.out.println("Capacity:     " + wc.getClass_capacity());
                System.out.println("Trainer ID:   " + wc.getTrainerId());
                System.out.println("--------------------------------------------------");
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving your classes.");
            e.printStackTrace();
        }
    }
}
