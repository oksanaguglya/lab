package by.bsu.guglya.library.logic;

import by.bsu.guglya.library.database.dao.BasketDAO;
import by.bsu.guglya.library.database.dao.CatalogDAO;
import by.bsu.guglya.library.database.dao.OrderDAO;

import java.util.List;

public class PageItemsLogic {

    private static final int ITEMS_PER_CATALOG_PAGE = 5;
    private static final int ITEMS_PER_BASKET_PAGE = 7;
    private static final int ITEMS_PER_ORDERS_PAGE = 7;

    public static PageItems search(String searchText, int pageNo) {
        CatalogDAO catalogDAO = new CatalogDAO();
        List<TableItem> items = catalogDAO.getItems(searchText, (pageNo - 1) * ITEMS_PER_CATALOG_PAGE, ITEMS_PER_CATALOG_PAGE);
        int noOfRecords = catalogDAO.getCatalogItemsCount(searchText);
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / ITEMS_PER_CATALOG_PAGE);
        return new PageItems(items, noOfPages);
    }

    public static PageItems userBasket(int idUser, int pageNo) {
        BasketDAO basketDAO = new BasketDAO();
        List<TableItem> items = basketDAO.getItems(idUser, (pageNo - 1) * ITEMS_PER_BASKET_PAGE, ITEMS_PER_BASKET_PAGE);
        int noOfRecords = basketDAO.getBasketItemsCount(idUser);
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / ITEMS_PER_BASKET_PAGE);
        return new PageItems(items, noOfPages);
    }

    public static PageItems userOrders(String searchText, int idUser, int pageNo) {
        OrderDAO orderDAO = new OrderDAO();
        List<TableItem> items = orderDAO.getOrderItems(searchText, idUser, (pageNo - 1) * ITEMS_PER_ORDERS_PAGE, ITEMS_PER_ORDERS_PAGE);
        int noOfRecords = orderDAO.getOrderItemsCount(searchText, idUser);
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / ITEMS_PER_ORDERS_PAGE);
        return new PageItems(items, noOfPages);
    }

    public static PageItems newOrders(int pageNo) {
        OrderDAO orderDAO = new OrderDAO();
        List<TableItem> items = orderDAO.getNewOrderItems((pageNo - 1) * ITEMS_PER_ORDERS_PAGE, ITEMS_PER_ORDERS_PAGE);
        int noOfRecords = orderDAO.getNewOrderItemsCount();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / ITEMS_PER_ORDERS_PAGE);
        return new PageItems(items, noOfPages);
    }
}
