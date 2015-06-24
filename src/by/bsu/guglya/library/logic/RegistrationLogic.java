package by.bsu.guglya.library.logic;

import by.bsu.guglya.library.database.dao.DAOException;
import by.bsu.guglya.library.database.dao.UserDAO;

public class RegistrationLogic {

    public static boolean registrateClient(String login, String password) throws LogicException {
        UserDAO userDao = new UserDAO();
        boolean result = false;
        try{
            result = userDao.registrateClient(login, password);
        }catch(DAOException ex){
            throw new LogicException(ex.getMessage());
        }
        return result;
    }

    public static boolean checkLoginExist(String login)throws LogicException {
        UserDAO userDao = new UserDAO();
        boolean result = false;
        try{
            result = userDao.checkLoginExist(login);
        }catch(DAOException ex){
            throw new LogicException(ex.getMessage());
        }
        return result;
    }
}
