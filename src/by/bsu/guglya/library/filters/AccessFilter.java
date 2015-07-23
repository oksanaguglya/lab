package by.bsu.guglya.library.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * This class filter user's access to urls
 */
public class AccessFilter implements Filter {

    private FilterConfig filterConfig;
    private static final String INDEX_PATH = "/index.jsp";
    private static final String USER_ATTR = "user";

    public AccessFilter() {
    }

    /**
     * This method initializes config
     * @param filterConfig
     */
    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * This method filter urls to forward unknown user to start page
     * @param request
     * @param response
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if (req.getSession().getAttribute(USER_ATTR) == null) {
            req.getServletContext().getRequestDispatcher(INDEX_PATH).forward(request, response);
            return;
        }
        filterChain.doFilter(request, response);

    }

    @Override
    public void destroy() {
    }

}

