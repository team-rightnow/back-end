package com.sidenow.team.rightnow.character.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import com.sidenow.team.rightnow.character.dto.CharacterDTO;
import com.sidenow.team.rightnow.character.entity.Character;
import com.sidenow.team.rightnow.character.service.CharacterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;

@RestController
@RequestMapping("/api/")
public class CharacterController {

    private final CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @PostMapping("/characters")
    public ResponseEntity<Void> create(@RequestBody CharacterDTO characterDTO){
        Character newCharacter = characterDTO.toEntity();  // 인스턴스 메소드로 호출
        characterService.createCharacter(newCharacter);    // 저장하는 로직
        return ResponseEntity.created(URI.create("/characters/" + newCharacter.getId())).build();
    }

    @GetMapping("/characters")
    public ResponseEntity<List<CharacterDTO>> read(){
        List<CharacterDTO> characterDTOs = characterService.getAllCharacters().stream()
                .map(CharacterDTO::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(characterDTOs);
    }

    @PutMapping("/characters/{id}")
    public ResponseEntity<Void> update(@RequestBody CharacterDTO newCharacterDTO, @PathVariable Long id) {
        Character updatedCharacter = characterService.updateCharacter(id, newCharacterDTO.toEntity());
        return updatedCharacter != null ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }


    @DeleteMapping("/characters/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean isDeleted = characterService.deleteCharacter(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
