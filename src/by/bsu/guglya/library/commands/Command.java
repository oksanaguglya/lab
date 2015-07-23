package by.bsu.guglya.library.commands;

import javax.servlet.http.HttpServletRequest;
/**
 * This interface implements for pattern command
 * @author Oksana
 */
public interface Command {
    /**
     * This is method which causes methods of business-logic and sends results on jsp
     * @param request a httpServletRequest
     */
    public String execute(HttpServletRequest request);
}