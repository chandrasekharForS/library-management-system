import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Transaction class handles borrowing and returning of books
 * Tracks: Member, Book, Borrow Date, Due Date, Return Date, Fine Amount
 */
public class Transaction implements Comparable<Transaction> {
    private static int transactionCounter = 1000;
    private int transactionId;
    private String memberId;
    private String bookIsbn;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private boolean isReturned;
    private double fineAmount;
    private static final int BORROW_DAYS = 14;
    private static final double FINE_PER_DAY = 5.0;  // Fine in currency units per day

    /**
     * Constructor for a new transaction (borrowing a book)
     */
    public Transaction(String memberId, String bookIsbn) {
        this.transactionId = ++transactionCounter;
        this.memberId = memberId;
        this.bookIsbn = bookIsbn;
        this.borrowDate = LocalDate.now();
        this.dueDate = borrowDate.plusDays(BORROW_DAYS);
        this.returnDate = null;
        this.isReturned = false;
        this.fineAmount = 0.0;
    }

    // Getters
    public int getTransactionId() {
        return transactionId;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public double getFineAmount() {
        return fineAmount;
    }

    /**
     * Return a book and calculate fine if applicable
     */
    public void returnBook() {
        if (!isReturned) {
            this.returnDate = LocalDate.now();
            this.isReturned = true;
            calculateFine();
        }
    }

    /**
     * Calculate fine based on return date
     */
    private void calculateFine() {
        if (returnDate.isAfter(dueDate)) {
            long daysOverdue = ChronoUnit.DAYS.between(dueDate, returnDate);
            this.fineAmount = daysOverdue * FINE_PER_DAY;
        } else {
            this.fineAmount = 0.0;
        }
    }

    /**
     * Check if the book is overdue
     */
    public boolean isOverdue() {
        if (isReturned) {
            return returnDate.isAfter(dueDate);
        } else {
            return LocalDate.now().isAfter(dueDate);
        }
    }

    /**
     * Get days overdue (only if book is not returned yet)
     */
    public long getDaysOverdue() {
        if (!isReturned && isOverdue()) {
            return ChronoUnit.DAYS.between(dueDate, LocalDate.now());
        }
        return 0;
    }

    /**
     * Calculate current fine for unreturned overdue books
     */
    public double getCurrentFine() {
        if (isReturned) {
            return fineAmount;
        } else if (isOverdue()) {
            long daysOverdue = getDaysOverdue();
            return daysOverdue * FINE_PER_DAY;
        }
        return 0.0;
    }

    /**
     * Display detailed transaction information
     */
    public void displayTransactionDetails() {
        System.out.println("=".repeat(60));
        System.out.println("Transaction ID: " + transactionId);
        System.out.println("Member ID: " + memberId);
        System.out.println("Book ISBN: " + bookIsbn);
        System.out.println("Borrow Date: " + borrowDate);
        System.out.println("Due Date: " + dueDate);
        System.out.println("Return Date: " + (returnDate != null ? returnDate : "Not Returned"));
        System.out.println("Status: " + (isReturned ? "RETURNED" : "ACTIVE"));
        if (isOverdue()) {
            System.out.println("⚠️ OVERDUE - Days Overdue: " + getDaysOverdue());
        }
        System.out.println("Fine Amount: " + String.format("%.2f", getCurrentFine()));
        System.out.println("=".repeat(60));
    }

    /**
     * Display transaction information in a compact format
     */
    public String getCompactInfo() {
        return String.format("TID: %d | Member: %s | ISBN: %s | Status: %s | Fine: %.2f", 
                             transactionId, memberId, bookIsbn, 
                             (isReturned ? "RETURNED" : "ACTIVE"), 
                             getCurrentFine());
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", memberId='" + memberId + '\'' +
                ", bookIsbn='" + bookIsbn + '\'' +
                ", borrowDate=" + borrowDate +
                ", dueDate=" + dueDate +
                ", returnDate=" + returnDate +
                ", isReturned=" + isReturned +
                ", fineAmount=" + fineAmount +
                '}';
    }

    @Override
    public int compareTo(Transaction other) {
        // Sort transactions by borrow date in ascending order
        return this.borrowDate.compareTo(other.borrowDate);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Transaction transaction = (Transaction) obj;
        return transactionId == transaction.transactionId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(transactionId);
    }
}
