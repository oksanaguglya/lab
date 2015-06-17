package by.bsu.guglya.library.logic;

import by.bsu.guglya.library.beans.Book;

public class TableItem {

    private int id;
    private Book book;
    private int quantity;
    private String state;

    public TableItem(int id, Book book, int quantity) {
        this.id = id;
        this.book = book;
        this.quantity = quantity;
    }

    public TableItem(int id, Book book, int quantity, String state) {
        this.id = id;
        this.book = book;
        this.quantity = quantity;
        this.state = state;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
