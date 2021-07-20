package servlet;

import filters.CookieFilter;
import filters.LoggingFilter;
import helpers.DBConnectionHelper;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.DispatcherType;
import javax.servlet.Servlet;
import java.sql.Connection;
import java.util.EnumSet;

public class WebServer {
    public void main() throws Exception {
        Connection dbConn = new DBConnectionHelper().getConnection();

        String whereTo = "/login";

        ServletHolder holderHome = new ServletHolder("static-home", DefaultServlet.class);
        holderHome.setInitParameter("resourceBase", "./web_content/static");
        holderHome.setInitParameter("dirAllowed", "true");
        holderHome.setInitParameter("pathInfoOnly", "true");
        ServletContextHandler handler = new ServletContextHandler();
        handler.addServlet(new ServletHolder(new UsersServlet(dbConn)), "/users");
        handler.addServlet(new ServletHolder(new MessagesServlet(dbConn)), "/messages/*");
        handler.addServlet(new ServletHolder(new LoginServlet(dbConn)), "/login");
        handler.addServlet(new ServletHolder(new LikedListServlet(dbConn)), "/liked");
        handler.addServlet(holderHome, "/static/*");
        Servlet redirectServlet = new RedirectServlet(whereTo);
        ServletHolder servletWrapped = new ServletHolder(redirectServlet);
        handler.addServlet(servletWrapped, "/*");

        handler.addFilter(LoggingFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
        handler.addFilter(CookieFilter.class, "/users", EnumSet.of(DispatcherType.REQUEST));

        Server server = new Server(8080);
        server.setHandler(handler);
        server.start();
        server.join();
    }

}
