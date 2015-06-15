package by.bsu.guglya.library.beans;

public class OrderItem {

    public enum TypeOfOrder{
        NEW, IN_PROCESSING, APPROVED
    }

    private int idUser;
    private int idBook;
    private int quantity;
    private TypeOfOrder type;

    public OrderItem(int idUser, int idBook, int quantity, TypeOfOrder type) {
        this.idUser = idUser;
        this.idBook = idBook;
        this.quantity = quantity;
        this.type = type;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
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
