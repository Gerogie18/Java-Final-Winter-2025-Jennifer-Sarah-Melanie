package GymApp.models.enums;

public enum MembershipType {
    BASIC("Basic", 49.99, "Access to gym and equipment"),
    STANDARD("Standard", 59.99, "Includes group classes"),
    PREMIUM("Premium", 79.99, "Classes and personal training");

    private final String label;
    private final double cost;
    private final String description;

    MembershipType(String label, double cost, String description) {
        this.label = label;
        this.cost = cost;
        this.description = description;
    }

    public String getLabel() { return label; }
    public double getCost() { return cost; }
    public String getDescription() { return description; }

    public static MembershipType fromString(String value) {
        return MembershipType.valueOf(value.toUpperCase());
    }
}

