package endpoint;

import coders.MessageDecoder;
import coders.MessageEncoder;
import dao.LoadInitialData;
import dao.WriteMessageToDB;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import lombok.SneakyThrows;
import model.Message;

@ServerEndpoint(value = "/chat/{user}",
        decoders = {MessageDecoder.class}, encoders = {MessageEncoder.class})

public class ChatEndpoint {

    private static List<Session> sessionList = new LinkedList<>();
    private Session session = null;
    private String username = "";

    @SneakyThrows
    @OnOpen
    public void onOpen(Session session, @PathParam("user") String username) {
        this.session = session;
        this.username = username;
        sessionList.add(session);
        LoadInitialData initialData = new LoadInitialData(session);
        initialData.load();
    }

    @OnClose
    public void onClose(Session session) {
        sessionList.remove(session);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }

    @SneakyThrows
    @OnMessage
    public void onMessage(Session session, Message message) {
        message.setName(this.username);
        sessionList.forEach(s -> {
            if (s == this.session) {
                return;
            }
            try {
                s.getBasicRemote().sendObject(message);
            } catch (IOException | EncodeException e) {
                e.printStackTrace();
            }
        });
        WriteMessageToDB writeMessage = new WriteMessageToDB(message);
        writeMessage.messageWriter();
    }
}
