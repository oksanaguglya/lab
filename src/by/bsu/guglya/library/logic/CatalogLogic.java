package by.bsu.guglya.library.logic;

import by.bsu.guglya.library.database.dao.CatalogDAO;
import by.bsu.guglya.library.database.dao.DAOException;
import  by.bsu.guglya.library.model.beans.*;

import java.util.List;

public class CatalogLogic {

    private static final int ITEMS_PER_CATALOG_PAGE = 5;

    public static PageItems getCatalogItems(String searchText, int pageNo) throws LogicException{
        CatalogDAO catalogDAO = CatalogDAO.getInstance();
        int noOfRecords = 0;
        List<CatalogItem> items = null;
        try {
            items = catalogDAO.getCatalogItemsBySearchText(searchText, (pageNo - 1) * ITEMS_PER_CATALOG_PAGE, ITEMS_PER_CATALOG_PAGE);
            noOfRecords = catalogDAO.getCatalogItemsBySearchTextCount(searchText);
        } catch (DAOException ex) {
            throw new LogicException(ex.getMessage());
        }
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / ITEMS_PER_CATALOG_PAGE);
        return new PageItems(items, noOfPages);
    }

    public static boolean delCatalogItem(int idCatalog) throws  LogicException{
        CatalogDAO catalogDAO = CatalogDAO.getInstance();
        boolean result = false;
        try{
            result = catalogDAO.delCatalogItemById(idCatalog);
        }catch(DAOException ex){
            throw new LogicException(ex.getMessage());
        }
        return result;
    }

    public static boolean addCatalogItem(int idBook, int quantity) throws  LogicException{
        CatalogDAO catalogDAO = CatalogDAO.getInstance();
        boolean result = false;
        try{
            result = catalogDAO.addCatalogItem(idBook, quantity);
        }catch(DAOException ex){
            throw new LogicException(ex.getMessage());
        }
        return result;
    }

    public static boolean addNewCatalogItem(String title, String author, int year, Book.TypeOfBook bookType, int quantity) throws  LogicException{
        CatalogDAO catalogDAO = CatalogDAO.getInstance();
        boolean result = false;
        try{
            result = catalogDAO.addNewCatalogItem(title, author, year, bookType, quantity);
        }catch(DAOException ex){
            throw new LogicException(ex.getMessage());
        }
        return result;
    }

    public static boolean checkCatalogItemExist(int idBook) throws  LogicException{
        CatalogDAO catalogDAO = CatalogDAO.getInstance();
        boolean result = false;
        try{
            result = catalogDAO.checkCatalogItemExist(idBook);
        }catch(DAOException ex){
            throw new LogicException(ex.getMessage());
        }
        return result;
    }

    public static CatalogItem getCatalogItem(int idCatalogItem) throws  LogicException{
        CatalogDAO catalogDAO = CatalogDAO.getInstance();
        CatalogItem catalogItem;
        try{
            catalogItem = catalogDAO.getCatalogItem(idCatalogItem);
        }catch(DAOException ex){
            throw new LogicException(ex.getMessage());
        }
        return catalogItem;
    }

    public static boolean changeCatalogItem(int idCatalog, String title, String author, int year, Book.TypeOfBook bookType, int quantity) throws  LogicException{
        CatalogDAO catalogDAO = CatalogDAO.getInstance();
        boolean result = false;
        try{
            result = catalogDAO.changeCatalogItem(idCatalog, title, author, year, bookType, quantity);
        }catch(DAOException ex){
            throw new LogicException(ex.getMessage());
        }
        return result;
    }

}
