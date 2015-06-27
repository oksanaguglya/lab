package by.bsu.guglya.library.database.dao;

import by.bsu.guglya.library.beans.Book;
import by.bsu.guglya.library.beans.CatalogItem;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CatalogDAO extends AbstractDAO {

    private static final Logger logger = Logger.getLogger(CatalogDAO.class);
    public static final String DELETE_CATALOG_ITEM_BY_ID = "delete from library.catalog where library.catalog.idcatalog=?;";
    public static final String GET_CATALOG_ITEMS =
            "select catalog.idcatalog, book.idbook, book.title, book.author, book.year, catalog.quantity, book_type.type from library.book " +
            "join library.catalog on library.catalog.book = library.book.idbook " +
            "join library.book_type on library.book.book_type = library.book_type.idbook_type " +
            "where book.title like ? or book.author like ? " +
            "order by book.title limit ? offset ?;";
    public static final String GET_CATALOG_ITEMS_COUNT =
            "select count(*) " +
            "from library.book " +
            "join library.catalog on library.catalog.book = library.book.idbook " +
            "where library.book.title like ? or library.book.author like ?;";
    public static final String UPDATE_CATALOG_QTY = "update library.catalog " +
            "join library.order on library.catalog.book=library.order.book " +
            "set library.catalog.quantity=library.catalog.quantity-? where library.order.idorder=?;";
    public static final String GET_CATALOG_ITEM_QTY = "select catalog.quantity from library.catalog "+
    "join library.order on library.catalog.book=library.order.book " +
    "where library.order.idorder=?;";

    public List<CatalogItem> getCatalogItems(String searchText, int offset, int limit) throws DAOException{
        List<CatalogItem> items = new ArrayList<>(limit);
        getConnection();
        try {
            ps = conn.prepareStatement(GET_CATALOG_ITEMS);
            ps.setString(1, "%" + searchText + "%");
            ps.setString(2, "%" + searchText + "%");
            ps.setInt(3, limit);
            ps.setInt(4, offset);
            resultSet = ps.executeQuery();
            CatalogItem item = null;
            Book book = null;
            while (resultSet.next()) {
                int idCatalog = resultSet.getInt("idcatalog");
                int idBook = resultSet.getInt("idbook");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                int year = resultSet.getInt("year");
                String bookType = resultSet.getString("type");
                book = new Book(idBook, title, author, year, Book.TypeOfBook.valueOf(bookType.toUpperCase()));
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

    public int getCatalogItemsCount(String searchText) throws DAOException{
        int result = 0;
        getConnection();
        try {
            ps = conn.prepareStatement(GET_CATALOG_ITEMS_COUNT);
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

    public boolean delCatalogItem(int idCatalog) throws DAOException {
        boolean result = false;
        getConnection();
        try {
            ps = conn.prepareStatement(DELETE_CATALOG_ITEM_BY_ID);
            ps.setInt(1, idCatalog);
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

    public boolean subCatalogItemQty(int idOrder, int qty) throws DAOException {
        boolean result = false;
        getConnection();
        try {
            ps = conn.prepareStatement(GET_CATALOG_ITEM_QTY);
            ps.setInt(1, idOrder);
            resultSet = ps.executeQuery();
            resultSet.next();
            int quantity = resultSet.getInt("catalog.quantity");
            if(quantity > 0){
                ps = conn.prepareStatement(UPDATE_CATALOG_QTY);
                ps.setInt(1, qty);
                ps.setInt(2, idOrder);
                ps.executeUpdate();
                result = true;
            }else{
                result = false;
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
