package com.sidenow.team.rightnow.character.dto;

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
    private String userId;

    public static CharacterDTO fromEntity(Character character){
        return new CharacterDTO(
                character.getId(),
                character.getCharacter(),
                character.getUserId()
        );
    }

    public Character toEntity() {
        return new Character(
                this.id,
                this.character,
                this.userId
        );
    }
}
