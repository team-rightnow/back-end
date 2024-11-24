package com.sidenow.team.rightnow.user.controller;

import com.sidenow.team.rightnow.global.ResponseDto;
import com.sidenow.team.rightnow.user.dto.CreateUserRequestDto;
import com.sidenow.team.rightnow.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/signup")
    public ResponseDto<?> createUser(@Valid @RequestBody CreateUserRequestDto request) {
        return userService.createUser(request);
    }

}
