import java.util.ArrayList;
import java.util.Scanner;

/**
 * ECommerceApp - Main driver program for the File-Based E-Commerce System
 * Provides interactive menu-driven user interface
 */
public class ECommerceApp {
    private static ECommerceService ecommerceService;
    private static Scanner scanner;
    private static User currentUser;

    public static void main(String[] args) {
        ecommerceService = new ECommerceService();
        scanner = new Scanner(System.in);
        currentUser = null;

        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║     Welcome to File-Based E-Commerce System                     ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        initializeSampleData();

        boolean running = true;
        while (running) {
            if (currentUser == null) {
                displayAuthenticationMenu();
                int choice = getIntInput();
                switch (choice) {
                    case 1:
                        registerUser();
                        break;
                    case 2:
                        loginUser();
                        break;
                    case 3:
                        running = false;
                        break;
                    default:
                        System.out.println("❌ Invalid choice. Please try again.");
                }
            } else {
                displayMainMenu();
                int choice = getIntInput();
                switch (choice) {
                    case 1:
                        browseProducts();
                        break;
                    case 2:
                        cartMenu();
                        break;
                    case 3:
                        checkoutMenu();
                        break;
                    case 4:
                        viewOrderHistory();
                        break;
                    case 5:
                        walletMenu();
                        break;
                    case 6:
                        currentUser.displayProfile();
                        break;
                    case 7:
                        logoutUser();
                        break;
                    default:
                        System.out.println("❌ Invalid choice. Please try again.");
                }
            }
        }

        ecommerceService.saveDataToFiles();
        System.out.println("\n✅ Thank you for using E-Commerce System. Goodbye!");
        scanner.close();
    }

    // ==================== MENU METHODS ====================

