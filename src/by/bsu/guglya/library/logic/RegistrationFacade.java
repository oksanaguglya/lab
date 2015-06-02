package by.bsu.guglya.library.logic;

import by.bsu.guglya.library.database.dao.UserDAO;
import by.bsu.guglya.library.database.dao.UserDAOImpl;

public class RegistrationFacade {
    public static boolean registrateClient(String login, String password)
    {
        UserDAO userDao = new UserDAOImpl();
        boolean result = userDao.registrateClient(login, password);
        return result;
    }

    public static boolean checkLoginExist(String login){
        UserDAO userDao = new UserDAOImpl();
        boolean result = userDao.checkLoginExist(login);
        return result;
    }
}
