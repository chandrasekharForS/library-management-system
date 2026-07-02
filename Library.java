import java.util.*;

/**
 * Library class serves as the main controller for the Library Management System
 * Manages books, members, and transactions
 */
public class Library {
    private ArrayList<Book> books;
    private HashMap<String, Member> members;
    private TreeSet<Transaction> transactions;
    private Set<String> isbnSet;

    /**
     * Constructor to initialize the Library with empty collections
     */
    public Library() {
        this.books = new ArrayList<>();
        this.members = new HashMap<>();
        this.transactions = new TreeSet<>();
        this.isbnSet = new HashSet<>();
    }

    // ==================== BOOK MANAGEMENT ====================

    /**
     * Add a new book to the library
     */
    public boolean addBook(Book book) {
        if (isbnSet.contains(book.getIsbn())) {
            System.out.println("❌ Book with ISBN " + book.getIsbn() + " already exists!");
            return false;
        }
        books.add(book);
        isbnSet.add(book.getIsbn());
        System.out.println("✅ Book added successfully: " + book.getTitle());
        return true;
    }

    /**
     * Remove a book from the library by ISBN
     */
    public boolean removeBook(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                books.remove(book);
                isbnSet.remove(isbn);
                System.out.println("✅ Book removed successfully: " + book.getTitle());
                return true;
            }
        }
        System.out.println("❌ Book with ISBN " + isbn + " not found!");
        return false;
    }

    /**
     * Search books by ISBN
     */
    public Book searchByISBN(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }

    /**
     * Search books by title (case-insensitive, partial match)
     */
    public ArrayList<Book> searchByTitle(String title) {
        ArrayList<Book> results = new ArrayList<>();
        String lowerTitle = title.toLowerCase();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(lowerTitle)) {
                results.add(book);
            }
        }
        return results;
    }

    /**
     * Search books by author (case-insensitive, partial match)
     */
    public ArrayList<Book> searchByAuthor(String author) {
        ArrayList<Book> results = new ArrayList<>();
        String lowerAuthor = author.toLowerCase();
        for (Book book : books) {
            if (book.getAuthor().toLowerCase().contains(lowerAuthor)) {
                results.add(book);
            }
        }
        return results;
    }

    /**
     * Search books by category
     */
    public ArrayList<Book> searchByCategory(String category) {
        ArrayList<Book> results = new ArrayList<>();
        String lowerCategory = category.toLowerCase();
        for (Book book : books) {
            if (book.getCategory().toLowerCase().equals(lowerCategory)) {
                results.add(book);
            }
        }
        return results;
    }

    /**
     * Get all available books
     */
    public ArrayList<Book> getAvailableBooks() {
        ArrayList<Book> available = new ArrayList<>();
        for (Book book : books) {
            if (book.isAvailable()) {
                available.add(book);
            }
        }
        return available;
    }

    /**
     * Display all books in the library
     */
    public void displayAllBooks() {
        if (books.isEmpty()) {
            System.out.println("📚 No books in the library!");
            return;
        }
        System.out.println("\n" + "=".repeat(70));
        System.out.println("📚 LIBRARY BOOKS (" + books.size() + " total)");
        System.out.println("=".repeat(70));
        for (Book book : books) {
            System.out.println(book.getCompactInfo());
        }
        System.out.println("=".repeat(70) + "\n");
    }

    // ==================== MEMBER MANAGEMENT ====================

    /**
     * Register a new member
     */
    public boolean registerMember(Member member) {
        if (members.containsKey(member.getMemberId())) {
            System.out.println("❌ Member with ID " + member.getMemberId() + " already exists!");
            return false;
        }
        members.put(member.getMemberId(), member);
        System.out.println("✅ Member registered successfully: " + member.getName());
        return true;
    }

    /**
     * Remove a member from the library
     */
    public boolean removeMember(String memberId) {
        if (members.containsKey(memberId)) {
            Member member = members.get(memberId);
            if (member.getBorrowedBookCount() > 0) {
                System.out.println("❌ Cannot remove member with borrowed books!");
                return false;
            }
            members.remove(memberId);
            System.out.println("✅ Member removed successfully: " + member.getName());
            return true;
        }
        System.out.println("❌ Member with ID " + memberId + " not found!");
        return false;
    }

    /**
     * Search for a member by ID
     */
    public Member searchMember(String memberId) {
        return members.get(memberId);
    }

    /**
     * Search members by name (case-insensitive, partial match)
     */
    public ArrayList<Member> searchMemberByName(String name) {
        ArrayList<Member> results = new ArrayList<>();
        String lowerName = name.toLowerCase();
        for (Member member : members.values()) {
            if (member.getName().toLowerCase().contains(lowerName)) {
                results.add(member);
            }
        }
        return results;
    }

    /**
     * Deactivate a member's membership
     */
    public boolean deactivateMember(String memberId) {
        Member member = members.get(memberId);
        if (member != null) {
            member.deactivateMembership();
            System.out.println("✅ Member deactivated: " + member.getName());
            return true;
        }
        System.out.println("❌ Member with ID " + memberId + " not found!");
        return false;
    }

    /**
     * Reactivate a member's membership
     */
    public boolean reactivateMember(String memberId) {
        Member member = members.get(memberId);
        if (member != null) {
            member.reactivateMembership();
            System.out.println("✅ Member reactivated: " + member.getName());
            return true;
        }
        System.out.println("❌ Member with ID " + memberId + " not found!");
        return false;
    }

    /**
     * Display all members
     */
    public void displayAllMembers() {
        if (members.isEmpty()) {
            System.out.println("👤 No members in the library!");
            return;
        }
        System.out.println("\n" + "=".repeat(80));
        System.out.println("👤 LIBRARY MEMBERS (" + members.size() + " total)");
        System.out.println("=".repeat(80));
        for (Member member : members.values()) {
            System.out.println(member.getCompactInfo());
        }
        System.out.println("=".repeat(80) + "\n");
    }

    // ==================== TRANSACTION MANAGEMENT ====================

    /**
     * Borrow a book for a member
     */
    public boolean borrowBook(String memberId, String isbn) {
        Member member = members.get(memberId);
        Book book = searchByISBN(isbn);

        if (member == null) {
            System.out.println("❌ Member with ID " + memberId + " not found!");
            return false;
        }

        if (!member.isActive()) {
            System.out.println("❌ Member " + member.getName() + " is not active!");
            return false;
        }

        if (book == null) {
            System.out.println("❌ Book with ISBN " + isbn + " not found!");
            return false;
        }

        if (!book.isAvailable()) {
            System.out.println("❌ Book \"" + book.getTitle() + "\" is not available!");
            return false;
        }

        if (member.hasBorrowedBook(isbn)) {
            System.out.println("❌ Member has already borrowed this book!");
            return false;
        }

        // Perform the borrow operation
        if (book.borrowBook()) {
            Transaction transaction = new Transaction(memberId, isbn);
            transactions.add(transaction);
            member.addBorrowedBook(isbn);
            System.out.println("✅ Book borrowed successfully!");
            System.out.println("   Title: " + book.getTitle());
            System.out.println("   Member: " + member.getName());
            System.out.println("   Due Date: " + transaction.getDueDate());
            return true;
        }

        return false;
    }

    /**
     * Return a book by a member
     */
    public boolean returnBook(String memberId, String isbn) {
        Member member = members.get(memberId);
        Book book = searchByISBN(isbn);

        if (member == null) {
            System.out.println("❌ Member with ID " + memberId + " not found!");
            return false;
        }

        if (book == null) {
            System.out.println("❌ Book with ISBN " + isbn + " not found!");
            return false;
        }

        if (!member.hasBorrowedBook(isbn)) {
            System.out.println("❌ Member has not borrowed this book!");
            return false;
        }

        // Find and process the transaction
        Transaction targetTransaction = null;
        for (Transaction transaction : transactions) {
            if (transaction.getMemberId().equals(memberId) && 
                transaction.getBookIsbn().equals(isbn) && 
                !transaction.isReturned()) {
                targetTransaction = transaction;
                break;
            }
        }

        if (targetTransaction == null) {
            System.out.println("❌ Active transaction not found!");
            return false;
        }

        // Perform the return operation
        if (book.returnBook()) {
            targetTransaction.returnBook();
            member.returnBorrowedBook(isbn);
            System.out.println("✅ Book returned successfully!");
            System.out.println("   Title: " + book.getTitle());
            System.out.println("   Member: " + member.getName());
            if (targetTransaction.getCurrentFine() > 0) {
                System.out.println("   ⚠️ Fine Amount: " + String.format("%.2f", targetTransaction.getCurrentFine()));
            }
            return true;
        }

        return false;
    }

    /**
     * Get all active (not returned) transactions
     */
    public ArrayList<Transaction> getActiveTransactions() {
        ArrayList<Transaction> active = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (!transaction.isReturned()) {
                active.add(transaction);
            }
        }
        return active;
    }

    /**
     * Get all overdue transactions
     */
    public ArrayList<Transaction> getOverdueTransactions() {
        ArrayList<Transaction> overdue = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (!transaction.isReturned() && transaction.isOverdue()) {
                overdue.add(transaction);
            }
        }
        return overdue;
    }

    /**
     * Get transaction history for a member
     */
    public ArrayList<Transaction> getMemberTransactionHistory(String memberId) {
        ArrayList<Transaction> history = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getMemberId().equals(memberId)) {
                history.add(transaction);
            }
        }
        return history;
    }

    /**
     * Display all transactions
     */
    public void displayAllTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("📝 No transactions in the library!");
            return;
        }
        System.out.println("\n" + "=".repeat(80));
        System.out.println("📝 ALL TRANSACTIONS (" + transactions.size() + " total)");
        System.out.println("=".repeat(80));
        for (Transaction transaction : transactions) {
            System.out.println(transaction.getCompactInfo());
        }
        System.out.println("=".repeat(80) + "\n");
    }

    // ==================== REPORTS ====================

    /**
     * Display overdue books report
     */
    public void displayOverdueReport() {
        ArrayList<Transaction> overdue = getOverdueTransactions();
        System.out.println("\n" + "=".repeat(80));
        System.out.println("⚠️ OVERDUE BOOKS REPORT (" + overdue.size() + " books)");
        System.out.println("=".repeat(80));
        if (overdue.isEmpty()) {
            System.out.println("✅ No overdue books!");
        } else {
            for (Transaction transaction : overdue) {
                System.out.println(transaction.getCompactInfo());
            }
        }
        System.out.println("=".repeat(80) + "\n");
    }

    /**
     * Display member borrowing history
     */
    public void displayMemberHistory(String memberId) {
        Member member = members.get(memberId);
        if (member == null) {
            System.out.println("❌ Member with ID " + memberId + " not found!");
            return;
        }

        ArrayList<Transaction> history = getMemberTransactionHistory(memberId);
        System.out.println("\n" + "=".repeat(80));
        System.out.println("📖 BORROWING HISTORY - " + member.getName());
        System.out.println("=".repeat(80));
        if (history.isEmpty()) {
            System.out.println("No borrowing history!");
        } else {
            for (Transaction transaction : history) {
                System.out.println(transaction.getCompactInfo());
            }
        }
        System.out.println("=".repeat(80) + "\n");
    }

    /**
     * Display library statistics
     */
    public void displayLibraryStatistics() {
        int totalBooks = books.size();
        int availableBooks = getAvailableBooks().size();
        int borrowedBooks = totalBooks - availableBooks;
        int activeTransactions = getActiveTransactions().size();
        int totalMembers = members.size();
        int activeMembers = (int) members.values().stream().filter(Member::isActive).count();

        System.out.println("\n" + "=".repeat(80));
        System.out.println("📊 LIBRARY STATISTICS");
        System.out.println("=".repeat(80));
        System.out.println("Total Books: " + totalBooks);
        System.out.println("Available Books: " + availableBooks);
        System.out.println("Borrowed Books: " + borrowedBooks);
        System.out.println("Active Transactions: " + activeTransactions);
        System.out.println("Total Members: " + totalMembers);
        System.out.println("Active Members: " + activeMembers);
        System.out.println("=".repeat(80) + "\n");
    }

    /**
     * Get total fine amount for all overdue books
     */
    public double getTotalFineAmount() {
        double totalFine = 0.0;
        for (Transaction transaction : getOverdueTransactions()) {
            totalFine += transaction.getCurrentFine();
        }
        return totalFine;
    }

    /**
     * Get total fine for a specific member
     */
    public double getMemberTotalFine(String memberId) {
        double totalFine = 0.0;
        ArrayList<Transaction> memberHistory = getMemberTransactionHistory(memberId);
        for (Transaction transaction : memberHistory) {
            totalFine += transaction.getCurrentFine();
        }
        return totalFine;
    }

    // ==================== UTILITY METHODS ====================

    /**
     * Get the total number of books in the library
     */
    public int getTotalBookCount() {
        return books.size();
    }

    /**
     * Get the total number of members
     */
    public int getTotalMemberCount() {
        return members.size();
    }

    /**
     * Get the total number of transactions
     */
    public int getTotalTransactionCount() {
        return transactions.size();
    }
}
