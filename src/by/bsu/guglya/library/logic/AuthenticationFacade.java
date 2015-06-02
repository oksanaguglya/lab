package by.bsu.guglya.library.logic;

import by.bsu.guglya.library.beans.User;
import by.bsu.guglya.library.database.dao.UserDAO;
import by.bsu.guglya.library.database.dao.UserDAOImpl;

public class AuthenticationFacade {

    public static boolean checkUserExist(String login, String password) {
        UserDAO userDAO = new UserDAOImpl();
        boolean result = userDAO.checkUserExist(login, password);
        return result;
    }

    public static User returnUser(String login, String password) {
        UserDAO userDAO = new UserDAOImpl();
        User user = userDAO.returnUser(login, password);
        return user;
    }
}
