package daoImpl;

import dao.UsersDao;
import helpers.FormattedHelpers;
import intity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UsersDao<User> {
    private Connection dbConn;

    public UserDAOImpl(Connection dbConn) {
        this.dbConn = dbConn;
    }

    @Override
    public void addUser(User item) {

    }

    @Override
    public Optional<User> getUserFromDB(int userId) throws SQLException {
        User user = null;
        PreparedStatement stmt = dbConn.prepareStatement("select id, name, surname, email, gender, urlimg, whom_liked " +
                "from users where id=?");
        {
            stmt.setInt(1, userId);
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                String surname = result.getString("surname");
                String email = result.getString("email");
                String urlImg = result.getString("urlimg");
                String gender = result.getString("gender");
                String whom_liked = result.getString("whom_liked");
                user = new User(id, name, surname, email, gender, urlImg, whom_liked);
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }


    @Override
    public Optional<Integer> getId(User value) {
        return Optional.empty();
    }

    @Override
    public Optional<Integer> getIdByEmailPassword(String email, String password) throws SQLException {
        PreparedStatement stmt = dbConn.prepareStatement("select id from users where  email=? and password=?");
        stmt.setString(1, email);
        stmt.setString(2, password);
        ResultSet result = stmt.executeQuery();
        while (result.next()) {
            Integer id = result.getObject("id", Integer.class);
            return Optional.of(id);
        }
        return Optional.empty();
    }

    @Override
    public Collection<User> getAllLiked(int userId) {
        ArrayList<User> users = new ArrayList<>();
        String whom_liked ="";

        try (PreparedStatement pstm = dbConn.prepareStatement("" +
                "SELECT whom_liked FROM users WHERE id=?")) {
            pstm.setInt(1, userId);
            ResultSet result = pstm.executeQuery();
            while (result.next()) {
                whom_liked = result.getString("whom_liked");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Integer> listUsersId = FormattedHelpers.getListIDLikedUsers(whom_liked);
        for (Integer item : listUsersId) {
            try (PreparedStatement pstm = dbConn.prepareStatement("" +
                    "SELECT id, name, gender, urlimg, position, date FROM users WHERE id=?")) {
                pstm.setInt(1, item);
                ResultSet result = pstm.executeQuery();
                while (result.next()) {
                    User user = new User(
                            result.getInt("id"),
                            result.getString("name"),
                            result.getString("urlimg"),
                            result.getString("position"),
                            result.getDate("date")
                    );
                    users.add(user);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    @Override
    public void updateDate(int userId){
        try(PreparedStatement ps = dbConn.prepareStatement(
                "UPDATE users SET date = ? WHERE id = ?")) {
            ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            ps.setInt(2, userId);
            ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Integer countUsers() throws SQLException {
        int count = 0;
        PreparedStatement stmt = dbConn.prepareStatement("select max(id) from users");
        ResultSet result = stmt.executeQuery();
        while (result.next()) {
            count = result.getInt(1);
        }
        return count;
    }
}
