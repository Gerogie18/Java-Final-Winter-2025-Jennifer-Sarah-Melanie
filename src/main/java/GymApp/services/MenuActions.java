package GymApp.services;

import GymApp.models.User;

import java.sql.SQLException;
import java.util.Scanner;

public class MenuActions {


//addWorkout(scanner);
//viewWorkoutsByTrainer(scanner, trainer.getUserId());
//deleteWorkout(scanner);
//updateWorkout(scanner);


// Users

private static void addNewUser(Scanner scanner, UserService userService) {
    System.out.print("Enter username: ");
    String username = scanner.nextLine();
    System.out.print("Enter password: ");
    String password = scanner.nextLine();
    System.out.print("Enter role (Admin/Trainer/Member): ");
    String role = scanner.nextLine();

    User user = new User(username, password, role);
    try {
        userService.addUser(user);
        System.out.println("User added successfully!");
    } catch (SQLException e) {
        System.out.println("Error adding user: " + e.getMessage());
    }
}


// Workouts
private static void addWorkout(Scanner scanner, UserService userService, WorkoutClassService workoutService) {
    System.out.print("Enter username: ");
    String username = scanner.nextLine();
    System.out.print("Enter password: ");
    String password = scanner.nextLine();
    System.out.print("Enter role (Admin/Trainer/Member): ");
    String role = scanner.nextLine();

    User user = new User(username, password, role);
    try {
        userService.addUser(user);
        System.out.println("User added successfully!");
    } catch (SQLException e) {
        System.out.println("Error adding user: " + e.getMessage());
    }
}
}
