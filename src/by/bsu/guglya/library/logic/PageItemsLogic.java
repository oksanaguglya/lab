package by.bsu.guglya.library.logic;

import by.bsu.guglya.library.database.dao.BasketDAO;
import by.bsu.guglya.library.database.dao.CatalogDAO;

import java.util.List;

public class PageItemsLogic {

    private static final int ITEMS_PER_CATALOG_PAGE = 5;
    private static final int ITEMS_PER_BASKET_PAGE = 7;

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
}
