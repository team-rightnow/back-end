package com.sidenow.team.rightnow.statistic.dto.response;

import com.sidenow.team.rightnow.statistic.entity.Statistic;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class StatisticResponseDto {

  private LocalDate date;
  private Integer temperature;

  public StatisticResponseDto(Statistic statistic) {
    this.date = statistic.getDate();
    this.temperature = statistic.getTemperature();
  }
}
