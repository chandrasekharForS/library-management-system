import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Order class represents a customer order in the e-commerce system
 * Implements Serializable for file persistence
 */
public class Order implements Serializable, Comparable<Order> {
    private static final long serialVersionUID = 1L;
    private static int orderCounter = 5000;
    
    private int orderId;
    private int userId;
    private List<OrderItem> orderItems;
    private double totalAmount;
    private OrderStatus status;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;
    private String paymentMethod;
    private String shippingAddress;

    /**
     * OrderStatus enum
     */
    public enum OrderStatus {
        PENDING("Pending"),
        CONFIRMED("Confirmed"),
        SHIPPED("Shipped"),
        DELIVERED("Delivered"),
        CANCELLED("Cancelled");

        private final String displayName;

        OrderStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    /**
     * OrderItem class for items in an order
     */
    public static class OrderItem implements Serializable {
        private static final long serialVersionUID = 1L;
        private int productId;
        private String productName;
        private double unitPrice;
        private int quantity;

        public OrderItem(int productId, String productName, double unitPrice, int quantity) {
            this.productId = productId;
            this.productName = productName;
            this.unitPrice = unitPrice;
            this.quantity = quantity;
        }

        // Getters
        public int getProductId() { return productId; }
        public String getProductName() { return productName; }
        public double getUnitPrice() { return unitPrice; }
        public int getQuantity() { return quantity; }
        public double getTotalPrice() { return unitPrice * quantity; }

        @Override
        public String toString() {
            return String.format("%s (x%d) - $%.2f", productName, quantity, getTotalPrice());
        }
    }

    /**
     * Constructor to initialize an Order
     */
    public Order(int userId, List<OrderItem> orderItems, double totalAmount, 
                 String paymentMethod, String shippingAddress) {
        this.orderId = ++orderCounter;
        this.userId = userId;
        this.orderItems = new ArrayList<>(orderItems);
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.shippingAddress = shippingAddress;
        this.status = OrderStatus.PENDING;
        this.orderDate = LocalDateTime.now();
        this.deliveryDate = null;
    }

    // Getters
    public int getOrderId() {
        return orderId;
    }

    public int getUserId() {
        return userId;
    }

    public List<OrderItem> getOrderItems() {
        return new ArrayList<>(orderItems);
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    /**
     * Update order status
     */
    public void updateStatus(OrderStatus newStatus) {
        this.status = newStatus;
        if (newStatus == OrderStatus.DELIVERED) {
            this.deliveryDate = LocalDateTime.now();
        }
    }

    /**
     * Cancel order (only if pending or confirmed)
     */
    public boolean cancelOrder() {
        if (status == OrderStatus.PENDING || status == OrderStatus.CONFIRMED) {
            status = OrderStatus.CANCELLED;
            return true;
        }
        return false;
    }

    /**
     * Get number of items in order
     */
    public int getItemCount() {
        return orderItems.stream().mapToInt(OrderItem::getQuantity).sum();
    }

    /**
     * Display order details
     */
    public void displayOrderDetails() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("📦 ORDER DETAILS");
        System.out.println("=".repeat(80));
        System.out.println("Order ID: " + orderId);
        System.out.println("User ID: " + userId);
        System.out.println("Order Date: " + orderDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println("Status: " + status.getDisplayName());
        if (deliveryDate != null) {
            System.out.println("Delivery Date: " + deliveryDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        System.out.println("Shipping Address: " + shippingAddress);
        System.out.println("Payment Method: " + paymentMethod);
        System.out.println("\nItems:");
        System.out.println("-".repeat(80));
        for (OrderItem item : orderItems) {
            System.out.println("  " + item);
        }
        System.out.println("-".repeat(80));
        System.out.println("Total Amount: $" + String.format("%.2f", totalAmount));
        System.out.println("=".repeat(80) + "\n");
    }

    /**
     * Get compact order info
     */
    public String getCompactInfo() {
        return String.format("Order #%d | User: %d | Amount: $%.2f | Status: %s | Date: %s",
                orderId, userId, totalAmount, status.getDisplayName(),
                orderDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

    @Override
    public int compareTo(Order other) {
        // Sort orders by order date in descending order (newest first)
        return other.getOrderDate().compareTo(this.getOrderDate());
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", totalAmount=" + totalAmount +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Order order = (Order) obj;
        return orderId == order.orderId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(orderId);
    }
}
