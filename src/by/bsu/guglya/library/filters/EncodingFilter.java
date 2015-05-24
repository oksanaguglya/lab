package by.bsu.guglya.library.filters;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {
    private FilterConfig filterConfig = null;
    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        encoding = filterConfig.getInitParameter("encoding");
    }

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
