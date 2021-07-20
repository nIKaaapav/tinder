package servlet;

import helpers.CookieHelpers;
import helpers.DateCalculator;
import intity.User;
import service.UsersService;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.util.*;

public class LikedListServlet extends HttpServlet {
    private UsersService serviceUsers;
    private final TemplateEngine te = TemplateEngine.folder("web_content");

    public LikedListServlet(Connection dbConn) {
        this.serviceUsers = new UsersService(dbConn);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        Integer myId = CookieHelpers.getIdUser(cookies).get();
        Map<String, Object> data = new HashMap<>();
        Collection <User> listUsers = serviceUsers.getAllLikedUsers(myId);
        listUsers.forEach(user -> user.setTimeDif(new DateCalculator().calcTimeDif((Date)user.getDate())));
        data.put("listUsers", listUsers);
        te.render("liked-list.ftl", data, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
