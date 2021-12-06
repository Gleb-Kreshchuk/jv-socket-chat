package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import lombok.SneakyThrows;
import model.Message;
import util.ConnectionUtil;

public class WriteMessageToDB {
    private Message message = new Message();

    @SneakyThrows
    public WriteMessageToDB(Message message) throws SQLException {
        this.message = message;
    }

    @SneakyThrows
    public void messageWriter() {
        Connection connection = ConnectionUtil.getConnection();

        String query = "INSERT INTO chat_websockets.chat (username, message) "
                + "VALUES (?, ?)";
        PreparedStatement createDriverStatement = connection.prepareStatement(query);
        createDriverStatement.setString(1, message.getName());
        createDriverStatement.setString(2, message.getText());
        createDriverStatement.executeUpdate();

        connection.close();
    }
}