    private static void displayAuthenticationMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("AUTHENTICATION MENU");
        System.out.println("=".repeat(50));
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void displayMainMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("MAIN MENU (Logged in as: " + currentUser.getUsername() + ")");
        System.out.println("=".repeat(50));
        System.out.println("1. Browse Products");
        System.out.println("2. Shopping Cart");
        System.out.println("3. Checkout & Place Order");
        System.out.println("4. View Order History");
        System.out.println("5. Wallet Management");
        System.out.println("6. View Profile");
        System.out.println("7. Logout");
        System.out.print("Enter your choice: ");
    }

    private static void cartMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("SHOPPING CART");
            System.out.println("=".repeat(50));
            System.out.println("1. View Cart");
            System.out.println("2. Add Item to Cart");
            System.out.println("3. Remove Item from Cart");
            System.out.println("4. Clear Cart");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getIntInput();
            switch (choice) {
                case 1:
                    ecommerceService.getCart(currentUser.getUserId()).displayCart();
                    break;
                case 2:
                    addToCart();
                    break;
                case 3:
                    removeFromCart();
                    break;
                case 4:
                    ecommerceService.clearCart(currentUser.getUserId());
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("❌ Invalid choice.");
            }
        }
    }

    private static void walletMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("WALLET MANAGEMENT");
        System.out.println("=".repeat(50));
        System.out.println("Current Balance: $" + String.format("%.2f", currentUser.getWalletBalance()));
        System.out.print("Enter amount to add: $");
        double amount = getDoubleInput();
        if (amount > 0) {
            ecommerceService.addWalletBalance(currentUser.getUserId(), amount);
        }
    }

    // ==================== AUTHENTICATION ====================

    private static void registerUser() {
        System.out.println("\n--- Register New User ---");
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Enter full name: ");
        String fullName = scanner.nextLine().trim();

        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine().trim();

        System.out.print("Enter address: ");
        String address = scanner.nextLine().trim();

        User newUser = ecommerceService.registerUser(username, password, email, fullName, phone, address);
        if (newUser != null) {
            System.out.println("Welcome, " + fullName + "!");
        }
    }

    private static void loginUser() {
        System.out.println("\n--- Login ---");
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        currentUser = ecommerceService.loginUser(username, password);
    }

    private static void logoutUser() {
        System.out.println("\n✅ Logged out successfully!");
        currentUser = null;
    }

    // ==================== PRODUCT BROWSING ====================

    private static void browseProducts() {
        boolean back = false;
        while (!back) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("BROWSE PRODUCTS");
            System.out.println("=".repeat(50));
            System.out.println("1. View All Products");
            System.out.println("2. Search by Name");
            System.out.println("3. Browse by Category");
            System.out.println("4. View Product Details");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getIntInput();
            switch (choice) {
                case 1:
                    ecommerceService.displayAllProducts();
                    break;
                case 2:
                    searchProducts();
                    break;
                case 3:
                    browseByCategory();
                    break;
                case 4:
                    viewProductDetails();
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("❌ Invalid choice.");
            }
        }
    }

    private static void searchProducts() {
        System.out.print("\nEnter product name to search: ");
        String name = scanner.nextLine().trim();
        ArrayList<Product> results = ecommerceService.searchProductsByName(name);
        if (results.isEmpty()) {
            System.out.println("❌ No products found!");
        } else {
            System.out.println("\n📦 Search Results:");
            for (Product product : results) {
                System.out.println(product.getCompactInfo());
            }
        }
    }

    private static void browseByCategory() {
        System.out.print("\nEnter category: ");
        String category = scanner.nextLine().trim();
        ArrayList<Product> results = ecommerceService.getProductsByCategory(category);
        if (results.isEmpty()) {
            System.out.println("❌ No products in this category!");
        } else {
            System.out.println("\n📦 Products in " + category + ":");
            for (Product product : results) {
                System.out.println(product.getCompactInfo());
            }
        }
    }

    private static void viewProductDetails() {
        System.out.print("\nEnter Product ID: ");
        int productId = getIntInput();
        Product product = ecommerceService.getProductById(productId);
        if (product != null) {
            product.displayProductDetails();
        } else {
            System.out.println("❌ Product not found!");
        }
    }

    // ==================== CART OPERATIONS ====================

    private static void addToCart() {
        System.out.print("\nEnter Product ID: ");
        int productId = getIntInput();

        System.out.print("Enter quantity: ");
        int quantity = getIntInput();

        if (quantity > 0) {
            ecommerceService.addToCart(currentUser.getUserId(), productId, quantity);
        }
    }

    private static void removeFromCart() {
        System.out.print("\nEnter Product ID to remove: ");
        int productId = getIntInput();
        ecommerceService.removeFromCart(currentUser.getUserId(), productId);
    }

    // ==================== CHECKOUT ====================

    private static void checkoutMenu() {
        Cart cart = ecommerceService.getCart(currentUser.getUserId());
        if (cart.isEmpty()) {
            System.out.println("\n❌ Your cart is empty!");
            return;
        }

        cart.displayCart();

        System.out.print("\nEnter shipping address (or press Enter for your registered address): ");
        String address = scanner.nextLine().trim();
        if (address.isEmpty()) {
            address = currentUser.getAddress();
        }

        System.out.println("\nPayment Methods:");
        System.out.println("1. Wallet");
        System.out.println("2. Card");
        System.out.println("3. UPI");
        System.out.print("Select payment method: ");
        int paymentChoice = getIntInput();

        String paymentMethod;
        switch (paymentChoice) {
            case 1:
                paymentMethod = "wallet";
                break;
            case 2:
                paymentMethod = "card";
                break;
            case 3:
                paymentMethod = "upi";
                break;
            default:
                System.out.println("❌ Invalid payment method!");
                return;
        }

        Order order = ecommerceService.placeOrder(currentUser.getUserId(), paymentMethod, address);
        if (order != null) {
            order.displayOrderDetails();
        }
    }

    // ==================== ORDER HISTORY ====================

    private static void viewOrderHistory() {
        ArrayList<Order> userOrders = ecommerceService.getUserOrderHistory(currentUser.getUserId());
        System.out.println("\n" + "=".repeat(100));
        System.out.println("📋 ORDER HISTORY");
        System.out.println("=".repeat(100));
        if (userOrders.isEmpty()) {
            System.out.println("No orders found!");
        } else {
            for (Order order : userOrders) {
                System.out.println(order.getCompactInfo());
            }
            System.out.println("\nEnter Order ID to view details (or 0 to go back): ");
            int orderId = getIntInput();
            if (orderId > 0) {
                Order order = ecommerceService.getOrderById(orderId);
                if (order != null) {
                    order.displayOrderDetails();
                }
            }
        }
        System.out.println("=".repeat(100));
    }

    // ==================== UTILITY METHODS ====================

    private static int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input. Please enter a number.");
            return -1;
        }
    }

    private static double getDoubleInput() {
        try {
            return Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input. Please enter a valid amount.");
            return -1;
        }
    }

    private static void initializeSampleData() {
        // Add sample products
        ecommerceService.addProduct("Laptop", "High-performance laptop for gaming", 999.99, "Electronics", 10, "Dell");
        ecommerceService.addProduct("Smartphone", "Latest smartphone with 5G", 799.99, "Electronics", 15, "Samsung");
        ecommerceService.addProduct("Headphones", "Wireless noise-canceling headphones", 199.99, "Electronics", 20, "Sony");
        ecommerceService.addProduct("T-Shirt", "Comfortable cotton t-shirt", 29.99, "Clothing", 50, "Nike");
        ecommerceService.addProduct("Jeans", "Classic blue denim jeans", 79.99, "Clothing", 30, "Levi's");
        ecommerceService.addProduct("Book", "Java Programming Guide", 49.99, "Books", 25, "Penguin");

        // Add sample users
        ecommerceService.registerUser("john_doe", "password123", "john@email.com", "John Doe", "555-0001", "123 Main St");
        ecommerceService.registerUser("jane_smith", "password456", "jane@email.com", "Jane Smith", "555-0002", "456 Oak Ave");
        
        // Add wallet balance to sample users
        ecommerceService.addWalletBalance(1001, 5000);
        ecommerceService.addWalletBalance(1002, 3000);

        System.out.println("\n✅ Sample data initialized successfully!\n");
    }
}
