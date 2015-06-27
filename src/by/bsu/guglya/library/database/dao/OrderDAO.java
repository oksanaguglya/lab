package by.bsu.guglya.library.database.dao;

import by.bsu.guglya.library.beans.*;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO extends AbstractDAO {
    private static final Logger logger = Logger.getLogger(OrderDAO.class);
    public static final String GET_IDORDER_TYPE = "select idorder_type from library.order_type where type=?;";
    public static final String GET_ORDER_BY_USER_AND_IDCATALOG_AND_STATE = "select * from library.order where library.order.catalog_item=? and library.order.user=? and library.order.state=?;";
    public static final String UPDATE_ORDER_WITH_QTY_BY_USER_AND_IDCATALOG_AND_STATE = "update library.order set library.order.quantity=library.order.quantity+? where library.order.catalog_item=? and library.order.user=? and library.order.state=?;";
    public static final String INSERT_ORDER = "insert into library.order (user, catalog_item, quantity, state, date_of_order) values (?,?,?,?,?);";
    public static final String GET_ITEMS_BY_USER_AND_STATE = "select order.idorder, book.idbook, book.title, book.author, book.year, book_type.type, catalog.idcatalog, catalog.quantity, order.quantity from library.order " +
            "join library.order_type on library.order.state = library.order_type.idorder_type " +
            "join library.catalog on library.order.catalog_item = library.catalog.idcatalog " +
            "join library.book on library.book.idbook = library.catalog.book " +
            "join library.book_type on library.book.book_type=library.book_type.idbook_type " +
            "where library.order.user=? and library.order.state=? " +
            "order by book.title limit ? offset ?;";
    public static final String GET_ITEMS_BY_USER_AND_STATE_COUNT =
            "select count(*) from library.order where library.order.user=? and library.order.state=?;";
    public static final String DELETE_ORDER_BY_ID = "delete from library.order where library.order.idorder=?;";
    public static final String CHANGE_ORDER_STATE_AND_DATE_BY_USER_AND_STATE = "update library.order set library.order.state=?, library.order.date_of_order=? where library.order.user=? and library.order.state=?;";
    public static final String CHANGE_ORDER_STATE_BY_ID = "update library.order set library.order.state=? where library.order.idorder=?;";
    public static final String GET_ORDERS_BY_IDCATALOG =
            "select library.order.idorder from library.order " +
                    "join library.catalog on library.order.catalog_item=library.catalog.idcatalog " +
                    "where library.catalog.idcatalog=?;";
    public static final String GET_ORDER_ITEMS_BY_SEARCHTEXT_AND_USER_AND_NOT_IN_STATE = "select order.idorder, user.login, book.idbook, book.title, book.author, book.year, book_type.type, catalog.idcatalog, catalog.quantity, order.quantity, order_type.type from library.order " +
            "join library.order_type on library.order.state = library.order_type.idorder_type " +
            "join library.catalog on library.order.catalog_item = library.catalog.idcatalog " +
            "join library.book on library.book.idbook = library.catalog.book " +
            "join library.book_type on library.book.book_type=library.book_type.idbook_type " +
            "join library.user on library.order.user = library.user.iduser " +
            "where (book.title like ? or book.author like ?) and (library.order.user=? and library.order.state!=?) " +
            "order by book.title limit ? offset ?;";
    public static final String GET_ORDER_ITEMS_BY_SEARCHTEXT_AND_USER_AND_NOT_IN_STATE_COUNT =
            "select count(*) from library.order " +
                    "join library.catalog on library.order.catalog_item = library.catalog.idcatalog " +
                    "join library.book on library.catalog.book = library.book.idbook " +
                    "where (book.title like ? or book.author like ?) and library.order.user=? and library.order.state!=?;";
    public static final String GET_ORDER_ITEMS_BY_STATE = "select order.idorder, user.iduser, user.login, book.idbook, book.title, book.author, book.year, book_type.type, catalog.idcatalog, catalog.quantity, order.quantity, order_type.type, order.date_of_order from library.order " +
            "join library.order_type on library.order.state = library.order_type.idorder_type " +
            "join library.catalog on library.order.catalog_item = library.catalog.idcatalog " +
            "join library.book on library.book.idbook = library.catalog.book " +
            "join library.book_type on library.book.book_type=library.book_type.idbook_type " +
            "join library.user on library.order.user = library.user.iduser " +
            "where library.order.state=? " +
            "order by order.date_of_order limit ? offset ?;";
    public static final String GET_ORDER_ITEMS_BY_STATE_COUNT =
            "select count(*) from library.order where library.order.state=?;";
    public static final String GET_ORDER_ITEMS_BY_SEARCHTEXT_NOT_IN_STATE = "select order.idorder, user.iduser, user.login, book.idbook, book.title, book.author, book.year, book_type.type, catalog.idcatalog, catalog.quantity, order.quantity, order_type.type, order.date_of_order from library.order " +
            "join library.order_type on library.order.state = library.order_type.idorder_type " +
            "join library.catalog on library.order.catalog_item = library.catalog.idcatalog " +
            "join library.book on library.book.idbook = library.catalog.book " +
            "join library.book_type on library.book.book_type=library.book_type.idbook_type " +
            "join library.user on library.order.user = library.user.iduser " +
            "where (user.login like ?) and library.order.state!=? " +
            "order by order.date_of_order limit ? offset ?;";
    public static final String GET_ORDER_ITEMS_BY_SEARCHTEXT_NOT_IN_STATE_COUNT =
            "select count(*) from library.order join library.user on library.order.user = library.user.iduser where (user.login like ?) and library.order.state!=?;";

    private int getIdOrderType(Order.TypeOfOrder type) throws DAOException {
        int result = 0;
        try {
            ps = conn.prepareStatement(GET_IDORDER_TYPE);
            ps.setString(1, type.toString().toLowerCase());
            resultSet = ps.executeQuery();
            resultSet.next();
            result = resultSet.getInt("idorder_type");
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DAOException("Error while trying to access the database!");
        }
        return result;
    }

    public boolean checkOrderExistByUserAndIdCatalogAndState(String idCatalog, int idUser, Order.TypeOfOrder state) throws DAOException {
        boolean result = false;
        getConnection();
        int idOrderType = getIdOrderType(state);
        try {
            ps = conn.prepareStatement(GET_ORDER_BY_USER_AND_IDCATALOG_AND_STATE);
            ps.setString(1, idCatalog);
            ps.setInt(2, idUser);
            ps.setInt(3, idOrderType);
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

    public boolean addQtyToOrderByUserAndIdCatalogAndState(String idCatalog, int idUser, int qty, Order.TypeOfOrder state) throws DAOException {
        boolean result = false;
        getConnection();
        int idOrderType = getIdOrderType(state);
        try {
            ps = conn.prepareStatement(UPDATE_ORDER_WITH_QTY_BY_USER_AND_IDCATALOG_AND_STATE);
            ps.setInt(1, qty);
            ps.setString(2, idCatalog);
            ps.setInt(3, idUser);
            ps.setInt(4, idOrderType);
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

    public boolean addOrder(String idCatalog, int idUser, int qty, Order.TypeOfOrder state, String date) throws DAOException {
        boolean result = false;
        getConnection();
        int idOrderType = getIdOrderType(state);
        try {
            ps = conn.prepareStatement(INSERT_ORDER);
            ps.setInt(1, idUser);
            ps.setString(2, idCatalog);
            ps.setInt(3, qty);
            ps.setInt(4, idOrderType);
            ps.setString(5, date);
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

    public List<Order> getOrderItemsByUserAndState(int idUser, Order.TypeOfOrder state, int offset, int limit) throws DAOException {
        List<Order> items = new ArrayList<>(limit);
        getConnection();
        int idOrderType = getIdOrderType(state);
        try {
            ps = conn.prepareStatement(GET_ITEMS_BY_USER_AND_STATE);
            ps.setInt(1, idUser);
            ps.setInt(2, idOrderType);
            ps.setInt(3, limit);
            ps.setInt(4, offset);
            resultSet = ps.executeQuery();
            Order item = null;
            CatalogItem catalogItem = null;
            Book book = null;
            while (resultSet.next()) {
                int idBook = resultSet.getInt("idbook");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                int year = resultSet.getInt("year");
                String bookType = resultSet.getString("type");
                book = new Book(idBook, title, author, year, Book.TypeOfBook.valueOf(bookType.toUpperCase()));
                int idCatalog = resultSet.getInt("idcatalog");
                int quantity = resultSet.getInt("catalog.quantity");
                int qty = resultSet.getInt("order.quantity");
                catalogItem = new CatalogItem(idCatalog, book, quantity);
                int idOrder = resultSet.getInt("idorder");
                item = new Order(idOrder, idUser, catalogItem, qty, Order.TypeOfOrder.NEW);
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

    public int getOrderItemsByUserAndStateCount(int idUser, Order.TypeOfOrder state) throws DAOException {
        int result = 0;
        getConnection();
        int idOrderType = getIdOrderType(state);
        try {
            ps = conn.prepareStatement(GET_ITEMS_BY_USER_AND_STATE_COUNT);
            ps.setInt(1, idUser);
            ps.setInt(2, idOrderType);
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

    public boolean delOrderById(int idOrder) throws DAOException {
        boolean result = false;
        getConnection();
        try {
            ps = conn.prepareStatement(DELETE_ORDER_BY_ID);
            ps.setInt(1, idOrder);
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

    public boolean changeOrderStateAndDateByUserAndState(int idUser, Order.TypeOfOrder state, Order.TypeOfOrder newState, String date) throws DAOException {
        boolean result = false;
        getConnection();
        int idOrderTypeNew = getIdOrderType(state);
        int idOrderTypeProc = getIdOrderType(newState);
        try {
            ps = conn.prepareStatement(CHANGE_ORDER_STATE_AND_DATE_BY_USER_AND_STATE);
            ps.setInt(1, idOrderTypeProc);
            ps.setString(2, date);
            ps.setInt(3, idUser);
            ps.setInt(4, idOrderTypeNew);
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

    public boolean changeOrderStateByIdOrder(int idOrder, Order.TypeOfOrder state) throws DAOException {
        boolean result = false;
        getConnection();
        int idOrderType = getIdOrderType(state);
        try {
            ps = conn.prepareStatement(CHANGE_ORDER_STATE_BY_ID);
            ps.setInt(1, idOrderType);
            ps.setInt(2, idOrder);
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

    public List<Order> getOrderItemsBySearchTextAndUserNotInState(String searchText, int idUser, Order.TypeOfOrder state, int offset, int limit) throws DAOException {
        List<Order> items = new ArrayList<>(limit);
        getConnection();
        int idOrderType = getIdOrderType(state);
        try {
            ps = conn.prepareStatement(GET_ORDER_ITEMS_BY_SEARCHTEXT_AND_USER_AND_NOT_IN_STATE);
            ps.setString(1, "%" + searchText + "%");
            ps.setString(2, "%" + searchText + "%");
            ps.setInt(3, idUser);
            ps.setInt(4, idOrderType);
            ps.setInt(5, limit);
            ps.setInt(6, offset);
            resultSet = ps.executeQuery();
            Order item = null;
            CatalogItem catalogItem = null;
            Book book = null;
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                int year = resultSet.getInt("year");
                String bookType = resultSet.getString("book_type.type");
                int idBook = resultSet.getInt("idbook");
                book = new Book(idBook, title, author, year, Book.TypeOfBook.valueOf(bookType.toUpperCase()));
                int idCatalog = resultSet.getInt("idcatalog");
                int quantity = resultSet.getInt("catalog.quantity");
                catalogItem = new CatalogItem(idCatalog, book, quantity);
                int idOrder = resultSet.getInt("idorder");
                String login = resultSet.getString("login");
                int qty = resultSet.getInt("order.quantity");
                String orderState = resultSet.getString("order_type.type");
                item = new Order(idOrder, idUser, login, catalogItem, qty, Order.TypeOfOrder.valueOf(orderState.toUpperCase()));
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

    public int getOrderItemsBySearchTextAndUserCount(String searchText, int idUser, Order.TypeOfOrder state) throws DAOException {
        int result = 0;
        getConnection();
        int idOrderType = getIdOrderType(state);
        try {
            ps = conn.prepareStatement(GET_ORDER_ITEMS_BY_SEARCHTEXT_AND_USER_AND_NOT_IN_STATE_COUNT);
            ps.setString(1, "%" + searchText + "%");
            ps.setString(2, "%" + searchText + "%");
            ps.setInt(3, idUser);
            ps.setInt(4, idOrderType);
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

    public List<Order> getOrderItemsByState(Order.TypeOfOrder state, int offset, int limit) throws DAOException {
        List<Order> items = new ArrayList<>(limit);
        getConnection();
        int idOrderType = getIdOrderType(state);
        try {
            ps = conn.prepareStatement(GET_ORDER_ITEMS_BY_STATE);
            ps.setInt(1, idOrderType);
            ps.setInt(2, limit);
            ps.setInt(3, offset);
            resultSet = ps.executeQuery();
            Order order = null;
            CatalogItem catalogItem = null;
            Book book = null;
            while (resultSet.next()) {
                int idBook = resultSet.getInt("idbook");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                int year = resultSet.getInt("year");
                String bookType = resultSet.getString("book_type.type");
                book = new Book(idBook, title, author, year, Book.TypeOfBook.valueOf(bookType.toUpperCase()));
                int idCatalog = resultSet.getInt("idcatalog");
                int quantity = resultSet.getInt("catalog.quantity");
                catalogItem = new CatalogItem(idCatalog, book, quantity);
                int idOrder = resultSet.getInt("idorder");
                int idUser = resultSet.getInt("user.iduser");
                String login = resultSet.getString("login");
                String orderState = resultSet.getString("order_type.type");
                int qty = resultSet.getInt("order.quantity");
                String date = resultSet.getString("date_of_order");
                order = new Order(idOrder, idUser, login, catalogItem, qty, Order.TypeOfOrder.valueOf(orderState.toUpperCase()), date);
                items.add(order);
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DAOException("Error while trying to access the database!");
        } finally {
            closeConnection();
        }
        return items;
    }

    public int getOrderItemsByStateCount(Order.TypeOfOrder state) throws DAOException {
        int result = 0;
        getConnection();
        int idOrderType = getIdOrderType(state);
        try {
            ps = conn.prepareStatement(GET_ORDER_ITEMS_BY_STATE_COUNT);
            ps.setInt(1, idOrderType);
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

    public List<Order> getOrderItemsBySearchTextNotInState(String searchText, Order.TypeOfOrder state, int offset, int limit) throws DAOException {
        List<Order> items = new ArrayList<>(limit);
        getConnection();
        int idOrderType = getIdOrderType(state);
        try {
            ps = conn.prepareStatement(GET_ORDER_ITEMS_BY_SEARCHTEXT_NOT_IN_STATE);
            if (searchText == "") {
                ps.setString(1, "%");
            } else {
                ps.setString(1, searchText);
            }
            ps.setInt(2, idOrderType);
            ps.setInt(3, limit);
            ps.setInt(4, offset);
            resultSet = ps.executeQuery();
            Order item = null;
            CatalogItem catalogItem = null;
            Book book = null;
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                int year = resultSet.getInt("year");
                String bookType = resultSet.getString("book_type.type");
                int idBook = resultSet.getInt("idbook");
                book = new Book(idBook, title, author, year, Book.TypeOfBook.valueOf(bookType.toUpperCase()));
                int idCatalog = resultSet.getInt("idcatalog");
                int quantity = resultSet.getInt("catalog.quantity");
                catalogItem = new CatalogItem(idCatalog, book, quantity);
                int idOrder = resultSet.getInt("idorder");
                int idUser = resultSet.getInt("user.iduser");
                String login = resultSet.getString("login");
                int qty = resultSet.getInt("order.quantity");
                String orderState = resultSet.getString("order_type.type");
                String date = resultSet.getString("date_of_order");
                item = new Order(idOrder, idUser, login, catalogItem, qty, Order.TypeOfOrder.valueOf(orderState.toUpperCase()), date);
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

    public int getOrderItemsBySearchTextNotInStateCount(String searchText, Order.TypeOfOrder state) throws DAOException {
        int result = 0;
        getConnection();
        int idOrderType = getIdOrderType(state);
        try {
            ps = conn.prepareStatement(GET_ORDER_ITEMS_BY_SEARCHTEXT_NOT_IN_STATE_COUNT);
            if (searchText == "") {
                ps.setString(1, "%");
            } else {
                ps.setString(1, searchText);
            }
            ps.setInt(2, idOrderType);
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

    public boolean checkCatalogItemExistInOrder(int idCatalog) throws DAOException {
        boolean result = false;
        getConnection();
        try {
            ps = conn.prepareStatement(GET_ORDERS_BY_IDCATALOG);
            ps.setInt(1, idCatalog);
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
}

