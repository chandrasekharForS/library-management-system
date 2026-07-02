import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

/**
 * Member class represents a library member
 * Properties: Member ID, Name, Email, Phone Number, Address, Membership Date, Membership Status
 */
public class Member {
    private String memberId;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private LocalDate membershipDate;
    private boolean isActive;
    private List<String> borrowedBookISBNs;  // Track borrowed book ISBNs

    /**
     * Constructor to initialize a Member with all properties
     */
    public Member(String memberId, String name, String email, String phoneNumber, String address) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.membershipDate = LocalDate.now();
        this.isActive = true;
        this.borrowedBookISBNs = new LinkedList<>();
    }

    // Getters
    public String getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public LocalDate getMembershipDate() {
        return membershipDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public List<String> getBorrowedBookISBNs() {
        return borrowedBookISBNs;
    }

    public int getBorrowedBookCount() {
        return borrowedBookISBNs.size();
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Deactivate membership
     */
    public void deactivateMembership() {
        this.isActive = false;
    }

    /**
     * Reactivate membership
     */
    public void reactivateMembership() {
        this.isActive = true;
    }

    /**
     * Add a borrowed book ISBN to the member's list
     */
    public void addBorrowedBook(String isbn) {
        if (!borrowedBookISBNs.contains(isbn)) {
            borrowedBookISBNs.add(isbn);
        }
    }

    /**
     * Remove a returned book ISBN from the member's list
     */
    public boolean returnBorrowedBook(String isbn) {
        return borrowedBookISBNs.remove(isbn);
    }

    /**
     * Check if member has borrowed a specific book
     */
    public boolean hasBorrowedBook(String isbn) {
        return borrowedBookISBNs.contains(isbn);
    }

    /**
     * Display detailed member information
     */
    public void displayMemberDetails() {
        System.out.println("=".repeat(50));
        System.out.println("Member ID: " + memberId);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Phone Number: " + phoneNumber);
        System.out.println("Address: " + address);
        System.out.println("Membership Date: " + membershipDate);
        System.out.println("Status: " + (isActive ? "ACTIVE" : "INACTIVE"));
        System.out.println("Borrowed Books: " + getBorrowedBookCount());
        if (!borrowedBookISBNs.isEmpty()) {
            System.out.println("Book ISBNs: " + borrowedBookISBNs);
        }
        System.out.println("=".repeat(50));
    }

    /**
     * Display member information in a compact format
     */
    public String getCompactInfo() {
        return String.format("Member ID: %s | Name: %s | Email: %s | Status: %s | Borrowed: %d", 
                             memberId, name, email, (isActive ? "ACTIVE" : "INACTIVE"), getBorrowedBookCount());
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberId='" + memberId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", isActive=" + isActive +
                ", borrowedBooks=" + getBorrowedBookCount() +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Member member = (Member) obj;
        return memberId.equals(member.memberId);
    }

    @Override
    public int hashCode() {
        return memberId.hashCode();
    }
}
