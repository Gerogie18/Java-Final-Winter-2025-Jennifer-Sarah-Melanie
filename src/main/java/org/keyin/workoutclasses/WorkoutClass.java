package org.keyin.workoutclasses;

public class WorkoutClass {
    private int id;
    private String name;
    private String type;
    private String description;
    private String status; // 'ACTIVE','CANCELLED','INACTIVE'
    private int class_capacity; // class_capacity >= 0 AND class_capacity <= 100
    private int trainer; // trainer id


    public WorkoutClass(String name, String type, String description, String status, int class_capacity, int trainer) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.status = status;
        this.class_capacity = class_capacity;
        this.trainer = trainer;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getClass_capacity() {
        return class_capacity;
    }

    public void setClass_capacity(int class_capacity) {
        this.class_capacity = class_capacity;
    }

    public int getTrainer() {
        return trainer;
    }

    public void setTrainer(int trainer) {
        this.trainer = trainer;
    }
}
