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
import com.sidenow.team.rightnow.global.ResponseDto;
import com.sidenow.team.rightnow.security.config.auth.LoginUser;
import com.sidenow.team.rightnow.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import com.sidenow.team.rightnow.user.repository.UserRepository;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;

@RestController
@RequestMapping("/api/")
@Slf4j
public class CharacterController {

    private final CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping("selection")
    public ResponseDto<Page<CharacterDTO>> getCharacter(
            @AuthenticationPrincipal LoginUser loginUser, Pageable pageable) {
        Page<CharacterDTO> characters = characterService.getCharacter(loginUser.getUser().getId(), pageable);
        return new ResponseDto<>(ResponseDto.SUCCESS, "캐릭터 조회가 완료되었습니다.", characters);
    }


    @PutMapping("/characters/{id}")
    public ResponseDto<Void> updateCharacter(
            @AuthenticationPrincipal LoginUser loginUser,
            @PathVariable Long id,
            @RequestBody CharacterDTO newCharacterDTO) {
        Character updatedCharacter = characterService.updateCharacter(id, newCharacterDTO);
        if (updatedCharacter != null) {
            return new ResponseDto<>(ResponseDto.SUCCESS, "캐릭터 수정이 완료되었습니다.");
        }
        return new ResponseDto<>(ResponseDto.FAILURE, "캐릭터 수정에 실패하였습니다.");
    }


    @DeleteMapping("/characters/{id}")
    public ResponseDto<Void> deleteCharacter(@AuthenticationPrincipal LoginUser loginUser,
                                             @PathVariable Long id) {
        boolean isDeleted = characterService.deleteCharacter(id);
        if(isDeleted) {
            return new ResponseDto<>(ResponseDto.SUCCESS, "캐릭터 삭제가 완료되었습니다.");
        }
        return new ResponseDto<>(ResponseDto.FAILURE, "캐릭터 삭제에 실패하였습니다.");
    }

}

