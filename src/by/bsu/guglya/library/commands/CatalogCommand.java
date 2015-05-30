package by.bsu.guglya.library.commands;

import by.bsu.guglya.library.logic.SearchItemsFacade;
import by.bsu.guglya.library.managers.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class CatalogCommand implements Command {

    private static final String PAGE_NO_PARAM = "page";
    private static final String SEARCH_PARAM = "search";
    private static final String CATALOG_ITEMS_LIST_PARAM = "catalogItems";
    private static final String NO_OF_PAGE_PARAM = "noOfPages";
    private static final String CURRENT_PAGE_PARAM = "currentPage";

    @Override
    public String execute(HttpServletRequest request) {
        int pageNo = 1;
        if(request.getParameter(PAGE_NO_PARAM) != null) {
            pageNo = Integer.parseInt(request.getParameter(PAGE_NO_PARAM));
        }

        String searchText = "";
        if (request.getParameter(SEARCH_PARAM) != null) {
            searchText = request.getParameter(SEARCH_PARAM);
            request.getSession().setAttribute(SEARCH_PARAM, searchText);
        } else {
            if (request.getSession().getAttribute(SEARCH_PARAM) != null) {
                searchText = request.getSession().getAttribute(SEARCH_PARAM).toString();
            }
        }
        SearchResult result = SearchItemsFacade.Search(searchText, pageNo);

        request.setAttribute(CATALOG_ITEMS_LIST_PARAM, result.getItems());
        request.setAttribute(NO_OF_PAGE_PARAM, result.getCount());
        request.setAttribute(CURRENT_PAGE_PARAM, pageNo);

        String page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.CATALOG_PATH_JSP);
        return page;
    }
}
