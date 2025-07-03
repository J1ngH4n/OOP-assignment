import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;


public class LibraryManagementSystem {

    private static ArrayList<LibraryMember> members = new ArrayList<>();
    private static ArrayList<LoanRecord> loans = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        //testing(Sample Members)
        members.add(new LibraryMember("Shaun"));
        members.add(new LibraryMember("Byran Wee"));

        boolean running = true;
        while (running) {
            displayMainMenu(); //Print out the menu first (method)
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    registerNewMember();
                    break;
                case 2:
                    borrowOrAccessResources();
                    break;
                case 3:
                    manageMemberAccount();
                    break;
                case 4:
                    viewAndReturnLoans();
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again...");
            }

            if (running) {
                System.out.println("\nPress <ENTER> to return to the main menu...");
                scanner.nextLine();
            }
        }
        System.out.println("Program Exited");

    }

    private static void displayMainMenu() {
        System.out.println("====Library Management System===");
        System.out.println("1. New Member Registration");
        System.out.println("2. Borrow / Access Resources");
        System.out.println("3. Manage Member Account (Top Up)");
        System.out.println("4. View / Return Loans");
        System.out.println("5. Exit");
        System.out.println("---------------------------------");
    }

    private static int getUserChoice() {
        System.out.print("Please Select an Option: ");
        try {
            int choice = scanner.nextInt(); //  Need to consume new line
            scanner.nextLine();
            return choice;
        } catch (InputMismatchException e) {
            System.out.println("Error: Please enter a valid number (1,2,3,4,5).");
            scanner.nextLine();
            return -1;
        }
    }

    //Option 1
    private static void registerNewMember() {
        System.out.println("\n--- New Member Registration ---");
        System.out.print("Enter member's full name: ");
        String name = scanner.nextLine();

        if (name.isEmpty()) {
            System.out.println("Error: Name cannot be empty.");
            return; //Exit the method if invalid
        }

        LibraryMember newMember = new LibraryMember(name);
        members.add(newMember);
        System.out.println("Registration successful! Member ID: " + newMember.getMemberID());
        System.out.println("Starting credit points: " + newMember.getCreditPoints());
    }

    //Option 2
    private static void borrowOrAccessResources() {
        LibraryMember member = findMember();
        if (member == null) return; // exit

        System.out.println("\nChoose resource type:");
        System.out.println("1. Borrow Physical Book \n2. Access Digital Resource");
        int choice = getUserChoice();

        if (choice == 1) {
            borrowPhysicalBook(member);
        } else if (choice == 2) {
            accessDigitalResource(member);
        } else {
            System.out.println("Invalid choice.");
        }
    }

    //Connect to option  2
    private static void borrowPhysicalBook(LibraryMember member) {
        System.out.println("--- Available Books ---");
        System.out.println("1. How Money Works");
        System.out.println("2. Dive into Design Patterns");
        System.out.println("3. Hello Algo");
        System.out.println("4. Grokking Algorithms");
        System.out.println("5. a random walk down wall street");

        System.out.print("Select book number to borrow (1-5): ");
        int bookChoice = getUserChoice();

        String bookTitle;
        String shelfLocation;

        switch (bookChoice) {
            case 1:
                bookTitle = "How Money Works";
                shelfLocation = "Shelf A1";
                break;
            case 2:
                bookTitle = "Dive into Design Patterns";
                shelfLocation = "Shelf B2";
                break;
            case 3:
                bookTitle = "Hello Algo";
                shelfLocation = "Shelf C3";
                break;
            case 4:
                bookTitle = "Grokking Algorithms";
                shelfLocation = "Shelf C4";
                break;
            case 5:
                bookTitle = "a random walk down wall street";
                shelfLocation = "Shelf C5";
                break;
            default:
                System.out.println("Invalid book choice.");
                return;
        }

        PhysicalBookLoan newLoan = new PhysicalBookLoan(member.getMemberID(), 1, bookTitle, shelfLocation);
        loans.add(newLoan); // Stored the loan to the array list
        System.out.println("Successfully borrowed '" + bookTitle + "'.");
        System.out.println("Due date: " + newLoan.getDueDate());
        System.out.println("Shelf location: " + newLoan.getShelfLocation());
    }
    // connect to option2
    private static void accessDigitalResource(LibraryMember member) {
        System.out.println("--- Available Digital Resources ---");
        System.out.println("1. Discrete Mathematics");
        System.out.println("2. computing essentials");
        System.out.println("3. JavaScript JQuery Jon Duckett");
        System.out.println("4. Designing Data-Intensive Applications");
        System.out.println("5. Head First Design Patterns");

        System.out.print("Number to access (1-5) \n");
        int resChoice = getUserChoice();

        String digitalTitle;
        String accessLink;
        int accessDurationDays;

        switch (resChoice) {
            case 1:
                digitalTitle = "Discrete Mathematics";
                accessLink = "https://library.com/Discrete-Mathematics";
                accessDurationDays = 30;
                break;
            case 2:
                digitalTitle = "computing essentials";
                accessLink = "https://library.com/computing-essentials";
                accessDurationDays = 60;
                break;
            case 3:
                digitalTitle = "JavaScript JQuery Jon Duckett";
                accessLink = "https://library.com/JavaScript-JQuer-Jon-Duckett";
                accessDurationDays = 45;
                break;
            case 4:
                digitalTitle = "Designing Data-Intensive Applications";
                accessLink = "https://library.com/Designing-Data-Intensive-Applications";
                accessDurationDays = 90;
                break;
            case 5:
                digitalTitle = "Head First Design Patterns";
                accessLink = "https://library.com/Head-First-Design-Pattern";
                accessDurationDays = 120;
                break;
            default:
                System.out.println("Invalid resource choice.");
                return;
        }

        int fee = 200; // RM2.00 fee (200 points = RM2.00) Flat fee
        if (member.deductCreditPoints(fee)) { // Check if the membeer has enough credit from Library Member class(method)
            DigitalAccessResource newAccess = new DigitalAccessResource(member.getMemberID(), digitalTitle, accessLink, accessDurationDays);
            loans.add(newAccess);
            System.out.println("Access granted to '" + digitalTitle + "'.");
            System.out.println("Access fee of " + fee + " points deducted.");
            System.out.println("Access link: " + accessLink);
            System.out.println("Access duration: " + accessDurationDays + " days");
        } else {
            System.out.println("Insufficient points to access resource. Required: " + fee + " points.");
            System.out.println("Current balance: " + member.getCreditPoints() + " points.");
            System.out.println("Please use option 3 to top up your account.");
        }
    }
    //Option 3
    private static void manageMemberAccount() {
        LibraryMember member = findMember();
        if (member == null) return;

        System.out.println("--- Account Top Up ---");
        System.out.println("Current Balance: " + member.getCreditPoints() + " points.");
        System.out.println("Select amount to top up:");
        System.out.println("1. RM5.00 | 2. RM10.00 | 3. RM20.00 | 4. RM50.00");

        int choice = getUserChoice();
        double amount;

        switch (choice) {
            case 1: amount = 5.00; break;
            case 2: amount = 10.00; break;
            case 3: amount = 20.00; break;
            case 4: amount = 50.00; break;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        // Transaction Fees
        double amountAfterFee = amount - 0.50;
        int pointsToAdd = (int) (amountAfterFee * 100); // RM1 = 100 points

        member.addCreditPoints(pointsToAdd);
        System.out.printf("Top-up successful! RM%.2f processed (RM0.50 transaction fee deducted).\n", amount);
        System.out.println("Points added: " + pointsToAdd);
        System.out.println("New balance: " + member.getCreditPoints() + " points.");
    }
    //Option 4
    private static void viewAndReturnLoans() {
        //Check for loan first
        LibraryMember member = findMember();
        if (member == null) return; // exit

        boolean hasActiveLoans = false;
        for (LoanRecord loan : loans) {
            if (loan.getMemberID().equals(member.getMemberID()) && !loan.isReturned()) {
                hasActiveLoans = true;
                break;
            }
        }

        if (!hasActiveLoans) {
            System.out.println("No active loans found.");
            return;
        }

        System.out.println("--- Your Active Loans ---");

        // Display all active loans first
        int count = 1;
        for (LoanRecord loan : loans) {
            if (loan.getMemberID().equals(member.getMemberID()) && !loan.isReturned()) { //gets loans belonging to the current member
                if (loan instanceof PhysicalBookLoan) {
                    PhysicalBookLoan book = (PhysicalBookLoan) loan;
                    System.out.println(count + ". Physical Book: " + book.getItemTitle() + " (Due: " + book.getDueDate() + ")");
                } else if (loan instanceof DigitalAccessResource) {
                    DigitalAccessResource digital = (DigitalAccessResource) loan;
                    System.out.println(count + ". Digital Resource: " + digital.getItemTitle() + " (Duration: " + digital.getAccessDurationDays() + " days)");
                }
                count++;
            }
        }

        System.out.print("Select loan number to return/manage: ");
        int choice = getUserChoice();

        // Find the selected loan
        count = 1;
        for (LoanRecord loan : loans) {
            if (loan.getMemberID().equals(member.getMemberID()) && !loan.isReturned()) {
                if (count == choice) {
                    // Found it! Now check what type it is
                    if (loan instanceof PhysicalBookLoan) {
                        PhysicalBookLoan book = (PhysicalBookLoan) loan;
                        processPhysicalBookReturn(member, book);
                        return;
                    }

                    if (loan instanceof DigitalAccessResource) {
                        // It's a digital resource
                        DigitalAccessResource digital = (DigitalAccessResource) loan;
                        processDigitalResourceReturn(member, digital);
                        return;
                    }
                }
                count++;
            }
        }

        System.out.println("That loan number doesn't exist!");
    }
    //Connected to option 4
    private static void processPhysicalBookReturn(LibraryMember member, PhysicalBookLoan bookLoan) {
        long daysOverdue = 0;
        Date currentDate = new Date();

        if (currentDate.after(bookLoan.getDueDate())) {
            long diff = currentDate.getTime() - bookLoan.getDueDate().getTime();
            daysOverdue = diff / (1000 * 60 * 60 * 24); //Convert milliseconds to days
        }

        if (daysOverdue > 0) {
            double fine = 0.50 * daysOverdue * bookLoan.getQuantity(); // RM 0.50 per day per book quantity
            System.out.printf("Book is overdue by %d days. Fine: RM%.2f\n", daysOverdue, fine);
            System.out.println("Pay with: 1. Points | 2. Cash");
            int payChoice = getUserChoice();

            if (payChoice == 1) {
                int fineInPoints = (int) (fine * 100); // Convert RM to points
                if (member.deductCreditPoints(fineInPoints)) {
                    System.out.printf("Fine of RM%.2f paid with points.\n", fine);
                    generateReceipt("Fine Payment", member.getName(), fine);
                } else {
                    System.out.println("Payment failed. Insufficient points.");
                    System.out.println("Required: " + fineInPoints + " points, Available: " + member.getCreditPoints() + " points.");
                    return;
                }
            } else if (payChoice == 2) {
                int loyaltyPoints = (int) fine; // RM1 = 1 loyalty point for cash payments
                member.addCreditPoints(loyaltyPoints);
                System.out.printf("Fine of RM%.2f paid with cash.\n", fine);
                System.out.println("Loyalty bonus: " + loyaltyPoints + " points added!");
                generateReceipt("Fine Payment", member.getName(), fine);
            } else {
                System.out.println("Invalid payment choice. Return cancelled.");
                return;
            }
        } else {
            System.out.println("Book returned on time. No fines.");
            generateReceipt("Book Return", member.getName(), 0.0);
        }

        bookLoan.setReturned(true);// Marked True if completed
        System.out.println("Book '" + bookLoan.getItemTitle() + "' has been successfully returned.");
    }

    //Connected to Option 4
    private static void processDigitalResourceReturn(LibraryMember member, DigitalAccessResource digitalResource) {
        System.out.println("--- Digital Resource Access Details ---");
        System.out.println("Title: " + digitalResource.getItemTitle());
        System.out.println("Access Link: " + digitalResource.getAccessLink());
        System.out.println("Access Duration: " + digitalResource.getAccessDurationDays() + " days");
        System.out.println("Resource accessed successfully.");

        digitalResource.setReturned(true); //to removed from loan
        generateReceipt("Digital Resource Access", member.getName(), 0.0);
        System.out.println("Digital resource access completed.");
    }

    //Extra
    private static void generateReceipt(String type, String memberName, double amount) {
        String filename = "receipt.txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("=== LIBRARY MANAGEMENT SYSTEM RECEIPT ===");
            writer.println("Date: " + new Date());
            writer.println("Type: " + type);
            writer.println("Member: " + memberName);
            if (amount > 0) {// Only write if it has a fine
                writer.printf("Amount Paid: RM%.2f%n", amount);
            }
            writer.println("Thank you for using our library services!");
            writer.println("==========================================");
            System.out.println("Receipt generated: " + filename);
        } catch (IOException e) {
            System.err.println("Error writing receipt file: " + e.getMessage());
        }
    }

    private static LibraryMember findMember() {
        System.out.print("Enter your Member ID: ");
        String memberId = scanner.nextLine();

        if (memberId.isEmpty()) {
            System.out.println("Error: Member ID cannot be empty.");
            return null;
        }

        for (LibraryMember member : members) {
            if (member.getMemberID().equals(memberId)) {
                System.out.println("Welcome, " + member.getName() + "!");
                return member;
            }
        }

        System.out.println("Error: Member ID '" + memberId + "' not found.");
        System.out.println("Please check your Member ID or register as a new member.");
        return null;
    }
}