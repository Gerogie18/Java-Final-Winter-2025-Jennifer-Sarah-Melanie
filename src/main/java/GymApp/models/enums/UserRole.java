package GymApp.models.enums;

public enum UserRole {
    ADMIN, TRAINER, MEMBER;

    public static UserRole fromString(String value) {
        return UserRole.valueOf(value.toUpperCase());
    }
}
