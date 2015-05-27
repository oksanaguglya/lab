package by.bsu.guglya.library.logic;

import by.bsu.guglya.library.beans.CatalogItem;
import by.bsu.guglya.library.commands.SearchResult;
import by.bsu.guglya.library.database.dao.CatalogDao;

import java.util.List;

public class SearchItemsFacade {

    private static final int ITEMS_PER_PAGE = 5;

    public static SearchResult Search(String searchText, int pageNo) {
        CatalogDao catalogDAO = new CatalogDao();
        List<CatalogItem> items = catalogDAO.getItems(searchText, (pageNo - 1) * ITEMS_PER_PAGE, ITEMS_PER_PAGE);
        int noOfRecords = catalogDAO.getCatalogItemsCount(searchText);
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / ITEMS_PER_PAGE);
        return new SearchResult(items, noOfPages);
    }
}