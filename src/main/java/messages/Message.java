package messages;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import jakarta.websocket.Session;

import java.util.Map;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = PrivateMessage.class, name = "PrivateMessage"),
        @JsonSubTypes.Type(value = PublicMessage.class, name = "PublicMessage")
})
public interface Message {
    String getSender();
    String getReceiver();
    String getContent();
    void sendMessage(Map<String, Session> sessions);
}
