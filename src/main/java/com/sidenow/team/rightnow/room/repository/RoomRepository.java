package com.sidenow.team.rightnow.room.repository;

import com.sidenow.team.rightnow.room.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long>{
    Page<Room> findByUserId(Long user_id, Pageable pageable);

}
