package com.sidenow.team.rightnow.room.entity;

import com.sidenow.team.rightnow.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Room")
@Getter
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING) // EnumType.STRING으로 Enum 값을 문자열로 저장
    private Roomtype color; // String -> Roomtype으로 변경

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public Room(Roomtype color, User user) { // String -> Roomtype으로 변경
        this.color = color;
        this.user = user;
    }

    public void update(Room newRoom) {
        this.color = newRoom.color;
    }
}
