package by.bsu.guglya.library.commands.navigation;

import by.bsu.guglya.library.commands.Command;
import by.bsu.guglya.library.logic.CatalogLogic;
import by.bsu.guglya.library.logic.LogicException;
import by.bsu.guglya.library.managers.ConfigurationManager;
import by.bsu.guglya.library.model.beans.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GoToEditBookInCatalogPageCommand implements Command {

    private final static String CATALOG_ID_PARAM = "idCatalogEdit";
    private final static String TITLE_ATTR = "title";
    private final static String AUTHOR_ATTR = "author";
    private final static String YEAR_ATTR = "year";
    private final static String BOOK_TYPE_ATTR = "bookType";
    private final static String QUANTITY_ATTR = "quantity";
    private final static String CATALOG_ID_ATTR = "idCatalogEdit";
    private static final String DATABASE_ERROR_MESSAGE_ATTR = "errorDatabaseMessage";

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession(true);
        int idCatalog;
        if(request.getParameter(CATALOG_ID_PARAM) != null){
            idCatalog = Integer.parseInt(request.getParameter(CATALOG_ID_PARAM));
        }else{
            idCatalog = (int)session.getAttribute(CATALOG_ID_ATTR);
        }
        CatalogItem catalogItem;
        try{
             catalogItem = CatalogLogic.getCatalogItem(idCatalog);
        }catch(LogicException ex){
            request.setAttribute(DATABASE_ERROR_MESSAGE_ATTR, ex.getMessage());
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PATH_JSP);
            return page;
        }
        request.setAttribute(TITLE_ATTR, catalogItem.getBook().getTitle());
        request.setAttribute(AUTHOR_ATTR, catalogItem.getBook().getAuthor());
        request.setAttribute(YEAR_ATTR, catalogItem.getBook().getYear());
        request.setAttribute(BOOK_TYPE_ATTR, catalogItem.getBook().getType().toString());
        request.setAttribute(QUANTITY_ATTR, catalogItem.getQuantity());
        session.setAttribute(CATALOG_ID_ATTR, idCatalog);
        page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.BOOK_EDITOR_CATALOG_PATH_JSP);
        return page;
    }

}
