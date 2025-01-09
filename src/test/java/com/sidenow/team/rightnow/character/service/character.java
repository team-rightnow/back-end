package com.sidenow.team.rightnow.character.service;


import com.sidenow.team.rightnow.acorn.service.AcornService;
import com.sidenow.team.rightnow.character.dto.CharacterDTO;
import com.sidenow.team.rightnow.character.entity.Character;
import com.sidenow.team.rightnow.character.repository.CharacterRepository;
import com.sidenow.team.rightnow.character.service.CharacterService;
import com.sidenow.team.rightnow.global.ex.CustomApiException;
import com.sidenow.team.rightnow.user.entity.User;
import com.sidenow.team.rightnow.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


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


@ExtendWith(MockitoExtension.class)
public class character {

class CharacterServiceTest {

    private CharacterService characterService;


    @Mock
    private CharacterRepository characterRepository;

    @Mock
    private UserRepository userRepository;


    @Mock
    private AcornService acornService;

    @InjectMocks
    private CharacterService characterService;

    @Test
    void testUpdateCharacterWithInsufficientAcorns() {
        // 테스트 데이터 생성
        Long userId = 1L;
        Long characterId = 1L;
        CharacterDTO characterDTO = new CharacterDTO(characterId, "테스트 캐릭터", userId); // CharacterDTO는 상황에 맞게 생성자 정의
        User user = mock(User.class);

        // 도토리 부족 상태 시뮬레이션
        when(userRepository.findByIdAndDeletedFalse(userId)).thenReturn(Optional.of(user));
        when(user.getAcornCount()).thenReturn(1); // 도토리 잔액이 1인 경우

        // 예외 검증
        CustomApiException exception = assertThrows(CustomApiException.class, () -> {
            characterService.updateCharacter(characterId, characterDTO);
        });

        assertEquals("도토리가 부족합니다. 현재 잔액: 1", exception.getMessage());

        // 메서드 호출 여부 확인
        verify(userRepository, times(1)).findByIdAndDeletedFalse(userId);
        verify(user, times(1)).getAcornCount();
        verify(acornService, never()).withdrawAcorn(userId, 2); // 도토리 차감 메서드는 호출되지 않아야 함

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
