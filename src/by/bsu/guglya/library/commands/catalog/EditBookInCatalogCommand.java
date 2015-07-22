package by.bsu.guglya.library.commands.catalog;

import by.bsu.guglya.library.commands.Command;

import javax.servlet.http.HttpServletRequest;

public class EditBookInCatalogCommand implements Command {

    private final static String LOCALE_PARAM = "locale";
    private final static String CATALOG_ID_ATTR = "idCatalogEdit";
    private final static String SUCCESS_DEL_BOOK_FROM_CATALOG_MESSAGE_ATTR = "successDelBookFromCatalog";
    private final static String UNSUCCESSFUL_DEL_BOOK_FROM_CATALOG_MESSAGE_ATTR = "unsuccessfulDelBookFromCatalog";
    private static final String DATABASE_ERROR_MESSAGE_ATTR = "errorDatabaseMessage";

    @Override
    public String execute(HttpServletRequest request) {
        return null;
    }

}
