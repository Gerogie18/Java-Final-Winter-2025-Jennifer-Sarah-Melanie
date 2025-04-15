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

    public Membership(int membershipID, String membershipType, String membershipDescription, double membershipCost,
            LocalDate membershipStarDate,
            int memberID) {
        this.membershipID = membershipID;
        this.membershipType = membershipType;
        this.membershipDescription = membershipDescription;
        this.membershipCost = membershipCost;
        this.membershipStartDate = LocalDate.now();
        this.memberID = memberID;
    }

    public Membership(String membershipType, String membershipDescription, double membershipCost,
            LocalDate membershipStarDate,
            int memberID) {
        this.membershipType = membershipType;
        this.membershipDescription = membershipDescription;
        this.membershipCost = membershipCost;
        this.membershipStartDate = LocalDate.now();
        this.memberID = memberID;
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
        return "Membership{" +
                "membershipID=" + membershipID +
                ", membershipType='" + membershipType + '\'' +
                ", membershipDescription='" + membershipDescription + '\'' +
                ", membershipCost=" + membershipCost +
                ", membershipStartDate=" + membershipStartDate +
                ", memberID=" + memberID +
                '}';
    }
}