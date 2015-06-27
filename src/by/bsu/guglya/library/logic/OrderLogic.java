package by.bsu.guglya.library.logic;

import by.bsu.guglya.library.beans.Order;
import by.bsu.guglya.library.database.dao.DAOException;
import by.bsu.guglya.library.database.dao.OrderDAO;

import java.util.List;

public class OrderLogic {

    private static final int ITEMS_PER_BASKET_PAGE = 7;
    private static final int ITEMS_PER_ORDERS_PAGE = 5;

    public static boolean checkOrderExist(String idCatalog, int idUser, Order.TypeOfOrder state) throws LogicException{
        OrderDAO orderDAO = new OrderDAO();
        boolean result = false;
        try{
            result = orderDAO.checkOrderExistByUserAndIdCatalogAndState(idCatalog, idUser, state);
        }catch(DAOException ex){
            throw new LogicException(ex.getMessage());
        }
        return result;
    }

    public static boolean addQtyToOrder(String idCatalog, int idUser, int qty,  Order.TypeOfOrder state) throws LogicException{
        OrderDAO orderDAO = new OrderDAO();
        boolean result = false;
        try{
            result = orderDAO.addQtyToOrderByUserAndIdCatalogAndState(idCatalog, idUser, qty, state);
        }catch(DAOException ex){
            throw new LogicException(ex.getMessage());
        }
        return result;
    }

    public static boolean addOrder(String idCatalog, int idUser, int qty,  Order.TypeOfOrder state, String date) throws LogicException{
        OrderDAO orderDAO = new OrderDAO();
        boolean result = false;
        try{
            result = orderDAO.addOrder(idCatalog, idUser, qty, state, date);
        }catch(DAOException ex){
            throw new LogicException(ex.getMessage());
        }
        return result;
    }

    public static boolean delOrder(int idOrder) throws  LogicException{
        OrderDAO orderDAO = new OrderDAO();
        boolean result = false;
        try{
            result = orderDAO.delOrderById(idOrder);
        }catch(DAOException ex){
            throw new LogicException(ex.getMessage());
        }
        return result;
    }

    public static boolean makeOrder(int idUser, Order.TypeOfOrder state, Order.TypeOfOrder newState, String date) throws  LogicException{
        OrderDAO orderDAO = new OrderDAO();
        boolean result = false;
        try{
            result = orderDAO.changeOrderStateAndDateByUserAndState(idUser, state, newState, date);
        }catch(DAOException ex){
            throw new LogicException(ex.getMessage());
        }
        return result;
    }

    public static boolean changeOrderState(int idOrder, Order.TypeOfOrder state) throws  LogicException{
        OrderDAO orderDAO = new OrderDAO();
        boolean result = false;
        try{
            result = orderDAO.changeOrderStateByIdOrder(idOrder, state);
        }catch(DAOException ex){
            throw new LogicException(ex.getMessage());
        }
        return result;
    }

    public static PageItems getUserBasketItems(int idUser, Order.TypeOfOrder state, int pageNo) throws LogicException{
        OrderDAO orderDAO = new OrderDAO();
        int noOfRecords = 0;
        List<Order> items = null;
        try {
            items = orderDAO.getOrderItemsByUserAndState(idUser, state, (pageNo - 1) * ITEMS_PER_BASKET_PAGE, ITEMS_PER_BASKET_PAGE);
            noOfRecords = orderDAO.getOrderItemsByUserAndStateCount(idUser, state);
        } catch (DAOException ex) {
            throw new LogicException(ex.getMessage());
        }
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / ITEMS_PER_BASKET_PAGE);
        return new PageItems(items, noOfPages);
    }

    public static PageItems getUserOrderItems(String searchText, int idUser, Order.TypeOfOrder state, int pageNo) throws LogicException{
        OrderDAO orderDAO = new OrderDAO();
        int noOfRecords = 0;
        List<Order> items = null;
        try {
            items = orderDAO.getOrderItemsBySearchTextAndUserNotInState(searchText, idUser, state, (pageNo - 1) * ITEMS_PER_ORDERS_PAGE, ITEMS_PER_ORDERS_PAGE);
            noOfRecords = orderDAO.getOrderItemsBySearchTextAndUserCount(searchText, idUser, state);
        } catch (DAOException ex) {
            throw new LogicException(ex.getMessage());
        }
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / ITEMS_PER_ORDERS_PAGE);
        return new PageItems(items, noOfPages);
    }

    public static PageItems getNewOrderItems(Order.TypeOfOrder state, int pageNo) throws  LogicException{
        OrderDAO orderDAO = new OrderDAO();
        int noOfRecords = 0;
        List<Order> items = null;
        try {
            items = orderDAO.getOrderItemsByState(state, (pageNo - 1) * ITEMS_PER_ORDERS_PAGE, ITEMS_PER_ORDERS_PAGE);
            noOfRecords = orderDAO.getOrderItemsByStateCount(state);
        } catch (DAOException ex) {
            throw new LogicException(ex.getMessage());
        }
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / ITEMS_PER_ORDERS_PAGE);
        return new PageItems(items, noOfPages);
    }

    public static PageItems getAllOrderItems(String searchText, Order.TypeOfOrder state, int pageNo) throws LogicException{
        OrderDAO orderDAO = new OrderDAO();
        int noOfRecords = 0;
        List<Order> items = null;
        try {
            items = orderDAO.getOrderItemsBySearchTextNotInState(searchText, state, (pageNo - 1) * ITEMS_PER_ORDERS_PAGE, ITEMS_PER_ORDERS_PAGE);
            noOfRecords = orderDAO.getOrderItemsBySearchTextNotInStateCount(searchText, state);
        } catch (DAOException ex) {
            throw new LogicException(ex.getMessage());
        }
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / ITEMS_PER_ORDERS_PAGE);
        return new PageItems(items, noOfPages);
    }

    public static boolean catalogItemActiveInOrder(int idCatalog) throws  LogicException{
        OrderDAO orderDAO = new OrderDAO();
        boolean result = false;
        try{
            result = orderDAO.checkCatalogItemExistInOrder(idCatalog);
        }catch(DAOException ex){
            throw new LogicException(ex.getMessage());
        }
        return result;
    }

    public static boolean giveBook(int idOrder) throws  LogicException{
        OrderDAO orderDAO = new OrderDAO();
        //CatalogDAO catalogDAO = new CatalogDAO();
        boolean result = false;
        //try{
            if(CatalogLogic.catalogItemInLibrary(idOrder)){
                if(CatalogLogic.subCatalogItemQty(idOrder, 1)){
                    OrderLogic.changeOrderState(idOrder, Order.TypeOfOrder.APPROVED);
                    result = true;
                }
            }else{
               result = false;
            }
        //}
        //}//catch(LogicException ex){
            //throw new LogicException(ex.getMessage());
        //}
        return result;
    }

}
