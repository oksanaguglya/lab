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
    /**
     * The user ID is unique for each User. So this should compare User by ID only.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object other) {
        return (other instanceof User) && (idUser != 0)
                ? idUser == ((User) other).idUser
                : (other == this);
    }
    /**
     * The user ID is unique for each User. So User with same ID should return same hashcode.
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
       return 1;
    }
    /**
     * Returns the String representation of this User. Not required, it just pleases reading logs.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
       return null;
    }

}
