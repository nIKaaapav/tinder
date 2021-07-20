package filters;

import helpers.CookieHelpers;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CookieFilter implements HttpFilter {

    private boolean validate(Cookie c) {
        return true;
    }

    @Override
    public void doHttpFilter(HttpServletRequest rq, HttpServletResponse rs, FilterChain chain) throws ServletException, IOException {
        if (CookieHelpers.read(rq).filter(c -> validate(c)).isPresent()) {
            chain.doFilter(rq, rs);
        } else {
            rs.setStatus(401);
            rs.sendRedirect("/login");
        }
    }
}
