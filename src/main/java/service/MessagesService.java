package service;

import dao.MessagesDAOSql;
import intity.MessageUser;

import java.sql.Connection;
import java.util.Collection;

public class MessagesService {
    private final MessagesDAOSql messagesDAOSql;

    public MessagesService(Connection connection) {
        messagesDAOSql = new MessagesDAOSql(connection);
    }

    public Collection<MessageUser> getChatUser(int userToId, int userFromId) {
        return messagesDAOSql.getChatUser(userToId, userFromId);
    }

    public void putMessages(int userToId, int userFromId, String text) {
        messagesDAOSql.put(text, userToId, userFromId);
    }

}
