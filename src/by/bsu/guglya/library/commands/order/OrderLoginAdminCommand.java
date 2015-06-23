package by.bsu.guglya.library.commands.order;

import by.bsu.guglya.library.beans.User;
import by.bsu.guglya.library.commands.Command;
import by.bsu.guglya.library.logic.PageItems;
import by.bsu.guglya.library.logic.PageItemsLogic;
import by.bsu.guglya.library.managers.ConfigurationManager;
import by.bsu.guglya.library.managers.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class OrderLoginAdminCommand implements Command {

    private static final String PAGE_NO_PARAM = "page";
    private static final String PAGE_NO_ATTR = "page";
    private static final String SEARCH_PARAM = "searchLoginOrder";
    private static final String ALL_ORDERS_ITEMS_LIST_PARAM = "allOrdersItems";
    private static final String NO_OF_PAGE_PARAM = "noOfPages";
    private static final String CURRENT_PAGE_PARAM = "currentPage";
    private static final String CURRENT_PAGE_ATTR = "currentPage";
    private final static String LOCALE_PARAM = "locale";
    private final static String EMPTY_LOGIN_ORDERS_RESULT_MESSAGE_ATTR = "emptySearchLoginOrderMessage";

    @Override
    public String execute(HttpServletRequest request) {
        //HttpSession session = request.getSession(true);
        HttpSession session = request.getSession();
        String locale = (String) session.getAttribute(LOCALE_PARAM);
        MessageManager messageManager = new MessageManager(locale);

        int pageNo = 1;
        if(request.getParameter(PAGE_NO_PARAM) != null) {
            pageNo = Integer.parseInt(request.getParameter(PAGE_NO_PARAM));
        }
        if(request.getAttribute(PAGE_NO_ATTR) != null) {
            pageNo = (int)request.getAttribute(PAGE_NO_ATTR);
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

        PageItems result = PageItemsLogic.getAllOrders(searchText, pageNo);

        if (result.getCount() == 0) {
            String message = messageManager.getProperty(MessageManager.EMPTY_SEARCH_LOGIN_ORDER_MESSAGE);
            request.setAttribute(EMPTY_LOGIN_ORDERS_RESULT_MESSAGE_ATTR, message);
        }

        request.setAttribute(ALL_ORDERS_ITEMS_LIST_PARAM, result.getItems());
        request.setAttribute(NO_OF_PAGE_PARAM, result.getCount());
        request.setAttribute(CURRENT_PAGE_PARAM, pageNo);
        session.setAttribute(CURRENT_PAGE_ATTR, pageNo);

        String page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ORDER_LOGIN_ADMIN_PATH_JSP);
        return page;
    }
}
