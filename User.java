import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * User class represents a customer in the e-commerce system
 * Implements Serializable for file persistence
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int userCounter = 1000;
    
    private int userId;
    private String username;
    private String password;
    private String email;
    private String fullName;
    private String phoneNumber;
    private String address;
    private double walletBalance;
    private LocalDateTime registrationDate;

    /**
     * Constructor to initialize a User
     */
    public User(String username, String password, String email, String fullName, 
                String phoneNumber, String address) {
        this.userId = ++userCounter;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.walletBalance = 0.0;
        this.registrationDate = LocalDateTime.now();
    }

    // Getters
    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public double getWalletBalance() {
        return walletBalance;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    // Setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Add balance to wallet (e.g., recharge)
     */
    public void addWalletBalance(double amount) {
        if (amount > 0) {
            this.walletBalance += amount;
        }
    }

    /**
     * Deduct balance from wallet
     */
    public boolean deductWalletBalance(double amount) {
        if (amount > 0 && this.walletBalance >= amount) {
            this.walletBalance -= amount;
            return true;
        }
        return false;
    }

    /**
     * Check if user has sufficient wallet balance
     */
    public boolean hasSufficientBalance(double amount) {
        return this.walletBalance >= amount;
    }

    /**
     * Verify password
     */
    public boolean verifyPassword(String password) {
        return this.password.equals(password);
    }

    /**
     * Display user profile
     */
    public void displayProfile() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("👤 USER PROFILE");
        System.out.println("=".repeat(60));
        System.out.println("User ID: " + userId);
        System.out.println("Username: " + username);
        System.out.println("Full Name: " + fullName);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phoneNumber);
        System.out.println("Address: " + address);
        System.out.println("Wallet Balance: $" + String.format("%.2f", walletBalance));
        System.out.println("Registration Date: " + registrationDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println("=".repeat(60) + "\n");
    }

    /**
     * Get compact user info
     */
    public String getCompactInfo() {
        return String.format("ID: %d | %s (%s) | Wallet: $%.2f", 
                             userId, username, fullName, walletBalance);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", walletBalance=" + walletBalance +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return userId == user.userId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(userId);
    }
}
