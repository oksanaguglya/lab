package by.bsu.guglya.library.customtags;

import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.ResourceBundle;

public class PaginationOutTag extends TagSupport {

    public static final String RESOURCE_PATH = "by.bsu.guglya.library.resources.gui";
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
            writer.println("<span class=\"pagination-wrap\"><div class=\"pagination-wrap2\"><div class=\"pagination\">");
            writer.println("<ul>");
            if(currentPage > 1){
                writer.println("<li>");
                writer.println("<form name=\"goPreviousPageForm\" method=\"POST\" action=\"LibraryServlet\">");
                writer.println("<input type=\"hidden\" name=\"command\" value=\"" + command + "\">");
                writer.println("<input type=\"hidden\" name=\"page\" value=\"" + (currentPage - 1) + "\">");
                writer.println(" <A HREF=\"javascript:document.goPreviousPageForm.submit()\" class=\"pagination_prev\"><</A>");
                writer.println(" </form>");
                writer.println("</li>");
            }

            for(int i = 1; i <= numberOfPages; i++){
                if( currentPage == i){
                    writer.println("<li class=\"pagination_active\">");
                    writer.println("<a href=\"#\">" + i + "</a>");
                    writer.println("</li>");
                }else{
                    writer.println("<li>");
                    writer.println("<form id=\"goPageForm" + i + "\" method=\"POST\" action=\"LibraryServlet\">");
                    writer.println("<input type=\"hidden\" name=\"command\" value=\"" + command + "\">");
                    writer.println("<input type=\"hidden\" name=\"page\" value=\"" + i + "\">");
                    writer.println("<A HREF=\"javascript:document.getElementById('goPageForm" + i + "').submit()\">" + i + "</A>");
                    writer.println(" </form>");
                    writer.println("</li>");
                }
            }

            if(currentPage < numberOfPages){
                writer.println("<li>");
                writer.println("<form name=\"goNextPageForm\" method=\"POST\" action=\"LibraryServlet\">");
                writer.println("<input type=\"hidden\" name=\"command\" value=\"" + command + "\">");
                writer.println("<input type=\"hidden\" name=\"page\" value=\"" + (currentPage + 1) + "\">");
                writer.println(" <A HREF=\"javascript:document.goNextPageForm.submit()\" class=\"pagination_next\">></A>");
                writer.println(" </form>");
                writer.println("</li>");
            }
            writer.println("</ul>");
            writer.println("</div></div></span>");
        } catch (IOException ex) {
            logger.error(ex);
        }
        return SKIP_BODY;
    }
}

