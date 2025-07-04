public class DigitalAccessResource extends LoanRecord {
    private String accessLink;
    private int accessDurationDays;
    private String resourceTitle;

    public DigitalAccessResource(String memberID, String resourceTitle, String accessLink, int accessDurationDays) {
        super(memberID, 1); // one time access (Digital Access)
        this.resourceTitle = resourceTitle;
        this.accessLink = accessLink;
        this.accessDurationDays = accessDurationDays;
    }

    public String getAccessLink() {
        return accessLink;
    }

    public int getAccessDurationDays() {
        return accessDurationDays;
    }

    @Override
    public String getItemTitle() {
        return this.resourceTitle;
    }

    @Override
    public String getLoanDetails() {
        return "  Loan Type: Digital Resource\n" +
                "  Title: " + getItemTitle() + "\n" +
                "  Access Link: " + getAccessLink() + "\n" +
                "  Access Duration: " + getAccessDurationDays() + " days";
    }
}