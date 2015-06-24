package by.bsu.guglya.library.database.dao;

import by.bsu.guglya.library.beans.Book;
import by.bsu.guglya.library.beans.Order;
import by.bsu.guglya.library.logic.TableItem;
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
    public static final String DELETE_ORDER = "delete from library.order where library.order.idorder=? and library.order.user=? and library.order.state=?;";
    public static final String MAKE_ORDER = "update library.order set library.order.state=? where library.order.user=? and library.order.state=?;";
    public static final String REQUEST_ORDER_ITEMS = "select order.idorder, book.title, book.author, book.year, order.quantity, book_type.type, order_type.type from library.order " +
            "join library.book on library.order.book = library.book.idbook " +
            "join library.book_type on library.book.book_type=library.book_type.idbook_type " +
            "join library.order_type on library.order.state = library.order_type.idorder_type " +
            "where (book.title like ? or book.author like ?) and (library.order.user=? and library.order.state!=?) " +
            "order by book.title limit ? offset ?;";
    public static final String REQUEST_ORDERS_ITEMS_COUNT =
            "select count(*) from library.order " +
            "join library.book on library.order.book = library.book.idbook " +
            "where (book.title like ? or book.author like ?) and library.order.user=? and library.order.state!=?;";
    public static final String REQUEST_NEW_ORDER_ITEMS = "select order.idorder, book.title, book.author, book.year, order.quantity, book_type.type, order_type.type, user.login from library.order " +
            "join library.book on library.order.book = library.book.idbook " +
            "join library.book_type on library.book.book_type=library.book_type.idbook_type " +
            "join library.order_type on library.order.state = library.order_type.idorder_type " +
            "join library.user on library.order.user = library.user.iduser " +
            "where library.order.state=? " +
            "order by book.title limit ? offset ?;";
    public static final String REQUEST_NEW_ORDERS_ITEMS_COUNT =
            "select count(*) from library.order where library.order.state=?;";
    public static final String REQUEST_LOGIN_ORDER_ITEMS = "select order.idorder, book.title, book.author, book.year, order.quantity, book_type.type, order_type.type, user.login from library.order " +
            "join library.book on library.order.book = library.book.idbook " +
            "join library.book_type on library.book.book_type=library.book_type.idbook_type " +
            "join library.order_type on library.order.state = library.order_type.idorder_type " +
            "join library.user on library.order.user = library.user.iduser " +
            "where (user.login like ?) and library.order.state!=? " +
            "order by book.title limit ? offset ?;";
    public static final String REQUEST_LOGIN_ORDERS_ITEMS_COUNT =
            "select count(*) from library.order join library.user on library.order.user = library.user.iduser where (user.login like ?) and library.order.state!=?;";

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
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                LOG.error(ex.getMessage());
            }
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

    public boolean delOrder(int idUser, int idOrder) {
        boolean result = false;
        PreparedStatement idTypePS = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            conn.setAutoCommit(false);
            idTypePS = conn.prepareStatement(GET_IDORDER_TYPE);
            idTypePS.setString(1, Order.TypeOfOrder.NEW.toString());
            try {
                resultSet = idTypePS.executeQuery();
                resultSet.next();
                int idOrderTypeNew = resultSet.getInt("idorder_type");
                ps = conn.prepareStatement(DELETE_ORDER);
                ps.setInt(1, idOrder);
                ps.setInt(2, idUser);
                ps.setInt(3, idOrderTypeNew);
                ps.executeUpdate();
                conn.commit();
                result = true;
            } catch (SQLException ex) {
                conn.rollback();
                LOG.error(ex.getMessage());
            } finally {
                if (ps != null) {
                    ps.close();
                }
                if (idTypePS != null) {
                    idTypePS.close();
                }
            }
        } catch(SQLException ex){
            LOG.error(ex.getMessage());
        } finally{
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                LOG.error(ex.getMessage());
            }
            closeConnection();
        }
        return result;
    }

    public boolean makeOrder(int idUser) {
        boolean result = false;
        PreparedStatement idTypePSNew = null;
        PreparedStatement idTypePSProc = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            ps = conn.prepareStatement(MAKE_ORDER);
            idTypePSNew = conn.prepareStatement(GET_IDORDER_TYPE);
            idTypePSProc = conn.prepareStatement(GET_IDORDER_TYPE);
            idTypePSNew.setString(1, Order.TypeOfOrder.NEW.toString());
            idTypePSProc.setString(1, Order.TypeOfOrder.IN_PROCESSING.toString());
            try{
            resultSet = idTypePSNew.executeQuery();
            resultSet.next();
            int idOrderTypeNew = resultSet.getInt("idorder_type");
            resultSet = idTypePSProc.executeQuery();
            resultSet.next();
            int idOrderTypeProc = resultSet.getInt("idorder_type");
            ps.setInt(1, idOrderTypeProc);
            ps.setInt(2, idUser);
            ps.setInt(3, idOrderTypeNew);
            ps.executeUpdate();
            result = true;
            } catch (SQLException ex) {
                conn.rollback();
                LOG.error(ex.getMessage());
            } finally {
                if (ps != null) {
                    ps.close();
                }
                if (idTypePSNew != null) {
                    idTypePSNew.close();
                }
                if (idTypePSProc != null) {
                    idTypePSProc.close();
                }
            }
        } catch(SQLException ex){
            LOG.error(ex.getMessage());
        } finally{
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                LOG.error(ex.getMessage());
            }
            closeConnection();
        }
        return result;
    }

    public List<TableItem> getOrderItems(String searchText, int idUser, int offset, int limit) {
        List<TableItem> items = new ArrayList<TableItem>(limit);
        PreparedStatement idTypePS = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            idTypePS = conn.prepareStatement(GET_IDORDER_TYPE);
            idTypePS.setString(1, Order.TypeOfOrder.NEW.toString());
            resultSet = idTypePS.executeQuery();
            resultSet.next();
            int idOrderTypeNew = resultSet.getInt("idorder_type");
            ps = conn.prepareStatement(REQUEST_ORDER_ITEMS);
            ps.setString(1, "%" + searchText + "%");
            ps.setString(2, "%" + searchText + "%");
            ps.setInt(3, idUser);
            ps.setInt(4, idOrderTypeNew);
            ps.setInt(5, limit);
            ps.setInt(6, offset);
            resultSet = ps.executeQuery();
            TableItem item = null;
            Book book = null;
            while (resultSet.next()) {
                int idOrder = resultSet.getInt("idorder");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                int year = resultSet.getInt("year");
                String bookType = resultSet.getString("book_type.type");
                book = new Book(title, author, year, Book.TypeOfBook.valueOf(bookType));
                int quantity = resultSet.getInt("quantity");
                String state = resultSet.getString("order_type.type");
                item = new TableItem(idOrder, book, quantity, state);
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

    public int getOrderItemsCount(String searchText, int idUser) {
        PreparedStatement idTypePS = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        int result = 0;
        try {
            idTypePS = conn.prepareStatement(GET_IDORDER_TYPE);
            idTypePS.setString(1, Order.TypeOfOrder.NEW.toString());
            resultSet = idTypePS.executeQuery();
            resultSet.next();
            int idOrderTypeNew = resultSet.getInt("idorder_type");
            ps = conn.prepareStatement(REQUEST_ORDERS_ITEMS_COUNT);
            ps.setString(1, "%" + searchText + "%");
            ps.setString(2, "%" + searchText + "%");
            ps.setInt(3, idUser);
            ps.setInt(4, idOrderTypeNew);
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

    public List<TableItem> getNewOrderItems(int offset, int limit) {
        List<TableItem> items = new ArrayList<TableItem>(limit);
        PreparedStatement idTypePS = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            idTypePS = conn.prepareStatement(GET_IDORDER_TYPE);
            idTypePS.setString(1, Order.TypeOfOrder.IN_PROCESSING.toString());
            resultSet = idTypePS.executeQuery();
            resultSet.next();
            int idOrderTypeProc = resultSet.getInt("idorder_type");
            ps = conn.prepareStatement(REQUEST_NEW_ORDER_ITEMS);
            ps.setInt(1, idOrderTypeProc);
            ps.setInt(2, limit);
            ps.setInt(3, offset);
            resultSet = ps.executeQuery();
            TableItem item = null;
            Book book = null;
            while (resultSet.next()) {
                int idOrder = resultSet.getInt("idorder");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                int year = resultSet.getInt("year");
                String bookType = resultSet.getString("book_type.type");
                book = new Book(title, author, year, Book.TypeOfBook.valueOf(bookType));
                int quantity = resultSet.getInt("quantity");
                String state = resultSet.getString("order_type.type");
                String login = resultSet.getString("login");
                item = new TableItem(idOrder, book, quantity, state, login);
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

    public int getNewOrderItemsCount() {
        PreparedStatement idTypePS = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        int result = 0;
        try {
            idTypePS = conn.prepareStatement(GET_IDORDER_TYPE);
            idTypePS.setString(1, Order.TypeOfOrder.IN_PROCESSING.toString());
            resultSet = idTypePS.executeQuery();
            resultSet.next();
            int idOrderTypeProc = resultSet.getInt("idorder_type");
            ps = conn.prepareStatement(REQUEST_NEW_ORDERS_ITEMS_COUNT);
            ps.setInt(1, idOrderTypeProc);
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

    public List<TableItem> getAllOrderItems(String searchText, int offset, int limit) {
        List<TableItem> items = new ArrayList<TableItem>(limit);
        PreparedStatement idTypePS = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            idTypePS = conn.prepareStatement(GET_IDORDER_TYPE);
            idTypePS.setString(1, Order.TypeOfOrder.NEW.toString());
            resultSet = idTypePS.executeQuery();
            resultSet.next();
            int idOrderTypeNew = resultSet.getInt("idorder_type");
            ps = conn.prepareStatement(REQUEST_LOGIN_ORDER_ITEMS);
            /*ps.setString(1, "%" + searchText + "%");*/
            if(searchText == ""){
                ps.setString(1, "%");
            }else{
                ps.setString(1, searchText);
            }
            ps.setInt(2, idOrderTypeNew);
            ps.setInt(3, limit);
            ps.setInt(4, offset);
            resultSet = ps.executeQuery();
            TableItem item = null;
            Book book = null;
            while (resultSet.next()) {
                int idOrder = resultSet.getInt("idorder");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                int year = resultSet.getInt("year");
                String bookType = resultSet.getString("book_type.type");
                book = new Book(title, author, year, Book.TypeOfBook.valueOf(bookType));
                int quantity = resultSet.getInt("quantity");
                String state = resultSet.getString("order_type.type");
                String login = resultSet.getString("login");
                item = new TableItem(idOrder, book, quantity, state, login);
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

    public int getAllOrderItemsCount(String searchText) {
        PreparedStatement idTypePS = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        int result = 0;
        try {
            idTypePS = conn.prepareStatement(GET_IDORDER_TYPE);
            idTypePS.setString(1, Order.TypeOfOrder.NEW.toString());
            resultSet = idTypePS.executeQuery();
            resultSet.next();
            int idOrderTypeNew = resultSet.getInt("idorder_type");
            ps = conn.prepareStatement(REQUEST_LOGIN_ORDERS_ITEMS_COUNT);
            /*ps.setString(1, "%" + searchText + "%");*/
            if(searchText == ""){
                ps.setString(1, "%");
            }else{
                ps.setString(1, searchText);
            }
            ps.setInt(2, idOrderTypeNew);
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

