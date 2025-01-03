package com.sidenow.team.rightnow.room.repository;

import com.sidenow.team.rightnow.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long>{
    List<Room> findByUserId(String user_id);
    Optional<Room> findById(Long id);
}
