package responses;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Column;

public class CreateRoomResponse {
    @JsonProperty("room_name")
    private String room_name;
    @JsonProperty("online_members")
    private int online_members;
    @JsonProperty("max_members")
    private int max_members;

    @JsonCreator
    public CreateRoomResponse(
            @JsonProperty("room_name") String room_name,
            @JsonProperty("online_members") int online_members,
            @JsonProperty("max_members") int max_members) {
        this.room_name = room_name;
        this.online_members = online_members;
        this.max_members = max_members;
    }

    public CreateRoomResponse(){}

    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }
}
