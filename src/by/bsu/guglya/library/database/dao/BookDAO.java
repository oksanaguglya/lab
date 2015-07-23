package by.bsu.guglya.library.database.dao;

import by.bsu.guglya.library.model.beans.*;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.concurrent.locks.ReentrantLock;

public class BookDAO extends AbstractDAO {

    private static final Logger logger = Logger.getLogger(BookDAO.class);
    public static final String GET_IDBOOK_TYPE = "select idbook_type from library.book_type where type=?;";
    public static final String GET_BOOK = "select idbook from library.book " +
            "join library.book_type on library.book.book_type = library.book_type.idbook_type " +
            "where title=? and author=? and year=? and book_type=?;";
    public static final String UPDATE_BOOK = "update library.book set library.book.title=?, library.book.author=?, library.book.year=?, library.book.book_type=? where library.book.idbook=?;";
    /**
     * This is a lock
     */
    private static ReentrantLock lock = new ReentrantLock();
    /**
     * This is a BookDAO instance
     */
    private static BookDAO instance;
    /**
     * This is a constructor
     */
    private BookDAO(){
    }
    /**
     * This method returns a BookDAO instance or call constructor to create it
     * @return a BookDAO
     */
    public static BookDAO getInstance(){
        try {
            lock.lock();
            if (instance == null) {
                instance = new BookDAO();
            }
        } finally {
            lock.unlock();
        }
        return instance;
    }

    public int checkBookExist(String title, String author, int year, Book.TypeOfBook bookType) throws DAOException {
        int result = -1;
        getConnection();
        try {
            ps = conn.prepareStatement(GET_IDBOOK_TYPE);
            ps.setString(1, bookType.toString().toLowerCase());
            resultSet = ps.executeQuery();
            resultSet.first();
            int idBookType = resultSet.getInt("idbook_type");
            ps = conn.prepareStatement(GET_BOOK);
            ps.setString(1, title);
            ps.setString(2, author);
            ps.setInt(3, year);
            ps.setInt(4, idBookType);
            resultSet = ps.executeQuery();
            resultSet.last();
            int size = resultSet.getRow();
            if(size != 0){
                resultSet.first();
                result = resultSet.getInt("idbook");
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DAOException("Error while trying to access the database!");
        } finally {
            closeConnection();
        }
        return result;
    }

}
