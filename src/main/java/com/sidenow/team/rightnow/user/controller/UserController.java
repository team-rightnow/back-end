package com.sidenow.team.rightnow.user.controller;

import com.sidenow.team.rightnow.global.ResponseDto;
import com.sidenow.team.rightnow.security.config.auth.LoginUser;
import com.sidenow.team.rightnow.user.dto.request.PasswordChangeRequestDto;
import com.sidenow.team.rightnow.user.dto.response.UserResponseDto;
import com.sidenow.team.rightnow.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseDto<UserResponseDto> get(@AuthenticationPrincipal LoginUser loginUser) {
        UserResponseDto user = userService.getUser(loginUser.getUser().getId());
        return new ResponseDto<>(ResponseDto.SUCCESS, "마이페이지 조회가 완료되었습니다.", user);
    }

    @PatchMapping
    public ResponseDto<?> modifyPassword(@AuthenticationPrincipal LoginUser loginUser,
        @RequestBody @Valid PasswordChangeRequestDto passwordChangeRequestDto) {
        userService.modifyUserPassword(loginUser.getUser().getId(), passwordChangeRequestDto);
        return new ResponseDto<>(ResponseDto.SUCCESS, "비밀번호 변경이 완료되었습니다.", null);
    }

}
