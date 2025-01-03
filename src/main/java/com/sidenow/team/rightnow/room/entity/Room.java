package com.sidenow.team.rightnow.room.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Entity(name = "Room")
@Getter
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private String color;

    @NotBlank
    private String user_id;

    public Room(Long id, String color, String user_id){
        this.id = id;
        this.color = color;
        this.user_id = user_id;
    }

    public Room(String color, String user_id){
        this.color = color;
        this.user_id = user_id;
    }

    public static Room toEntity(Room room, Long id) {
        return new Room(id, room.color, room.user_id);
    }

    public void update(Room newRoom) {
        this.color = newRoom.color;
        this.user_id = newRoom.user_id;
    }


}
