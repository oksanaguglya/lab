package by.bsu.guglya.library.customtags;

import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.ResourceBundle;

public class PaginationOutTag extends TagSupport {

    private static final String RESOURCE_PATH = "resources.gui";
    private static final String FORM_NAME = "form.pagination.prev_next";
    private static final String FORM_NAME_ID = "form.pagination.id";
    private static final String ACTIVE_LINK = "active.link";
    private static final String ALIAS_PAGE = "1";
    private static final int LINKS_PER_PAGE = 5;
    private static final String FORM_PREV_NAME = "goPreviousPageForm";
    private static final String FORM_NEXT_NAME = "goNextPageForm";
    private static final String FORM_MID_NAME = "goPageForm";
    private static final String FORM_PREV_CLASS = "pagination_prev";
    private static final String FORM_NEXT_CLASS = "pagination_next";

    private int currentPage;
    private String command;
    private int numberOfPages;

    private static Logger logger = Logger.getLogger(PaginationOutTag.class);

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    @Override
    public int doStartTag() throws JspException {
        ResourceBundle resource = ResourceBundle.getBundle(RESOURCE_PATH);
        JspWriter writer = pageContext.getOut();
        try {
            writer.println("<div class=\"pagination\">");
            writer.println("<ul>");
            if (currentPage > 1) {
                writer.println("<li>");
                writer.println(String.format(resource.getString(FORM_NAME), FORM_PREV_NAME, command, (currentPage - 1),
                        FORM_PREV_NAME, FORM_PREV_CLASS, "<"));
                writer.println("</li>");
            }

            int firstNum, lastNum;
            if ((currentPage % LINKS_PER_PAGE) == 0) {
                firstNum = currentPage - (LINKS_PER_PAGE - 1);
                lastNum = currentPage;
            } else {
                firstNum = (currentPage / LINKS_PER_PAGE) * LINKS_PER_PAGE + 1;
                lastNum = (firstNum + (LINKS_PER_PAGE - 1) < numberOfPages ? firstNum + (LINKS_PER_PAGE - 1) : numberOfPages);
            }

            for (int i = firstNum; i <= lastNum; i++) {
                if (currentPage == i) {
                    writer.println("<li class=\"pagination_active\">");
                    writer.println(resource.getString(ACTIVE_LINK).replace(ALIAS_PAGE, String.valueOf(i)));
                    writer.println("</li>");
                } else {
                    writer.println("<li>");
                    writer.println(String.format(resource.getString(FORM_NAME_ID), FORM_MID_NAME, i ,command, i,
                            FORM_MID_NAME, i, i));
                    writer.println("</li>");
                }
            }

            if (currentPage < numberOfPages) {
                writer.println("<li>");
                writer.println(String.format(resource.getString(FORM_NAME), FORM_NEXT_NAME, command, (currentPage + 1),
                        FORM_NEXT_NAME, FORM_NEXT_CLASS, ">"));
                writer.println("</li>");
            }
            writer.println("</ul>");
            writer.println("</div>");
        } catch (IOException ex) {
            logger.error(ex);
        }
        return SKIP_BODY;
    }

}

