package GymApp;
import GymApp.menus.*;
import GymApp.models.enums.*;
import GymApp.services.*;
import GymApp.models.User;
import GymApp.dao.*;
import GymApp.setup.DemoDatabaseSeeder;
import javax.naming.AuthenticationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.*;

/**
 * Main application class for the Gym Management System.
 * <p>
 * Handles application startup, service initialization, logging setup, and the main menu flow for
 * login and registration. Routes users to role-specific submenus based on their credentials.
 * </p>
 *
 * <p>Supported roles:</p>
 * <ul>
 *     <li>Admin</li>
 *     <li>Trainer</li>
 *     <li>Member</li>
 * </ul>
 *
 * <p>Command-line arguments:</p>
 * <ul>
 *     <li><code>--seed</code> — Seeds the database with demo data (if implemented)</li>
 * </ul>
 *
 * @author SD12-JMS
 * @version 1.0
 */


public class GymApp {

    /**
     * The main entry point for the Gym Management System.
     * <p>
     * Initializes application services, sets up logging, and displays the top-level menu for
     * signing up, logging in, or exiting the application.
     * </p>
     *
     * @param args optional command-line arguments (e.g., "--seed" to populate demo data)
     * @throws SQLException if a database connection or query fails during initialization
     */


    public static void main(String[] args) throws SQLException {

        // create logger
        try {
            Logger rootLogger = Logger.getLogger("");
            FileHandler fileHandler = new FileHandler("gymapp.log", true); // true = append mode
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.ALL);

            rootLogger.addHandler(fileHandler);
            rootLogger.setLevel(Level.ALL);

            // Remove default console handler
            for (Handler handler : rootLogger.getHandlers()) {
                if (handler instanceof ConsoleHandler) {
                    rootLogger.removeHandler(handler);
                }
            }

        } catch (IOException e) {
            System.err.println("Logging setup failed: " + e.getMessage());
        }


        // Optional seed for database
        if (args.length > 0 && args[0].equals("--seed")) {
            DemoDatabaseSeeder.main(null);
            return;
        }

        // Initialize services
        UserDAO userDAO = new UserDAO();
        WorkoutClassDAO workoutDAO = new WorkoutClassDAO();
        MembershipDAO membershipDAO = new MembershipDAO();
        UserService userService = new UserService(userDAO);
        MembershipService membershipService = new MembershipService(membershipDAO);
        WorkoutClassService workoutService = new WorkoutClassService(workoutDAO, userDAO);

