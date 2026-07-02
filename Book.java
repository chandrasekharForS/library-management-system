/**
 * Book class represents a book in the library
 * Properties: ISBN, Title, Author, Publisher, Publication Year, Category, Availability, Number of copies
 */
public class Book {
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private int publicationYear;
    private String category;
    private int availableCopies;
    private int totalCopies;

    /**
     * Constructor to initialize a Book with all properties
     */
    public Book(String isbn, String title, String author, String publisher, 
                int publicationYear, String category, int totalCopies) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.category = category;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
    }

    // Getters
    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public String getCategory() {
        return category;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
        // If adding new copies, update available copies proportionally
        if (totalCopies > this.totalCopies) {
            this.availableCopies += (totalCopies - this.totalCopies);
        }
    }

    /**
     * Check if the book is available for borrowing
     */
    public boolean isAvailable() {
        return availableCopies > 0;
    }

    /**
     * Borrow a copy of the book (decrease available count)
     */
    public boolean borrowBook() {
        if (isAvailable()) {
            availableCopies--;
            return true;
        }
        return false;
    }

    /**
     * Return a copy of the book (increase available count)
     */
    public boolean returnBook() {
        if (availableCopies < totalCopies) {
            availableCopies++;
            return true;
        }
        return false;
    }

    /**
     * Display detailed book information
     */
    public void displayBookDetails() {
        System.out.println("=".repeat(50));
        System.out.println("ISBN: " + isbn);
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Publisher: " + publisher);
        System.out.println("Publication Year: " + publicationYear);
        System.out.println("Category: " + category);
        System.out.println("Total Copies: " + totalCopies);
        System.out.println("Available Copies: " + availableCopies);
        System.out.println("Status: " + (isAvailable() ? "AVAILABLE" : "NOT AVAILABLE"));
        System.out.println("=".repeat(50));
    }

    /**
     * Display book information in a compact format
     */
    public String getCompactInfo() {
        return String.format("ISBN: %s | Title: %s | Author: %s | Available: %d/%d", 
                             isbn, title, author, availableCopies, totalCopies);
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", availableCopies=" + availableCopies +
                ", totalCopies=" + totalCopies +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Book book = (Book) obj;
        return isbn.equals(book.isbn);
    }

    @Override
    public int hashCode() {
        return isbn.hashCode();
    }
}
