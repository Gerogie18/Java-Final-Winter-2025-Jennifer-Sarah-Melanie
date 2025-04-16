package GymApp.models.enums;

public enum Status {
    active, cancelled, inactive;

    public static Status fromString(String value) {
        return Status.valueOf(value);
    }
}
