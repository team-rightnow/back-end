package com.sidenow.team.rightnow.diary.repository;

import com.sidenow.team.rightnow.diary.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findByUserIdAndDeletedFalse(Long userId);
    List<Diary> findAllByUserIdAndCreatedDateBetween(Long user_id, LocalDateTime createdDate, LocalDateTime createdDate2);
}
