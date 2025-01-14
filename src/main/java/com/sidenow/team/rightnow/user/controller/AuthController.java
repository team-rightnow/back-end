package com.sidenow.team.rightnow.user.controller;

import com.sidenow.team.rightnow.global.ResponseDto;
import com.sidenow.team.rightnow.user.dto.response.CreateUserResponseDto;
import com.sidenow.team.rightnow.user.service.AuthService;
import com.sidenow.team.rightnow.user.dto.request.CreateUserRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseDto<CreateUserResponseDto> create(@RequestBody @Valid CreateUserRequestDto request) {
        CreateUserResponseDto user = authService.createUser(request);
        return new ResponseDto<>(ResponseDto.SUCCESS, "회원가입이 완료되었습니다.", user);
    }

}