package by.bsu.guglya.library.commands.basket;

import by.bsu.guglya.library.beans.User;
import by.bsu.guglya.library.commands.Command;
import by.bsu.guglya.library.commands.basket.BasketCommand;
import by.bsu.guglya.library.logic.OrderLogic;
import by.bsu.guglya.library.managers.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class MakeOrderCommand implements Command {

    private final static String USER_ATTR = "user";
    private final static String LOCALE_PARAM = "locale";
    private final static String ORDER_MADE_SUCCESS_MESSAGE_ATTR = "OrderMakeSuccessMessage";
    private final static String ORDER_MADE_NO_SUCCESS_MESSAGE_ATTR = "OrderMakeNoSuccessMessage";

    @Override
    public String execute(HttpServletRequest request) {
        //HttpSession session = request.getSession(true);
        HttpSession session = request.getSession();
        String locale = (String)session.getAttribute(LOCALE_PARAM);
        MessageManager messageManager = new MessageManager(locale);
        User user = (User)session.getAttribute(USER_ATTR);
        int idUser = user.getIdUser();
        if(OrderLogic.makeOrder(idUser)){
            String message = messageManager.getProperty(MessageManager.ORDER_MADE_SUCCESS_MESSAGE);
            request.setAttribute(ORDER_MADE_SUCCESS_MESSAGE_ATTR, message);
        }else{
            String message = messageManager.getProperty(MessageManager.ORDER_MADE_NO_SUCCESS_MESSAGE);
            request.setAttribute(ORDER_MADE_NO_SUCCESS_MESSAGE_ATTR, message);
        }
        String page = new BasketCommand().execute(request);
        return page;
    }
}
