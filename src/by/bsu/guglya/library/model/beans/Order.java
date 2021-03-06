package by.bsu.guglya.library.model.beans;

public class Order {

    /**
     * Order's types
     */
    public enum TypeOfOrder{
        NEW, IN_PROCESSING, APPROVED, DENIED, RETURNED
    }

    /**
     * Order's ID
     */
    private int id;
    /**
     * Order's user's ID
     */
    private int idUser;
    /**
     * User's name
     */
    private String userName;
    /**
     * Order's catalog item
     */
    private CatalogItem catalogItem;
    /**
     * Orders quantity
     */
    private int quantity;
    /**
     * Order's type
     */
    private TypeOfOrder type;
    /**
     * Order's date
     */
    private String dateOfOrder;

    public Order(int id, int idUser, CatalogItem catalogItem, int quantity, TypeOfOrder type) {
        this.id = id;
        this.idUser = idUser;
        this.catalogItem = catalogItem;
        this.quantity = quantity;
        this.type = type;
    }

    public Order(int id, int idUser, String userName, CatalogItem catalogItem, int quantity, TypeOfOrder type) {
        this.id = id;
        this.idUser = idUser;
        this.userName = userName;
        this.catalogItem = catalogItem;
        this.quantity = quantity;
        this.type = type;
    }

    public Order(int id, int idUser, CatalogItem catalogItem, int quantity, TypeOfOrder type, String dateOfOrder) {
        this.id = id;
        this.idUser = idUser;
        this.catalogItem = catalogItem;
        this.quantity = quantity;
        this.type = type;
        this.dateOfOrder = dateOfOrder;
    }

    public Order(int id, int idUser, String userName, CatalogItem catalogItem, int quantity, TypeOfOrder type, String dateOfOrder) {
        this.id = id;
        this.idUser = idUser;
        this.userName = userName;
        this.catalogItem = catalogItem;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public CatalogItem getCatalogItem() {
        return catalogItem;
    }

    public void setCatalogItem(CatalogItem catalogItem) {
        this.catalogItem = catalogItem;
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
