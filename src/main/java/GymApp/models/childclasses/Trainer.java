package GymApp.models.childclasses;

import GymApp.models.User;
import GymApp.models.enums.UserRole;

public class Trainer extends User {
    public Trainer (String username, String password, String email, String phoneNumber, String address) {
        super(username, password, email, phoneNumber, address, UserRole.TRAINER);
    }
}
