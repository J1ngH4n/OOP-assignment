import java.util.Random;

public class LibraryMember {
    private String memberID;
    private String name;
    private int creditPoints;

    public LibraryMember(String name) {
        this.name = name;
        this.creditPoints = 100;
        this.memberID = "TC" + (10000 + new Random().nextInt(90000));
    }

    public String getMemberID() {
        return memberID;
    }

    public String getName() {
        return name;
    }

    public int getCreditPoints() {
        return creditPoints;
    }

    public void addCreditPoints(int points) {
        if (points > 0) {
            this.creditPoints += points;
        }
    }

    public boolean deductCreditPoints(int points) {
        if (points > 0 && this.creditPoints >= points) {  //make surr it is sufficient
            this.creditPoints -= points;
            return true;
        }
        return false;
    }
}