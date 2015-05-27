package by.bsu.guglya.library.commands;

import by.bsu.guglya.library.beans.Book;
import by.bsu.guglya.library.beans.CatalogItem;
import by.bsu.guglya.library.database.dao.CatalogDao;
import by.bsu.guglya.library.managers.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class GoToCatalogPageCommand implements Command {

    private static final int ITEMS_PER_PAGE = 5;
    private static final String PAGE_NO_PARAM = "page";
    private static final String CATALOG_ITEMS_LIST_PARAM = "catalogItems";
    private static final String NO_OF_PAGE_PARAM = "noOfPages";
    private static final String CURRENT_PAGE_PARAM = "currentPage";

    @Override
    public String execute(HttpServletRequest request) {
        int pageNo = 1;
        if(request.getParameter(PAGE_NO_PARAM) != null)
            pageNo = Integer.parseInt(request.getParameter(PAGE_NO_PARAM));
        CatalogDao catalogDAO = new CatalogDao();
        List<CatalogItem> items = catalogDAO.getItems((pageNo - 1) * ITEMS_PER_PAGE, ITEMS_PER_PAGE);
        int noOfRecords = catalogDAO.getCatalogItemsCount();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / ITEMS_PER_PAGE);
        request.setAttribute(CATALOG_ITEMS_LIST_PARAM, items);
        request.setAttribute(NO_OF_PAGE_PARAM, noOfPages);
        request.setAttribute(CURRENT_PAGE_PARAM, pageNo);
        String page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.CATALOG_PATH_JSP);
        return page;
    }
}
