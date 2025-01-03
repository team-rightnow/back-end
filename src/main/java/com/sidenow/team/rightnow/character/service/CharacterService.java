package com.sidenow.team.rightnow.character.service;

import com.sidenow.team.rightnow.character.entity.Character;
import com.sidenow.team.rightnow.character.repository.CharacterRepository;
import com.sidenow.team.rightnow.character.dto.CharacterDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CharacterService {

    private final CharacterRepository characterRepository;

    public CharacterService(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    public Character createCharacter(Character character) {
        return characterRepository.save(character);
    }

    public List<Character> getAllCharacters() {
        return characterRepository.findAll();
    }

    public Optional<Character> getCharacterById(Long id) {
        return characterRepository.findById(id);
    }

    public List<Character> getCharactersByUserId(String userId) {
        return characterRepository.findByUserId(userId);
    }

    public Character updateCharacter(Long id, Character newCharacter) {
        Optional<Character> existingCharacter = characterRepository.findById(id);
        if (existingCharacter.isPresent()) {
            Character character = existingCharacter.get();
            character.update(newCharacter);
            return characterRepository.save(character);
        }
        return null;
    }

    public boolean deleteCharacter(Long id) {
        Optional<Character> character = characterRepository.findById(id);
        if (character.isPresent()) {
            characterRepository.delete(character.get());
            return true;
        }
        return false;
    }

    public List<CharacterDTO> getAllCharacterDTOs() {
        return characterRepository.findAll().stream()
                .map(CharacterDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
