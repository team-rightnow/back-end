package com.sidenow.team.rightnow.room.service;

import com.sidenow.team.rightnow.acorn.service.AcornService;
import com.sidenow.team.rightnow.global.ex.CustomApiException;
import com.sidenow.team.rightnow.room.dto.RoomDTO;
import com.sidenow.team.rightnow.room.entity.Room;
import com.sidenow.team.rightnow.room.entity.Roomtype;
import com.sidenow.team.rightnow.room.repository.RoomRepository;
import com.sidenow.team.rightnow.user.entity.User;
import com.sidenow.team.rightnow.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final AcornService acornService;

    @Transactional
    public void createRoom(Long userId, RoomDTO roomDTO) {
        User user = userRepository.findByIdAndDeletedFalse(userId)
                .orElseThrow(() -> new CustomApiException("존재하지 않는 userId 입니다."));


        acornService.withdrawAcorn(userId, 5);


        Roomtype color = Roomtype.valueOf(roomDTO.getColor().toUpperCase());

        Room room = Room.builder()
                .color(color)
                .user(user)
                .build();

        roomRepository.save(room);
    }

    @Transactional(readOnly = true)
    public Page<RoomDTO> getRoom(Long userId, Pageable pageable) {
        User user = userRepository.findByIdAndDeletedFalse(userId)
                .orElseThrow(() -> new CustomApiException("존재하지 않는 userId 입니다."));

        return roomRepository.findByUserId(userId, pageable)
                .map(RoomDTO::fromEntity);
    }

    @Transactional
    public Room updateRoom(Long id, RoomDTO newRoomDTO) {
        Room existingRoom = roomRepository.findById(id)
                .orElseThrow(() -> new CustomApiException("존재하지 않는 Room ID 입니다."));

        User user = userRepository.findByIdAndDeletedFalse(newRoomDTO.getUser_id())
                .orElseThrow(() -> new CustomApiException("존재하지 않는 userID 입니다."));

        // Acorn 사용
        acornService.withdrawAcorn(user.getId(), 2);

        // Roomtype으로 변환
        Roomtype color = Roomtype.valueOf(newRoomDTO.getColor().toUpperCase());

        existingRoom.update(Room.builder()
                .color(color)
                .user(user)
                .build());

        return roomRepository.save(existingRoom);
    }

    @Transactional
    public boolean deleteRoom(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new CustomApiException("존재하지 않는 Room ID 입니다."));

        roomRepository.delete(room);
        return true;
    }
}
