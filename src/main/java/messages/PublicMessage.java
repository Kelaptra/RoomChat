package messages;

import jakarta.websocket.EncodeException;
import jakarta.websocket.Session;
import lombok.Builder;
import lombok.Data;

import java.io.IOException;
import java.util.Map;

@Data
@Builder
public class PublicMessage implements Message {
    private String sender;
    private String content;

    @Override
    public String getReceiver() {
        return null;
    }

    @Override
    public void sendMessage(Map<String, Session> sessions) {
        sessions.forEach((key, value) -> {
            try {
                value.getBasicRemote().sendObject(this);
            } catch (IOException | EncodeException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
