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
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class GymApp {
    public static void main(String[] args) throws SQLException {

        // set up a global logger
        Logger logger = Logger.getLogger("GymAppLogger");

        try {
            FileHandler fileHandler = new FileHandler("gymapp.log", true); // true = append mode
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
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
            e.printStackTrace();
        }
    }

}
