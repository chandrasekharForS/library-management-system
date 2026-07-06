import java.io.Serializable;
import java.util.*;

/**
 * CartItem class represents an item in the shopping cart
 */
class CartItem implements Serializable {
    private static final long serialVersionUID = 1L;
    private int productId;
    private String productName;
    private double unitPrice;
    private int quantity;

    public CartItem(int productId, String productName, double unitPrice, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    // Getters
    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return unitPrice * quantity;
    }

    // Setters
    public void setQuantity(int quantity) {
        if (quantity > 0) {
            this.quantity = quantity;
        }
    }

    public void increaseQuantity(int amount) {
        if (amount > 0) {
            this.quantity += amount;
        }
    }

    public void decreaseQuantity(int amount) {
        if (amount > 0 && this.quantity > amount) {
            this.quantity -= amount;
        }
    }

    @Override
    public String toString() {
        return String.format("PID: %d | %s | $%.2f x %d = $%.2f",
                productId, productName, unitPrice, quantity, getTotalPrice());
    }
}

/**
 * Cart class represents a shopping cart for a user
 * Implements Serializable for file persistence
 */
public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int userId;
    private List<CartItem> items;

    /**
     * Constructor to initialize a Cart
     */
    public Cart(int userId) {
        this.userId = userId;
        this.items = new ArrayList<>();
    }

    /**
     * Add product to cart
     */
    public void addItem(int productId, String productName, double unitPrice, int quantity) {
        // Check if item already exists
        for (CartItem item : items) {
            if (item.getProductId() == productId) {
                item.increaseQuantity(quantity);
                System.out.println("✅ Item quantity updated in cart");
                return;
            }
        }
        // Add new item
        items.add(new CartItem(productId, productName, unitPrice, quantity));
        System.out.println("✅ Item added to cart");
    }

    /**
     * Remove product from cart
     */
    public boolean removeItem(int productId) {
        for (CartItem item : items) {
            if (item.getProductId() == productId) {
                items.remove(item);
                System.out.println("✅ Item removed from cart");
                return true;
            }
        }
        System.out.println("❌ Item not found in cart");
        return false;
    }

    /**
     * Update quantity of a product in cart
     */
    public boolean updateItemQuantity(int productId, int newQuantity) {
        for (CartItem item : items) {
            if (item.getProductId() == productId) {
                if (newQuantity > 0) {
                    item.setQuantity(newQuantity);
                    System.out.println("✅ Cart updated");
                    return true;
                } else {
                    removeItem(productId);
                    return true;
                }
            }
        }
        System.out.println("❌ Item not found in cart");
        return false;
    }

    /**
     * Get cart item by product ID
     */
    public CartItem getItem(int productId) {
        for (CartItem item : items) {
            if (item.getProductId() == productId) {
                return item;
            }
        }
        return null;
    }

    /**
     * Check if cart contains product
     */
    public boolean containsProduct(int productId) {
        return getItem(productId) != null;
    }

    /**
     * Get total number of items in cart
     */
    public int getTotalItems() {
        return items.stream().mapToInt(CartItem::getQuantity).sum();
    }

    /**
     * Get total price of cart
     */
    public double getTotalPrice() {
        return items.stream().mapToDouble(CartItem::getTotalPrice).sum();
    }

    /**
     * Get number of unique products in cart
     */
    public int getUniqueProductCount() {
        return items.size();
    }

    /**
     * Get all items
     */
    public List<CartItem> getItems() {
        return new ArrayList<>(items);
    }

    /**
     * Clear the cart
     */
    public void clear() {
        items.clear();
        System.out.println("✅ Cart cleared");
    }

    /**
     * Check if cart is empty
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * Display cart contents
     */
    public void displayCart() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("🛒 SHOPPING CART (User ID: " + userId + ")");
        System.out.println("=".repeat(80));
        
        if (items.isEmpty()) {
            System.out.println("Your cart is empty!");
        } else {
            System.out.println(String.format("%-5s | %-30s | %-10s | %-8s | %-12s",
                    "ID", "Product", "Unit Price", "Qty", "Total"));
            System.out.println("-".repeat(80));
            
            for (CartItem item : items) {
                System.out.println(String.format("%-5d | %-30s | $%-9.2f | %-8d | $%.2f",
                        item.getProductId(),
                        item.getProductName().length() > 28 ? 
                            item.getProductName().substring(0, 28) : item.getProductName(),
                        item.getUnitPrice(),
                        item.getQuantity(),
                        item.getTotalPrice()));
            }
            System.out.println("-".repeat(80));
            System.out.println(String.format("%-5s | %-30s | %-10s | %-8s | TOTAL: $%.2f",
                    "", "", "", "", getTotalPrice()));
        }
        System.out.println("=".repeat(80) + "\n");
    }

    @Override
    public String toString() {
        return "Cart{" +
                "userId=" + userId +
                ", items=" + items.size() +
                ", totalPrice=$" + String.format("%.2f", getTotalPrice()) +
                '}';
    }
}
