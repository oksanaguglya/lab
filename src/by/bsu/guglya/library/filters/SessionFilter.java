package by.bsu.guglya.library.filters;

import by.bsu.guglya.library.managers.ConfigurationManager;
import by.bsu.guglya.library.managers.MessageManager;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class SessionFilter implements Filter {

        private FilterConfig filterConfig;
        private ArrayList<String> urlList;
        private static final String INDEX_PATH = "/index.jsp";

        public SessionFilter() {
        }

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
