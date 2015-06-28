package by.bsu.guglya.library.commands.order;

import by.bsu.guglya.library.commands.Command;
import by.bsu.guglya.library.logic.LogicException;
import by.bsu.guglya.library.logic.OrderLogic;
import by.bsu.guglya.library.managers.ConfigurationManager;
import by.bsu.guglya.library.managers.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ReturnUserOrderCommand implements Command {

    private final static String LOCALE_PARAM = "locale";
    private final static String ORDER_ID_PARAM = "idOrderReturn";
    private final static String SUCCESS_RETURN_ORDER_MESSAGE_ATTR = "successReturnOrderMessage";
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
        try{
            if(OrderLogic.returnOrder(idOrder)){
                message = messageManager.getProperty(MessageManager.RETURN_USER_ORDER_MESSAGE);
                request.setAttribute(SUCCESS_RETURN_ORDER_MESSAGE_ATTR, message);
            }
        }catch(LogicException ex){
            request.setAttribute(DATABASE_ERROR_MESSAGE_ATTR, ex.getMessage());
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PATH_JSP);
            return page;
        }
        page = new UserOrdersCommand().execute(request);
        return page;
    }
}
