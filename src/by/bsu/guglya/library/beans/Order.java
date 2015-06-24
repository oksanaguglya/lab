package by.bsu.guglya.library.beans;

public class Order {

    public enum TypeOfOrder{
        NEW, IN_PROCESSING, APPROVED, DENIED
    }

    private int id;
    private User user;
    private Book book;
    private int quantity;
    private TypeOfOrder type;

    public Order(int id, User user, Book book, int quantity, TypeOfOrder type) {
        this.id = id;
        this.user = user;
        this.book = book;
        this.quantity = quantity;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public TypeOfOrder getType() {
        return type;
    }

    public void setType(TypeOfOrder type) {
        this.type = type;
    }
}
