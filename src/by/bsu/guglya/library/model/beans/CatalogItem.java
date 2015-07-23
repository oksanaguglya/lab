package by.bsu.guglya.library.model.beans;

public class CatalogItem {

    /**
     * Catalog item's ID
     */
    private int id;
    /**
     * Catalog item's book
     */
    private Book book;
    /**
     * Book's quantity in library
     */
    private int quantity;

    public CatalogItem(int id, Book book, int quantity) {
        this.id = id;
        this.book = book;
        this.quantity = quantity;
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

}
