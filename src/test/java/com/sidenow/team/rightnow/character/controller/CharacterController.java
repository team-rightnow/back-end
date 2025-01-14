/*
package com.sidenow.team.rightnow.character.controller;

import com.sidenow.team.rightnow.character.dto.CharacterDTO;
import com.sidenow.team.rightnow.character.entity.Character;
import com.sidenow.team.rightnow.character.service.CharacterService;
import com.sidenow.team.rightnow.global.ResponseDto;
import com.sidenow.team.rightnow.global.ex.CustomApiException;
import com.sidenow.team.rightnow.security.config.auth.LoginUser;
import com.sidenow.team.rightnow.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CharacterControllerTest {

    @Mock
    private CharacterService characterService;

    @InjectMocks
    private CharacterController characterController;

    private LoginUser loginUser;
    private CharacterDTO characterDTO;
    private User mockUser;

    

    @Test
    void createCharacter_Success() {
        // Given
        doNothing().when(characterService).createCharacter(anyLong(), any(CharacterDTO.class));

        // When
        ResponseDto<Void> response = characterController.createCharacter(loginUser, characterDTO);

        // Then
        assertEquals(ResponseDto.SUCCESS, response.getCode());
        assertEquals("캐릭터가 성공적으로 생성되었습니다.", response.getMsg());
        verify(characterService, times(1)).createCharacter(1L, characterDTO);
    }

    @Test
    void createCharacter_Failure() {
        // Given
        doThrow(new CustomApiException("에러 발생"))
                .when(characterService).createCharacter(anyLong(), any(CharacterDTO.class));

        // When
        ResponseDto<Void> response = characterController.createCharacter(loginUser, characterDTO);

        // Then
        assertEquals(ResponseDto.FAILURE, response.getCode());
        assertEquals("에러 발생", response.getMsg());
        verify(characterService, times(1)).createCharacter(1L, characterDTO);
    }

    @Test
    void getCharacter_Success() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Page<CharacterDTO> page = new PageImpl<>(Collections.singletonList(characterDTO));
        when(characterService.getCharacter(anyLong(), any(Pageable.class))).thenReturn(page);

        // When
        ResponseDto<Page<CharacterDTO>> response = characterController.getCharacter(loginUser, pageable);

        // Then
        assertEquals(ResponseDto.SUCCESS, response.getCode());
        assertEquals("캐릭터 조회가 완료되었습니다.", response.getMsg());
        assertEquals(1, response.getData().getTotalElements());
        verify(characterService, times(1)).getCharacter(1L, pageable);
    }

    @Test
    void updateCharacter_Success() {
        // Given
        Character updatedCharacter = new Character("LEON", mockUser);
        when(characterService.updateCharacter(anyLong(), any(CharacterDTO.class))).thenReturn(updatedCharacter);

        // When
        ResponseDto<Void> response = characterController.updateCharacter(loginUser, 1L, characterDTO);

        // Then
        assertEquals(ResponseDto.SUCCESS, response.getCode());
        assertEquals("캐릭터 수정이 완료되었습니다.", response.getMsg());
        verify(characterService, times(1)).updateCharacter(1L, characterDTO);
    }

    @Test
    void updateCharacter_Failure() {
        // Given
        when(characterService.updateCharacter(anyLong(), any(CharacterDTO.class))).thenReturn(null);

        // When
        ResponseDto<Void> response = characterController.updateCharacter(loginUser, 1L, characterDTO);

        // Then
        assertEquals(ResponseDto.FAILURE, response.getCode());
        assertEquals("캐릭터 수정에 실패하였습니다.", response.getMsg());
        verify(characterService, times(1)).updateCharacter(1L, characterDTO);
    }

    @Test
    void deleteCharacter_Success() {
        // Given
        when(characterService.deleteCharacter(anyLong())).thenReturn(true);

        // When
        ResponseDto<Void> response = characterController.deleteCharacter(loginUser, 1L);

        // Then
        assertEquals(ResponseDto.SUCCESS, response.getCode());
        assertEquals("캐릭터 삭제가 완료되었습니다.", response.getMsg());
        verify(characterService, times(1)).deleteCharacter(1L);
    }

    @Test
    void deleteCharacter_Failure() {
        // Given
        when(characterService.deleteCharacter(anyLong())).thenReturn(false);

        // When
        ResponseDto<Void> response = characterController.deleteCharacter(loginUser, 1L);

        // Then
        assertEquals(ResponseDto.FAILURE, response.getCode());
        assertEquals("캐릭터 삭제에 실패하였습니다.", response.getMsg());
        verify(characterService, times(1)).deleteCharacter(1L);
    }
}

 */