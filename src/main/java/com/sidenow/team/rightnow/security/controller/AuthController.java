package com.sidenow.team.rightnow.security.controller;

import com.sidenow.team.rightnow.global.ResponseDto;
import com.sidenow.team.rightnow.security.service.AuthService;
import com.sidenow.team.rightnow.user.dto.request.CreateUserRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
  private final AuthService authService;

  @PostMapping("/signup")
  public ResponseDto<?> create(@RequestBody @Valid CreateUserRequestDto request) {
    return authService.createUser(request);
  }
}
