package com.sidenow.team.rightnow.diary.repository;

import com.sidenow.team.rightnow.diary.entity.Diary;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findByUserIdAndDeletedFalse(Long userId);

    @Query("SELECT d FROM Diary d " +
            "WHERE d.user.id = :userId " +
            "AND FUNCTION('YEAR', d.createdDate) = :year " +
            "AND FUNCTION('MONTH', d.createdDate) = :month ")
    List<Diary> findByUserAndYearMonth(@Param("userId") Long userId, @Param("year") int year, @Param("month") int month);
}
