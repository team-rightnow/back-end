package com.sidenow.team.rightnow.room.service;

import com.sidenow.team.rightnow.room.entity.Room;
import com.sidenow.team.rightnow.room.repository.RoomRepository;
import com.sidenow.team.rightnow.room.dto.RoomDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Optional<Room> getRoomById(Long id){
        return roomRepository.findById(id);
    }

    public List<Room> getRoomByUserId(String user_id){
        return roomRepository.findByUserId(user_id);
    }

    public Room updateRoom(Long id, Room newRoom) {
        Optional<Room> existingRoom = roomRepository.findById(id);
        if(existingRoom.isPresent()){
            Room room = existingRoom.get();
            room.update(newRoom);
            return roomRepository.save(room);
        }
        return null;
    }

    public boolean deleteRoom(Long id) {
        Optional<Room> room = roomRepository.findById(id);
        if(room.isPresent()) {
            roomRepository.delete(room.get());
            return true;
        }
        return false;
    }
}
