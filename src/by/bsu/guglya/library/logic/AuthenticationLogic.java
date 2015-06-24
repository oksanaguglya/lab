package by.bsu.guglya.library.logic;

import by.bsu.guglya.library.beans.User;
import by.bsu.guglya.library.database.dao.DAOException;
import by.bsu.guglya.library.database.dao.UserDAO;

public class AuthenticationLogic {

    public static boolean checkUserExist(String login, String password) throws LogicException {
        UserDAO userDAO = new UserDAO();
        boolean result = false;
        try {
            result = userDAO.checkUserExist(login, password);
        } catch (DAOException ex) {
            throw new LogicException(ex.getMessage());
        }
        return result;
    }

    public static User returnUser(String login, String password) throws LogicException{
        UserDAO userDAO = new UserDAO();
        User user = null;
        try {
            user = userDAO.returnUser(login, password);
        } catch (DAOException ex) {
            throw new LogicException(ex.getMessage());
        }
        return user;
    }
}
