package messages;

import jakarta.websocket.EncodeException;
import jakarta.websocket.Session;
import lombok.Builder;
import lombok.Data;

import java.io.IOException;
import java.util.Map;

@Data
@Builder
public class PrivateMessage implements Message {
    private String sender;
    private String receiver;
    private String content;

    @Override
    public void sendMessage(Map<String, Session> sessions) {
        try {
            sessions.get(receiver).getBasicRemote().sendObject(this);
            sessions.get(sender).getBasicRemote().sendObject(this);
        } catch (IOException | EncodeException e) {
            throw new RuntimeException(e);
        }
    }
}
