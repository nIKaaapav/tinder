package filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface HttpFilter extends Filter {
    @Override
    default void init(FilterConfig filterConfig) { }

    @Override
    default void destroy() { }

    default boolean isHttp(ServletRequest rq, ServletResponse rs) {
        return rq instanceof HttpServletRequest && rs instanceof HttpServletResponse;
    }

    void doHttpFilter(HttpServletRequest rq, HttpServletResponse rs, FilterChain chain) throws ServletException, IOException;

    @Override
    default void doFilter(ServletRequest rq0, ServletResponse rs0, FilterChain chain) throws IOException, ServletException {
        if (isHttp(rq0, rs0)) {
            HttpServletRequest rq = (HttpServletRequest) rq0;
            HttpServletResponse rs = (HttpServletResponse) rs0;
            doHttpFilter(rq, rs, chain);
        } else {
            chain.doFilter(rq0, rs0);
        }
    }

}
