package com.sidenow.team.rightnow.character.dto;

import com.sidenow.team.rightnow.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.sidenow.team.rightnow.character.entity.Character;
import com.sidenow.team.rightnow.character.entity.Charactertype;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CharacterDTO {
    private Long id;
    private String character;
    private Long userId;

    public static CharacterDTO fromEntity(Character character){
        return new CharacterDTO(
                character.getId(),
                character.getCharacter(),
                character.getUser().getId()
        );
    }

    public Character toEntity(User user) {
        return new Character(
                this.character,
                user
        );
    }
}
