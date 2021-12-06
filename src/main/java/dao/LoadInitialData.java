package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.websocket.EncodeException;
import javax.websocket.Session;
import lombok.SneakyThrows;
import model.Message;
import util.ConnectionUtil;

public class LoadInitialData {
    private Message message = new Message();
    private Session session = null;

    @SneakyThrows
    public LoadInitialData(Session session) throws SQLException, EncodeException, IOException {
        this.session = session;
    }

    @SneakyThrows
    public void load() {
        Connection connection = ConnectionUtil.getConnection();
        String query = "select a.username, a.message from "
                + "(select * from chat_websockets.chat order by id desc limit 50) a order by id";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            String chatName = resultSet.getString(1);
            String chatMessage = resultSet.getString(2);
            message.setName(chatName);
            message.setText(chatMessage);
            session.getBasicRemote().sendObject(message);
        }
        connection.close();
    }
}
