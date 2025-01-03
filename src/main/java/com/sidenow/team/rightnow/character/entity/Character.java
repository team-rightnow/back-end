package com.sidenow.team.rightnow.character.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Entity(name = "characters")
@Getter
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private String character;

    @NotBlank
    private String userId;

    public Character() {

    }

    public Character(long id, String character, String userId) {
        this.id = id;
        this.character = character;
        this.userId = userId;
    }

    public Character(String character, String userId) {
        this.character = character;
        this.userId = userId;
    }

    public static Character toEntity(Character character, Long id) {
        return  new Character(id, character.character, character.userId);
    }

    public void update(Character newCharacter){
        this.character = newCharacter.character;
        this.userId = newCharacter.userId;
    }
}
