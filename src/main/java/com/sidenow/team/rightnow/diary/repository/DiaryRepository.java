package com.sidenow.team.rightnow.diary.repository;

import com.sidenow.team.rightnow.diary.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
}
