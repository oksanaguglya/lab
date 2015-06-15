package by.bsu.guglya.library.commands;

import by.bsu.guglya.library.beans.User;
import by.bsu.guglya.library.logic.PageItemsLogic;
import by.bsu.guglya.library.logic.PageItems;
import by.bsu.guglya.library.managers.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class BasketCommand implements Command{
    private static final String PAGE_NO_PARAM = "page";
    private static final String BASKET_ITEMS_LIST_PARAM = "basketItems";
    private static final String NO_OF_PAGE_PARAM = "noOfPages";
    private static final String CURRENT_PAGE_PARAM = "currentPage";
    private final static String USER_ATTR = "user";

    @Override
    public String execute(HttpServletRequest request) {
        int pageNo = 1;
        if(request.getParameter(PAGE_NO_PARAM) != null) {
            pageNo = Integer.parseInt(request.getParameter(PAGE_NO_PARAM));
        }

        HttpSession session = request.getSession(true);
        User user = (User)session.getAttribute(USER_ATTR);
        int idUser = user.getIdUser();
        PageItems result = PageItemsLogic.userBasket(idUser, pageNo);

        request.setAttribute(BASKET_ITEMS_LIST_PARAM, result.getItems());
        request.setAttribute(NO_OF_PAGE_PARAM, result.getCount());
        request.setAttribute(CURRENT_PAGE_PARAM, pageNo);

        String page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.BASKET_PATH_JSP);
        return page;
    }
}
