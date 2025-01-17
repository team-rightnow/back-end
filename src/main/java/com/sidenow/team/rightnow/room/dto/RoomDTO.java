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
    private String color; // 여전히 String으로 유지 (입출력 데이터를 위해)
    private Long user_id;

    public static RoomDTO fromEntity(Room room) {
        return new RoomDTO(
                room.getId(),
                room.getColor().name(), // Enum의 name()으로 String 변환
                room.getUser().getId()
        );
    }

    public Room toEntity(User user) {
        return new Room(
                Roomtype.valueOf(this.color), // String -> Enum 변환
                user
        );
    }
}
