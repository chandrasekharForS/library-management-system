import java.util.Scanner;
import java.util.ArrayList;

/**
 * LibraryManagementApp - Main driver program for the Library Management System
 * Provides a console-based user interface for library operations
 */
public class LibraryManagementApp {
    private static Library library;
    private static Scanner scanner;

    public static void main(String[] args) {
        library = new Library();
        scanner = new Scanner(System.in);

        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║     Welcome to Library Management System                        ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        initializeSampleData();

        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getIntInput();

            switch (choice) {
                case 1:
                    bookManagementMenu();
                    break;
                case 2:
                    memberManagementMenu();
                    break;
                case 3:
                    transactionManagementMenu();
                    break;
                case 4:
                    reportsMenu();
                    break;
                case 5:
                    running = false;
                    System.out.println("\n✅ Thank you for using Library Management System. Goodbye!");
                    break;
                default:
                    System.out.println("❌ Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    // ==================== MENU METHODS ====================

    private static void displayMainMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("MAIN MENU");
        System.out.println("=".repeat(50));
        System.out.println("1. Book Management");
        System.out.println("2. Member Management");
        System.out.println("3. Transaction Management (Borrow/Return)");
        System.out.println("4. Reports & Statistics");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void bookManagementMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("BOOK MANAGEMENT");
            System.out.println("=".repeat(50));
            System.out.println("1. Add a Book");
            System.out.println("2. Remove a Book");
            System.out.println("3. Search Book by ISBN");
            System.out.println("4. Search Book by Title");
            System.out.println("5. Search Book by Author");
            System.out.println("6. Search Book by Category");
            System.out.println("7. View All Books");
            System.out.println("8. View Available Books");
            System.out.println("9. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getIntInput();
            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    removeBook();
                    break;
                case 3:
                    searchByISBN();
                    break;
                case 4:
                    searchByTitle();
                    break;
                case 5:
                    searchByAuthor();
                    break;
                case 6:
                    searchByCategory();
                    break;
                case 7:
                    library.displayAllBooks();
                    break;
                case 8:
                    displayAvailableBooks();
                    break;
                case 9:
                    back = true;
                    break;
                default:
                    System.out.println("❌ Invalid choice. Please try again.");
            }
        }
    }

    private static void memberManagementMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("MEMBER MANAGEMENT");
            System.out.println("=".repeat(50));
            System.out.println("1. Register a New Member");
            System.out.println("2. Remove a Member");
            System.out.println("3. Search Member by ID");
            System.out.println("4. Search Member by Name");
            System.out.println("5. View All Members");
            System.out.println("6. Deactivate Membership");
            System.out.println("7. Reactivate Membership");
            System.out.println("8. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getIntInput();
            switch (choice) {
                case 1:
                    registerMember();
                    break;
                case 2:
                    removeMember();
                    break;
                case 3:
                    searchMemberByID();
                    break;
                case 4:
                    searchMemberByName();
                    break;
                case 5:
                    library.displayAllMembers();
                    break;
                case 6:
                    deactivateMembership();
                    break;
                case 7:
                    reactivateMembership();
                    break;
                case 8:
                    back = true;
                    break;
                default:
                    System.out.println("❌ Invalid choice. Please try again.");
            }
        }
    }

