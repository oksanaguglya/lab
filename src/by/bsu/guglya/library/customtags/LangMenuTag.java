package by.bsu.guglya.library.customtags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;
/**
 * This tag prints menu which allows to change language of the interface
 * @author Oksana Guglya
 */
public class LangMenuTag extends TagSupport {

    public static final String RESOURCE_PATH = "by.bsu.guglya.library.resources.gui";
    public static final String CHOICE_RU = "choice.ru";
    public static final String CHOICE_EN = "choice.en";
    public static final String ALIAS_PAGE = "1";
    /**
     * This is logger which print some messages to log file
     */
    private static Logger logger = Logger.getLogger(LangMenuTag.class);
    /**
     * This is an address of page to on which will is realized transition
     * after change the language
     */
    private String forwardString;
    /**
     * This method set value to parameter forwardString
     */
    public void setForwardString(String forwardString) {
        this.forwardString = forwardString;
    }
    /**
     * This method prints menu which allows to change language of the interface
     * @return SKIP_BODY
     * @throws javax.servlet.jsp.JspException a JspException
     */
    @Override
    public int doStartTag() throws JspException {
        ResourceBundle resource = ResourceBundle.getBundle(RESOURCE_PATH);
        JspWriter writer = pageContext.getOut();
        try {
            writer.print(resource.getString(CHOICE_RU).replace(ALIAS_PAGE, forwardString));
            writer.print(resource.getString(CHOICE_EN).replace(ALIAS_PAGE, forwardString));
        } catch (IOException ex) {
            logger.error(ex);
        }
        return SKIP_BODY;
    }

}
