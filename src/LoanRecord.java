import java.util.Date;

public abstract class LoanRecord {
    protected final String loanID;
    protected final String memberID;
    protected final int quantity;
    protected boolean returned;

    public LoanRecord(String memberID, int quantity) {
        this.loanID = "LOAN" + new Date().getTime();
        this.memberID = memberID;
        this.quantity = quantity;
        this.returned = false;
    }

    public String getLoanID() {
        return loanID;
    }

    public String getMemberID() {
        return memberID;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public abstract String getItemTitle();
    public abstract String getLoanDetails();

    public void printLoanDetails() {
        System.out.println("----Loan Record Details------");
        System.out.println("Loan ID: " + getLoanID());
        System.out.println("Member ID: " + getMemberID());
        System.out.println("Quantity: " + getQuantity());
        System.out.println("Status: " + (isReturned() ? "Returned" : "Active"));
        System.out.println(getLoanDetails());
        System.out.println("-----------------------------");
    }
}