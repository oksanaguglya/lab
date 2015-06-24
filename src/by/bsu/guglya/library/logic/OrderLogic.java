package by.bsu.guglya.library.logic;

import by.bsu.guglya.library.beans.Order;
import by.bsu.guglya.library.database.dao.DAOException;
import by.bsu.guglya.library.database.dao.OrderDAO;

public class OrderLogic {

    public static boolean orderExist(String idBook, int idUser, Order.TypeOfOrder state) throws LogicException{
        OrderDAO orderDAO = new OrderDAO();
        boolean result = false;
        try{
            result = orderDAO.orderExist(idBook, idUser, state);
        }catch(DAOException ex){
            throw new LogicException(ex.getMessage());
        }
        return result;
    }

    public static boolean addQtyToOrder(String idBook, int idUser, int qty,  Order.TypeOfOrder state) throws LogicException{
        OrderDAO orderDAO = new OrderDAO();
        boolean result = false;
        try{
            result = orderDAO.addQtyToOrder(idBook, idUser, qty, state);
        }catch(DAOException ex){
            throw new LogicException(ex.getMessage());
        }
        return result;
    }

    public static boolean addOrder(String idBook, int idUser, int qty,  Order.TypeOfOrder state) throws LogicException{
        OrderDAO orderDAO = new OrderDAO();
        boolean result = false;
        try{
            result = orderDAO.addOrder(idBook, idUser, qty, state);
        }catch(DAOException ex){
            throw new LogicException(ex.getMessage());
        }
        return result;
    }

    public static boolean delOrder(int idOrder) throws  LogicException{
        OrderDAO orderDAO = new OrderDAO();
        boolean result = false;
        try{
            result = orderDAO.delOrder(idOrder);
        }catch(DAOException ex){
            throw new LogicException(ex.getMessage());
        }
        return result;
    }

    public static boolean makeOrder(int idUser) throws  LogicException{
        OrderDAO orderDAO = new OrderDAO();
        boolean result = false;
        try{
            result = orderDAO.makeOrder(idUser);
        }catch(DAOException ex){
            throw new LogicException(ex.getMessage());
        }
        return result;
    }

}
