package GymApp;

import GymApp.dao.UserDAO;
import GymApp.services.UserService;
import GymApp.models.childclasses.Trainer;


import java.sql.SQLException;

public class UserTest {
    public static void main(String[] args) throws SQLException {
        // Initialize services
        UserDAO userDAO = new UserDAO();

        UserService userService = new UserService(userDAO);

        Trainer trainer = new Trainer("the trainer", "password123", "trainer@email.com", "7095551234", "123 Main St");
    userService.createUser(trainer);
}}

