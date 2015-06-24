package by.bsu.guglya.library.logic;

import by.bsu.guglya.library.beans.Order;
import by.bsu.guglya.library.beans.User;
import by.bsu.guglya.library.database.dao.DAOException;
import by.bsu.guglya.library.database.dao.OrderDAO;

import java.util.List;

public class OrderLogic {

    private static final int ITEMS_PER_BASKET_PAGE = 7;
    private static final int ITEMS_PER_ORDERS_PAGE = 7;

    public static boolean checkOrderExist(String idBook, int idUser, Order.TypeOfOrder state) throws LogicException{
        OrderDAO orderDAO = new OrderDAO();
        boolean result = false;
        try{
            result = orderDAO.orderExist(idBook, idUser, state);
        }catch(DAOException ex){
            throw new LogicException(ex.getMessage());
        }
        return result;
    }

    public static boolean addQtyToOrder(String idBook, int idUser, int qty,  Order.TypeOfOrder state) throws LogicException{
        OrderDAO orderDAO = new OrderDAO();
        boolean result = false;
        try{
            result = orderDAO.addQtyToOrder(idBook, idUser, qty, state);
        }catch(DAOException ex){
            throw new LogicException(ex.getMessage());
        }
        return result;
    }

    public static boolean addOrder(String idBook, int idUser, int qty,  Order.TypeOfOrder state) throws LogicException{
        OrderDAO orderDAO = new OrderDAO();
        boolean result = false;
        try{
            result = orderDAO.addOrder(idBook, idUser, qty, state);
        }catch(DAOException ex){
            throw new LogicException(ex.getMessage());
        }
        return result;
    }

    public static boolean delOrder(int idOrder) throws  LogicException{
        OrderDAO orderDAO = new OrderDAO();
        boolean result = false;
        try{
            result = orderDAO.delOrder(idOrder);
        }catch(DAOException ex){
            throw new LogicException(ex.getMessage());
        }
        return result;
    }

    public static boolean makeOrder(int idUser) throws  LogicException{
        OrderDAO orderDAO = new OrderDAO();
        boolean result = false;
        try{
            result = orderDAO.makeOrder(idUser);
        }catch(DAOException ex){
            throw new LogicException(ex.getMessage());
        }
        return result;
    }

    public static PageItems getUserBasketItems(User user, int pageNo) throws LogicException{
        OrderDAO orderDAO = new OrderDAO();
        int noOfRecords = 0;
        List<Order> items = null;
        try {
            items = orderDAO.getUserBasketItems(user, (pageNo - 1) * ITEMS_PER_BASKET_PAGE, ITEMS_PER_BASKET_PAGE);
            noOfRecords = orderDAO.getUserBasketItemsCount(user.getId());
        } catch (DAOException ex) {
            throw new LogicException(ex.getMessage());
        }
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / ITEMS_PER_BASKET_PAGE);
        return new PageItems(items, noOfPages);
    }

    public static PageItems getUserOrderItems(String searchText, User user, int pageNo) throws LogicException{
        OrderDAO orderDAO = new OrderDAO();
        int noOfRecords = 0;
        List<Order> items = null;
        try {
            items = orderDAO.getOrderItems(searchText, user, (pageNo - 1) * ITEMS_PER_ORDERS_PAGE, ITEMS_PER_ORDERS_PAGE);
            noOfRecords = orderDAO.getOrderItemsCount(searchText, user.getId());
        } catch (DAOException ex) {
            throw new LogicException(ex.getMessage());
        }
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / ITEMS_PER_ORDERS_PAGE);
        return new PageItems(items, noOfPages);
    }

    public static PageItems getNewOrderItems(int pageNo) throws  LogicException{
        OrderDAO orderDAO = new OrderDAO();
        int noOfRecords = 0;
        List<Order> items = null;
        try {
            items = orderDAO.getNewOrderItems((pageNo - 1) * ITEMS_PER_ORDERS_PAGE, ITEMS_PER_ORDERS_PAGE);
            noOfRecords = orderDAO.getNewOrderItemsCount();
        } catch (DAOException ex) {
            throw new LogicException(ex.getMessage());
        }
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / ITEMS_PER_ORDERS_PAGE);
        return new PageItems(items, noOfPages);
    }

    public static PageItems getAllOrderItems(String searchText, int pageNo) throws LogicException{
        OrderDAO orderDAO = new OrderDAO();
        int noOfRecords = 0;
        List<Order> items = null;
        try {
            items = orderDAO.getAllOrderItems(searchText, (pageNo - 1) * ITEMS_PER_ORDERS_PAGE, ITEMS_PER_ORDERS_PAGE);
            noOfRecords = orderDAO.getAllOrderItemsCount(searchText);
        } catch (DAOException ex) {
            throw new LogicException(ex.getMessage());
        }
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / ITEMS_PER_ORDERS_PAGE);
        return new PageItems(items, noOfPages);
    }

}
