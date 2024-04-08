package responses;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Room;

public class GetRoomsResponse {
    @JsonProperty("rooms")
    private Room[] rooms;

    @JsonCreator
    public GetRoomsResponse(@JsonProperty("rooms") Room[] rooms) {
        this.rooms = rooms;
    }

    public GetRoomsResponse(){}

    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }

}
