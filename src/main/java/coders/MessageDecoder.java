package coders;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import lombok.SneakyThrows;
import model.Message;

public class MessageDecoder implements Decoder.Text<Message> {
    private static ObjectMapper mapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public Message decode(String s) {
        return mapper.readValue(s, Message.class);
    }

    @Override
    public boolean willDecode(String s) {
        return s != null;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
