package by.bsu.guglya.library.commands.util;

import by.bsu.guglya.library.commands.Command;
import by.bsu.guglya.library.commands.basket.BasketCommand;
import by.bsu.guglya.library.commands.catalog.CatalogCommand;
import by.bsu.guglya.library.commands.order.OrderLoginAdminCommand;
import by.bsu.guglya.library.commands.order.OrderNewAdminCommand;
import by.bsu.guglya.library.commands.order.OrderReaderCommand;
import by.bsu.guglya.library.managers.ConfigurationManager;

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
    private static final String PAGE_NO_PARAM = "page";
    private static final String CURRENT_PAGE_ATTR = "currentPage1";
    private static final String PARAM_PAGE = "forwardPage";
    private static final String catalogPage = "/jsp/catalog.jsp";
    private static final String basketPage = "/jsp/users/reader/basket.jsp";
    private static final String orderReaderPage = "/jsp/users/reader/orderreader.jsp";
    private static final String loginOrderAdminPage = "/jsp/users/admin/loginorderadmin.jsp";
    private static final String newOrderAdminPage = "/jsp/users/admin/neworderadmin.jsp";

    public ChangeLanguageCommand() {
    }

    @Override
    public String execute(HttpServletRequest request) {
        String forwardPageDir = request.getParameter(PARAM_PAGE);
        String locale = request.getParameter(LOCALE_ATTR);
        String lang = Locale.valueOf(locale).getLang();
        HttpSession session = request.getSession(true);
        session.setAttribute(LOCALE_ATTR, lang);
        int currentPage = (int)(request.getSession().getAttribute(CURRENT_PAGE_ATTR));
        switch(forwardPageDir){
            case catalogPage:
                request.setAttribute(PAGE_NO_PARAM, currentPage);
                return new CatalogCommand().execute(request);
            case basketPage:
                return new BasketCommand().execute(request);
            case orderReaderPage:
                return new OrderReaderCommand().execute(request);
            case loginOrderAdminPage:
                return new OrderLoginAdminCommand().execute(request);
            case newOrderAdminPage:
                return new OrderNewAdminCommand().execute(request);
            default:
                return forwardPageDir;
        }
    }
}