package by.bsu.guglya.library.logic;

import by.bsu.guglya.library.beans.CatalogItem;
import by.bsu.guglya.library.database.dao.CatalogDAO;
import by.bsu.guglya.library.database.dao.DAOException;
import by.bsu.guglya.library.database.dao.OrderDAO;

import java.util.List;

public class CatalogLogic {

    private static final int ITEMS_PER_CATALOG_PAGE = 5;

    public static PageItems getCatalogItems(String searchText, int pageNo) throws LogicException{
        CatalogDAO catalogDAO = new CatalogDAO();
        int noOfRecords = 0;
        List<CatalogItem> items = null;
        try {
            items = catalogDAO.getCatalogItems(searchText, (pageNo - 1) * ITEMS_PER_CATALOG_PAGE, ITEMS_PER_CATALOG_PAGE);
            noOfRecords = catalogDAO.getCatalogItemsCount(searchText);
        } catch (DAOException ex) {
            throw new LogicException(ex.getMessage());
        }
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / ITEMS_PER_CATALOG_PAGE);
        return new PageItems(items, noOfPages);
    }

    public static boolean delCatalogItem(int idCatalog) throws  LogicException{
        CatalogDAO catalogDAO = new CatalogDAO();
        boolean result = false;
        try{
            result = catalogDAO.delCatalogItem(idCatalog);
        }catch(DAOException ex){
            throw new LogicException(ex.getMessage());
        }
        return result;
    }

    public static boolean subCatalogItemQty(int idOrder, int qty) throws  LogicException{
        CatalogDAO catalogDAO = new CatalogDAO();
        boolean result = false;
        try{
            result = catalogDAO.subCatalogItemQty(idOrder, qty);
        }catch(DAOException ex){
            throw new LogicException(ex.getMessage());
        }
        return result;
    }
}
