package com.sidenow.team.rightnow.acorn.controller;


import com.sidenow.team.rightnow.acorn.dto.response.AcornResponseDto;
import com.sidenow.team.rightnow.acorn.service.AcornService;
import com.sidenow.team.rightnow.global.ResponseDto;
import com.sidenow.team.rightnow.security.config.auth.LoginUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/acorns")
@RequiredArgsConstructor
@Slf4j
public class AcornController {

  private final AcornService acornService;

  @GetMapping("/{userId}")
  public ResponseDto<AcornResponseDto> get(@AuthenticationPrincipal LoginUser loginUser) {
    AcornResponseDto acorn = acornService.getAcorn(loginUser.getUser().getId());
    return new ResponseDto<>(ResponseDto.SUCCESS, "도토리 조회가 완료되었습니다.", acorn);

  }
}
