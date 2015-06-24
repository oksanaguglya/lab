package by.bsu.guglya.library.commands.util;

import by.bsu.guglya.library.commands.Command;
import by.bsu.guglya.library.commands.basket.BasketCommand;
import by.bsu.guglya.library.commands.catalog.CatalogCommand;
import by.bsu.guglya.library.commands.order.AllUserOrdersCommand;
import by.bsu.guglya.library.commands.order.NewOrdersCommand;
import by.bsu.guglya.library.commands.order.UserOrdersCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ChangeLanguageCommand implements Command {

    private enum Locale{
        RU("ru_RU"),
        EN("en_EN");
        private String lang;
        Locale(String lang){
            this.lang = lang;
        }
        public String getLang(){
            return lang;
        }
    }

    private static final String LOCALE_ATTR = "locale";
    private static final String PAGE_NO_ATTR = "page";
    private static final String CURRENT_PAGE_ATTR = "currentPage";
    private static final String PARAM_PAGE = "forwardPage";
    private static final String catalogPage = "/jsp/catalog.jsp";
    private static final String basketPage = "/jsp/users/reader/basket.jsp";
    private static final String orderReaderPage = "/jsp/users/reader/userorders.jsp";
    private static final String loginOrderAdminPage = "/jsp/users/admin/allorders.jsp";
    private static final String newOrderAdminPage = "/jsp/users/admin/neworders.jsp";

    public ChangeLanguageCommand() {
    }

    @Override
    public String execute(HttpServletRequest request) {
        String forwardPageDir = request.getParameter(PARAM_PAGE);
        String locale = request.getParameter(LOCALE_ATTR);
        String lang = Locale.valueOf(locale).getLang();
        HttpSession session = request.getSession(true);
        session.setAttribute(LOCALE_ATTR, lang);
        if(session.getAttribute(CURRENT_PAGE_ATTR) != null){
            int currentPage = (int)session.getAttribute(CURRENT_PAGE_ATTR);
            request.setAttribute(PAGE_NO_ATTR, currentPage);
        }
        switch(forwardPageDir){
            case catalogPage:
                return new CatalogCommand().execute(request);
            case basketPage:
                return new BasketCommand().execute(request);
            case orderReaderPage:
                return new UserOrdersCommand().execute(request);
            case loginOrderAdminPage:
                return new AllUserOrdersCommand().execute(request);
            case newOrderAdminPage:
                return new NewOrdersCommand().execute(request);
            default:
                return forwardPageDir;
        }
    }
}