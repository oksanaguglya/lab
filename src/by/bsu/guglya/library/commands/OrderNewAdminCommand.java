package by.bsu.guglya.library.commands;

import by.bsu.guglya.library.beans.User;
import by.bsu.guglya.library.logic.PageItems;
import by.bsu.guglya.library.logic.PageItemsLogic;
import by.bsu.guglya.library.managers.ConfigurationManager;
import by.bsu.guglya.library.managers.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class OrderNewAdminCommand implements Command {

    private static final String PAGE_NO_PARAM = "page";
    private static final String NEW_ORDERS_ITEMS_LIST_PARAM = "newOrdersItems";
    private static final String NO_OF_PAGE_PARAM = "noOfPages";
    private static final String CURRENT_PAGE_PARAM = "currentPage";
    private final static String LOCALE_PARAM = "locale";
    private final static String EMPTY_NEW_ORDERS_RESULT_MESSAGE_ATTR = "emptySearchNewOrderMessage";

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

        PageItems result = PageItemsLogic.newOrders(pageNo);

        if (result.getCount() == 0) {
            String message = messageManager.getProperty(MessageManager.EMPTY_SEARCH_NEW_ORDER_MESSAGE);
            request.setAttribute(EMPTY_NEW_ORDERS_RESULT_MESSAGE_ATTR, message);
        }

        request.setAttribute(NEW_ORDERS_ITEMS_LIST_PARAM, result.getItems());
        request.setAttribute(NO_OF_PAGE_PARAM, result.getCount());
        request.setAttribute(CURRENT_PAGE_PARAM, pageNo);

        String page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ORDER_NEW_ADMIN_PATH_JSP);
        return page;
    }
}
