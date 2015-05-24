package by.bsu.guglya.library.database.dao;

import by.bsu.guglya.library.bean.User;

abstract public class UserDao extends AbstractDao{
    abstract public boolean checkLogin(String login,String password);
    abstract public User returnUser(String login,String password);
    //abstract public boolean registerClient(String login, String password, int account);
}
