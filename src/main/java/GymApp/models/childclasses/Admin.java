package GymApp.models.childclasses;

import GymApp.models.User;
import GymApp.models.enums.Role;

public class Admin extends User {
    public Admin (String username, String password, String email, String phoneNumber, String address) {
        super(username, password, email, phoneNumber, address, Role.ADMIN);
    }
}
