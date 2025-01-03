package com.sidenow.team.rightnow.room.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.sidenow.team.rightnow.character.entity.Character;
import com.sidenow.team.rightnow.room.dto.RoomDTO;
import com.sidenow.team.rightnow.room.entity.Room;
import com.sidenow.team.rightnow.room.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/rooms")
    public ResponseEntity<Void> create(@RequestBody RoomDTO roomDTO){
        Room newRoom = roomDTO.toEntity();
        roomService.createRoom(newRoom);
        return ResponseEntity.created(URI.create("/rooms/" + newRoom.getId())).build();
    }
    @GetMapping("/rooms")
    public ResponseEntity<List<RoomDTO>> read(){
        List<RoomDTO> roomDTOs = roomService.getAllRooms().stream()
                .map(RoomDTO::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(roomDTOs);
    }

    @PutMapping("/rooms/{id}")
    public ResponseEntity<Void> update(@RequestBody RoomDTO newRoomDTO, @PathVariable Long id) {
        Room updatedRoom = roomService.updateRoom(id, newRoomDTO.toEntity());
        return updatedRoom != null ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/rooms/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean isDeleted = roomService.deleteRoom(id);
        return isDeleted? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
