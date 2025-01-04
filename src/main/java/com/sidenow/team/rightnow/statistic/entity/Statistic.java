package com.sidenow.team.rightnow.statistic.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "statistic")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Statistic {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long userId;

  @Column(nullable = false)
  @Temporal(TemporalType.DATE)
  private LocalDate date;

  @Column(nullable = false)
  private Integer updateCount;

  @Column(nullable = false)
  private Integer temperature;

  @Column(nullable = false)
  private Boolean deleted;

  public void updateUserAndTemperature(Long userId, Integer temperature) {
    this.userId = userId;
    this.updateCount++;
    this.temperature = (this.temperature * (this.updateCount - 1) + temperature) / this.updateCount;
  }

  public void deleteTemperature(Integer temperature) {
    if (this.updateCount <= 1) {
      this.temperature = 0;  // 마지막 데이터 삭제 시 온도를 0으로 초기화
      this.updateCount = 0;
      return;
    }
    this.updateCount--;
    this.temperature = (this.temperature * (this.updateCount + 1) - temperature) / this.updateCount;
  }
}
