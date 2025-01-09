package com.sidenow.team.rightnow.statistic.repository;

import com.sidenow.team.rightnow.statistic.entity.Statistic;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, Long> {

  Optional<Statistic> findByDateAndDeletedFalse(LocalDate date);

  Optional<Statistic> findByUserIdAndDeletedFalse(Long userId);

  Optional<Statistic> findByUserIdAndDateAndDeletedFalse(Long userId, LocalDate date);

}
