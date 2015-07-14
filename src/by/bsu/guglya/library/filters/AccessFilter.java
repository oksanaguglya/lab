package by.bsu.guglya.library.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AccessFilter implements Filter {

    private FilterConfig filterConfig;
    private static final String INDEX_PATH = "/index.jsp";
    private static final String USER_ATTR = "user";
    //private static final String ERROR_PATH_JSP = "jsp/error.jsp";


    public AccessFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if (req.getSession().getAttribute(USER_ATTR) != null) {
            filterChain.doFilter(request, response);
        } else {
            //Если ресурс находится в другом контексте, то необходимо предварительно получить контекст методом
            req.getServletContext().getRequestDispatcher(INDEX_PATH).forward(request, response);
            filterChain.doFilter(request, response);
        }

       /* if(((boolean)(session.getAttribute("timeout"))) && (session.getAttribute("user") != null)){
            req.getServletContext().getRequestDispatcher(ERROR_PATH_JSP).forward(request, response);*/

    }

    @Override
    public void destroy() {
    }

}

