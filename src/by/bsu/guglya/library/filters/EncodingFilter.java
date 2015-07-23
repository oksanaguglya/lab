package by.bsu.guglya.library.filters;

import javax.servlet.*;
import java.io.IOException;

/**
 * This class is necessary for coding translation
 */
public class EncodingFilter implements Filter {
    private FilterConfig filterConfig = null;
    private String encoding;

    /**
     * This method initializes filter's config
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        encoding = filterConfig.getInitParameter("encoding");
    }

    /**
     * This method checks a request encoding and sets a necessary encoding
     * @param request
     * @param response
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        String encodingFromReq = request.getCharacterEncoding();
        if (!encoding.equalsIgnoreCase(encodingFromReq)) {
            request.setCharacterEncoding(encoding);
            response.setCharacterEncoding(encoding);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
