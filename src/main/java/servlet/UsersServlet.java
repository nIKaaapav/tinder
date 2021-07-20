package servlet;

import helpers.CookieHelpers;
import helpers.FormattedHelpers;
import intity.User;
import service.UsersService;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;


public class UsersServlet extends HttpServlet {
    private Connection dbConn;
    private UsersService serviceUsers;
    private final TemplateEngine te = TemplateEngine.folder("web_content");
    public int count = 1;

    public UsersServlet(Connection dbConn) {
        this.serviceUsers = new UsersService(dbConn);
        this.dbConn = dbConn;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> data = new HashMap<>();
        Optional<User> user;
        Cookie[] cookies = req.getCookies();
        Integer myId = CookieHelpers.getIdUser(cookies).get();

        try {
            int maxUser = serviceUsers.countUsers();
            user = serviceUsers.getUser(count);
            String likedString = serviceUsers.getUser(myId).get().getWhom_liked();
            PreparedStatement stmt = dbConn.prepareStatement("update users SET whom_liked=? where  id=?");

            if (req.getParameter("like") != null) {
                likedString = FormattedHelpers.addToLikedStr(likedString, count - 1);
            }
            if (req.getParameter("dislike") != null) {
                likedString = FormattedHelpers.removeFromLikedStr(likedString, count - 1);
            }

            stmt.setString(1, likedString);
            stmt.setInt(2, myId);
            stmt.executeUpdate();

            if (count > maxUser) {
                count = 0;
                resp.sendRedirect("/liked");
            } else {
                data.put("maxUser", maxUser);
                data.put("count", count);
                data.put("user", user);
                te.render("like-page.ftl", data, resp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        count++;
        if (myId==count) count++;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
