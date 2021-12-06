package coders;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import lombok.SneakyThrows;
import model.Message;

public class MessageEncoder implements Encoder.Text<Message> {
    private static ObjectMapper mapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public String encode(Message message) throws EncodeException {
        return mapper.writeValueAsString(message);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
