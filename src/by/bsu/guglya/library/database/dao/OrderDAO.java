package by.bsu.guglya.library.database.dao;

import by.bsu.guglya.library.beans.BasketItem;
import by.bsu.guglya.library.beans.Book;
import by.bsu.guglya.library.beans.Order;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO extends AbstractDAO {
    private static final Logger LOG = Logger.getLogger(OrderDAO.class);
    public static final String GET_IDORDER_TYPE = "select idorder_type from library.order_type where type=?;";
    public static final String INSERT_ORDER = "insert into library.order (user, book, quantity, state) values (?,?,?,?);";
    public static final String GET_ORDER = "select * from library.order where library.order.book=? and library.order.user=? and library.order.state=?;";
    public static final String UPDATE_ORDER = "update library.order set library.order.quantity=library.order.quantity+? where library.order.book=? and library.order.user=? and library.order.state=?;";
    public static final String REQUEST_BASKET_ITEMS = "select order.idorder, book.title, book.author, book.year, order.quantity, order_type.type from library.order " +
            "join library.book on library.order.book = library.book.idbook " +
            "join library.order_type on library.order.state = library.order_type.idorder_type " +
            "where library.order.user=? " +
            "order by book.title limit ? offset ?;";
    public static final String REQUEST_BASKET_ITEMS_COUNT =
            "select count(*) from library.order where library.order.user=? and library.order.state=?;";


    public boolean addOrder(String idBook, int idUser, int qty) {
        boolean result = false;
        PreparedStatement idTypePS = null;
        PreparedStatement insertOrderPS = null;
        try {
            conn.setAutoCommit(false);
            idTypePS = conn.prepareStatement(GET_IDORDER_TYPE);
            idTypePS.setString(1, Order.TypeOfOrder.NEW.toString());
            insertOrderPS = conn.prepareStatement(INSERT_ORDER);
            try {
                ResultSet resultSet = idTypePS.executeQuery();
                resultSet.next();
                int idOrderTypeProc = resultSet.getInt("idorder_type");
                insertOrderPS.setInt(1, idUser);
                insertOrderPS.setString(2, idBook);
                insertOrderPS.setInt(3, qty);
                insertOrderPS.setInt(4, idOrderTypeProc);
                insertOrderPS.executeUpdate();
                conn.commit();
                result = true;
            } catch (SQLException ex) {
                conn.rollback();
                LOG.error(ex.getMessage());
            } finally {
                if (insertOrderPS != null) {
                    insertOrderPS.close();
                }
                if (idTypePS != null) {
                    idTypePS.close();
                }
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage());
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean orderExist(String idBook, int idUser, int state) {
        boolean result = false;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(GET_ORDER);
            ps.setString(1, idBook);
            ps.setInt(2, idUser);
            ps.setInt(3, state);
            ResultSet resultSet = ps.executeQuery();
            result = resultSet.first();
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

    public boolean addQtyToOrder(String idBook, int idUser, int qty, int state) {
        boolean result = false;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(UPDATE_ORDER);
            ps.setInt(1, qty);
            ps.setString(2, idBook);
            ps.setInt(3, idUser);
            ps.setInt(4, state);
            ps.executeUpdate();
            result = true;
        }
        catch ( SQLException ex)
        {
            LOG.error(ex.getMessage());
        }
        finally {
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

    public List<BasketItem> getItems(int idUser, int offset, int limit) {
        List<BasketItem> items = new ArrayList<BasketItem>(limit);
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(REQUEST_BASKET_ITEMS);
            ps.setInt(1, idUser);
            ps.setInt(2, limit);
            ps.setInt(3, offset);
            ResultSet resultSet = ps.executeQuery();
            BasketItem item = null;
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
                String orderType = resultSet.getString("type");
                item = new BasketItem(idOrder, book, quantity, Order.TypeOfOrder.valueOf(orderType));
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

