package requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DeleteRoomRequest {
    @JsonProperty("room_name")
    private String room_name;

    @JsonCreator
    public DeleteRoomRequest(@JsonProperty("room_name") String room_name) {
        this.room_name = room_name;
    }

    public DeleteRoomRequest(){}
}
