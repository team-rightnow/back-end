package com.sidenow.team.rightnow.room.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.sidenow.team.rightnow.room.entity.Room;
import com.sidenow.team.rightnow.room.entity.Roomtype;
import com.sidenow.team.rightnow.user.entity.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {
    private Long id;
    private String color;
    private Long user_id;

    public static RoomDTO fromEntity(Room room) {
        return new RoomDTO(
                room.getId(),
                room.getColor().name(),
                room.getUser().getId()
        );
    }

    public Room toEntity(User user) {
        return new Room(
                Roomtype.valueOf(this.color),
                user
        );
    }
}
