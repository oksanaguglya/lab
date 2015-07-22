package by.bsu.guglya.library.commands.basket;

import by.bsu.guglya.library.model.beans.User;
import by.bsu.guglya.library.commands.Command;
import by.bsu.guglya.library.logic.LogicException;
import by.bsu.guglya.library.logic.OrderLogic;
import by.bsu.guglya.library.managers.ConfigurationManager;
import by.bsu.guglya.library.managers.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class DelBookFromBasketCommand implements Command {

    private final static String LOCALE_PARAM = "locale";
    private final static String ORDER_ID_PARAM = "idOrderDel";
    private final static String SUCCESS_DEL_BOOK_FROM_BASKET_MESSAGE_ATTR = "successDelBookFromBasket";
    private static final String DATABASE_ERROR_MESSAGE_ATTR = "errorDatabaseMessage";

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        //HttpSession session = request.getSession(true);
        HttpSession session = request.getSession();
        String locale = (String)session.getAttribute(LOCALE_PARAM);
        MessageManager messageManager = new MessageManager(locale);
        int idOrder = Integer.parseInt(request.getParameter(ORDER_ID_PARAM));
        try{
            if(OrderLogic.delOrder(idOrder)){
                String message = messageManager.getProperty(MessageManager.DEL_BOOK_FROM_BASKET_MESSAGE);
                request.setAttribute(SUCCESS_DEL_BOOK_FROM_BASKET_MESSAGE_ATTR, message);
            }
        }catch(LogicException ex){
            request.setAttribute(DATABASE_ERROR_MESSAGE_ATTR, ex.getMessage());
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PATH_JSP);
            return page;
        }
        page = new BasketCommand().execute(request);
        return page;
    }
}
