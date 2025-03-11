package org.keyin;//package org.keyin;
//
////*
//// This file can be where you build the main menu to get your application to run
//// SOLO LEARNERS: You do not have to have a working menu. Just be able to show all the features of your application.
//// *//
//package entities;
//
//import entities.membership.MembershipService;
//import entities.user.User;
//import entities.user.UserService;
//import entities.workoutclass.WorkoutService;
//
//import java.sql.SQLException;
//import java.util.Scanner;
//
public class GymApp {
//
//    public static void main(String[] args) throws SQLException {

//        // Create instances of the services
//        // This is where you will be able to call the methods that you have created in the services


//        UserService userService = new UserService();
//        MembershipService membershipService = new MembershipService();
//        WorkoutService workoutService = new WorkoutService();
//
//
//// Solo submissions can do something like this to show the functionality of the application


////        User user = new User("Jkells","12345","Member");
////        Admin admin = new Admin("Admin2","123-123-2133");
////        Trainer trainer = new Trainer("Trainer","1123123123123123");
////
////        Membership membership = new Membership("Monthly",50,"24/7 Gym Access",2);
//////        membershipService.membershipPurchase(membership);
////
////        User user1 = userService.loginForUser("Admin2","123-123-2133");
////
//////        System.out.println(user1.getUserRole());
////        membershipService.printAllMemberShips();
////
////        membershipService.printGymRevenue();
////        membershipService.printMemberSpendByMonth(1);
////
////        WorkoutClass workoutClass = new WorkoutClass("Zumba","We be vibing",4);
////
//////        workoutService.addNewWorkoutClass(workoutClass);
////
////        userService.getAllUsersInSystem();
//
//
//
//// Team Submissions should build a menu to show the functionality of the application



//
//        // The menu has will show errors. This is 100% normal there is stuff
//        // missing, you will need to fix
//        // the errors by adding the missing code
//
//        Scanner scanner = new Scanner(System.in);
//        int choice;
//
//        do {
//            System.out.println("\n=== Gym Management System ===");
//            System.out.println("1. Add a new user");
//            System.out.println("2. Login as a user");
//            System.out.println("3. Exit");
//            System.out.print("Enter your choice: ");
//
//            // Validate input
//            while (!scanner.hasNextInt()) {
//                System.out.println("Invalid input! Please enter a number.");
//                scanner.next();
//            }
//
//            choice = scanner.nextInt();
//            scanner.nextLine(); // Consume newline
//
//            switch (choice) {
//                case 1:
//                    addingANewUser(scanner, userService);
//                    break;
//                case 2:
//                    System.out.println("Logging in as a user...");
//                    logInAsUser(scanner, userService, membershipService, workoutService);
//                    break;
//                case 3:
//                    System.out.println("Exiting the program...");
//                    break;
//                default:
//                    System.out.println("Invalid choice! Please select a valid option.");
//            }
//
//        } while (choice != 2);
//
//        scanner.close();
//    }
//
//
//
//    private static void logInAsUser(Scanner scanner, UserService userService,MembershipService membershipService, WorkoutService workoutService) {
//
//
//        try{
//            User user = userService.loginForUser(username, password);
//            if(user != null){
//                System.out.println("Login Successful!");
//                System.out.println("Welcome " + user.getUserName());
//
//                if(user.getUserRole().equalsIgnoreCase("Admin")){
//                    showAdminMenu(scanner,user,userService,membershipService,workoutService);
//                } else if(user.getUserRole().equalsIgnoreCase("Trainer")){
//                    showTrainerMenu(scanner,user,userService,workoutService);
//                } else {
//                    showMemberMenu(scanner,user,userService,membershipService);
//                }
//            } else {
//                System.out.println("Login Failed!");
//            }
//        } catch (SQLException e) {
//            System.out.println("An error occurred while logging in.");
//            System.out.println(e.getMessage());
//        }
//    }
//
//    private static void showMemberMenu(Scanner scanner, User user, UserService userService, MembershipService membershipService) {
//    }
//
//    private static void showTrainerMenu(Scanner scanner, User user, UserService userService, WorkoutService workoutService) {
//    }
//
//    private static void showAdminMenu(Scanner scanner, User user, UserService userService, MembershipService membershipService, WorkoutService workoutService) throws SQLException {
//
//
//        int choice = scanner.nextInt();
//        scanner.nextLine();
//        switch (choice){
//            case 1:
//                printAllMembersInStystem(userService);
//
//                break;
//            case 2:
//                printGymRevenueByMonth(membershipService);
//                break;
//            case 3:
//                deleteUser(scanner,userService);
//                break;
//            default:
//                System.out.println("Invalid choice! Please select a valid option.");
//        }
//
//
//    }
//
//    public static void deleteUser(Scanner scanner, UserService userService) throws SQLException {
//
//    }
//
//
//
//    private static void printGymRevenueByMonth(MembershipService membershipService) {
//
//    }
//
//    private static void printAllMembersInStystem(UserService userService) throws SQLException {
//    }
//
//
//    private static void addingANewUser(Scanner scanner, UserService userService) {
//
//        User user = new User(username, password, role);
//        try {
//            userService.addUser(user);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//
//
//
//
//    }
}
