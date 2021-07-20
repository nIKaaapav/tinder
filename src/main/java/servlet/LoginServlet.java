package servlet;

import helpers.CookieHelpers;
import intity.User;
import service.UsersService;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;

public class LoginServlet extends HttpServlet {
    private final UsersService usersService;
    private final TemplateEngine te = TemplateEngine.folder("web_content");


    private int expTime = 60 * 60; // 60 min

    public LoginServlet(Connection dbConn) {
        this.usersService = new UsersService(dbConn);
    }

    @Override
    protected void doGet(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
        HashMap<String, Object> data = new HashMap<>();
        data.put("message", "");
        te.render("login.ftl", data, rs);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Optional<String> oemail = Optional.ofNullable(req.getParameter("email"));

        Optional<Integer> id = oemail
                .flatMap(name ->
                        Optional.ofNullable(req.getParameter("password"))
                                .flatMap(pass -> {
                                            try {
                                                return usersService.getIdUser(name, pass);
                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                            }
                                            return null;
                                        }
                                )
                );

        if (id.isPresent()) {
            //set date
            usersService.updateUserDate(id.get());
            // set cookie
            Cookie c = new Cookie(CookieHelpers.cookieName, id.get().toString());

            c.setMaxAge(expTime);
            c.setPath("/");
            res.addCookie(c);

            // redirect
            res.sendRedirect("/users");
        } else {
            HashMap<String, Object> data = new HashMap<>();
            data.put("message", "wrong user/password combination given");
            te.render("login.ftl", data, res);
        }
    }
}
