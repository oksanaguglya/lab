package by.bsu.guglya.library.commands.basket;

import by.bsu.guglya.library.beans.User;
import by.bsu.guglya.library.commands.Command;
import by.bsu.guglya.library.commands.basket.BasketCommand;
import by.bsu.guglya.library.logic.OrderLogic;
import by.bsu.guglya.library.managers.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class DelBookFromBasketCommand implements Command {

    private final static String USER_ATTR = "user";
    private final static String LOCALE_PARAM = "locale";
    private final static String ODER_ID_PARAM = "idOrderDel";
    private final static String SUCCESS_DEL_BOOK_FROM_BASKET_MESSAGE_ATTR = "successDelBookFromBasket";

    @Override
    public String execute(HttpServletRequest request) {
        //HttpSession session = request.getSession(true);
        HttpSession session = request.getSession();
        String locale = (String)session.getAttribute(LOCALE_PARAM);
        MessageManager messageManager = new MessageManager(locale);
        User user = (User)session.getAttribute(USER_ATTR);
        int idUser = user.getIdUser();
        int idOrder = Integer.parseInt(request.getParameter(ODER_ID_PARAM));
        if(OrderLogic.delOrder(idUser, idOrder)){
            String message = messageManager.getProperty(MessageManager.DEL_BOOK_FROM_BASKET_MESSAGE);
            request.setAttribute(SUCCESS_DEL_BOOK_FROM_BASKET_MESSAGE_ATTR, message);
        }
        String page = new BasketCommand().execute(request);
        return page;
    }
}
