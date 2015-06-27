package by.bsu.guglya.library.commands.catalog;


import by.bsu.guglya.library.commands.Command;
import by.bsu.guglya.library.commands.basket.BasketCommand;
import by.bsu.guglya.library.logic.CatalogLogic;
import by.bsu.guglya.library.logic.LogicException;
import by.bsu.guglya.library.logic.OrderLogic;
import by.bsu.guglya.library.managers.ConfigurationManager;
import by.bsu.guglya.library.managers.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class DelBookFromCatalogCommand implements Command {

    private final static String LOCALE_PARAM = "locale";
    private final static String CATALOG_ID_PARAM = "idCatalogDel";
    private final static String SUCCESS_DEL_BOOK_FROM_CATALOG_MESSAGE_ATTR = "successDelBookFromCatalog";
    private final static String UNSUCCESSFUL_DEL_BOOK_FROM_CATALOG_MESSAGE_ATTR = "unsuccessfulDelBookFromCatalog";
    private static final String DATABASE_ERROR_MESSAGE_ATTR = "errorDatabaseMessage";

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        //HttpSession session = request.getSession(true);
        HttpSession session = request.getSession();
        String locale = (String)session.getAttribute(LOCALE_PARAM);
        MessageManager messageManager = new MessageManager(locale);
        int idCatalog = Integer.parseInt(request.getParameter(CATALOG_ID_PARAM));
        try{
            if(OrderLogic.bookInOrder(idCatalog)){
                String message = messageManager.getProperty(MessageManager.NOT_DEL_BOOK_FROM_CATALOG_MESSAGE);
                request.setAttribute(UNSUCCESSFUL_DEL_BOOK_FROM_CATALOG_MESSAGE_ATTR, message);
            }else{
                if(CatalogLogic.delCatalogItem(idCatalog)){
                    String message = messageManager.getProperty(MessageManager.DEL_BOOK_FROM_CATALOG_MESSAGE);
                    request.setAttribute(SUCCESS_DEL_BOOK_FROM_CATALOG_MESSAGE_ATTR, message);
                }
            }
        }catch(LogicException ex){
            request.setAttribute(DATABASE_ERROR_MESSAGE_ATTR, ex.getMessage());
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PATH_JSP);
            return page;
        }
        page = new CatalogCommand().execute(request);
        return page;
    }
}
