package utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;
import messages.Message;

public class MessageDecoder implements Decoder.Text<Message> {
    @Override
    public Message decode(String s) throws DecodeException {
        Message message;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            message = objectMapper.readValue(s, Message.class);
            return message;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean willDecode(String s) {
        if(s == null) {
            return false;
        }
        try {
            return decode(s) != null;
        } catch (DecodeException e) {
            throw new RuntimeException(e);
        }
    }
}
