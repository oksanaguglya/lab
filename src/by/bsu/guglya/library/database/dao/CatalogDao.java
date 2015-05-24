package by.bsu.guglya.library.database.dao;

import by.bsu.guglya.library.beans.Book;
import by.bsu.guglya.library.beans.CatalogItem;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CatalogDao extends AbstractDao {

    private static final Logger LOG = Logger.getLogger(CatalogDao.class);
    public static final String REQUEST_CATALOG_ITEMS =
            "select book.title, book.author, book.year, catalog.quantity, book_type.type " +
            "from library.book " +
            "join library.catalog on library.catalog.book = library.book.idbook " +
            "join library.book_type on library.book.book_type = library.book_type.idbook_type limit ? offset ?;";
    public static final String REQUEST_CATALOG_ITEMS_COUNT = "select count(*) from library.catalog";

    public List<CatalogItem> getItems(int offset, int limit) {
        List<CatalogItem> items = new ArrayList<CatalogItem>(limit);
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(REQUEST_CATALOG_ITEMS);
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            ResultSet resultSet = ps.executeQuery();
            CatalogItem item = null;
            Book book = null;
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                int year = resultSet.getInt("year");
                String bookType = resultSet.getString("type");
                book = new Book(title, author, year, Book.TypeOfBook.valueOf(bookType));
                int quantity = resultSet.getInt("quantity");
                item = new CatalogItem(book, quantity);
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
        }
        return items;
    }

    public int getCatalogItemsCount() {
        PreparedStatement ps = null;
        int result = 0;
        try {
            ps = conn.prepareStatement(REQUEST_CATALOG_ITEMS_COUNT);
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
        }
        return result;
    }
}
