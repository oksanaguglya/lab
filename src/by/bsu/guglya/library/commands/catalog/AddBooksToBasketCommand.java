package by.bsu.guglya.library.commands.catalog;

import by.bsu.guglya.library.beans.Order;
import by.bsu.guglya.library.beans.User;
import by.bsu.guglya.library.commands.Command;
import by.bsu.guglya.library.logic.LogicException;
import by.bsu.guglya.library.logic.OrderLogic;
import by.bsu.guglya.library.managers.ConfigurationManager;
import by.bsu.guglya.library.managers.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class AddBooksToBasketCommand implements Command {

    private HashMap<String, String> orderBooks = new HashMap<String, String>();
    private final static String USER_ATTR = "user";
    private final static String LOCALE_PARAM = "locale";
    private final static String SUCCESS_ORDER_MESSAGE_ATTR = "successOrderMessage";
    private final static String ORDER_NO_CHECKS_MESSAGE_ATTR = "orderNoChecksMessage";
    private final static String NUM_OF_ORDERS_MESSAGE_ATTR = "numOfOrdersMessage";
    private final static String NUM_OF_SUCCESS_ORDERS_MESSAGE_ATTR = "numOfSuccessOrdersMessage";
    private static final String DATABASE_ERROR_MESSAGE_ATTR = "errorDatabaseMessage";

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        //HttpSession session = request.getSession(true);
        HttpSession session = request.getSession();
        String locale = (String)session.getAttribute(LOCALE_PARAM);
        MessageManager messageManager = new MessageManager(locale);
        String selectedItems = request.getParameter("selectedItems");
        String selectedItemsQty = request.getParameter("selectedItemsQty");
        if (selectedItems != null) {
            if(selectedItems.equals("")){
                String message = messageManager.getProperty(MessageManager.ORDER_NO_CHECKS_MESSAGE);
                request.setAttribute(ORDER_NO_CHECKS_MESSAGE_ATTR, message);
            }else{
                String[] selectedItemsArray = selectedItems.split(",");
                String[] selectedItemsQtyArray = selectedItemsQty.split(",");
                for(int i = 0; i < selectedItemsArray.length; i++){
                    orderBooks.put(selectedItemsArray[i], selectedItemsQtyArray[i]);
                }
                User user = (User)session.getAttribute(USER_ATTR);
                int idUser = user.getId();
                int qty = 0;
                Order.TypeOfOrder state = Order.TypeOfOrder.NEW;
                int numOfOrders = 0;
                int numOfSuccessOrders = 0;
                Date d = new Date();
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                String date = format.format(d);
                for(String idCatalog : selectedItemsArray){
                    numOfOrders++;
                    qty = Integer.parseInt(orderBooks.get(idCatalog));
                    if(qty != 0){
                        try{
                            if(OrderLogic.checkOrderExist(idCatalog, idUser, state)){
                                if(OrderLogic.addQtyToOrder(idCatalog, idUser, qty, state)){
                                    numOfSuccessOrders++;
                                }
                            }else{
                                if(OrderLogic.addOrder(idCatalog, idUser, qty, state, date)){
                                    numOfSuccessOrders++;
                                }
                            }
                        } catch(LogicException ex){
                            request.setAttribute(DATABASE_ERROR_MESSAGE_ATTR, ex.getMessage());
                            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PATH_JSP);
                            return page;
                        }
                    }
                }
                String message = messageManager.getProperty(MessageManager.ORDER_SUCCESS_MESSAGE);
                request.setAttribute(SUCCESS_ORDER_MESSAGE_ATTR, message);
                request.setAttribute(NUM_OF_SUCCESS_ORDERS_MESSAGE_ATTR, numOfSuccessOrders);
                request.setAttribute(NUM_OF_ORDERS_MESSAGE_ATTR, numOfOrders);
            }
        }
        page = new CatalogCommand().execute(request);
        return page;
    }
}
