package by.bsu.guglya.library.logic;

import by.bsu.guglya.library.database.dao.BookDAO;
import by.bsu.guglya.library.database.dao.DAOException;
import by.bsu.guglya.library.model.beans.*;

public class BookLogic {

    public static int checkBookExist(String title, String author, int year, Book.TypeOfBook bookType) throws LogicException {
        BookDAO BookDao = BookDAO.getInstance();
        int result = -1;
        try {
            result = BookDao.checkBookExist(title, author, year, bookType);
        } catch (DAOException ex) {
            throw new LogicException(ex.getMessage());
        }
        return result;
    }

}
