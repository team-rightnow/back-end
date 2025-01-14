package com.sidenow.team.rightnow.character.service;

import com.sidenow.team.rightnow.acorn.service.AcornService;
import com.sidenow.team.rightnow.character.dto.CharacterDTO;
import com.sidenow.team.rightnow.character.entity.Character;
import com.sidenow.team.rightnow.character.entity.Charactertype;
import com.sidenow.team.rightnow.character.repository.CharacterRepository;
import com.sidenow.team.rightnow.global.ex.CustomApiException;
import com.sidenow.team.rightnow.user.entity.User;
import com.sidenow.team.rightnow.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CharacterServiceTest {

    @Mock
    private CharacterRepository characterRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AcornService acornService;

    @InjectMocks
    private CharacterService characterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateCharacter_WhenAcornsInsufficient_ShouldThrowException() {
        // Given
        Long userId = 1L;
        Charactertype charactertype = Charactertype.THEO;  // Charactertype으로 변경
        Long characterId = 1L;
        CharacterDTO characterDTO = new CharacterDTO(characterId, charactertype, userId);
        User user = mock(User.class);

        when(userRepository.findByIdAndDeletedFalse(userId)).thenReturn(Optional.of(user));
        when(user.getAcornCount()).thenReturn(1);

        // When & Then
        CustomApiException exception = assertThrows(CustomApiException.class, () ->
                characterService.updateCharacter(characterId, characterDTO)
        );

        assertEquals("도토리가 부족합니다. 현재 잔액: 1", exception.getMessage());

        verify(userRepository, times(1)).findByIdAndDeletedFalse(userId);
        verify(user, times(1)).getAcornCount();
        verify(acornService, never()).withdrawAcorn(userId, 2);
    }

    @Test
    void createCharacter_WhenValid_ShouldSaveCharacter() {
        // Given
        User user = User.builder().id(1L).build();
        CharacterDTO characterDTO = new CharacterDTO(null, Charactertype.LEON, 1L);  // Charactertype으로 변경

        when(userRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(user));

        // When
        characterService.createCharacter(1L, characterDTO);

        // Then
        verify(characterRepository, times(1)).save(any(Character.class));
    }

    @Test
    void getCharacter_WhenValid_ShouldReturnCharacters() {
        // Given
        User user = User.builder().id(1L).build();
        Page<Character> characterPage = new PageImpl<>(Collections.singletonList(new Character(Charactertype.LEON, user)));

        when(userRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(user));
        when(characterRepository.findByUserId(eq(1L), any(PageRequest.class))).thenReturn(characterPage);

        // When
        Page<CharacterDTO> result = characterService.getCharacter(1L, PageRequest.of(0, 10));

        // Then
        assertEquals(1, result.getTotalElements());
        assertEquals(Charactertype.LEON, result.getContent().get(0).getCharacter());
    }

    @Test
    void updateCharacter_WhenValid_ShouldUpdateCharacter() {
        // Given
        User user = User.builder().id(1L).build();
        Character character = new Character(Charactertype.LEON, user);
        CharacterDTO newCharacterDTO = new CharacterDTO(null, Charactertype.THEO, 1L);  // Charactertype으로 변경

        when(characterRepository.findById(1L)).thenReturn(Optional.of(character));
        when(userRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(user));

        // When
        Character updatedCharacter = characterService.updateCharacter(1L, newCharacterDTO);

        // Then
        assertNotNull(updatedCharacter);
        assertEquals(Charactertype.THEO, updatedCharacter.getCharacter());
    }

    @Test
    void deleteCharacter_WhenValid_ShouldDeleteCharacter() {
        // Given
        Character character = new Character(Charactertype.LEON, null);

        when(characterRepository.findById(1L)).thenReturn(Optional.of(character));

        // When
        boolean result = characterService.deleteCharacter(1L);

        // Then
        assertTrue(result);
        verify(characterRepository, times(1)).delete(character);
    }
}
