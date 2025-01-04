package com.sidenow.team.rightnow.statistic.controller;

import com.sidenow.team.rightnow.global.ResponseDto;
import com.sidenow.team.rightnow.security.config.auth.LoginUser;
import com.sidenow.team.rightnow.statistic.dto.request.TemperatureRequest;
import com.sidenow.team.rightnow.statistic.dto.response.StatisticResponseDto;
import com.sidenow.team.rightnow.statistic.service.StatisticService;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistic")
@RequiredArgsConstructor
@Slf4j
public class StatisticController {

  private final StatisticService statisticService;

  @GetMapping("/{date}")
  public ResponseDto<StatisticResponseDto> get(@AuthenticationPrincipal LoginUser loginUser,
      @PathVariable LocalDate date) {
    StatisticResponseDto statistic = statisticService.getStatistic(loginUser.getUser().getId(),
        date);
    return new ResponseDto<>(ResponseDto.SUCCESS, "해당 날짜의 통계 조회가 완료되었습니다.", statistic);
  }


  // 통계 추가 테스트용
  @PostMapping("/add")
  public void updateStatistic(@AuthenticationPrincipal LoginUser loginUser,
      @RequestBody TemperatureRequest temperatureRequest) {
    statisticService.updateStatistic(loginUser.getUser().getId(),
        temperatureRequest.getTemperature());
  }

  // 통계 삭제 테스트용
  @PostMapping("/delete")
  public void deleteStatistic(@AuthenticationPrincipal LoginUser loginUser,
      @RequestBody TemperatureRequest temperatureRequest) {
    statisticService.deleteStatistic(loginUser.getUser().getId(),
        temperatureRequest.getTemperature());
  }

}