    private static void transactionManagementMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("TRANSACTION MANAGEMENT");
            System.out.println("=".repeat(50));
            System.out.println("1. Borrow a Book");
            System.out.println("2. Return a Book");
            System.out.println("3. View All Transactions");
            System.out.println("4. View Active Transactions");
            System.out.println("5. View Overdue Transactions");
            System.out.println("6. View Member Borrowing History");
            System.out.println("7. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getIntInput();
            switch (choice) {
                case 1:
                    borrowBook();
                    break;
                case 2:
                    returnBook();
                    break;
                case 3:
                    library.displayAllTransactions();
                    break;
                case 4:
                    displayActiveTransactions();
                    break;
                case 5:
                    library.displayOverdueReport();
                    break;
                case 6:
                    viewMemberHistory();
                    break;
                case 7:
                    back = true;
                    break;
                default:
                    System.out.println("❌ Invalid choice. Please try again.");
            }
        }
    }

    private static void reportsMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("REPORTS & STATISTICS");
            System.out.println("=".repeat(50));
            System.out.println("1. Library Statistics");
            System.out.println("2. Overdue Books Report");
            System.out.println("3. Total Fine Amount");
            System.out.println("4. Member Fine Amount");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getIntInput();
            switch (choice) {
                case 1:
                    library.displayLibraryStatistics();
                    break;
                case 2:
                    library.displayOverdueReport();
                    break;
                case 3:
                    displayTotalFine();
                    break;
                case 4:
                    displayMemberFine();
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("❌ Invalid choice. Please try again.");
            }
        }
    }

    // ==================== BOOK OPERATIONS ====================

    private static void addBook() {
        System.out.println("\n--- Add New Book ---");
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine().trim();

        System.out.print("Enter Title: ");
        String title = scanner.nextLine().trim();

        System.out.print("Enter Author: ");
        String author = scanner.nextLine().trim();

        System.out.print("Enter Publisher: ");
        String publisher = scanner.nextLine().trim();

        System.out.print("Enter Publication Year: ");
        int year = getIntInput();

        System.out.print("Enter Category: ");
        String category = scanner.nextLine().trim();

        System.out.print("Enter Number of Copies: ");
        int copies = getIntInput();

        Book book = new Book(isbn, title, author, publisher, year, category, copies);
        library.addBook(book);
    }

    private static void removeBook() {
        System.out.println("\n--- Remove Book ---");
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine().trim();
        library.removeBook(isbn);
    }

    private static void searchByISBN() {
        System.out.println("\n--- Search Book by ISBN ---");
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine().trim();
        Book book = library.searchByISBN(isbn);
        if (book != null) {
            book.displayBookDetails();
        } else {
            System.out.println("❌ Book not found!");
        }
    }

    private static void searchByTitle() {
        System.out.println("\n--- Search Book by Title ---");
        System.out.print("Enter Title (or part of it): ");
        String title = scanner.nextLine().trim();
        ArrayList<Book> results = library.searchByTitle(title);
        if (results.isEmpty()) {
            System.out.println("❌ No books found!");
        } else {
            System.out.println("\n📚 Search Results:");
            for (Book book : results) {
                System.out.println(book.getCompactInfo());
            }
        }
    }

    private static void searchByAuthor() {
        System.out.println("\n--- Search Book by Author ---");
        System.out.print("Enter Author Name (or part of it): ");
        String author = scanner.nextLine().trim();
        ArrayList<Book> results = library.searchByAuthor(author);
        if (results.isEmpty()) {
            System.out.println("❌ No books found!");
        } else {
            System.out.println("\n📚 Search Results:");
            for (Book book : results) {
                System.out.println(book.getCompactInfo());
            }
        }
    }

    private static void searchByCategory() {
        System.out.println("\n--- Search Book by Category ---");
        System.out.print("Enter Category: ");
        String category = scanner.nextLine().trim();
        ArrayList<Book> results = library.searchByCategory(category);
        if (results.isEmpty()) {
            System.out.println("❌ No books found!");
        } else {
            System.out.println("\n📚 Search Results:");
            for (Book book : results) {
                System.out.println(book.getCompactInfo());
            }
        }
    }

    private static void displayAvailableBooks() {
        ArrayList<Book> available = library.getAvailableBooks();
        if (available.isEmpty()) {
            System.out.println("❌ No available books!");
        } else {
            System.out.println("\n✅ Available Books:");
            for (Book book : available) {
                System.out.println(book.getCompactInfo());
            }
        }
    }

    // ==================== MEMBER OPERATIONS ====================

    private static void registerMember() {
        System.out.println("\n--- Register New Member ---");
        System.out.print("Enter Member ID: ");
        String memberId = scanner.nextLine().trim();

        System.out.print("Enter Name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Enter Phone Number: ");
        String phone = scanner.nextLine().trim();

        System.out.print("Enter Address: ");
        String address = scanner.nextLine().trim();

        Member member = new Member(memberId, name, email, phone, address);
        library.registerMember(member);
    }

    private static void removeMember() {
        System.out.println("\n--- Remove Member ---");
        System.out.print("Enter Member ID: ");
        String memberId = scanner.nextLine().trim();
        library.removeMember(memberId);
    }

    private static void searchMemberByID() {
        System.out.println("\n--- Search Member by ID ---");
        System.out.print("Enter Member ID: ");
        String memberId = scanner.nextLine().trim();
        Member member = library.searchMember(memberId);
        if (member != null) {
            member.displayMemberDetails();
        } else {
            System.out.println("❌ Member not found!");
        }
    }

    private static void searchMemberByName() {
        System.out.println("\n--- Search Member by Name ---");
        System.out.print("Enter Name (or part of it): ");
        String name = scanner.nextLine().trim();
        ArrayList<Member> results = library.searchMemberByName(name);
        if (results.isEmpty()) {
            System.out.println("❌ No members found!");
        } else {
            System.out.println("\n👤 Search Results:");
            for (Member member : results) {
                System.out.println(member.getCompactInfo());
            }
        }
    }

    private static void deactivateMembership() {
        System.out.println("\n--- Deactivate Membership ---");
        System.out.print("Enter Member ID: ");
        String memberId = scanner.nextLine().trim();
        library.deactivateMember(memberId);
    }

    private static void reactivateMembership() {
        System.out.println("\n--- Reactivate Membership ---");
        System.out.print("Enter Member ID: ");
        String memberId = scanner.nextLine().trim();
        library.reactivateMember(memberId);
    }

    // ==================== TRANSACTION OPERATIONS ====================

    private static void borrowBook() {
        System.out.println("\n--- Borrow a Book ---");
        System.out.print("Enter Member ID: ");
        String memberId = scanner.nextLine().trim();

        System.out.print("Enter Book ISBN: ");
        String isbn = scanner.nextLine().trim();

        library.borrowBook(memberId, isbn);
    }

    private static void returnBook() {
        System.out.println("\n--- Return a Book ---");
        System.out.print("Enter Member ID: ");
        String memberId = scanner.nextLine().trim();

        System.out.print("Enter Book ISBN: ");
        String isbn = scanner.nextLine().trim();

        library.returnBook(memberId, isbn);
    }

    private static void displayActiveTransactions() {
        ArrayList<Transaction> active = library.getActiveTransactions();
        System.out.println("\n" + "=".repeat(80));
        System.out.println("📝 ACTIVE TRANSACTIONS (" + active.size() + " total)");
        System.out.println("=".repeat(80));
        if (active.isEmpty()) {
            System.out.println("No active transactions!");
        } else {
            for (Transaction transaction : active) {
                System.out.println(transaction.getCompactInfo());
            }
        }
        System.out.println("=".repeat(80));
    }

    private static void viewMemberHistory() {
        System.out.println("\n--- View Member Borrowing History ---");
        System.out.print("Enter Member ID: ");
        String memberId = scanner.nextLine().trim();
        library.displayMemberHistory(memberId);
    }

    // ==================== REPORT OPERATIONS ====================

    private static void displayTotalFine() {
        double totalFine = library.getTotalFineAmount();
        System.out.println("\n" + "=".repeat(50));
        System.out.println("💰 TOTAL FINE AMOUNT");
        System.out.println("=".repeat(50));
        System.out.println("Total Fine: " + String.format("%.2f", totalFine));
        System.out.println("=".repeat(50));
    }

    private static void displayMemberFine() {
        System.out.println("\n--- Member Fine Amount ---");
        System.out.print("Enter Member ID: ");
        String memberId = scanner.nextLine().trim();
        double memberFine = library.getMemberTotalFine(memberId);
        System.out.println("\n" + "=".repeat(50));
        System.out.println("💰 MEMBER FINE AMOUNT");
        System.out.println("=".repeat(50));
        System.out.println("Member: " + memberId);
        System.out.println("Total Fine: " + String.format("%.2f", memberFine));
        System.out.println("=".repeat(50));
    }

    // ==================== UTILITY METHODS ====================

    private static int getIntInput() {
        try {
            int input = Integer.parseInt(scanner.nextLine().trim());
            return input;
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input. Please enter a number.");
            return -1;
        }
    }

    private static void initializeSampleData() {
        // Add sample books
        library.addBook(new Book("ISBN001", "The Great Gatsby", "F. Scott Fitzgerald", "Scribner", 1925, "Fiction", 3));
        library.addBook(new Book("ISBN002", "To Kill a Mockingbird", "Harper Lee", "Lippincott", 1960, "Fiction", 2));
        library.addBook(new Book("ISBN003", "1984", "George Orwell", "Secker & Warburg", 1949, "Dystopian", 4));
        library.addBook(new Book("ISBN004", "Java Programming", "Herbert Schildt", "McGraw-Hill", 2018, "Technology", 2));
        library.addBook(new Book("ISBN005", "The Catcher in the Rye", "J.D. Salinger", "Little Brown", 1951, "Fiction", 1));

        // Add sample members
        library.registerMember(new Member("M001", "Alice Johnson", "alice@email.com", "555-0001", "123 Main St"));
        library.registerMember(new Member("M002", "Bob Smith", "bob@email.com", "555-0002", "456 Oak Ave"));
        library.registerMember(new Member("M003", "Charlie Brown", "charlie@email.com", "555-0003", "789 Pine Rd"));

        System.out.println("✅ Sample data initialized successfully!\n");
    }
}
