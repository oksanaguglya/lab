package by.bsu.guglya.library.commands.order;

import by.bsu.guglya.library.beans.Order;
import by.bsu.guglya.library.beans.User;
import by.bsu.guglya.library.commands.Command;
import by.bsu.guglya.library.commands.basket.BasketCommand;
import by.bsu.guglya.library.logic.CatalogLogic;
import by.bsu.guglya.library.logic.LogicException;
import by.bsu.guglya.library.logic.OrderLogic;
import by.bsu.guglya.library.managers.ConfigurationManager;
import by.bsu.guglya.library.managers.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProcessOrderCommand implements Command {

    enum TypeOfAction{
        APPROVE_ORDER,
        DENIED_ORDER
    }

    private final static String LOCALE_PARAM = "locale";
    private final static String ORDER_ID_PARAM = "idOrder";
    private final static String ORDER_ACTION_PARAM = "action";
    private final static String ORDER_PROCESS_APPROVED_MESSAGE_ATTR = "OrderProcessApprovedMessage";
    private final static String ORDER_PROCESS_DENIED_MESSAGE_ATTR = "OrderProcessDeniedMessage";
    private final static String ORDER_PROCESS_NO_ENOUGH_MESSAGE_ATTR = "OrderProcessNoEnoughMessage";
    private final static String ORDER_PROCESS_NO_SUCCESS_MESSAGE_ATTR = "OrderProcessNoSuccessMessage";
    private static final String DATABASE_ERROR_MESSAGE_ATTR = "errorDatabaseMessage";

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        //HttpSession session = request.getSession(true);
        HttpSession session = request.getSession();
        String locale = (String)session.getAttribute(LOCALE_PARAM);
        MessageManager messageManager = new MessageManager(locale);
        String message;
        int idOrder = Integer.parseInt(request.getParameter(ORDER_ID_PARAM));
        TypeOfAction action  = TypeOfAction.valueOf(request.getParameter(ORDER_ACTION_PARAM).toUpperCase());
        try{
            switch(action){
                case APPROVE_ORDER:
                    if(OrderLogic.giveBook(idOrder)){
                        message = messageManager.getProperty(MessageManager.ORDER_PROCESS_APPROVED_MESSAGE);
                        request.setAttribute(ORDER_PROCESS_APPROVED_MESSAGE_ATTR, message);
                    }else{
                        message = messageManager.getProperty(MessageManager.ORDER_PROCESS_NOT_ENOUGH_MESSAGE);
                        request.setAttribute(ORDER_PROCESS_NO_ENOUGH_MESSAGE_ATTR, message);
                    }
                    break;
                case DENIED_ORDER:
                    OrderLogic.changeOrderState(idOrder, Order.TypeOfOrder.DENIED);
                    message = messageManager.getProperty(MessageManager.ORDER_PROCESS_DENIED_MESSAGE);
                    request.setAttribute(ORDER_PROCESS_DENIED_MESSAGE_ATTR, message);
                    break;
                default:
                    message = messageManager.getProperty(MessageManager.ORDER_PROCESS_NO_SUCCESS_MESSAGE);
                    request.setAttribute(ORDER_PROCESS_NO_SUCCESS_MESSAGE_ATTR, message);
            }
        }catch(LogicException ex){
            request.setAttribute(DATABASE_ERROR_MESSAGE_ATTR, ex.getMessage());
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PATH_JSP);
            return page;
        }
        page = new NewOrdersCommand().execute(request);
        return page;
    }
}
