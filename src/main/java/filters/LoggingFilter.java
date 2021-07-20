package filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoggingFilter implements HttpFilter {
    @Override
    public void doHttpFilter(HttpServletRequest rq, HttpServletResponse rs, FilterChain chain) throws ServletException, IOException {
        System.out.printf("HTTP => %s => %s\n", rq.getMethod(), rq.getRequestURI());
        chain.doFilter(rq, rs);
    }
}
