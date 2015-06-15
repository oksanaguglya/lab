package by.bsu.guglya.library.logic;

import by.bsu.guglya.library.beans.OrderItem;
import by.bsu.guglya.library.database.dao.OrderDAO;

public class OrderLogic {

    public static boolean addOrder(String idBook, int idUser, int qty)
    {
        OrderDAO orderDAO = new OrderDAO();
        boolean result = orderDAO.addOrder(idBook, idUser, qty);
        return result;
    }

    public static boolean orderExist(String idBook, int idUser, int state)
    {
        OrderDAO orderDAO = new OrderDAO();
        boolean result = orderDAO.orderExist(idBook, idUser, state);
        return result;
    }

    public static boolean addQtyToOrder(String idBook, int idUser, int qty, int state)
    {
        OrderDAO orderDAO = new OrderDAO();
        boolean result = orderDAO.addQtyToOrder(idBook, idUser, qty, state);
        return result;
    }

}
