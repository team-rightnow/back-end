package com.sidenow.team.rightnow.statistic.service;

import com.sidenow.team.rightnow.global.ex.CustomApiException;
import com.sidenow.team.rightnow.statistic.dto.response.StatisticResponseDto;
import com.sidenow.team.rightnow.statistic.entity.Statistic;
import com.sidenow.team.rightnow.statistic.repository.StatisticRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticService {

  private final StatisticRepository statisticRepository;

  @Transactional(readOnly = true)
  public StatisticResponseDto getStatistic(Long userId, LocalDate date) {
    Statistic savedStatistic = statisticRepository.findByUserIdAndDateAndDeletedFalse(userId, date)
        .orElseThrow(
            () -> new CustomApiException("존재하지 않는 통계 테이블입니다.")
        );
    return new StatisticResponseDto(savedStatistic);
  }

  // 통계 추가 처리
  @Transactional
  public void updateStatistic(Long userId, Integer temperature) {

    // 없으면 생성
    if (statisticRepository.findByUserIdAndDeletedFalse(userId).isEmpty()) {
      log.info("통계 데이터 생성 중...");
      Statistic statistic = Statistic.builder()
          .userId(userId)
          .date(LocalDate.now())
          .updateCount(1)
          .temperature(temperature)
          .deleted(false)
          .build();
      statisticRepository.save(statistic);
      log.info("통계 데이터 생성 완료: {}", statistic.getDate());
    } else {
      // 있으면 수정
      Statistic savedStatistic = statisticRepository.findByDateAndDeletedFalse(LocalDate.now())
          .orElseThrow(
              () -> new CustomApiException("존재하지 않는 통계 테이블입니다.")
          );
      savedStatistic.updateUserAndTemperature(userId, temperature);
      statisticRepository.save(savedStatistic);
    }
  }

  // 통계 삭제 처리
  @Transactional
  public void deleteStatistic(Long userId, Integer temperature) {
    Statistic savedStatistic = statisticRepository.findByUserIdAndDeletedFalse(userId).orElseThrow(
        () -> new CustomApiException("존재하지 않는 통계 테이블입니다.")
    );
    savedStatistic.deleteTemperature(temperature);
    statisticRepository.save(savedStatistic);
  }
}
