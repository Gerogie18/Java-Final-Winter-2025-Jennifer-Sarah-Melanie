package GymApp.models.enums;

public enum WorkoutStatus {
    active, cancelled, inactive;

    public static WorkoutStatus fromString(String value) {

        return WorkoutStatus.valueOf(value.toLowerCase());
    }
}