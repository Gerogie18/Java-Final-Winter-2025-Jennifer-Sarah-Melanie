package GymApp.menus;

import GymApp.models.User;
import GymApp.services.*;

import java.sql.SQLException;
import java.util.Scanner;

public interface Menu {
    void show(Scanner scanner, User user, UserService userService, MembershipService membershipService, WorkoutClassService workoutService) throws SQLException;
}