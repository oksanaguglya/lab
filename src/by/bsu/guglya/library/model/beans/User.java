package by.bsu.guglya.library.model.beans;

public class User {

    /**
     * User's types
     */
    public enum TypeOfUser {
        ADMINISTRATOR, READER
    }

    /**
     * User's ID
     */
    private int id;
    /**
     * User's login
     */
    private String login;
    /**
     * User's password
     */
    private String password;
    /**
     * User's type
     */
    private TypeOfUser type;

    public User(int id, String login, String password, String type) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.type = TypeOfUser.valueOf(type);
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public TypeOfUser getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
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
        return (other instanceof User) && (id != 0)
                ? id == ((User) other).id
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
