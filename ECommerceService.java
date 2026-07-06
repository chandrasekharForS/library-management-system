import java.io.*;
import java.util.*;

/**
 * ECommerceService class handles all e-commerce operations
 * Manages users, products, carts, and orders with file persistence
 */
public class ECommerceService {
    private ArrayList<User> users;
    private ArrayList<Product> products;
    private HashMap<Integer, Cart> userCarts;
    private TreeSet<Order> orders;
    
    private static final String USERS_FILE = "users.ser";
    private static final String PRODUCTS_FILE = "products.ser";
    private static final String ORDERS_FILE = "orders.ser";

    /**
     * Constructor to initialize ECommerceService
     */
    public ECommerceService() {
        this.users = new ArrayList<>();
        this.products = new ArrayList<>();
        this.userCarts = new HashMap<>();
        this.orders = new TreeSet<>();
        loadDataFromFiles();
    }

    // ==================== FILE OPERATIONS ====================

    /**
     * Load data from serialized files
     */
    @SuppressWarnings("unchecked")
    private void loadDataFromFiles() {
        // Load users
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USERS_FILE))) {
            users = (ArrayList<User>) ois.readObject();
            System.out.println("✅ Users loaded from file: " + users.size() + " users");
        } catch (FileNotFoundException e) {
            System.out.println("ℹ️ Users file not found. Starting with empty user list.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("❌ Error loading users: " + e.getMessage());
        }

        // Load products
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PRODUCTS_FILE))) {
            products = (ArrayList<Product>) ois.readObject();
            System.out.println("✅ Products loaded from file: " + products.size() + " products");
        } catch (FileNotFoundException e) {
            System.out.println("ℹ️ Products file not found. Starting with empty product list.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("❌ Error loading products: " + e.getMessage());
        }

        // Load orders
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ORDERS_FILE))) {
            orders = (TreeSet<Order>) ois.readObject();
            System.out.println("✅ Orders loaded from file: " + orders.size() + " orders");
        } catch (FileNotFoundException e) {
            System.out.println("ℹ️ Orders file not found. Starting with empty order list.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("❌ Error loading orders: " + e.getMessage());
        }
    }

    /**
     * Save data to serialized files
     */
    public void saveDataToFiles() {
        // Save users
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_FILE))) {
            oos.writeObject(users);
            System.out.println("✅ Users saved to file");
        } catch (IOException e) {
            System.out.println("❌ Error saving users: " + e.getMessage());
        }

        // Save products
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PRODUCTS_FILE))) {
            oos.writeObject(products);
            System.out.println("✅ Products saved to file");
        } catch (IOException e) {
            System.out.println("❌ Error saving products: " + e.getMessage());
        }

        // Save orders
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ORDERS_FILE))) {
            oos.writeObject(orders);
            System.out.println("✅ Orders saved to file");
        } catch (IOException e) {
            System.out.println("❌ Error saving orders: " + e.getMessage());
        }
    }

    // ==================== USER MANAGEMENT ====================

    /**
     * Register a new user
     */
    public User registerUser(String username, String password, String email, 
                            String fullName, String phoneNumber, String address) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                System.out.println("❌ Username already exists!");
                return null;
            }
        }
        User newUser = new User(username, password, email, fullName, phoneNumber, address);
        users.add(newUser);
        userCarts.put(newUser.getUserId(), new Cart(newUser.getUserId()));
        System.out.println("✅ User registered successfully! User ID: " + newUser.getUserId());
        return newUser;
    }

    /**
     * Login user
     */
    public User loginUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.verifyPassword(password)) {
                System.out.println("✅ Login successful!");
                return user;
            }
        }
        System.out.println("❌ Invalid username or password!");
        return null;
    }

    /**
     * Get user by ID
     */
    public User getUserById(int userId) {
        for (User user : users) {
            if (user.getUserId() == userId) {
                return user;
            }
        }
        return null;
    }

    /**
     * Add balance to user wallet
     */
    public boolean addWalletBalance(int userId, double amount) {
        User user = getUserById(userId);
        if (user != null && amount > 0) {
            user.addWalletBalance(amount);
            System.out.println("✅ Balance added. New balance: $" + String.format("%.2f", user.getWalletBalance()));
            return true;
        }
        return false;
    }

    // ==================== PRODUCT MANAGEMENT ====================

    /**
     * Add product to catalog
     */
    public Product addProduct(String productName, String description, double price,
                             String category, int stockQuantity, String manufacturer) {
        Product product = new Product(productName, description, price, category, stockQuantity, manufacturer);
        products.add(product);
        System.out.println("✅ Product added successfully! Product ID: " + product.getProductId());
        return product;
    }

    /**
     * Get product by ID
     */
    public Product getProductById(int productId) {
        for (Product product : products) {
            if (product.getProductId() == productId) {
                return product;
            }
        }
        return null;
    }

    /**
     * Search products by name
     */
    public ArrayList<Product> searchProductsByName(String name) {
        ArrayList<Product> results = new ArrayList<>();
        String lowerName = name.toLowerCase();
        for (Product product : products) {
            if (product.getProductName().toLowerCase().contains(lowerName)) {
                results.add(product);
            }
        }
        return results;
    }

    /**
     * Get products by category
     */
    public ArrayList<Product> getProductsByCategory(String category) {
        ArrayList<Product> results = new ArrayList<>();
        for (Product product : products) {
            if (product.getCategory().equalsIgnoreCase(category)) {
                results.add(product);
            }
        }
        return results;
    }

    /**
     * Display all products
     */
    public void displayAllProducts() {
        System.out.println("\n" + "=".repeat(90));
        System.out.println("📦 ALL PRODUCTS (" + products.size() + " total)");
        System.out.println("=".repeat(90));
        if (products.isEmpty()) {
            System.out.println("No products available!");
        } else {
            for (Product product : products) {
                System.out.println(product.getCompactInfo());
            }
        }
        System.out.println("=".repeat(90) + "\n");
    }

    // ==================== CART MANAGEMENT ====================

    /**
     * Get or create user cart
     */
    public Cart getCart(int userId) {
        return userCarts.computeIfAbsent(userId, k -> new Cart(userId));
    }

    /**
     * Add item to cart
     */
    public boolean addToCart(int userId, int productId, int quantity) {
        Product product = getProductById(productId);
        if (product == null) {
            System.out.println("❌ Product not found!");
            return false;
        }
        if (!product.isAvailable(quantity)) {
            System.out.println("❌ Insufficient stock! Available: " + product.getStockQuantity());
            return false;
        }
        Cart cart = getCart(userId);
        cart.addItem(productId, product.getProductName(), product.getPrice(), quantity);
        return true;
    }

    /**
     * Remove item from cart
     */
    public boolean removeFromCart(int userId, int productId) {
        Cart cart = getCart(userId);
        return cart.removeItem(productId);
    }

    /**
     * Clear user cart
     */
    public void clearCart(int userId) {
        Cart cart = getCart(userId);
        cart.clear();
    }

    // ==================== ORDER MANAGEMENT ====================

    /**
     * Place an order from cart
     */
    public Order placeOrder(int userId, String paymentMethod, String shippingAddress) {
        User user = getUserById(userId);
        if (user == null) {
            System.out.println("❌ User not found!");
            return null;
        }

        Cart cart = getCart(userId);
        if (cart.isEmpty()) {
            System.out.println("❌ Cart is empty!");
            return null;
        }

        double totalAmount = cart.getTotalPrice();

        // Process payment
        if (!processPayment(user, totalAmount, paymentMethod)) {
            return null;
        }

        // Create order
        List<Order.OrderItem> orderItems = new ArrayList<>();
        for (Cart.CartItem cartItem : cart.getItems()) {
            orderItems.add(new Order.OrderItem(cartItem.getProductId(), cartItem.getProductName(),
                    cartItem.getUnitPrice(), cartItem.getQuantity()));
            // Reduce product stock
            Product product = getProductById(cartItem.getProductId());
            if (product != null) {
                product.reduceStock(cartItem.getQuantity());
            }
        }

        Order order = new Order(userId, orderItems, totalAmount, paymentMethod, shippingAddress);
        orders.add(order);
        cart.clear();

        System.out.println("✅ Order placed successfully! Order ID: " + order.getOrderId());
        return order;
    }

    /**
     * Process payment
     */
    private boolean processPayment(User user, double amount, String paymentMethod) {
        if (paymentMethod.equalsIgnoreCase("wallet")) {
            if (user.deductWalletBalance(amount)) {
                System.out.println("✅ Payment processed from wallet. Amount: $" + String.format("%.2f", amount));
                return true;
            } else {
                System.out.println("❌ Insufficient wallet balance!");
                return false;
            }
        } else if (paymentMethod.equalsIgnoreCase("card") || paymentMethod.equalsIgnoreCase("upi")) {
            System.out.println("✅ Payment processed via " + paymentMethod.toUpperCase() + ". Amount: $" + String.format("%.2f", amount));
            return true;
        }
        System.out.println("❌ Invalid payment method!");
        return false;
    }

    /**
     * Get order by ID
     */
    public Order getOrderById(int orderId) {
        for (Order order : orders) {
            if (order.getOrderId() == orderId) {
                return order;
            }
        }
        return null;
    }

    /**
     * Get user order history
     */
    public ArrayList<Order> getUserOrderHistory(int userId) {
        ArrayList<Order> userOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getUserId() == userId) {
                userOrders.add(order);
            }
        }
        return userOrders;
    }

    /**
     * Cancel order
     */
    public boolean cancelOrder(int orderId) {
        Order order = getOrderById(orderId);
        if (order != null && order.cancelOrder()) {
            System.out.println("✅ Order cancelled successfully!");
            return true;
        }
        System.out.println("❌ Cannot cancel this order!");
        return false;
    }

    /**
     * Update order status
     */
    public boolean updateOrderStatus(int orderId, Order.OrderStatus newStatus) {
        Order order = getOrderById(orderId);
        if (order != null) {
            order.updateStatus(newStatus);
            System.out.println("✅ Order status updated to: " + newStatus.getDisplayName());
            return true;
        }
        return false;
    }

    /**
     * Display all orders
     */
    public void displayAllOrders() {
        System.out.println("\n" + "=".repeat(100));
        System.out.println("📋 ALL ORDERS (" + orders.size() + " total)");
        System.out.println("=".repeat(100));
        if (orders.isEmpty()) {
            System.out.println("No orders found!");
        } else {
            for (Order order : orders) {
                System.out.println(order.getCompactInfo());
            }
        }
        System.out.println("=".repeat(100) + "\n");
    }

    // ==================== UTILITY METHODS ====================

    /**
     * Get total number of users
     */
    public int getTotalUsers() {
        return users.size();
    }

    /**
     * Get total number of products
     */
    public int getTotalProducts() {
        return products.size();
    }

    /**
     * Get total number of orders
     */
    public int getTotalOrders() {
        return orders.size();
    }

    /**
     * Get total revenue from completed orders
     */
    public double getTotalRevenue() {
        double revenue = 0;
        for (Order order : orders) {
            if (order.getStatus() == Order.OrderStatus.DELIVERED) {
                revenue += order.getTotalAmount();
            }
        }
        return revenue;
    }
}
