package by.bsu.guglya.library.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AccessFilter implements Filter {

    private FilterConfig filterConfig;
    private static final String INDEX_PATH = "/index.jsp";

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
       //Если ресурс находится в другом контексте, то необходимо предварительно получить контекст методом
       req.getServletContext().getRequestDispatcher(INDEX_PATH).forward(request, response);
       //filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}

