package GymApp.models;

import java.time.LocalDate;

//*\
// This is class file that represents a membership
//

public class Membership {
    private int membershipID;

    private String membershipType;

    private String membershipDescription;

    private double membershipCost;

    private LocalDate membershipStartDate;

    private int memberID;

    public Membership(int membershipID, String membershipType, double membershipCost, String membershipDescription,
            int memberID) {
        this.membershipID = membershipID;
        this.membershipType = membershipType;
        this.membershipDescription = membershipDescription;
        this.membershipCost = membershipCost;
        this.membershipStartDate = LocalDate.now();
        this.memberID = memberID;
    }

    public Membership(String membershipType, double membershipCost, String membershipDescription,
            int memberID) {
        this.membershipType = membershipType;
        this.membershipDescription = membershipDescription;
        this.membershipCost = membershipCost;
        this.membershipStartDate = LocalDate.now();
        this.memberID = memberID;
    }

    public Membership(int membershipID, String membershipType, double membershipCost, String membershipDescription, LocalDate datePurchased, int memberId) {
        this.membershipID = membershipID;
        this.membershipType = membershipType;
        this.membershipCost = membershipCost;
        this.membershipDescription = membershipDescription;
        this.membershipStartDate = datePurchased;
        this.memberID = memberId;
    }

    // Getters and Setters
    public int getMembershipID() {
        return membershipID;
    }

    public void setMembershipID(int membershipID) {
        this.membershipID = membershipID;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public String getMembershipDescription() {
        return membershipDescription;
    }

    public void setMembershipDescription(String membershipDescription) {
        this.membershipDescription = membershipDescription;
    }

    public double getMembershipCost() {
        return membershipCost;
    }

    public void setMembershipCost(double membershipCost) {
        this.membershipCost = membershipCost;
    }

    public LocalDate getMembershipStartDate() {
        return membershipStartDate;
    }

    public void setMembershipStartDate(LocalDate membershipStartDate) {
        this.membershipStartDate = membershipStartDate;
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    @Override
    public String toString() {
        return String.format("%-12d | %-15s | %-30s | $%-8.2f | %-15s | %-8d",
                membershipID, membershipType, membershipDescription,
                membershipCost, membershipStartDate, memberID);
    }
}