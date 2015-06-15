package by.bsu.guglya.library.beans;


public class BasketItem {

    private int id;
    private Book book;
    private int quantity;
    private Order.TypeOfOrder type;

    public BasketItem(int id, Book book, int quantity, Order.TypeOfOrder type) {
        this.id = id;
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

    public Order.TypeOfOrder getType() {
        return type;
    }

    public void setType(Order.TypeOfOrder type) {
        this.type = type;
    }
}
