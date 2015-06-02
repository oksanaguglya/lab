package by.bsu.guglya.library.database.dao;

import by.bsu.guglya.library.beans.User;

abstract public class UserDAO extends AbstractDAO {
    abstract public boolean checkUserExist(String login, String password);
    abstract public User returnUser(String login,String password);
    abstract public boolean registrateClient(String login, String password);
    abstract public boolean checkLoginExist(String login);
}
