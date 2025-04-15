package GymApp.menus;

import GymApp.models.User;
import GymApp.models.WorkoutClass;
import GymApp.models.childclasses.Trainer;
import GymApp.services.*;

import java.sql.SQLException;
import java.util.Scanner;

public class TrainerMenu implements Menu {

    @Override
    public void show(Scanner scanner, User user, UserService userService, MembershipService membershipService, WorkoutClassService workoutService) throws SQLException {
        int choice;
        do {
            System.out.println("\n=== Trainer Menu ===");
            System.out.println("1. Add a Workout Class");
            System.out.println("2. View My Workout Classes");
            System.out.println("3. Logout");
            System.out.print("Enter your choice: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.next();
            }

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addWorkout(scanner, user, workoutService);
                    break;
                case 2:
                    viewMyClasses(user, workoutService);
                    break;
                case 3:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice! Try again.");
            }

        } while (choice != 3);
    }

    private void addWorkout(Scanner scanner, User trainer, WorkoutClassService workoutService) {
        System.out.println("Enter workout class name: ");
        String name = scanner.nextLine();

        System.out.println("Enter workout type: ");
        String type = scanner.nextLine();

        System.out.println("Enter description: ");
        String description = scanner.nextLine();

        WorkoutClass workoutClass = new WorkoutClass();
        workoutClass.setName(name);
        workoutClass.setType(type);
        workoutClass.setDescription(description);
        workoutClass.setTrainerByID(trainer.getUserId());

        try {
            workoutService.createWorkoutClass(workoutClass);
            System.out.println("Workout class added successfully!");
        } catch (Exception e) {
            System.out.println("Error adding workout class: " + e.getMessage());
        }
    }

    private void viewMyClasses(int trainerId, WorkoutClassService workoutService) {
        try {
            workoutService.listWorkoutsByTrainer(trainer.getUserId()).forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println("Error retrieving classes.");
            e.printStackTrace();
        }
    }
}
