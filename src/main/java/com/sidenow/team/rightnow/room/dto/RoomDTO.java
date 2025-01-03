package com.sidenow.team.rightnow.room.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.sidenow.team.rightnow.room.entity.Room;
import com.sidenow.team.rightnow.room.entity.Roomtype;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {
    private Long id;
    private String color;
    private String user_id;

    public static RoomDTO fromEntity(Room room) {
        return new RoomDTO(
                room.getId(),
                room.getColor(),
                room.getUser_id()
        );
    }

    public Room toEntity() {
        return new Room(
             this.id, this.color, this.user_id
        );
    }
}
