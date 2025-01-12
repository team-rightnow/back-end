package com.sidenow.team.rightnow.room.service;

import com.sidenow.team.rightnow.acorn.dto.response.AcornCountResponseDto;
import com.sidenow.team.rightnow.acorn.repository.AcornRepository;
import com.sidenow.team.rightnow.room.entity.Room;
import com.sidenow.team.rightnow.room.repository.RoomRepository;
import com.sidenow.team.rightnow.room.dto.RoomDTO;
import com.sidenow.team.rightnow.global.ex.CustomApiException;
import com.sidenow.team.rightnow.user.entity.User;
import com.sidenow.team.rightnow.user.repository.UserRepository;
import com.sidenow.team.rightnow.acorn.repository.AcornRepository;
import com.sidenow.team.rightnow.acorn.service.AcornService;
import com.sidenow.team.rightnow.room.repository.RoomRepository;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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


        acornService.withdrawAcorn(userId, 2);

        Room room = Room.builder()
                .color(roomDTO.getColor())
                .user(user)
                .build();

        roomRepository.save(room);
    }

    @Transactional(readOnly = true)
    public Page<RoomDTO> getRoom(Long userId, Pageable pageable){
        User user = userRepository.findByIdAndDeletedFalse(userId)
                .orElseThrow(() -> new CustomApiException("존재하지 않는 userId 입니다."));

        return roomRepository.findByUserId(userId, pageable)
                .map(RoomDTO::fromEntity);
    }

   @Transactional
    public Room updateRoom(Long id, RoomDTO newRoomDTO) {
        Optional<Room> existingRoom = roomRepository.findById(id);
        if(existingRoom.isPresent()){
            User user = userRepository.findByIdAndDeletedFalse(newRoomDTO.getUser_id())
                    .orElseThrow(() -> new CustomApiException("존재하지 않는 userID 입니다."));

            acornService.withdrawAcorn(user.getId(), 2);

            Room room = existingRoom.get();
            room.update(new Room(newRoomDTO.getColor(), user));
            return roomRepository.save(room);
        }
        return null;
    }


    @Transactional(readOnly = false)
    public boolean deleteRoom(Long id) {
        Optional<Room> room = roomRepository.findById(id);
        if(room.isPresent()) {
            roomRepository.delete(room.get());
            return true;
        }
        return false;
    }
}
