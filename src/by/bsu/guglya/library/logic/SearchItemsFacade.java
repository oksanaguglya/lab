package by.bsu.guglya.library.logic;

import by.bsu.guglya.library.beans.BasketItem;
import by.bsu.guglya.library.beans.CatalogItem;
import by.bsu.guglya.library.beans.Order;
import by.bsu.guglya.library.commands.SearchResult;
import by.bsu.guglya.library.database.dao.CatalogDAO;
import by.bsu.guglya.library.database.dao.OrderDAO;

import java.util.List;

public class SearchItemsFacade {

    private static final int ITEMS_PER_PAGE = 5;

    public static SearchResult search(String searchText, int pageNo) {
        CatalogDAO catalogDAO = new CatalogDAO();
        List<CatalogItem> items = catalogDAO.getItems(searchText, (pageNo - 1) * ITEMS_PER_PAGE, ITEMS_PER_PAGE);
        int noOfRecords = catalogDAO.getCatalogItemsCount(searchText);
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / ITEMS_PER_PAGE);
        return new SearchResult(items, noOfPages);
    }

    public static SearchResult userBasket(int idUser, int pageNo) {
        OrderDAO orderDAO = new OrderDAO();
        List<BasketItem> items = orderDAO.getItems(idUser, (pageNo - 1) * ITEMS_PER_PAGE, ITEMS_PER_PAGE);
        int noOfRecords = orderDAO.getBasketItemsCount(idUser);
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / ITEMS_PER_PAGE);
        return new SearchResult(items, noOfPages);
    }
}
