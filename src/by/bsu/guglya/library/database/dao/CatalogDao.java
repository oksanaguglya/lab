package by.bsu.guglya.library.database.dao;

import by.bsu.guglya.library.beans.Book;
import by.bsu.guglya.library.logic.TableItem;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CatalogDAO extends AbstractDAO {

    private static final Logger LOG = Logger.getLogger(CatalogDAO.class);
    public static final String REQUEST_CATALOG_ITEMS =
            "select catalog.idcatalog, book.title, book.author, book.year, catalog.quantity, book_type.type from library.book " +
            "join library.catalog on library.catalog.book = library.book.idbook " +
            "join library.book_type on library.book.book_type = library.book_type.idbook_type " +
            "where book.title like ? or book.author like ? " +
            "order by book.title limit ? offset ?;";
    public static final String REQUEST_CATALOG_ITEMS_COUNT =
            "select count(*) " +
            "from library.book " +
            "join library.catalog on library.catalog.book = library.book.idbook " +
            "where library.book.title like ? or library.book.author like ?;";

    public List<TableItem> getItems(String searchText, int offset, int limit) {
        List<TableItem> items = new ArrayList<TableItem>(limit);
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(REQUEST_CATALOG_ITEMS);
            ps.setString(1, "%" + searchText + "%");
            ps.setString(2, "%" + searchText + "%");
            ps.setInt(3, limit);
            ps.setInt(4, offset);
            ResultSet resultSet = ps.executeQuery();
            TableItem item = null;
            Book book = null;
            while (resultSet.next()) {
                int idCatalog = resultSet.getInt("idcatalog");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                int year = resultSet.getInt("year");
                String bookType = resultSet.getString("type");
                book = new Book(title, author, year, Book.TypeOfBook.valueOf(bookType));
                int quantity = resultSet.getInt("quantity");
                item = new TableItem(idCatalog, book, quantity);
                items.add(item);
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    LOG.error(ex.getMessage());
                }
            }
            closeConnection();
        }
        return items;
    }

    public int getCatalogItemsCount(String searchText) {
        PreparedStatement ps = null;
        int result = 0;
        try {
            ps = conn.prepareStatement(REQUEST_CATALOG_ITEMS_COUNT);
            ps.setString(1, "%" + searchText + "%");
            ps.setString(2, "%" + searchText + "%");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getInt("count(*)");
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    LOG.error(ex.getMessage());
                }
            }
            closeConnection();
        }
        return result;
    }
}
