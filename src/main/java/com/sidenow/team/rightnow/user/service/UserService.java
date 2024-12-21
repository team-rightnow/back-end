package com.sidenow.team.rightnow.user.service;

import com.sidenow.team.rightnow.global.ex.CustomApiException;
import com.sidenow.team.rightnow.user.dto.response.UserResponseDto;
import com.sidenow.team.rightnow.user.entity.User;
import com.sidenow.team.rightnow.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponseDto getUser(Long id) {
        User user = userRepository.findByIdAndDeletedFalse(id).orElseThrow(
            ()-> new CustomApiException("존재하지 않는 userId 입니다.")
        );
        return new UserResponseDto(user);
    }
}
