package by.bsu.guglya.library.database.dao;

import by.bsu.guglya.library.model.beans.*;
import com.mysql.jdbc.Statement;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CatalogDAO extends AbstractDAO {

    private static final Logger logger = Logger.getLogger(CatalogDAO.class);
    public static final String DELETE_CATALOG_ITEM_BY_ID = "delete from library.catalog where library.catalog.idcatalog=?;";
    public static final String GET_CATALOG_ITEMS_BY_SEARCHTEXT =
            "select catalog.idcatalog, book.idbook, book.title, book.author, book.year, book_type.type, catalog.quantity  from library.catalog " +
                    "join library.book on library.catalog.book = library.book.idbook " +
                    "join library.book_type on library.book.book_type = library.book_type.idbook_type " +
                    "where book.title like ? or book.author like ? " +
                    "order by book.title limit ? offset ?;";
    public static final String GET_CATALOG_ITEMS_BY_SEARCHTEXT_COUNT =
            "select count(*) " +
                    "from library.catalog " +
                    "join library.book on library.catalog.book = library.book.idbook " +
                    "where library.book.title like ? or library.book.author like ?;";
    public static final String GET_ORDERS_BY_IDCATALOG =
            "select library.order.idorder from library.order " +
                    "join library.catalog on library.order.catalog_item=library.catalog.idcatalog " +
                    "where library.catalog.idcatalog=?;";
    public static final String INSERT_BOOK = "insert into library.book (title, author, year, book_type) values (?,?,?,?);";
    public static final String INSERT_CATALOG_ITEM = "insert into library.catalog (book, quantity) values (?,?);";
    public static final String GET_CATALOG_ITEM_BY_BOOK = "select * from library.catalog where book=?;";
    public static final String GET_CATALOG_ITEM_BY_ID = "select catalog.book, catalog.quantity, book.title, book.author, book.year, book_type.type " +
            "from library.catalog join library.book on library.catalog.book=library.book.idbook " +
            "join library.book_type on library.book.book_type = library.book_type.idbook_type " +
            "where idcatalog=?;";
    public static final String GET_IDBOOK_BY_IDCATALOG = "select book from library.catalog where library.catalog.idcatalog=?;";
    public static final String UPDATE_CATALOG_ITEM = "update library.catalog set library.catalog.quantity=? where library.catalog.idcatalog=?;";

    public List<CatalogItem> getCatalogItemsBySearchText(String searchText, int offset, int limit) throws DAOException {
        List<CatalogItem> items = new ArrayList<>(limit);
        getConnection();
        try {
            ps = conn.prepareStatement(GET_CATALOG_ITEMS_BY_SEARCHTEXT);
            ps.setString(1, "%" + searchText + "%");
            ps.setString(2, "%" + searchText + "%");
            ps.setInt(3, limit);
            ps.setInt(4, offset);
            resultSet = ps.executeQuery();
            CatalogItem item = null;
            Book book = null;
            while (resultSet.next()) {
                int idBook = resultSet.getInt("idbook");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                int year = resultSet.getInt("year");
                String bookType = resultSet.getString("type");
                book = new Book(idBook, title, author, year, Book.TypeOfBook.valueOf(bookType.toUpperCase()));
                int idCatalog = resultSet.getInt("idcatalog");
                int quantity = resultSet.getInt("quantity");
                item = new CatalogItem(idCatalog, book, quantity);
                items.add(item);
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DAOException("Error while trying to access the database!");
        } finally {
            closeConnection();
        }
        return items;
    }

    public int getCatalogItemsBySearchTextCount(String searchText) throws DAOException {
        int result = 0;
        getConnection();
        try {
            ps = conn.prepareStatement(GET_CATALOG_ITEMS_BY_SEARCHTEXT_COUNT);
            ps.setString(1, "%" + searchText + "%");
            ps.setString(2, "%" + searchText + "%");
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getInt("count(*)");
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DAOException("Error while trying to access the database!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean delCatalogItemById(int idCatalog) throws DAOException {
        boolean result = false;
        getConnection();
        try {
            ps = conn.prepareStatement(GET_ORDERS_BY_IDCATALOG);
            ps.setInt(1, idCatalog);
            resultSet = ps.executeQuery();
            boolean active = resultSet.first();
            if (active) {
                result = false;
            } else {
                ps = conn.prepareStatement(DELETE_CATALOG_ITEM_BY_ID);
                ps.setInt(1, idCatalog);
                ps.executeUpdate();
                result = true;
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DAOException("Error while trying to access the database!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean addCatalogItem(int idBook, int quantity) throws DAOException {
        boolean result = false;
        getConnection();
        try {
            ps = conn.prepareStatement(INSERT_CATALOG_ITEM);
            ps.setInt(1, idBook);
            ps.setInt(2, quantity);
            ps.executeUpdate();
            result = true;
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DAOException("Error while trying to access the database!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean addNewCatalogItem(String title, String author, int year, Book.TypeOfBook bookType, int quantity) throws DAOException {
        boolean result = false;
        getConnection();
        try {
            ps = conn.prepareStatement(BookDAO.GET_IDBOOK_TYPE);
            ps.setString(1, bookType.toString().toLowerCase());
            resultSet = ps.executeQuery();
            resultSet.first();
            int idBookType = resultSet.getInt("idbook_type");
            try {
                conn.setAutoCommit(false);
                ps = conn.prepareStatement(INSERT_BOOK, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, title);
                ps.setString(2, author);
                ps.setInt(3, year);
                ps.setInt(4, idBookType);
                ps.executeUpdate();
                resultSet = ps.getGeneratedKeys();
                resultSet.next();
                int idBook = resultSet.getInt(1);
                ps = conn.prepareStatement(INSERT_CATALOG_ITEM);
                ps.setInt(1, idBook);
                ps.setInt(2, quantity);
                ps.executeUpdate();
                result = true;
            } catch (SQLException ex) {
                conn.rollback();
                logger.error(ex.getMessage());
                throw new DAOException("Error while trying to access the database!");
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DAOException("Error while trying to access the database!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean checkCatalogItemExist(int idBook) throws DAOException {
        boolean result = false;
        getConnection();
        try {
            ps = conn.prepareStatement(GET_CATALOG_ITEM_BY_BOOK);
            ps.setInt(1, idBook);
            resultSet = ps.executeQuery();
            result = resultSet.first();
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DAOException("Error while trying to access the database!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public CatalogItem getCatalogItem(int idCatalog) throws DAOException {
        CatalogItem catalogItem;
        getConnection();
        try {
            ps = conn.prepareStatement(GET_CATALOG_ITEM_BY_ID);
            ps.setInt(1, idCatalog);
            resultSet = ps.executeQuery();
            resultSet.first();
            int idBook = resultSet.getInt("catalog.book");
            String title = resultSet.getString("book.title");
            String author = resultSet.getString("book.author");
            int year = resultSet.getInt("book.year");
            String bookType = resultSet.getString("book_type.type");
            Book book = new Book(idBook, title, author, year, Book.TypeOfBook.valueOf(bookType.toUpperCase()));
            int quantity = resultSet.getInt("catalog.quantity");
            catalogItem = new CatalogItem(idCatalog, book, quantity);
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DAOException("Error while trying to access the database!");
        } finally {
            closeConnection();
        }
        return catalogItem;
    }

    public boolean changeCatalogItem(int idCatalog, String title, String author, int year, Book.TypeOfBook bookType, int quantity) throws DAOException {
        boolean result = false;
        getConnection();
        try {
            ps = conn.prepareStatement(BookDAO.GET_IDBOOK_TYPE);
            ps.setString(1, bookType.toString().toLowerCase());
            resultSet = ps.executeQuery();
            resultSet.first();
            int idBookType = resultSet.getInt("idbook_type");
            ps = conn.prepareStatement(GET_IDBOOK_BY_IDCATALOG);
            ps.setInt(1, idCatalog);
            resultSet = ps.executeQuery();
            resultSet.first();
            int idBook = resultSet.getInt("book");
            try {
                conn.setAutoCommit(false);
                ps = conn.prepareStatement(BookDAO.UPDATE_BOOK);
                ps.setString(1, title);
                ps.setString(2, author);
                ps.setInt(3, year);
                ps.setInt(4, idBookType);
                ps.setInt(5, idBook);
                ps.executeUpdate();
                ps = conn.prepareStatement(UPDATE_CATALOG_ITEM);
                ps.setInt(1, quantity);
                ps.setInt(2, idCatalog);
                ps.executeUpdate();
                result = true;
            } catch (SQLException ex) {
                conn.rollback();
                logger.error(ex.getMessage());
                throw new DAOException("Error while trying to access the database!");
            } finally {
                conn.setAutoCommit(true);
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
