package GymApp.models.enums;

public enum Role {
    ADMIN, TRAINER, MEMBER;

    public static Role fromString(String value) {
        return Role.valueOf(value.toLowerCase());
    }
}
