package by.bsu.guglya.library.commands.basket;

import by.bsu.guglya.library.beans.User;
import by.bsu.guglya.library.commands.Command;
import by.bsu.guglya.library.logic.LogicException;
import by.bsu.guglya.library.logic.PageItemsLogic;
import by.bsu.guglya.library.logic.PageItems;
import by.bsu.guglya.library.managers.ConfigurationManager;
import by.bsu.guglya.library.managers.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class BasketCommand implements Command {

    private static final String PAGE_NO_PARAM = "page";
    private static final String PAGE_NO_ATTR = "page";
    private static final String BASKET_ITEMS_LIST_PARAM = "basketItems";
    private static final String NO_OF_PAGE_PARAM = "noOfPages";
    private static final String CURRENT_PAGE_PARAM = "currentPage";
    private static final String CURRENT_PAGE_ATTR = "currentPage";
    private final static String USER_ATTR = "user";
    private final static String LOCALE_PARAM = "locale";
    private final static String EMPTY_BASKET_MESSAGE_ATTR = "emptyBasketMessage";
    private static final String DATABASE_ERROR_MESSAGE_ATTR = "errorDatabaseMessage";

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        //HttpSession session = request.getSession(true);
        HttpSession session = request.getSession();
        String locale = (String) session.getAttribute(LOCALE_PARAM);
        MessageManager messageManager = new MessageManager(locale);

        int pageNo = 1;
        if (request.getParameter(PAGE_NO_PARAM) != null) {
            pageNo = Integer.parseInt(request.getParameter(PAGE_NO_PARAM));
        }
        if(request.getAttribute(PAGE_NO_ATTR) != null) {
            pageNo = (int)request.getAttribute(PAGE_NO_ATTR);
        }

        User user = (User) session.getAttribute(USER_ATTR);
        PageItems result;
        try{
            result = PageItemsLogic.userBasket(user, pageNo);
        }catch(LogicException ex){
            request.setAttribute(DATABASE_ERROR_MESSAGE_ATTR, ex.getMessage());
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PATH_JSP);
            return page;
        }

        if (result.getCount() == 0) {
            String message = messageManager.getProperty(MessageManager.EMPTY_BASKET_MESSAGE);
            request.setAttribute(EMPTY_BASKET_MESSAGE_ATTR, message);
        }

        request.setAttribute(BASKET_ITEMS_LIST_PARAM, result.getItems());
        request.setAttribute(NO_OF_PAGE_PARAM, result.getCount());
        request.setAttribute(CURRENT_PAGE_PARAM, pageNo);
        session.setAttribute(CURRENT_PAGE_ATTR, pageNo);

        page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.BASKET_PATH_JSP);
        return page;
    }
}
