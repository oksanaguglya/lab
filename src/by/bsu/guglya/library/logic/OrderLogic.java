package by.bsu.guglya.library.logic;

import by.bsu.guglya.library.database.dao.DAOException;
import by.bsu.guglya.library.database.dao.OrderDAO;

public class OrderLogic {

    public static boolean addOrder(String idBook, int idUser, int qty)
    {
        OrderDAO orderDAO = new OrderDAO();
        boolean result = false;
        try{
            result = orderDAO.addOrder(idBook, idUser, qty);
        }catch(DAOException ex){

        }
        return result;
    }

    public static boolean orderExist(String idBook, int idUser, int state)
    {
        OrderDAO orderDAO = new OrderDAO();
        boolean result = false;
        try{
            result = orderDAO.orderExist(idBook, idUser, state);
        }catch(DAOException ex){

        }

        return result;
    }

    public static boolean addQtyToOrder(String idBook, int idUser, int qty, int state)
    {
        OrderDAO orderDAO = new OrderDAO();
        boolean result = false;
        try{
            result = orderDAO.addQtyToOrder(idBook, idUser, qty, state);
        }catch(DAOException ex){

        }

        return result;
    }

    public static boolean delOrder(int idUser, int idOrder)
    {
        OrderDAO orderDAO = new OrderDAO();
        boolean result = false;
        try{
            result = orderDAO.delOrder(idUser, idOrder);
        }catch(DAOException ex){

        }
        return result;
    }

    public static boolean makeOrder(int idUser)
    {
        OrderDAO orderDAO = new OrderDAO();
        boolean result = false;
        try{
            result = orderDAO.makeOrder(idUser);
        }catch(DAOException ex){

        }
        return result;
    }

}
