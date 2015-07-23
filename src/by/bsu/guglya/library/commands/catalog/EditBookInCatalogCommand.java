package by.bsu.guglya.library.commands.catalog;

import by.bsu.guglya.library.commands.Command;
import by.bsu.guglya.library.logic.BookLogic;
import by.bsu.guglya.library.logic.CatalogLogic;
import by.bsu.guglya.library.logic.LogicException;
import by.bsu.guglya.library.managers.ConfigurationManager;
import by.bsu.guglya.library.managers.MessageManager;
import by.bsu.guglya.library.model.InputException;
import by.bsu.guglya.library.model.Validator;
import by.bsu.guglya.library.model.beans.Book;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class EditBookInCatalogCommand implements Command {

    private final static String LOCALE_PARAM = "locale";
    private final static String TITLE_PARAM = "title";
    private final static String AUTHOR_PARAM = "author";
    private final static String YEAR_PARAM = "year";
    private final static String BOOK_TYPE_PARAM = "bookType";
    private final static String QUANTITY_PARAM = "quantity";
    private final static String CATALOG_ID_ATTR = "idCatalogEdit";
    private final static String RESULT_MESSAGE_ATTR = "resultMessage";
    private static final String DATABASE_ERROR_MESSAGE_ATTR = "errorDatabaseMessage";

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        //HttpSession session = request.getSession(true);
        HttpSession session = request.getSession();
        String locale = (String) session.getAttribute(LOCALE_PARAM);
        MessageManager messageManager = new MessageManager(locale);
        String title = request.getParameter(TITLE_PARAM);
        String author = request.getParameter(AUTHOR_PARAM);
        String year_str = request.getParameter(YEAR_PARAM);
        Book.TypeOfBook bookType = Book.TypeOfBook.valueOf(request.getParameter(BOOK_TYPE_PARAM));
        String quantity_str = request.getParameter(QUANTITY_PARAM);
        int idCatalog = (int)session.getAttribute(CATALOG_ID_ATTR);
        try {
            String message;
            if (Validator.validatorCatalogItemInput(title, author, year_str, quantity_str)) {
                int year = Integer.parseInt(year_str);
                int quantity = Integer.parseInt(quantity_str);
                if (CatalogLogic.changeCatalogItem(idCatalog, title, author, year, bookType, quantity)) {
                    message = messageManager.getProperty(MessageManager.EDIT_BOOK_IN_CATALOG_SUCCESS_MESSAGE);
                } else {
                    message = messageManager.getProperty(MessageManager.EDIT_BOOK_IN_CATALOG_UNSUCCESS_MESSAGE);
                }
                request.setAttribute(RESULT_MESSAGE_ATTR, message);
            }
        } catch (InputException ex) {
            request.setAttribute(RESULT_MESSAGE_ATTR, messageManager.getProperty(ex.getMessage()));
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.BOOK_EDITOR_CATALOG_PATH_JSP);
            return page;
        } catch (LogicException ex) {
            request.setAttribute(DATABASE_ERROR_MESSAGE_ATTR, ex.getMessage());
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PATH_JSP);
            return page;
        }
        page = new CatalogCommand().execute(request);
        return page;
    }

}
