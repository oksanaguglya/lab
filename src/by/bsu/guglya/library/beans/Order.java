package by.bsu.guglya.library.beans;

public class Order {

    public enum TypeOfOrder{
        NEW, IN_PROCESSING, APPROVED, DENIED
    }

    private int id;
    private int idUser;
    private Book book;
    private int quantity;
    private TypeOfOrder type;
    private String dateOfOrder;

    public Order(int id, int idUser, Book book, int quantity, TypeOfOrder type) {
        this.id = id;
        this.idUser = idUser;
        this.book = book;
        this.quantity = quantity;
        this.type = type;

    }
    public Order(int id, int idUser, Book book, int quantity, TypeOfOrder type, String dateOfOrder) {
        this.id = id;
        this.idUser = idUser;
        this.book = book;
        this.quantity = quantity;
        this.type = type;
        this.dateOfOrder = dateOfOrder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
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

    public String getDateOfOrder() {
        return dateOfOrder;
    }

    public void setDateOfOrder(String dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

}
