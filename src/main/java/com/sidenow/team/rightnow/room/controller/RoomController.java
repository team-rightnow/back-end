package com.sidenow.team.rightnow.room.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.sidenow.team.rightnow.global.ex.CustomApiException;
import com.sidenow.team.rightnow.character.dto.CharacterDTO;
import com.sidenow.team.rightnow.character.entity.Character;
import com.sidenow.team.rightnow.global.ResponseDto;
import com.sidenow.team.rightnow.room.dto.RoomDTO;
import com.sidenow.team.rightnow.room.entity.Room;
import com.sidenow.team.rightnow.room.service.RoomService;
import com.sidenow.team.rightnow.security.config.auth.LoginUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Slf4j
@RestController
@RequestMapping("/api")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }


    @PostMapping("/rooms")
    public ResponseDto<Void> createRoom(
            @AuthenticationPrincipal LoginUser loginUser,
            @RequestBody RoomDTO roomDTO) {
        try {
            roomService.createRoom(loginUser.getUser().getId(), roomDTO);
            return new ResponseDto<>(ResponseDto.SUCCESS, "방이 성공적으로 생성되었습니다.");
        } catch (CustomApiException e) {
            return new ResponseDto<>(ResponseDto.FAILURE, e.getMessage());
        }
    }

    @GetMapping("selection")
    public ResponseDto<Page<RoomDTO>> getRoom(
            @AuthenticationPrincipal LoginUser loginUser, Pageable pageable) {
        Page<RoomDTO> rooms = roomService.getRoom(loginUser.getUser().getId(), pageable);
        return new ResponseDto<>(ResponseDto.SUCCESS, "방 조회가 완료되었습니다.", rooms);
    }

    @PutMapping("/rooms/{id}")
    public ResponseDto<Void> updateRoom(
            @AuthenticationPrincipal LoginUser loginUser,
            @PathVariable Long id,
            @RequestBody RoomDTO newRoomDTO) {
        Room updatedRoom = roomService.updateRoom(id, newRoomDTO);
        if(updatedRoom != null) {
            return new ResponseDto<>(ResponseDto.SUCCESS, "방 색상 변경이 완료되었습니다.");
        }
        return new ResponseDto<>(ResponseDto.FAILURE, "방 색상 수정에 실패하였습니다.");
    }

    @DeleteMapping("/rooms/{id}")
    public ResponseDto<Void> deleteRoom(@AuthenticationPrincipal LoginUser loginUser,
                                        @PathVariable Long id) {
        boolean isDeleted = roomService.deleteRoom(id);
        if(isDeleted) {
            return new ResponseDto<>(ResponseDto.SUCCESS, "방 색상이 삭제되었습니다.");
        }
        return new ResponseDto<>(ResponseDto.FAILURE, "방 색상 삭제를 실패하였습니다.");
    }
}
