package by.bsu.guglya.library.logic;

import by.bsu.guglya.library.beans.User;
import by.bsu.guglya.library.database.dao.UserDAO;

public class AuthenticationLogic {

    public static boolean checkUserExist(String login, String password) {
        UserDAO userDAO = new UserDAO();
        boolean result = userDAO.checkUserExist(login, password);
        return result;
    }

    public static User returnUser(String login, String password) {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.returnUser(login, password);
        return user;
    }
}
