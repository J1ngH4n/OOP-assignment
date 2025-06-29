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
                    System.out.println("Invalid option. Please try again.");
            }

            if (running) {
                System.out.println("\nPress <ENTER> to return to the main menu...");
                scanner.nextLine();
            }
        }
        System.out.println("Exiting... Goodbye!");
        scanner.close();
    }

    private static void displayMainMenu() {
        System.out.println("\n--- Library Management System ---");
        System.out.println("1. New Member Registration");
        System.out.println("2. Borrow / Access Resources");
        System.out.println("3. Manage Member Account (Top Up)");
        System.out.println("4. View / Return Loans");
        System.out.println("5. Exit");
        System.out.println("---------------------------------");
    }

    private static int getUserChoice() {
        System.out.print("Select an option >> ");
        try {
            int choice = scanner.nextInt();
            scanner.nextLine();
            return choice;
        } catch (InputMismatchException e) {
            System.out.println("Error: Please enter a valid number (1,2,3,4,5).");
            scanner.nextLine();
            return -1;
        }
    }

    //Method 1
    private static void registerNewMember() {
        System.out.println("\n--- New Member Registration ---");
        System.out.print("Enter member's full name: ");
        String name = scanner.nextLine();

        if (name.isEmpty()) {
            System.out.println("Error: Name cannot be empty.");
            return;
        }

        LibraryMember newMember = new LibraryMember(name);
        members.add(newMember);
        System.out.println("Registration successful! Member ID: " + newMember.getMemberID());
        System.out.println("Starting credit points: " + newMember.getCreditPoints());
    }

    //Method 2
    private static void borrowOrAccessResources() {
        LibraryMember member = findMember();
        if (member == null) return;

        System.out.println("\nChoose resource type:");
        System.out.println("1. Borrow Physical Book | 2. Access Digital Resource");
        int choice = getUserChoice();

        if (choice == 1) {
            borrowPhysicalBook(member);
        } else if (choice == 2) {
            accessDigitalResource(member);
        } else {
            System.out.println("Invalid choice.");
        }
    }

    //Method 3
    private static void borrowPhysicalBook(LibraryMember member) {
        System.out.println("\n--- Available Books ---");
        System.out.println("1. How Money Works");
        System.out.println("2. Dive into Design Patterns");
        System.out.println("3. Hello Algo");
        System.out.println("4. Grokking Algorithms");
        System.out.println("5. a random walk down wall street");

        System.out.print("Select book number to borrow (1-5) >> ");
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
        loans.add(newLoan);
        System.out.println("Successfully borrowed '" + bookTitle + "'.");
        System.out.println("Due date: " + newLoan.getDueDate());
        System.out.println("Shelf location: " + newLoan.getShelfLocation());
    }

    private static void accessDigitalResource(LibraryMember member) {
        System.out.println("\n--- Available Digital Resources ---");
        System.out.println("1. Discrete Mathematics");
        System.out.println("2. computing essentials");
        System.out.println("3. JavaScript JQuery Jon Duckett");
        System.out.println("4. Designing Data-Intensive Applications");
        System.out.println("5. Head First Design Patterns");

        System.out.print("Select resource number to access (1-5) >> ");
        int resChoice = getUserChoice();

        String resTitle;
        String resLink;
        int resDuration;

        switch (resChoice) {
            case 1:
                resTitle = "Discrete Mathematics";
                resLink = "https://library.example.com/Discrete-Mathematics";
                resDuration = 30;
                break;
            case 2:
                resTitle = "computing essentials";
                resLink = "https://library.example.com/computing-essentials";
                resDuration = 60;
                break;
            case 3:
                resTitle = "JavaScript JQuery Jon Duckett";
                resLink = "https://library.example.com/JavaScript-JQuer-Jon-Duckett";
                resDuration = 45;
                break;
            case 4:
                resTitle = "Designing Data-Intensive Applications";
                resLink = "https://library.example.com/Designing-Data-Intensive-Applications";
                resDuration = 90;
                break;
            case 5:
                resTitle = "Head First Design Patterns";
                resLink = "https://library.example.com/Head-First-Design-Pattern";
                resDuration = 120;
                break;
            default:
                System.out.println("Invalid resource choice.");
                return;
        }

        int fee = 200; // RM2.00 fee (200 points = RM2.00)
        if (member.deductCreditPoints(fee)) {
            DigitalAccessResource newAccess = new DigitalAccessResource(member.getMemberID(), resTitle, resLink, resDuration);
            loans.add(newAccess);
            System.out.println("Access granted to '" + resTitle + "'.");
            System.out.println("Access fee of " + fee + " points deducted.");
            System.out.println("Access link: " + resLink);
            System.out.println("Access duration: " + resDuration + " days");
        } else {
            System.out.println("Insufficient points to access resource. Required: " + fee + " points.");
            System.out.println("Current balance: " + member.getCreditPoints() + " points.");
            System.out.println("Please use option 3 to top up your account.");
        }
    }

    private static void manageMemberAccount() {
        LibraryMember member = findMember();
        if (member == null) return;

        System.out.println("\n--- Account Top Up ---");
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


        double amountAfterFee = amount - 0.50;
        int pointsToAdd = (int) (amountAfterFee * 100); // RM1 = 100 points

        member.addCreditPoints(pointsToAdd);
        System.out.printf("Top-up successful! RM%.2f processed (RM0.50 transaction fee deducted).\n", amount);
        System.out.println("Points added: " + pointsToAdd);
        System.out.println("New balance: " + member.getCreditPoints() + " points.");
    }

    private static void viewAndReturnLoans() {
        LibraryMember member = findMember();
        if (member == null) return;

        // Find active loans for this member
        ArrayList<LoanRecord> activeLoans = new ArrayList<>();
        for (LoanRecord loan : loans) {
            if (loan.getMemberID().equals(member.getMemberID()) && !loan.isReturned()) {
                activeLoans.add(loan);
            }
        }

        if (activeLoans.isEmpty()) {
            System.out.println("No active loans found.");
            return;
        }

        System.out.println("\n--- Your Active Loans ---");
        for (int i = 0; i < activeLoans.size(); i++) {
            LoanRecord loan = activeLoans.get(i);
            System.out.println("\n--- Loan #" + (i + 1) + " (ID: " + loan.getLoanID() + ") ---");
            System.out.println(loan.getLoanDetails());
        }

        System.out.print("\nSelect loan number to return (0 to cancel) >> ");
        int choice = getUserChoice();

        if (choice > 0 && choice <= activeLoans.size()) {
            processReturn(member, activeLoans.get(choice - 1));
        } else if (choice != 0) {
            System.out.println("Invalid loan selection.");
        }
    }

    private static void processReturn(LibraryMember member, LoanRecord loan) {
        if (loan instanceof PhysicalBookLoan bookLoan) {
            processPhysicalBookReturn(member, bookLoan);
        } else if (loan instanceof DigitalAccessResource digitalResource) {
            processDigitalResourceReturn(member, digitalResource);
        }
    }

    private static void processPhysicalBookReturn(LibraryMember member, PhysicalBookLoan bookLoan) {
        long daysOverdue = 0;
        Date currentDate = new Date();

        if (currentDate.after(bookLoan.getDueDate())) {
            long diff = currentDate.getTime() - bookLoan.getDueDate().getTime();
            daysOverdue = diff / (1000 * 60 * 60 * 24);
        }

        if (daysOverdue > 0) {
            double fine = 0.50 * daysOverdue * bookLoan.getQuantity();
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

        bookLoan.setReturned(true);
        System.out.println("Book '" + bookLoan.getItemTitle() + "' has been successfully returned.");
    }


    private static void processDigitalResourceReturn(LibraryMember member, DigitalAccessResource digitalResource) {
        System.out.println("\n--- Digital Resource Access Details ---");
        System.out.println("Title: " + digitalResource.getItemTitle());
        System.out.println("Access Link: " + digitalResource.getAccessLink());
        System.out.println("Access Duration: " + digitalResource.getAccessDurationDays() + " days");
        System.out.println("Resource accessed successfully.");

        digitalResource.setReturned(true);
        generateReceipt("Digital Resource Access", member.getName(), 0.0);
        System.out.println("Digital resource access completed.");
    }

    //Bonus Part
    private static void generateReceipt(String type, String memberName, double amount) {
        String filename = "receipt_" + System.currentTimeMillis() + ".txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("=== LIBRARY MANAGEMENT SYSTEM RECEIPT ===");
            writer.println("Date: " + new Date());
            writer.println("Type: " + type);
            writer.println("Member: " + memberName);
            if (amount > 0) {
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