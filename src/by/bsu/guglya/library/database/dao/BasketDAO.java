package by.bsu.guglya.library.database.dao;

import by.bsu.guglya.library.beans.Book;
import by.bsu.guglya.library.logic.TableItem;
import by.bsu.guglya.library.beans.Order;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BasketDAO extends AbstractDAO {

    private static final Logger LOG = Logger.getLogger(OrderDAO.class);
    public static final String GET_IDORDER_TYPE = "select idorder_type from library.order_type where type=?;";
    public static final String REQUEST_BASKET_ITEMS = "select order.idorder, book.title, book.author, book.year, order.quantity from library.order " +
            "join library.book on library.order.book = library.book.idbook " +
            "join library.order_type on library.order.state = library.order_type.idorder_type " +
            "where library.order.user=? " +
            "order by book.title limit ? offset ?;";
    public static final String REQUEST_BASKET_ITEMS_COUNT =
            "select count(*) from library.order where library.order.user=? and library.order.state=?;";

    public List<TableItem> getItems(int idUser, int offset, int limit) {
        List<TableItem> items = new ArrayList<TableItem>(limit);
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(REQUEST_BASKET_ITEMS);
            ps.setInt(1, idUser);
            ps.setInt(2, limit);
            ps.setInt(3, offset);
            ResultSet resultSet = ps.executeQuery();
            TableItem item = null;
            Book book = null;
            while (resultSet.next()) {
                int idOrder = resultSet.getInt("idorder");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                int year = resultSet.getInt("year");
                /*String bookType = resultSet.getString("type");*/
                /*book = new Book(title, author, year, Book.TypeOfBook.valueOf(bookType));*/
                book = new Book(title, author, year, Book.TypeOfBook.LIBRARY_CARD);
                int quantity = resultSet.getInt("quantity");
                item = new TableItem(idOrder, book, quantity);
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

    public int getBasketItemsCount(int idUser) {
        PreparedStatement idTypePS = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        int result = 0;
        try {
            idTypePS = conn.prepareStatement(GET_IDORDER_TYPE);
            idTypePS.setString(1, Order.TypeOfOrder.NEW.toString());
            resultSet = idTypePS.executeQuery();
            resultSet.next();
            int idOrderTypeProc = resultSet.getInt("idorder_type");
            ps = conn.prepareStatement(REQUEST_BASKET_ITEMS_COUNT);
            ps.setInt(1, idUser);
            ps.setInt(2, idOrderTypeProc);
            resultSet = ps.executeQuery();
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
