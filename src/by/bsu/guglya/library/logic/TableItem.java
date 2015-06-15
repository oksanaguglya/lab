package by.bsu.guglya.library.logic;

import by.bsu.guglya.library.beans.Book;

public class TableItem {

    private int id;
    private Book book;
    private int quantity;

    public TableItem(int id, Book book, int quantity) {
        this.id = id;
        this.book = book;
        this.quantity = quantity;
    }

    public int getId() { return id; }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
