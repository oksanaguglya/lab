package by.bsu.guglya.library.logic;

import by.bsu.guglya.library.database.dao.OrderDAO;

public class OrderLogic {

    public static boolean addOrder(String idBook, int idUser)
    {
        OrderDAO orderDAO = new OrderDAO();
        boolean result = orderDAO.addOrder(idBook, idUser);
        return result;
    }
}
