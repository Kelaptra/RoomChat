package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "room")
@Data
public class Room {

//    @Id
//    @Column(name = "room_name")
//    @OneToMany(mappedBy = "room", fetch = FetchType.EAGER)
//    private List<User> users;

    @Id
    @Column(name = "room_name")
    private String room_name;
    @Column(name = "online_members")
    private int online_members;
    @Column(name = "max_members")
    private int max_members;

    public Room(){}

    public Room(String room_name, int max_members) {
        this.room_name = room_name;
        this.max_members = max_members;
    }
    @JsonCreator
    public Room(
            @JsonProperty("room_name") String room_name,
            @JsonProperty("online_members") int online_members,
            @JsonProperty("max_members") int max_members) {
        this.room_name = room_name;
        this.online_members = online_members;
        this.max_members = max_members;
    }
}
