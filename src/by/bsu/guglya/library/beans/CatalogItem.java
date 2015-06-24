package by.bsu.guglya.library.beans;

public class CatalogItem {

    private int idCatalog;
    private Book book;
    private int quantity;

    public CatalogItem(int idCatalog, Book book, int quantity) {
        this.idCatalog = idCatalog;
        this.book = book;
        this.quantity = quantity;
    }

    public int getIdCatalog() {
        return idCatalog;
    }

    public void setIdCatalog(int idCatalog) {
        this.idCatalog = idCatalog;
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
