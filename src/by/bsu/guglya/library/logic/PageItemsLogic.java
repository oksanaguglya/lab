package by.bsu.guglya.library.logic;

import by.bsu.guglya.library.beans.CatalogItem;
import by.bsu.guglya.library.beans.Order;
import by.bsu.guglya.library.beans.User;
import by.bsu.guglya.library.database.dao.CatalogDAO;
import by.bsu.guglya.library.database.dao.DAOException;
import by.bsu.guglya.library.database.dao.OrderDAO;

import java.util.List;

public class PageItemsLogic {

    private static final int ITEMS_PER_CATALOG_PAGE = 5;
    private static final int ITEMS_PER_BASKET_PAGE = 7;
    private static final int ITEMS_PER_ORDERS_PAGE = 7;

    public static PageItems catalogSearch(String searchText, int pageNo) throws LogicException{
        CatalogDAO catalogDAO = new CatalogDAO();
        int noOfRecords = 0;
        List<CatalogItem> items = null;
        try {
            items = catalogDAO.getCatalogItems(searchText, (pageNo - 1) * ITEMS_PER_CATALOG_PAGE, ITEMS_PER_CATALOG_PAGE);
            noOfRecords = catalogDAO.getCatalogItemsCount(searchText);
        } catch (DAOException ex) {
            throw new LogicException(ex.getMessage());
        }
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / ITEMS_PER_CATALOG_PAGE);
        return new PageItems(items, noOfPages);
    }

    public static PageItems userBasket(User user, int pageNo) throws LogicException{
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

    public static PageItems userOrders(String searchText, User user, int pageNo) throws LogicException{
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

    public static PageItems newOrders(int pageNo) throws  LogicException{
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

    public static PageItems getAllOrders(String searchText, int pageNo) throws LogicException{
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
