package GymApp.menus;

import GymApp.services.MembershipService;
import GymApp.services.WorkoutClassService;
import java.util.Scanner;

public class MemberMenu {

    public static void displayMenu(Scanner scanner, MembershipService membershipService, WorkoutClassService workoutClassService, int memberId) {
        int choice;
        do {
            System.out.println("\n=== Member Menu ===");
            System.out.println("1. Browse Workout Classes");
            System.out.println("2. View Total Membership Expenses");
            System.out.println("3. Purchase New Gym Membership");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.next(); // Consume invalid input
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    //browseWorkoutClasses(scanner, workoutClassService);
                    break;
                case 2:
                    //viewTotalMembershipExpenses(scanner, membershipService, memberId);
                    break;
                case 3:
                    //purchaseNewMembership(scanner, membershipService, memberId);
                    break;
                case 4:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 4);
    }
}