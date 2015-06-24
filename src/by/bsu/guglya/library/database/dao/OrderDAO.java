package by.bsu.guglya.library.database.dao;

import by.bsu.guglya.library.beans.Book;
import by.bsu.guglya.library.beans.Order;
import by.bsu.guglya.library.beans.User;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO extends AbstractDAO {
    private static final Logger logger = Logger.getLogger(OrderDAO.class);
    public static final String GET_IDORDER_TYPE = "select idorder_type from library.order_type where type=?;";
    public static final String GET_ORDER = "select * from library.order where library.order.book=? and library.order.user=? and library.order.state=?;";
    public static final String UPDATE_ORDER_WITH_QTY = "update library.order set library.order.quantity=library.order.quantity+? where library.order.book=? and library.order.user=? and library.order.state=?;";
    public static final String INSERT_ORDER = "insert into library.order (user, book, quantity, state) values (?,?,?,?);";
    public static final String GET_BASKET_ITEMS = "select order.idorder, book.idbook, book.title, book.author, book.year, order.quantity, book_type.type from library.order " +
            "join library.book on library.order.book = library.book.idbook " +
            "join library.book_type on library.book.book_type=library.book_type.idbook_type " +
            "join library.order_type on library.order.state = library.order_type.idorder_type " +
            "where library.order.user=? and library.order.state=? " +
            "order by book.title limit ? offset ?;";
    public static final String GET_BASKET_ITEMS_COUNT =
            "select count(*) from library.order where library.order.user=? and library.order.state=?;";
    public static final String DELETE_ORDER_BY_ID = "delete from library.order where library.order.idorder=?;";
    public static final String CHANGE_ORDER_STATE = "update library.order set library.order.state=? where library.order.user=? and library.order.state=?;";
    public static final String GET_ORDER_ITEMS = "select order.idorder, book.idbook, book.title, book.author, book.year, order.quantity, book_type.type, order_type.type from library.order " +
            "join library.book on library.order.book = library.book.idbook " +
            "join library.book_type on library.book.book_type=library.book_type.idbook_type " +
            "join library.order_type on library.order.state = library.order_type.idorder_type " +
            "where (book.title like ? or book.author like ?) and (library.order.user=? and library.order.state!=?) " +
            "order by book.title limit ? offset ?;";
    public static final String GET_ORDERS_ITEMS_COUNT =
            "select count(*) from library.order " +
                    "join library.book on library.order.book = library.book.idbook " +
                    "where (book.title like ? or book.author like ?) and library.order.user=? and library.order.state!=?;";
    public static final String GET_NEW_ORDER_ITEMS = "select order.idorder, book.idbook, book.title, book.author, book.year, order.quantity, book_type.type, order_type.type, user.iduser, user.login, user.password, user_type.type from library.order " +
            "join library.book on library.order.book = library.book.idbook " +
            "join library.book_type on library.book.book_type=library.book_type.idbook_type " +
            "join library.order_type on library.order.state = library.order_type.idorder_type " +
            "join library.user on library.order.user = library.user.iduser " +
            "join library.user_type on library.user.user_type = library.user_type.iduser_type " +
            "where library.order.state=? " +
            "order by book.title limit ? offset ?;";
    public static final String GET_NEW_ORDERS_ITEMS_COUNT =
            "select count(*) from library.order where library.order.state=?;";
    public static final String GET_LOGIN_ORDER_ITEMS = "select order.idorder, book.idbook, book.title, book.author, book.year, order.quantity, book_type.type, order_type.type, user.login, user.iduser, user.password, user_type.type from library.order " +
            "join library.book on library.order.book = library.book.idbook " +
            "join library.book_type on library.book.book_type=library.book_type.idbook_type " +
            "join library.order_type on library.order.state = library.order_type.idorder_type " +
            "join library.user on library.order.user = library.user.iduser " +
            "join library.user_type on library.user.user_type = library.user_type.iduser_type " +
            "where (user.login like ?) and library.order.state!=? " +
            "order by book.title limit ? offset ?;";
    public static final String GET_LOGIN_ORDERS_ITEMS_COUNT =
            "select count(*) from library.order join library.user on library.order.user = library.user.iduser where (user.login like ?) and library.order.state!=?;";

    public int getIdOrderType(Order.TypeOfOrder state) throws DAOException {
        int result = 0;
        getConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            ps = conn.prepareStatement(GET_IDORDER_TYPE);
            ps.setString(1, state.toString().toLowerCase());
            resultSet = ps.executeQuery();
            resultSet.next();
            result = resultSet.getInt("idorder_type");
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DAOException("Error while trying to access the database!");
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    logger.error(ex.getMessage());
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    logger.error(ex);
                }
            }
            closeConnection();
        }
        return result;
    }

    public boolean orderExist(String idBook, int idUser, Order.TypeOfOrder state) throws DAOException {
        boolean result = false;
        getConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        int idOrderType = getIdOrderType(state);
        try {
            ps = conn.prepareStatement(GET_ORDER);
            ps.setString(1, idBook);
            ps.setInt(2, idUser);
            ps.setInt(3, idOrderType);
            resultSet = ps.executeQuery();
            result = resultSet.first();
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DAOException("Error while trying to access the database!");
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    logger.error(ex.getMessage());
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    logger.error(ex);
                }
            }
            closeConnection();
        }
        return result;
    }

    public boolean addQtyToOrder(String idBook, int idUser, int qty, Order.TypeOfOrder state) throws DAOException {
        boolean result = false;
        getConnection();
        PreparedStatement ps = null;
        int idOrderType = getIdOrderType(state);
        try {
            ps = conn.prepareStatement(UPDATE_ORDER_WITH_QTY);
            ps.setInt(1, qty);
            ps.setString(2, idBook);
            ps.setInt(3, idUser);
            ps.setInt(4, idOrderType);
            ps.executeUpdate();
            result = true;
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DAOException("Error while trying to access the database!");
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    logger.error(ex.getMessage());
                }
            }
            closeConnection();
        }
        return result;
    }

    public boolean addOrder(String idBook, int idUser, int qty, Order.TypeOfOrder state) throws DAOException {
        boolean result = false;
        getConnection();
        PreparedStatement ps = null;
        int idOrderType = getIdOrderType(state);
        try {
            ps = conn.prepareStatement(INSERT_ORDER);
            ps.setInt(1, idUser);
            ps.setString(2, idBook);
            ps.setInt(3, qty);
            ps.setInt(4, idOrderType);
            ps.executeUpdate();
            result = true;
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DAOException("Error while trying to access the database!");
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    logger.error(ex.getMessage());
                }
            }
            closeConnection();
        }
        return result;
    }

    public List<Order> getUserBasketItems(User user, int offset, int limit) throws DAOException {
        List<Order> items = new ArrayList<>(limit);
        getConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        Order item = null;
        Book book = null;
        int idOrderType = getIdOrderType(Order.TypeOfOrder.NEW);
        try {
            ps = conn.prepareStatement(GET_BASKET_ITEMS);
            ps.setInt(1, user.getId());
            ps.setInt(2, idOrderType);
            ps.setInt(3, limit);
            ps.setInt(4, offset);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int idOrder = resultSet.getInt("idorder");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                int year = resultSet.getInt("year");
                String bookType = resultSet.getString("type");
                int idBook = resultSet.getInt("idbook");
                book = new Book(idBook, title, author, year, Book.TypeOfBook.valueOf(bookType.toUpperCase()));
                int quantity = resultSet.getInt("quantity");
                item = new Order(idOrder, user, book, quantity, Order.TypeOfOrder.NEW);
                items.add(item);
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DAOException("Error while trying to access the database!");
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    logger.error(ex.getMessage());
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    logger.error(ex);
                }
            }
            closeConnection();
        }
        return items;
    }

    public int getUserBasketItemsCount(int idUser) throws DAOException {
        getConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        int result = 0;
        int idOrderType = getIdOrderType(Order.TypeOfOrder.NEW);
        try {
            ps = conn.prepareStatement(GET_BASKET_ITEMS_COUNT);
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
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    logger.error(ex.getMessage());
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    logger.error(ex);
                }
            }
            closeConnection();
        }
        return result;
    }

    public boolean delOrder(int idOrder) throws DAOException {
        getConnection();
        boolean result = false;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(DELETE_ORDER_BY_ID);
            ps.setInt(1, idOrder);
            ps.executeUpdate();
            result = true;
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DAOException("Error while trying to access the database!");
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    logger.error(ex.getMessage());
                }
            }
            closeConnection();
        }
        return result;
    }

    public boolean makeOrder(int idUser) throws DAOException {
        boolean result = false;
        getConnection();
        PreparedStatement ps = null;
        int idOrderTypeNew = getIdOrderType(Order.TypeOfOrder.NEW);
        int idOrderTypeProc = getIdOrderType(Order.TypeOfOrder.IN_PROCESSING);
        try {
            ps = conn.prepareStatement(CHANGE_ORDER_STATE);
                ps.setInt(1, idOrderTypeProc);
                ps.setInt(2, idUser);
                ps.setInt(3, idOrderTypeNew);
                ps.executeUpdate();
                result = true;
        } catch (SQLException ex) {
             logger.error(ex.getMessage());
            throw new DAOException("Error while trying to access the database!");
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    logger.error(ex.getMessage());
                }
            }
            closeConnection();
        }
        return result;
    }

    public List<Order> getOrderItems(String searchText, User user, int offset, int limit) throws DAOException {
        getConnection();
        List<Order> items = new ArrayList<>(limit);
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        int idOrderType = getIdOrderType(Order.TypeOfOrder.NEW);
        Order item = null;
        Book book = null;
        try {
            ps = conn.prepareStatement(GET_ORDER_ITEMS);
            ps.setString(1, "%" + searchText + "%");
            ps.setString(2, "%" + searchText + "%");
            ps.setInt(3, user.getId());
            ps.setInt(4, idOrderType);
            ps.setInt(5, limit);
            ps.setInt(6, offset);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int idOrder = resultSet.getInt("idorder");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                int year = resultSet.getInt("year");
                String bookType = resultSet.getString("book_type.type");
                int idBook = resultSet.getInt("idbook");
                book = new Book(idBook, title, author, year, Book.TypeOfBook.valueOf(bookType.toUpperCase()));
                int quantity = resultSet.getInt("quantity");
                String state = resultSet.getString("order_type.type");
                item = new Order(idOrder, user, book, quantity, Order.TypeOfOrder.valueOf(state.toUpperCase()));
                items.add(item);
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DAOException("Error while trying to access the database!");
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    logger.error(ex.getMessage());
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    logger.error(ex);
                }
            }
            closeConnection();
        }
        return items;
    }

    public int getOrderItemsCount(String searchText, int idUser) throws DAOException {
        getConnection();
        PreparedStatement idTypePS = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        int result = 0;
        int idOrderType = getIdOrderType(Order.TypeOfOrder.NEW);
        try {
            ps = conn.prepareStatement(GET_ORDERS_ITEMS_COUNT);
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
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    logger.error(ex.getMessage());
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    logger.error(ex);
                }
            }
            closeConnection();
        }
        return result;
    }

    public List<Order> getNewOrderItems(int offset, int limit) throws DAOException {
        getConnection();
        List<Order> items = new ArrayList<>(limit);
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        int idOrderType = getIdOrderType(Order.TypeOfOrder.IN_PROCESSING);
        Order item = null;
        Book book = null;
        User user = null;
        try {
            ps = conn.prepareStatement(GET_NEW_ORDER_ITEMS);
            ps.setInt(1, idOrderType);
            ps.setInt(2, limit);
            ps.setInt(3, offset);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int idOrder = resultSet.getInt("idorder");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                int year = resultSet.getInt("year");
                String bookType = resultSet.getString("book_type.type");
                int idBook = resultSet.getInt("idbook");
                book = new Book(idBook, title, author, year, Book.TypeOfBook.valueOf(bookType.toUpperCase()));
                int quantity = resultSet.getInt("quantity");
                String state = resultSet.getString("order_type.type");
                int idUser= resultSet.getInt("user.iduser");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                String userType = resultSet.getString("user_type.type");
                user = new User(idUser, login, password, userType.toUpperCase());
                item = new Order(idOrder, user, book, quantity, Order.TypeOfOrder.valueOf(state.toUpperCase()));
                items.add(item);
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DAOException("Error while trying to access the database!");
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    logger.error(ex.getMessage());
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    logger.error(ex);
                }
            }
            closeConnection();
        }
        return items;
    }

    public int getNewOrderItemsCount() throws DAOException {
        getConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        int result = 0;
        int idOrderType = getIdOrderType(Order.TypeOfOrder.IN_PROCESSING);
        try {
            ps = conn.prepareStatement(GET_NEW_ORDERS_ITEMS_COUNT);
            ps.setInt(1, idOrderType);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getInt("count(*)");
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DAOException("Error while trying to access the database!");
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    logger.error(ex.getMessage());
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    logger.error(ex);
                }
            }
            closeConnection();
        }
        return result;
    }

    public List<Order> getAllOrderItems(String searchText, int offset, int limit) throws DAOException {
        getConnection();
        List<Order> items = new ArrayList<Order>(limit);
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        int idOrderType = getIdOrderType(Order.TypeOfOrder.NEW);
        Order item = null;
        Book book = null;
        User user = null;
        try {
            ps = conn.prepareStatement(GET_LOGIN_ORDER_ITEMS);
            if (searchText == "") {
                ps.setString(1, "%");
            } else {
                ps.setString(1, searchText);
            }
            ps.setInt(2, idOrderType);
            ps.setInt(3, limit);
            ps.setInt(4, offset);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int idOrder = resultSet.getInt("idorder");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                int year = resultSet.getInt("year");
                String bookType = resultSet.getString("book_type.type");
                int idBook = resultSet.getInt("idbook");
                book = new Book(idBook, title, author, year, Book.TypeOfBook.valueOf(bookType.toUpperCase()));
                int quantity = resultSet.getInt("quantity");
                String state = resultSet.getString("order_type.type");
                int idUser= resultSet.getInt("user.iduser");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                String userType = resultSet.getString("user_type.type");
                user = new User(idUser, login, password, userType.toUpperCase());
                item = new Order(idOrder, user, book, quantity, Order.TypeOfOrder.valueOf(state.toUpperCase()));
                items.add(item);
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DAOException("Error while trying to access the database!");
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    logger.error(ex.getMessage());
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    logger.error(ex);
                }
            }
            closeConnection();
        }
        return items;
    }

    public int getAllOrderItemsCount(String searchText) throws DAOException {
        getConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        int result = 0;
        int idOrderType = getIdOrderType(Order.TypeOfOrder.NEW);
        try {
            ps = conn.prepareStatement(GET_LOGIN_ORDERS_ITEMS_COUNT);
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
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    logger.error(ex.getMessage());
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    logger.error(ex);
                }
            }
            closeConnection();
        }
        return result;
    }
}

