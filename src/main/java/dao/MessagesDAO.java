package dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MessagesDAO<M> {
    List<M> getAll() throws SQLException;
    Collection<M> getChatUser(int userToId, int userFromId) throws SQLException;
    void put(String getMessageText, int userToId, int userFromId) throws SQLException;
}
