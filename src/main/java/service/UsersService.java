package service;

import daoImpl.UserDAOImpl;
import intity.User;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

public class UsersService {
    private final UserDAOImpl daoUsers;

    public UsersService(Connection dbConn) {
        this.daoUsers=new UserDAOImpl(dbConn);
    }

    public Optional<User> getUser(int userId) throws SQLException {
        return daoUsers.getUserFromDB(userId);
    }

    public void updateUserDate(int userId){
        daoUsers.updateDate(userId);
    }

    @SneakyThrows
    public Optional<Integer> getIdUser(String email, String password) throws SQLException {
        return daoUsers.getIdByEmailPassword(email, password);
    }


    public Collection<User> getAllLikedUsers(int id){
        return daoUsers.getAllLiked(id);
    }

    public Integer countUsers() throws SQLException {
        return daoUsers.countUsers();
    }

}
