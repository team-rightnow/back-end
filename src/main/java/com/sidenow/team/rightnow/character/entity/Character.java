package com.sidenow.team.rightnow.character.entity;


import com.sidenow.team.rightnow.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private String character;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder

    public Character(String character, User user) {
        this.character = character;
        this.user = user;
    }

    public void update(Character newCharacter){
        this.character = newCharacter.character;

    }
}
