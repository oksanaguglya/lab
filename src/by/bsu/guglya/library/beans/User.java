package by.bsu.guglya.library.beans;

public class User {

    public enum TypeOfUser {
        ADMINISTRATOR, READER
    }
    private int idUser;
    private String login;
    private String password;
    private TypeOfUser type;

    public User() {
    }

    public User(int idUser, String login, String password,String type) {
        this.idUser = idUser;
        this.login = login;
        this.password = password;
        this.type = TypeOfUser.valueOf(type);
    }

    public TypeOfUser getType() {
        return type;
    }

    public int getIdUser() {
        return idUser;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setType(String type) {
        this.type = TypeOfUser.valueOf(type);
    }

}
