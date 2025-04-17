package GymApp.models.enums;

public enum WorkoutStatus {
    ACTIVE, CANCELLED, INACTIVE;

    public static WorkoutStatus fromString(String value) {

        return WorkoutStatus.valueOf(value.toUpperCase());
    }
}