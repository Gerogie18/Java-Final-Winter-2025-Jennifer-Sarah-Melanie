package GymApp.services;

import GymApp.models.WorkoutClass;
import GymApp.models.enums.UserRole;
import GymApp.models.enums.WorkoutStatus;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class MenuActions {
    public static void promptBackToMenu(Scanner scanner) {
        System.out.println();
        System.out.print("Back to menu?: ");
        scanner.nextLine(); // Consume the newline character left by previous nextLine() calls
        String input = scanner.nextLine();
    }

    // Users
    public static void viewAllUsers(Scanner scanner, UserService userService) {
        userService.printAllUsers();
        promptBackToMenu(scanner);
    }

    public static void viewUsersByRole(Scanner scanner, UserService userService) {
        UserRole userRole = null;
        // Role validation loop
        while (userRole == null) {
            System.out.print("Enter role (Admin/Trainer/Member): ");
            String roleInput = scanner.nextLine().trim();

            try {
                userRole = UserRole.fromString(roleInput);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid role entered: " + roleInput + ". Please enter Admin, Trainer, or Member.");
            }
        }
        switch (userRole) {
            case ADMIN:
                userService.printAllAdmins();
                promptBackToMenu(scanner);
                break;
            case MEMBER:
                userService.printAllMembers();
                promptBackToMenu(scanner);
                break;
            case TRAINER:
                userService.printAllTrainers();
                promptBackToMenu(scanner);
                break;
        }
    }

    public static void deleteUser(Scanner scanner, int adminId, UserService userService) {
        boolean validInput = false;
        int userIdToDelete = -1; // Initialize with an invalid value

        while (!validInput) {
            System.out.println("Enter ID of user to delete:");
            System.out.println("Leave empty to go back to menu");
            String userIdStr = scanner.nextLine().trim();
            if (userIdStr.isEmpty()) {
                break;
            }
            try {
                userIdToDelete = Integer.parseInt(userIdStr);
                validInput = true; // Parsing successful, exit the loop

                if (adminId == userIdToDelete) {
                    System.out.println("Cannot delete yourself.");
                    validInput = false; // Reset to prompt again
                } else {
                    try {
                        userService.deleteUser(userIdToDelete);
                        System.out.println("User with ID " + userIdToDelete + " deleted successfully.");
                    } catch (SQLException e) {
                        System.err.println("Error deleting user: " + e.getMessage());
                        validInput = false; // Consider if you want to loop again on SQL error
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid user ID format. Please enter a number.");
            }
        }
    }

    private static final Logger log = Logger.getLogger(MenuActions.class.getName());

    // Memberships
    public static void viewAllMemberships(Scanner scanner, MembershipService membershipService) {
        membershipService.printAllMemberships();
        promptBackToMenu(scanner);
    }

    public static void browseWorkoutClasses(Scanner scanner, UserRole userRole, WorkoutClassService workoutClassService) {
        try {
            List<WorkoutClass> workoutClasses = workoutClassService.listAllWorkouts(userRole);
            if (workoutClasses.isEmpty()) {
                System.out.println("No classes found.");
            } else {
                System.out.println("--- All Classes ---");
                System.out.println(
                        "--------------------------------------------------------------------------------------------------");
                System.out.println(String.format("%-12d | %-15s | %-10s | $%-35s | %-8s | %-8d",
                        "CLASS ID", "NAME", "TYPE", "DESCRIPTION", "STATUS", "TRAINER"));
                System.out.println(
                        "--------------------------------------------------------------------------------------------------");
                for (WorkoutClass workoutClass : workoutClasses) {
                    System.out.println(workoutClass.toString());
                }
                System.out.println(
                        "--------------------------------------------------------------------------------------------------");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving class list: " + e.getMessage());
        }
    }

    public static void viewAnnualRevenue(Scanner scanner, MembershipService membershipService) {
        System.out.print("Enter the year to view total revenue for: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a year (e.g., 2023).");
            scanner.next(); // consume the invalid input
        }
        int year = scanner.nextInt();
        scanner.nextLine(); // consume the newline character

        try {
            double annualRevenue = membershipService.calculateAnnualRevenue(year);
            System.out.println("Total revenue for the year " + year + ": $" + String.format("%.2f", annualRevenue));
        } catch (SQLException e) {
            System.err.println("Error retrieving annual revenue: " + e.getMessage());
        }
        promptBackToMenu(scanner);
    }

    private static void viewTotalMembershipExpenses(Scanner scanner, MembershipService membershipService,
            int memberId) {
        System.out.println("\n--- Function: View Total Membership Expenses ---");
    }

    private static void purchaseNewMembership(Scanner scanner, MembershipService membershipService, int memberId) {
        System.out.println("\n--- Function: Purchase New Gym Membership ---");
    }

    // Workouts
    private static void browseWorkoutClasses(Scanner scanner, WorkoutClassService workoutClassService) {
        System.out.println("\n--- Function: Browse Workout Classes ---");
    }

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
        workoutClass.setStatus(WorkoutStatus.active);
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

    public static void deleteWorkout(Scanner scanner, int userId, UserRole userRole, WorkoutClassService workoutService) {
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

    public static void updateWorkout(Scanner scanner, int userId, UserRole userRole, WorkoutClassService workoutService) {
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
        if (!name.isBlank())
            workout.setName(name);

        System.out.println("Current type: " + workout.getType());
        System.out.print("New type: ");
        String type = scanner.nextLine();
        if (!type.isBlank())
            workout.setType(type);

        System.out.println("Current description: " + workout.getDescription());
        System.out.print("New description: ");
        String description = scanner.nextLine();
        if (!description.isBlank())
            workout.setDescription(description);

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
            workout.setStatus(WorkoutStatus.fromString(statusInput));
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
