package by.bsu.guglya.library.beans;

public class OrderAddition extends Order{

    private String login;
    private int qty;

    public OrderAddition(int id, int idUser, Book book, int quantity, TypeOfOrder type, String login){
        super(id, idUser, book, quantity, type);
        this.login = login;
    }

    public OrderAddition(int id, int idUser, Book book, int quantity, TypeOfOrder type, String dateOfOrder, String login){
        super(id, idUser, book, quantity, type, dateOfOrder);
        this.login = login;
    }

    public OrderAddition(int id, int idUser, Book book, int quantity, TypeOfOrder type, String dateOfOrder, String login, int qty){
        super(id, idUser, book, quantity, type, dateOfOrder);
        this.login = login;
        this.qty = qty;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

}
