package com.sidenow.team.rightnow.acorn.controller;


import com.sidenow.team.rightnow.acorn.dto.request.AcornRequestDto;
import com.sidenow.team.rightnow.acorn.dto.response.AcornCountResponseDto;
import com.sidenow.team.rightnow.acorn.service.AcornService;
import com.sidenow.team.rightnow.global.ResponseDto;
import com.sidenow.team.rightnow.global.ex.CustomApiException;
import com.sidenow.team.rightnow.security.config.auth.LoginUser;
import jakarta.validation.Valid;
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
@RequestMapping("/api/acorns")
@RequiredArgsConstructor
@Slf4j
public class AcornController {

  private final AcornService acornService;

  @GetMapping("/acornCount")
  public ResponseDto<AcornCountResponseDto> get(@AuthenticationPrincipal LoginUser loginUser) {
    AcornCountResponseDto acorn = acornService.getAcorn(loginUser.getUser().getId());
    return new ResponseDto<>(ResponseDto.SUCCESS, "도토리 잔액 조회가 완료되었습니다.", acorn);
  }

  @GetMapping("")

  // 트랜잭션 생성용 컨트롤러, 실제 서비스에선 필요 없음
  @PostMapping("/test")
  public void create(@AuthenticationPrincipal LoginUser loginUser, @RequestBody @Valid AcornRequestDto acornRequestDto) {
    if (acornRequestDto.getActions().equals("deposit")) {
      acornService.depositAcorn(loginUser.getUser().getId(), acornRequestDto.getBalance());
    } else if (acornRequestDto.getActions().equals("withdraw")) {
      acornService.withdrawAcorn(loginUser.getUser().getId(), acornRequestDto.getBalance());
    } else {
      throw new CustomApiException("사용할 수 없는 action");
    }
  }
}
