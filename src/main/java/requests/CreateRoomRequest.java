package requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Data;
import model.Room;
import responses.CreateRoomResponse;

@Data
public class CreateRoomRequest {
    @JsonProperty("room_name")
    private String room_name;
    @JsonProperty("max_members")
    private int max_members;

    @JsonCreator
    public CreateRoomRequest(
            @JsonProperty("room_name") String room_name,
            @JsonProperty("max_members") int max_members) {
        this.room_name = room_name;
        this.max_members = max_members;
    }

    public CreateRoomRequest(){}

    public CreateRoomResponse generateResponse(){
        return new CreateRoomResponse(room_name, 1, max_members);
    }


}
