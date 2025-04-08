package org.keyin.user.childclasses;

import org.keyin.user.User;

public class Trainer extends User {
    public Trainer (String username, String password, String email, int phoneNumber, String address) {
        super(username, password, email, phoneNumber, address, Role.TRAINER);
    }
}
