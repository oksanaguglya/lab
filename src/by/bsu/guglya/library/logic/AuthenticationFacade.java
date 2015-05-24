package by.bsu.guglya.library.logic;

import by.bsu.guglya.library.beans.User;
import by.bsu.guglya.library.database.dao.UserDao;
import by.bsu.guglya.library.database.dao.UserDaoImpl;

public class AuthenticationFacade {

    public static boolean checkLogin(String login, String password) {
        UserDao userDAO = new UserDaoImpl();
        boolean result = userDAO.checkLogin(login, password);
        return result;
    }

    public static User returnUser(String login, String password) {
        UserDao userDAO = new UserDaoImpl();
        User user = userDAO.returnUser(login, password);
        return user;
    }
}
