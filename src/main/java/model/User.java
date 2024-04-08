package model;

import jakarta.persistence.*;

@Entity
@Table(name = "session")
public class User {
    @Id
    @Column(name = "username")
    private String username;
    @ManyToOne
    private String room_name;

//    @JoinColumn(name = "room_name")
//    @ManyToOne
//    private Room room;

    public User(String username, String room_name) {
        this.username = username;
        this.room_name = room_name;
    }

    public User(){}
}
