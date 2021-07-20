package servlet;

import helpers.CookieHelpers;
import intity.MessageUser;
import intity.User;
import service.MessagesService;
import service.UsersService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.*;

public class MessagesServlet extends HttpServlet  {
    private final TemplateEngine te = TemplateEngine.folder("web_content");
    private final UsersService usersService;
    private final MessagesService messagesService;

    public MessagesServlet(Connection dbConn) {
        this.usersService = new UsersService(dbConn);
        this.messagesService = new MessagesService(dbConn);
    }

    private static int toInt (String s){
        return Integer.parseInt(s);
    }

    private static Optional<Integer> parseInt (String s){
        try{
            int value = toInt(s);
            return Optional.of(value);
        } catch (NumberFormatException x){
            return  Optional.empty();
        }
    }

    private static Optional<Integer> getPathChat (HttpServletRequest rq) {
        String path = rq.getPathInfo();
        if (path.charAt(0) == '/') {
            path = path.substring(1);
        }
        if (parseInt(path).isPresent()) {
            return Optional.of(parseInt(path).get());
        } else {
            return Optional.empty();
        }
    }

    @Override
    protected void doGet(HttpServletRequest rq, HttpServletResponse rs) throws IOException {
        try {
       if (getPathChat(rq).isPresent()){
           Cookie[] cookies = rq.getCookies();
           Integer userCurrentId = CookieHelpers.getIdUser(cookies).get();
           Integer userChatId = getPathChat(rq).get();
           User userChat = usersService.getUser(userChatId).get();

           Collection<MessageUser> massages = messagesService.getChatUser(userCurrentId, userChatId);
           Map<String, Object> data = new  HashMap<>();

           data.put("messages", massages);
           data.put("currentUser", userCurrentId);
           data.put("userChat", userChat.getName());

           te.render("chat.ftl", data, rs);
       } else {
           rs.sendRedirect("/users");
       }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest rq, HttpServletResponse rs) throws IOException {
        StringBuilder pathChat = new StringBuilder();
        pathChat.append("/messages/");
        if (getPathChat(rq).isPresent()) {
            Integer userToId = getPathChat(rq).get();
            pathChat.append(userToId);
            Cookie[] cookies = rq.getCookies();
            Integer userFromId = CookieHelpers.getIdUser(cookies).get();

            messagesService.putMessages(userToId, userFromId, rq.getParameter("msg"));

            rs.sendRedirect(pathChat.toString());
        }  else {
            rs.sendRedirect("/users");
        }
    }
}
