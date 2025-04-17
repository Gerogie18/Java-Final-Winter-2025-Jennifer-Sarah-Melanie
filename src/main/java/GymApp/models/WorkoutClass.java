package GymApp.models;

import GymApp.models.enums.Status;

public class WorkoutClass {
    private int id;
    private String name;
    private String type;
    private String description;
    private Status status;
    private int class_capacity; // class_capacity >= 0 AND class_capacity <= 100
    private int trainerId; // trainer id

    public WorkoutClass() {
    }

    public WorkoutClass(int id, String name, String type, String description, Status status, int class_capacity,
            int trainerId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.status = status;
        this.class_capacity = class_capacity;
        this.trainerId = trainerId;
    }

    public WorkoutClass(String name, String type, String description, Status status, int class_capacity,
            int trainerId) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.status = status;
        this.class_capacity = class_capacity;
        this.trainerId = trainerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public String getStatusAsString() {
        return status.name();
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getClass_capacity() {
        return class_capacity;
    }

    public void setClass_capacity(int class_capacity) {
        this.class_capacity = class_capacity;
    }

    public int getTrainerId() {
        return trainerId;
    }

    public void setTrainerByID(int trainerId) {
        this.trainerId = trainerId;
    }

    @Override
    public String toString() {
        return String.format("%-12d | %-15s | %-10s | $%-35s | %-8s | %-8d",
                id, name, type, description, status, trainerId);
    }
}
