package com.sidenow.team.rightnow.character.service;

import com.sidenow.team.rightnow.character.dto.CharacterDTO;
import com.sidenow.team.rightnow.character.entity.Character;
import com.sidenow.team.rightnow.character.repository.CharacterRepository;
import com.sidenow.team.rightnow.global.ex.CustomApiException;
import com.sidenow.team.rightnow.user.entity.User;
import com.sidenow.team.rightnow.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CharacterServiceTest {

    private CharacterService characterService;

    @Mock
    private CharacterRepository characterRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        characterService = new CharacterService(characterRepository, userRepository, null, null);
    }

    @Test
    void createCharacter_Success() {
        User user = User.builder().id(1L).build();
        when(userRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(user));

        CharacterDTO characterDTO = new CharacterDTO(null, "LEON", 1L);

        characterService.createCharacter(1L, characterDTO);

        verify(characterRepository, times(1)).save(any(Character.class));
    }

    @Test
    void getCharacter_Success() {
        User user = User.builder().id(1L).build();
        when(userRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(user));

        Page<Character> characterPage = new PageImpl<>(Collections.singletonList(new Character("LEON", user)));
        when(characterRepository.findByUserId(eq(1L), any(PageRequest.class))).thenReturn(characterPage);

        Page<CharacterDTO> result = characterService.getCharacter(1L, PageRequest.of(0, 10));
        assertEquals(1, result.getTotalElements());
        assertEquals("LEON", result.getContent().get(0).getCharacter());
    }

    @Test
    void updateCharacter_Success() {
        User user = User.builder().id(1L).build();
        Character character = new Character("LEON", user);

        when(characterRepository.findById(1L)).thenReturn(Optional.of(character));
        when(userRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(user));

        CharacterDTO newCharacterDTO = new CharacterDTO(null, "THEO", 1L);

        Character updatedCharacter = characterService.updateCharacter(1L, newCharacterDTO);

        assertNotNull(updatedCharacter);
        assertEquals("THEO", updatedCharacter.getCharacter());
    }

    @Test
    void deleteCharacter_Success() {
        Character character = new Character("LEON", null);
        when(characterRepository.findById(1L)).thenReturn(Optional.of(character));

        boolean result = characterService.deleteCharacter(1L);

        assertTrue(result);
        verify(characterRepository, times(1)).delete(character);
    }
}
