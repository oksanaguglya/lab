package by.bsu.guglya.library.commands.order;

import by.bsu.guglya.library.beans.User;
import by.bsu.guglya.library.commands.Command;
import by.bsu.guglya.library.logic.PageItems;
import by.bsu.guglya.library.logic.PageItemsLogic;
import by.bsu.guglya.library.managers.ConfigurationManager;
import by.bsu.guglya.library.managers.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class OrderReaderCommand implements Command {

    private static final String PAGE_NO_PARAM = "page";
    private static final String PAGE_NO_ATTR = "page";
    private static final String SEARCH_PARAM = "searchOrder";
    private final static String USER_ATTR = "user";
    private static final String ORDERS_ITEMS_LIST_PARAM = "ordersItems";
    private static final String NO_OF_PAGE_PARAM = "noOfPages";
    private static final String CURRENT_PAGE_PARAM = "currentPage";
    private static final String CURRENT_PAGE_ATTR = "currentPage";
    private final static String LOCALE_PARAM = "locale";
    private final static String EMPTY_ORDERS_RESULT_MESSAGE_ATTR = "emptySearchOrderMessage";

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

        User user = (User) session.getAttribute(USER_ATTR);
        int idUser = user.getId();
        PageItems result = PageItemsLogic.userOrders(searchText, idUser, pageNo);

        if (result.getCount() == 0) {
            String message = messageManager.getProperty(MessageManager.EMPTY_SEARCH_ORDER_MESSAGE);
            request.setAttribute(EMPTY_ORDERS_RESULT_MESSAGE_ATTR, message);
        }

        request.setAttribute(ORDERS_ITEMS_LIST_PARAM, result.getItems());
        request.setAttribute(NO_OF_PAGE_PARAM, result.getCount());
        request.setAttribute(CURRENT_PAGE_PARAM, pageNo);
        session.setAttribute(CURRENT_PAGE_ATTR, pageNo);

        String page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ORDER_READER_PATH_JSP);
        return page;
    }
}
