package by.bsu.guglya.library.commands;

import by.bsu.guglya.library.beans.User;
import by.bsu.guglya.library.logic.OrderLogic;
import by.bsu.guglya.library.managers.ConfigurationManager;
import by.bsu.guglya.library.managers.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class OrderBooksCommand implements Command {
    private final static String USER_ATTR = "user";
    private final static String LOCALE_PARAM = "locale";
    private final static String SUCCESS_ORDER_MESSAGE_ATTR = "successOrderMessage";
    private final static String ORDER_NO_CHECKS_MESSAGE_ATTR = "orderNoChecksMessage";
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String locale = (String)session.getAttribute(LOCALE_PARAM);
        MessageManager messageManager = new MessageManager(locale);
        String selectedItems = request.getParameter("selectedItems");
        if (selectedItems != null) {
            if(selectedItems.equals("")){
                String message = messageManager.getProperty(MessageManager.ORDER_NO_CHECKS_MESSAGE);
                request.setAttribute(ORDER_NO_CHECKS_MESSAGE_ATTR, message);
            }else{
                String[] selectedItemsArray = selectedItems.split(",");
                User user = (User)session.getAttribute(USER_ATTR);
                int idUser = user.getIdUser();
                for(String idBook : selectedItemsArray){
                    if(OrderLogic.addOrder(idBook, idUser)){
                    }
                }
                String message = messageManager.getProperty(MessageManager.ORDER_SUCCESS_MESSAGE);
                request.setAttribute(SUCCESS_ORDER_MESSAGE_ATTR, message);
            }
        }
        String page = new CatalogCommand().execute(request);
        return page;
    }
}
