package GymApp.models.childclasses;

import GymApp.models.User;
import GymApp.models.enums.UserRole;

public class Admin extends User {
    public Admin (String username, String password, String email, String phoneNumber, String address) {
        super(username, password, email, phoneNumber, address, UserRole.ADMIN);
    }
}