        // Scanner for user input
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== Gym Management System ===");
            System.out.println("1. Add a new user");
            System.out.println("2. Login as a user");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            // Validate input
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.next();
            }

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    registerUser(scanner, userService);
                    break;
                case 2:
                    logInAsUser(scanner, userService, membershipService, workoutService);
                    break;
                case 3:
                    System.out.println("Exiting the program...");
                    break;
                default:
                    System.out.println("Invalid choice! Please select a valid option.");
            }
        } while (choice != 3);

        scanner.close();
    }


    /**
     * Handles the registration of a new user through CLI input.
     * <p>
     * This method prompts the user to enter their email, username, password,
     * phone number, and address. It performs input validation for each field,
     * ensuring that no required field is left empty and that the provided email
     * address is not already registered.
     * </p>
     *
     * <p>
     * Upon successful validation, the user information is passed to the
     * {@link UserService} to create the new user, and a confirmation message is displayed.
     * </p>
     *
     * @param scanner      the Scanner object used to read user input
     * @param userService  the service used to interact with user data and validation
     */

    private static void registerUser(Scanner scanner, UserService userService) {
        String email = "";
        String username = "";
        String password = "";
        String phoneNumber = "";
        String address = "";
        UserRole userRole = UserRole.MEMBER;

        try {
            // Get email, ensuring it's not empty and not already taken
            while (email.trim().isEmpty() || userService.isEmailTaken(email)) {
                System.out.print("Enter email: ");
                email = scanner.nextLine().trim();
                if (email.isEmpty()) {
                    System.out.println("Email cannot be empty. Please try again.");
                } else if (userService.isEmailTaken(email)) {
                    System.out.println("Error: This email address is already registered. Please enter a different email.");
                }
            }
            // Get username, ensuring it's not empty
            while (username.trim().isEmpty()) {
                System.out.print("Enter username: ");
                username = scanner.nextLine().trim();
                if (username.isEmpty()) {
                    System.out.println("Username cannot be empty. Please try again.");
                }
            }

            // Get password, ensuring it's not empty
            while (password.trim().isEmpty()) {
                System.out.print("Enter password: ");
                password = scanner.nextLine().trim();
                if (password.isEmpty()) {
                    System.out.println("Password cannot be empty. Please try again.");
                }
            }

            // Get phone number, ensuring it's not empty
            while (phoneNumber.trim().isEmpty()) {
                System.out.print("Enter phone number: ");
                phoneNumber = scanner.nextLine().trim();
                if (phoneNumber.isEmpty()) {
                    System.out.println("Phone number cannot be empty. Please try again.");
                }
            }

            // Get address, ensuring it's not empty
            while (address.trim().isEmpty()) {
                System.out.print("Enter address: ");
                address = scanner.nextLine().trim();
                if (address.isEmpty()) {
                    System.out.println("Address cannot be empty. Please try again.");
                }
            }

            User user = new User(username, password, email, phoneNumber, address, userRole);
            int newUserId = userService.createUser(user);
            System.out.println("New user added successfully! User ID: " + newUserId);

        } catch (SQLException e) {
            System.out.println("Error adding user: " + e.getMessage());
        }
    }

    /**
     * Prompts the user to log in using their email and password, validates credentials,
     * and redirects the user to the appropriate menu based on their role.
     * <p>
     * This method handles basic input validation for empty email and password fields.
     * It attempts authentication through the {@link UserService}, and if successful,
     * routes the user to the corresponding CLI menu for their role (Admin, Trainer, or Member).
     * </p>
     *
     * @param scanner          the Scanner object used to read user input
     * @param userService      the service used for authenticating user credentials
     * @param membershipService the service providing membership-related functionality
     * @param workoutService   the service providing workout class-related functionality
     */

    private static void logInAsUser(Scanner scanner, UserService userService, MembershipService membershipService, WorkoutClassService workoutService) {
        String email = "";
        String password = "";

        // Get username, ensuring it's not empty
        while (email.trim().isEmpty()) {
            System.out.print("Enter email: ");
            email = scanner.nextLine();
            if (email.trim().isEmpty()) {
                System.out.println("Email cannot be empty. Please try again.");
            }
        }

        // Get password, ensuring it's not empty
        while (password.trim().isEmpty()) {
            System.out.print("Enter password: ");
            password = scanner.nextLine();
            if (password.trim().isEmpty()) {
                System.out.println("Password cannot be empty. Please try again.");
            }
        }

        try {
            User user = userService.login(email, password);
            if (user != null) {
                System.out.println("Login Successful! Welcome " + user.getUsername());


                switch (user.getRole()) {
                    case ADMIN:
                        AdminMenu.displayMenu(scanner, membershipService, userService, user.getUserId());
                        break;
                    case TRAINER:
                        TrainerMenu.displayMenu(scanner, workoutService, membershipService, user.getUserId());
                        break;
                    case MEMBER:
                        MemberMenu.displayMenu(scanner, membershipService, workoutService, user.getUserId());
                        break;
                    default:
                        break;
                }
            } else {
                System.out.println("Login Failed! Invalid credentials.");
            }
        } catch (AuthenticationException ae) {
            System.out.println("Login failed: " + ae.getMessage());
        } catch (SQLException e) {
            System.out.println("A system error occurred while trying to log in.");
        }
    }

}
