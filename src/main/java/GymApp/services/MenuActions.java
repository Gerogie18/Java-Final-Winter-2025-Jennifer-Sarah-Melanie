package GymApp.services;

import GymApp.models.Membership;
import GymApp.models.WorkoutClass;
import GymApp.models.enums.MembershipType;
import GymApp.models.enums.UserRole;
import GymApp.models.enums.WorkoutStatus;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class MenuActions {

    private static final Logger log = Logger.getLogger(MenuActions.class.getName());

    // Users
    public static void viewAllUsers(Scanner scanner, UserService userService) {
        try {
            userService.printAllUsers();
            log.info("All users displayed successfully.");
        } catch (SQLException e) {
            System.err.println("Error retrieving user list. Please try again later.");
            log.warning("Database error while retrieving all users: " + e.getMessage());
        }
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
                log.info("Invalid role entered: '" + roleInput + "'. Expected: ADMIN, TRAINER, or MEMBER. " + e.getMessage());
            }
        }
        switch (userRole) {
            case ADMIN:
                userService.printAllAdmins();
                break;
            case MEMBER:
                userService.printAllMembers();
                break;
            case TRAINER:
                userService.printAllTrainers();
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
                log.info("User ID=" + userIdToDelete + " deleted by admin ID=" + adminId);

                if (adminId == userIdToDelete) {
                    System.out.println("Cannot delete yourself.");
                    log.warning("Admin ID=" + adminId + " attempted to delete themselves.");
                    validInput = false; // Reset to prompt again
                } else {
                    try {
                        userService.deleteUser(userIdToDelete);
                        System.out.println("User with ID " + userIdToDelete + " deleted successfully.");
                    } catch (SQLException e) {
                        System.err.println("Error deleting user: " + e.getMessage());
                        log.warning("Database error while deleting user ID=" + userIdToDelete +
                                " by admin ID=" + adminId + ": " + e.getMessage());
                        validInput = false;
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid user ID format. Please enter a number.");
                log.info("Invalid user ID format entered: '" + userIdStr + "'");

            }
        }
    }



    // Memberships
    public static void viewAllMemberships(MembershipService membershipService) {
        try {
            log.info("Retrieving all memberships...");
            membershipService.printAllMemberships();
            log.info("All memberships displayed successfully.");
        } catch (SQLException e) {
            System.err.println("Error retrieving membership list.");
            log.warning("Database error while retrieving all memberships: " + e.getMessage());
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
            log.info("Revenue retrieved for " + year + ": $" + String.format("%.2f", annualRevenue));
        } catch (SQLException e) {
            System.err.println("Error retrieving annual revenue: " + e.getMessage());
            log.warning("Database error retrieving annual revenue for " + year + ": " + e.getMessage());
        }
    }

    public static void viewTotalMembershipExpenses(MembershipService membershipService, int memberId) {

        membershipService.printMembershipByMember(memberId);

        try {
            double totalCost = membershipService.calculateMembershipCosts(memberId);
            System.out.println("Total membership costs for member ID " + memberId + ": $" + String.format("%.2f", totalCost));
            log.info("Membership cost retrieved for member ID=" + memberId + ": $" + String.format("%.2f", totalCost));
        } catch (SQLException e) {
            System.err.println("Error retrieving total cost.");
            log.warning("Database error retrieving membership cost for member ID=" + memberId + ": " + e.getMessage());
        }
    }




    public static void purchaseNewMembership(Scanner scanner, MembershipService membershipService, int userId) {
        System.out.println("Enter membership type (basic, standard, premium): ");
        String type = scanner.nextLine();

        MembershipType membershipType = MembershipType.fromString(type);

        Membership membership = new Membership(membershipType, userId);

        try {
            membershipService.addMembership(membership);
            System.out.println("Membership added successfully!");

        } catch (IllegalArgumentException err) {
            System.out.println("Input error: " + err.getMessage());
        } catch (SQLException err) {
            System.out.println("Sorry! We couldn’t purchase the membership right now.");
            log.warning("Error while adding membership: " + err.getMessage());
        }
    }



    // Workouts

    public static void addWorkout(Scanner scanner, int userId, WorkoutClassService workoutService) {
        System.out.print("Enter workout class name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter workout type: ");
        String type = scanner.nextLine().trim();

        System.out.print("Enter description: ");
        String description = scanner.nextLine().trim();

        System.out.print("Enter max capacity for class: ");
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
            System.out.println("Sorry! We couldn’t add the workout class right now. Please try again later.");
            log.warning("Database error while adding workout class (trainer ID: " + userId + "): " + err.getMessage());
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

            //check permissions
            try {
                workout = workoutService.getWorkoutClassById(workoutId);
                if (userRole != UserRole.ADMIN && workout.getTrainerId() != userId) {
                    System.out.println("You may only delete classes assigned to you.");
                    workout = null;
                }
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
            System.out.println("We couldn’t delete the workout class right now. Please try again later.");
            log.warning("Database error while deleting workout class (ID: " + workoutId +
                    ", user ID: " + userId + "): " + err.getMessage());
        }
    }

    public static void updateWorkout(Scanner scanner, int userId, UserRole userRole, WorkoutClassService workoutService) {
        // Initialize variables outside of while loop
        WorkoutClass workout = null;
        int classId = -1;

        // === Prompt for class ID ===
        while (true) {
            System.out.print("Enter the ID of the workout class you want to update: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid numeric ID.");
                scanner.nextLine(); // Clear invalid input
                continue;
            }

            classId = scanner.nextInt();
            scanner.nextLine(); // Clear the newline

            try {
                workout = workoutService.getWorkoutClassById(classId);

                // Check user permission
                if (userRole != UserRole.ADMIN && workout.getTrainerId() != userId) {
                    System.out.println("You can only update classes that belong to you.");
                    workout = null; // Force re-prompt
                    continue;
                }

                // Valid class found and permission confirmed
                break;

            } catch (IllegalArgumentException | SQLException err) {
                System.out.println("Workout class not found. Please try again.");
                log.warning("Database error retrieving class (ID: " + classId +
                        ", user ID: " + userId + "): " + err.getMessage());
            }
        }

        // === Get user input ===
            // Allow user to leave fields empty if they don't want to change anything
        System.out.println();
        System.out.println("Update the fields you wish to change.");
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
        try {
            int capacity = Integer.parseInt(capacityInput);
            workout.setClass_capacity(capacity);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Capacity not changed.");
        }

        System.out.println("Current status: " + workout.getStatus());
        System.out.print("New status (active, cancelled, inactive): ");
        String statusInput = scanner.nextLine().toLowerCase().trim();
        if (!statusInput.isBlank()) {
            workout.setStatus(WorkoutStatus.fromString(statusInput));
        }

        // === Apply updates ===
        try {
            workoutService.updateWorkoutClass(workout, userRole, userId);
            System.out.println("Workout class updated successfully!");
        } catch (IllegalArgumentException err) {
            System.out.println("Input error: " + err.getMessage());
        } catch (SQLException err) {
            System.out.println("We couldn’t update the workout class right now. Please try again later.");
            log.warning("Database error while updating workout class (ID: " + workout.getId() +
                    ", user ID: " + userId + "): " + err.getMessage());
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

        } catch (SQLException err) {
            System.out.println("Error retrieving your classes.");
            log.warning("Database error while retrieving workout classes for trainer ID: " + trainerId + ": " + err.getMessage());
        }
    }

    public static void browseWorkoutClasses(UserRole userRole, WorkoutClassService workoutClassService) {
        try {
            List<WorkoutClass> workoutClasses = workoutClassService.listAllWorkouts(userRole);
            if (workoutClasses.isEmpty()) {
                System.out.println("No classes found.");
            } else {
                System.out.println("--- All Classes ---");
                System.out.println(
                        "--------------------------------------------------------------------------------------------------");
                System.out.println(String.format("%-12s | %-15s | %-10s | %-35s | %-8s | %-8s",
                        "CLASS ID", "NAME", "TYPE", "DESCRIPTION", "STATUS", "TRAINER"));
                System.out.println(
                        "--------------------------------------------------------------------------------------------------");
                for (WorkoutClass workoutClass : workoutClasses) {
                    System.out.println(workoutClass.toString());
                }
                System.out.println(
                        "--------------------------------------------------------------------------------------------------");
            }
        } catch (SQLException err) {
            System.err.println("Error retrieving class list: " + err.getMessage());
            log.warning("Database error while retrieving list of workout classes for user ID: " + err.getMessage());
        }
    }

}