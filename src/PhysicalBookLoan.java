import java.util.Date;

public class PhysicalBookLoan extends LoanRecord {
    private Date dueDate;
    private String shelfLocation;
    private String bookTitle;

    public PhysicalBookLoan(String memberID, int quantity, String bookTitle, String shelfLocation) {
        super(memberID, quantity);
        this.bookTitle = bookTitle;
        this.shelfLocation = shelfLocation;

        this.dueDate = new Date(new Date().getTime() + (7 * 24 * 60 * 60 * 1000));         //Convert Days to Millis
    }

    public Date getDueDate() {
        return new Date(dueDate.getTime());
    }

    public String getShelfLocation() {
        return shelfLocation;
    }

    @Override
    public String getItemTitle() {
        return this.bookTitle;
    }

    @Override
    public String getLoanDetails() {
        return "  Loan Type: Physical Book\n" +
                "  Title: " + getItemTitle() + "\n" +
                "  Quantity: " + getQuantity() + "\n" +
                "  Due Date: " + getDueDate() + "\n" +
                "  Shelf Location: " + getShelfLocation();
    }
}