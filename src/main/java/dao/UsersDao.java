package dao;

import intity.User;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

public interface UsersDao<U> {
    Optional<User> getUserFromDB(int userId) throws SQLException;

    void addUser(U item);

    Optional<Integer> getId(U value);

    Optional<Integer> getIdByEmailPassword(String email, String password) throws SQLException;

    Collection<User> getAllLiked(int userId);

    void updateDate(int valueId);

    Integer countUsers() throws SQLException;
}
