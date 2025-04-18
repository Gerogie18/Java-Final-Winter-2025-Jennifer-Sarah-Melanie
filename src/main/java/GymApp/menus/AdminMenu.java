package GymApp.menus;

import GymApp.services.MembershipService;
import GymApp.services.MenuActions;
import GymApp.services.UserService;
import java.util.Scanner;

/**
 * Handles the command-line interface for admin-specific actions.
 * <p>
 * This class provides a static method to display and manage the admin menu,
 * allowing administrators to view all users, filter users by role, delete users,
 * review gym memberships, and calculate total revenue.
 * </p>
 *  @author Jennifer Lyver
 *  @version 1.0
 */

public class AdminMenu {

    public static void displayMenu(Scanner scanner, MembershipService membershipService, UserService userService, int adminId) {
        int choice;
        do {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. View all Users");
            System.out.println("2. View Users by Role");
            System.out.println("3. Delete User");
            System.out.println("4. View Memberships");
            System.out.println("5. View Total Revenue");
            System.out.println("6. Logout");
            System.out.print("Enter your choice: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.next();
            }

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    MenuActions.viewAllUsers(scanner, userService);
                    break;
                case 2:
                    MenuActions.viewUsersByRole(scanner, userService);
                    break;
                case 3:
                    MenuActions.deleteUser(scanner, adminId, userService);
                    break;
                case 4:
                    MenuActions.viewAllMemberships(membershipService);
                    break;
                case 5:
                    MenuActions.viewAnnualRevenue(scanner, membershipService);
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