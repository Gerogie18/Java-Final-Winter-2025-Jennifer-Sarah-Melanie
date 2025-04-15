package GymApp.models.childclasses;

import GymApp.models.User;

public class Admin extends User {
    public Admin (String username, String password, String email, int phoneNumber, String address) {
        super(username, password, email, phoneNumber, address, Role.ADMIN);
    }
}
