import java.io.Serializable;

/**
 * Product class represents a product in the e-commerce system
 * Implements Serializable for file persistence
 */
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int productCounter = 2000;
    
    private int productId;
    private String productName;
    private String description;
    private double price;
    private String category;
    private int stockQuantity;
    private String manufacturer;
    private double rating;
    private int reviewCount;

    /**
     * Constructor to initialize a Product
     */
    public Product(String productName, String description, double price, 
                   String category, int stockQuantity, String manufacturer) {
        this.productId = ++productCounter;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.category = category;
        this.stockQuantity = stockQuantity;
        this.manufacturer = manufacturer;
        this.rating = 0.0;
        this.reviewCount = 0;
    }

    // Getters
    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public double getRating() {
        return rating;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    // Setters
    public void setPrice(double price) {
        this.price = price;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Check if product is in stock
     */
    public boolean isInStock() {
        return stockQuantity > 0;
    }

    /**
     * Check if requested quantity is available
     */
    public boolean isAvailable(int quantity) {
        return stockQuantity >= quantity;
    }

    /**
     * Reduce stock when product is sold
     */
    public boolean reduceStock(int quantity) {
        if (isAvailable(quantity)) {
            this.stockQuantity -= quantity;
            return true;
        }
        return false;
    }

    /**
     * Increase stock (e.g., restock)
     */
    public void increaseStock(int quantity) {
        if (quantity > 0) {
            this.stockQuantity += quantity;
        }
    }

    /**
     * Add a review/rating
     */
    public void addReview(double rating) {
        if (rating >= 1.0 && rating <= 5.0) {
            this.rating = ((this.rating * this.reviewCount) + rating) / (this.reviewCount + 1);
            this.reviewCount++;
        }
    }

    /**
     * Display product details
     */
    public void displayProductDetails() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("📦 PRODUCT DETAILS");
        System.out.println("=".repeat(70));
        System.out.println("Product ID: " + productId);
        System.out.println("Name: " + productName);
        System.out.println("Description: " + description);
        System.out.println("Price: $" + String.format("%.2f", price));
        System.out.println("Category: " + category);
        System.out.println("Manufacturer: " + manufacturer);
        System.out.println("Stock Quantity: " + stockQuantity);
        System.out.println("Rating: " + String.format("%.1f", rating) + "/5.0 (" + reviewCount + " reviews)");
        System.out.println("Status: " + (isInStock() ? "IN STOCK" : "OUT OF STOCK"));
        System.out.println("=".repeat(70) + "\n");
    }

    /**
     * Get compact product info
     */
    public String getCompactInfo() {
        return String.format("ID: %d | %s | $%.2f | Stock: %d | Rating: %.1f/5.0", 
                             productId, productName, price, stockQuantity, rating);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        return productId == product.productId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(productId);
    }
}
