package GymApp.menus;
import GymApp.services.MembershipService;
import GymApp.services.MenuActions;
import GymApp.models.enums.UserRole;
import GymApp.services.WorkoutClassService;
import java.sql.SQLException;
import java.util.Scanner;


/**
 * Handles the command-line interface for trainer-specific actions.
 * <p>
 * This class provides a static method to display and manage the trainer menu,
 * allowing trainers to create, update, and delete workout classes, view their classes,
 * and purchase memberships for themselves.
 * </p>
 */

public class TrainerMenu {

    public static void displayMenu(Scanner scanner, WorkoutClassService workoutService, MembershipService membershipService, int trainerId) throws SQLException {
        int choice;
        do {
            System.out.println("\n=== Trainer Menu ===");
            System.out.println("1. Add a Workout Class");
            System.out.println("2. Delete a Workout Class");
            System.out.println("3. Update a Workout Class");
            System.out.println("4. View My Workout Classes");
            System.out.println("5. Buy Membership");
            System.out.println("6. Logout");
            System.out.print("Enter your choice: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.next();
            }

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    MenuActions.addWorkout(scanner, trainerId, workoutService);
                    break;
                case 2:
                    MenuActions.deleteWorkout(scanner, trainerId, UserRole.TRAINER, workoutService);
                    break;
                case 3:
                    MenuActions.updateWorkout(scanner, trainerId, UserRole.TRAINER, workoutService);
                    break;
                case 4:
                    MenuActions.viewMyClasses(trainerId, workoutService);
                    break;
                case 5:
                    MenuActions.purchaseNewMembership(scanner, membershipService, trainerId);
                    break;
                case 6:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice! Try again.");
            }

        } while (choice != 6);
    }

}
