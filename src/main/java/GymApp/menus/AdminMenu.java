package GymApp.menus;

import GymApp.services.MembershipService;
import GymApp.services.MenuActions;
import GymApp.services.UserService;
import java.sql.SQLException;
import java.util.Scanner;

public abstract class AdminMenu implements Menu {

    //Admin must be able to:
    // - view all users
    // - delete users
    // - view memberships
    // - annual total revenue

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
                    MenuActions.viewAllMemberships(scanner, membershipService);
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