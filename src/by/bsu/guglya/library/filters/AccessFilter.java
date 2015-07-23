package by.bsu.guglya.library.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccessFilter implements Filter {

    private FilterConfig filterConfig;
    private static final String INDEX_PATH = "/index.jsp";
    private static final String USER_ATTR = "user";

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

