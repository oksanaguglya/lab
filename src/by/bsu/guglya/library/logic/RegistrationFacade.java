package by.bsu.guglya.library.logic;

import by.bsu.guglya.library.database.dao.UserDao;
import by.bsu.guglya.library.database.dao.UserDaoImpl;

public class RegistrationFacade {
    public static boolean registrateClient(String login, String password)
    {
        UserDao userDao = new UserDaoImpl();
        boolean result = userDao.registrateClient(login, password);
        return result;
    }

    public static boolean checkLoginExist(String login){
        UserDao userDao = new UserDaoImpl();
        boolean result = userDao.checkLoginExist(login);
        return result;
    }
}
