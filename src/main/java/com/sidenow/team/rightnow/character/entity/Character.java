package com.sidenow.team.rightnow.character.entity;

import com.sidenow.team.rightnow.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "characters")
@Getter
@NoArgsConstructor
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Charactertype character;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public Character(Charactertype character, User user) {
        this.character = character;
        this.user = user;
    }

    public void update(Charactertype newCharacterType) {
        this.character = newCharacterType;
    }
}
