package GymApp.menus;

import GymApp.models.*;
import GymApp.services.*;

import java.sql.SQLException;
import java.util.Scanner;

public interface Menu {
    void show(Scanner scanner, User user, WorkoutClass workoutClass, Membership membership, UserService userService, MembershipService membershipService, WorkoutClassService workoutService) throws SQLException;
}