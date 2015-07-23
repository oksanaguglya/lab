package by.bsu.guglya.library.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * This class is necessary for filtering avoid urls
 */
public class SessionFilter implements Filter {

    private FilterConfig filterConfig;
    private ArrayList<String> urlList;
    private static final String INDEX_PATH = "/index.jsp";

    public SessionFilter() {
    }

    /**
     * This method initializes avoid urls
     * @param filterConfig
     */
    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        String urls = filterConfig.getInitParameter("avoid-urls");
        StringTokenizer token = new StringTokenizer(urls, ",");
        urlList = new ArrayList<String>();
        while (token.hasMoreTokens()) {
            urlList.add(token.nextToken());
        }
    }

    /**
     * This method filters urls to forward user to start page from avoid urls
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
        String url = req.getServletPath();
        boolean allowedRequest = false;

        if(urlList.contains(url)) {
            allowedRequest = true;
        }

        if (!allowedRequest) {
            HttpSession session = req.getSession(false);
            if (session == null) {
                req.getServletContext().getRequestDispatcher(INDEX_PATH).forward(request, response);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}
