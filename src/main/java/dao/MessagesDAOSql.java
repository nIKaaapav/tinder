package dao;

import intity.MessageUser;
import intity.User;
import lombok.SneakyThrows;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MessagesDAOSql implements MessagesDAO<MessageUser> {
    private final Connection connection;


    private static String selectAll = "select * from messages";
    private static String select = "select t.nameUserFrom, t.urlimgUserFrom, t.idUserFrom, u.id idUserTo, u.name nameUserTo, u.urlimg urlimgUserTo, t.text, t.data from (select urlimg urlimgUserFrom, name nameUserFrom, ut.id idUserFrom, text, data, userto from messages join users ut on messages.userfrom = ut.id where (messages.userto = ? and messages.userfrom = ?) or (messages.userfrom = ? and messages.userto= ?)) t join users u on userto= u.id";

    private static String put = "insert into messages (userto, userfrom, text) VALUES (?,?, ?)";

    public MessagesDAOSql(Connection connection) {
        this.connection = connection;
    }

    @SneakyThrows
    @Override
    public List<MessageUser> getAll() throws SQLException {
        PreparedStatement ps = connection.prepareStatement(selectAll);
        ResultSet rs = ps.executeQuery();
        List<MessageUser> msg = new ArrayList<>();
        while (rs.next()){
            int userTo = rs.getInt("userTo");
            int userFrom = rs.getInt("userFrom");
            String text = rs.getString("text");
            Date data = rs.getDate("data");

        }
        return null;
    }

    @Override
    public Collection<MessageUser> getChatUser(int userToId, int userFromId) {

        ArrayList<MessageUser> messageUsers = new ArrayList<>();

        try( PreparedStatement ps = connection.prepareStatement(select)){
            ps.setInt(1, userToId);
            ps.setInt(2, userFromId);
            ps.setInt(3, userToId);
            ps.setInt(4, userFromId);

            ResultSet rset = ps.executeQuery();

            while (rset.next()){
                String nameUserFrom = rset.getString("nameUserFrom");
                String urlimgUserFrom = rset.getString("urlimgUserFrom");
                String nameUserTo = rset.getString("nameUserTo");
                String urlimgUserTo = rset.getString("urlimgUserTo");
                int idUserFrom = rset.getInt("idUserFrom");
                int idUserTo = rset.getInt("idUserTo");
                String text = rset.getString("text");
                String data = rset.getString("data");
                User userTo = new User(idUserTo, nameUserTo, urlimgUserTo);
                User userFrom = new User(idUserFrom, nameUserFrom, urlimgUserFrom);

                messageUsers.add(new MessageUser(userTo, userFrom, text, data));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return messageUsers;
    }

    @Override
    public void put(String getMessageText, int userToId, int userFromId) {
        try( PreparedStatement ps = connection.prepareStatement("insert into messages (userto, userfrom, text, data) VALUES (?,?, ?, ?)")) {
            ps.setInt(1, userToId);
            ps.setInt(2, userFromId);
            ps.setString(3, getMessageText);
            ps.setString(4, LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm a")));

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
